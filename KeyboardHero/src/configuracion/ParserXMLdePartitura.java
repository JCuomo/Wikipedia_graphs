package configuracion;
// Librerías de JDOM 

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.Duracion;
import modelo.ValorMusical;
import modelo.notas.generico.Nota;


/**
 * ParserXMLdePartitura sabe como convertir un archivo XML formado por tags "Partitura", "ValorMusical", "Nota" 
 * en este orden, a una lista de modelo.ValoresMusicales. Puede haber otros tags.
 * "ValorMusical" tiene que tener un atributo "duracion" con los siguientes valores posibles: "REDONDA", "BLANCA", "NEGRA", "CORCHEA".
 * "Nota" tiene que tener contenido del tipo: "DO", "RE", "MI", "FA", "SOL", "LA", "SI", "Silencio"
 */
public class ParserXMLdePartitura{
	
	LinkedList<ValorMusical> valoresMusicales=new LinkedList<ValorMusical>();
	String nombre;
	int tempo;
	Element partitura;
	
	public ParserXMLdePartitura(String archivo) throws ParserConfigurationException, SAXException, IOException{
		 //crea un arbol con los tags del xml como nodos
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		partitura= db.parse(archivo).getDocumentElement();
		//setea la duracion de la Duracion Patron acorde al tempo de la partitura
		Duracion.setDuracionPatron(60000/(getTempo()*Duracion.NEGRA));
	}

	/**
	 * @param archivo XML del cual se obtiene una lista de modelo.ValorMusical
	 * @return una lista de valores musicales
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public LinkedList<ValorMusical> getValoresMusicales(String archivo) throws ParserConfigurationException, SAXException, IOException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
		
		//pide todos los nodos de los tag "ValorMusical"
		NodeList nodosValoresMusicales=partitura.getElementsByTagName("ValorMusical");
		LinkedList<ValorMusical> valoresMusicales=new LinkedList<ValorMusical>();
		List<Nota> unasNotas;
		Duracion unaDuracion = null, duracionAnterior;
		long unMomentoEjecucion = 0, momentoAnterior;

        for  (int i = 0; i < nodosValoresMusicales.getLength(); i++) {
        	duracionAnterior=unaDuracion;
        	momentoAnterior=unMomentoEjecucion;
        //a cada valor musical le pide la duracion y las notas
        	Element unValorMusical=(Element)nodosValoresMusicales.item(i);
           	unaDuracion=getDuracion(unValorMusical);
        	unasNotas=getNotas(unValorMusical);
        	unMomentoEjecucion=getMomento(unValorMusical, duracionAnterior, momentoAnterior);
        //crea un ValorMusical con la duracion y las notas obtenidas
        valoresMusicales.add(new ValorMusical(unaDuracion, unasNotas, unMomentoEjecucion));
		}
		return valoresMusicales;
	}

	/**
	 * A partir de un ValorMusical obtiene las notas del mismo
	 * @param valorMusical
	 * @return una lista de notas asociadas al ValorMusical
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private List<Nota> getNotas(Element valorMusical) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		//arma una lista con los nodos "Nota" del valor musical pasado
		NodeList nodosNotas=valorMusical.getElementsByTagName("Nota");
		String nombreNota;
		List<Nota> notas=new LinkedList<Nota>();
		
		 for  (int i = 0; i < nodosNotas.getLength(); i++) {	
	        	//a cada nodo "Nota" le pide el nombre de su contenido y crea una clase de ese tipo
	        	Element unNodoNota=(Element)nodosNotas.item(i);
	        	
	        	nombreNota=unNodoNota.getFirstChild().getNodeValue();
	        	//como las notas son de instancia única se le pide el metodo que retorna esa instancia
				notas.add((Nota)(Class.forName("modelo.notas."+nombreNota)).getMethod("getNota").invoke((Object[])null,(Object[])null));
			}
		 return notas;
	}

	/**
	 * A partir de un nodo de ValorMusical obtiene el silencio
	 * @param valorMusical
	 * @return la duracion del ValorMusical
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 */
	private Duracion getDuracion(Element valorMusical) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
		//crea una duracion cualquiera para luego pedir los campos, como son estaticos de la clase no importa a que instancia se lo pida
		Duracion unaDuracionCualquiera=new Duracion(Duracion.SEMICORCHEA);
		//pide el nombre de la duracion para luego crear la Duracion
		String nombreDuracion=valorMusical.getAttribute("duracion");
		//retorna una nueva Duracion creada a partir del nombre obtenido
		return new Duracion((Class.forName("modelo."+"Duracion").getField(nombreDuracion).getInt(unaDuracionCualquiera)));
	}
	
	private long getMomento(Element valorMusical, Duracion duracionAnterior, long momentoAnterior){
		
		long i=Integer.parseInt(valorMusical.getAttribute("momentoEjecucion"));
		if(i>0) return i;
		if(duracionAnterior==null) return 0;
		return (long) (duracionAnterior.getDuracion()*Duracion.getDuracionPatron()+momentoAnterior);
	}

	public int getTempo() throws ParserConfigurationException, SAXException, IOException {
		
		//pide el valor del atributo "tempo"
		return Integer.parseInt(partitura.getAttribute("tempo"));
	}

	public String getNombre() {
		
		return partitura.getAttribute("nombre");
	}
}
