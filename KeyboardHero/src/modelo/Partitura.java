package modelo;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import configuracion.ParserXMLdePartitura;

/**
 * Una partitura tiene valores musicales ordenados, los cuales pueden leerse (implica avanzar en la partitura)
 * ó espiar cuales serán los proximos
 * 
 */
public class Partitura {
	
	//ATRIBUTOS
	/** sirve para indicar que la partitura se acabó*/
	public static final ValorMusical FIN_PARTITURA = null;
	private double tempo;
	private String nombre;
	private LinkedList<ValorMusical> valoresMusicales;
	/** permite seguir un registro del avance de la partitura*/
	private int indiceDeLectura;
	
	//CONSTRUCTORES
	/**
	 * Crea una partitura con valores musicales y un tempo asociado
	 * @param valoresMusicales son los valores musicales que tiene la partitura
	 * @param tempo
	 */
	public Partitura(LinkedList<ValorMusical> valoresMusicales, int tempo) {
		
		this.valoresMusicales=valoresMusicales;
		this.tempo = tempo;
		this.indiceDeLectura=0;
	}
	
	/**
	 * Genera una partitura a partir de un archivo
	 * @param archivoDeParitura a partir de cual se genera una partitura
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public Partitura(String archivoDeParitura) throws IllegalArgumentException, SecurityException, ParserConfigurationException, SAXException, IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException{
		
		this();
		ParserXMLdePartitura parser= new ParserXMLdePartitura(archivoDeParitura);
		valoresMusicales=parser.getValoresMusicales(archivoDeParitura);
		tempo=parser.getTempo();
		nombre=parser.getNombre();
	}
	
	/**
	 * Crea una partitura sin valores musicales
	 */
	public Partitura(){
		
		tempo=0;
		valoresMusicales=new LinkedList<ValorMusical>();
		this.indiceDeLectura=0;
	}
	
	//SETERS
	public void setTempo(int tempo) {
		
		this.tempo = tempo;
//		Duracion.setDuracionPatron(0.25);
	}	
	
	/**Permite comenzar devuelta a leer la partitura*/
	public void reiniciar(){
		
		this.indiceDeLectura=0;
		for (ValorMusical unValorMusical : valoresMusicales) 
			unValorMusical.resetear();
	}
	
	//GETERS
	public double getTempo() {
		
		return tempo;
	}
	
	/**
	 * Devuelve el valor musical ubicado, a partir de donde se estaba leyendo, la cantidad de valores + 1
	 * Si se saltea cero devuelve el inmediato posterior.
	 * Permite espiar valores ya leídos, como también sobre el que se esta parado utilizando parametros negativos.
	 * @param valorMusicalDespuesDelIndice es la cantidad de valores que se necesita que pase de largo
	 * @return el valor musical ubicado la cantidad que se saltea mas uno. Ó FIN_PARTITURA si en la posicion buscada ya se acabó.
	 */
	public ValorMusical espiarProximoValorMusicalSalteando(int valorMusicalDespuesDelIndice) {
		
		int indice=valorMusicalDespuesDelIndice+indiceDeLectura;
		if(valoresMusicales.size() > indice+1)
				return valoresMusicales.get(indice+1);
		return FIN_PARTITURA;
	}
	
	/**
	 * @return el valor musical que se esta leyendo actualmente
	 */
	public ValorMusical getValorMusicalActual() {

		try{
			return valoresMusicales.get(indiceDeLectura);
		}
		catch (IndexOutOfBoundsException e) {
			return FIN_PARTITURA;
		}
	}
	
	public String getNombre() {
		return nombre;
	}

	//OTROS METODOS
	/**
	 * Avanza en la partitura la cantidad de valores musicales que se le pide luego de haber 
	 * finalizado el valor musical que se estaba leyendo
	 * @param cantidadDePasos es la cantidad de valores musicales que avanza
	 * @return devuelve el valor musical que se esta leyendo (si no termino de sonar) o el proximo acorde a la cantidad de pasos que se le pide que avance
	 */
	public ValorMusical avanzar(int cantidadDePasos ){
//cantidadDePasos es de cuantos valores musicales va salteandose	
//no deberia llamarse sin antes preguntar por si termino la cancion
		ValorMusical valorMusicalActualmenteLeyendose=this.getValorMusicalActual();
		valorMusicalActualmenteLeyendose.sonar(1);//suena el ValorM actual
		if(!valorMusicalActualmenteLeyendose.estaSonando())//si termino de sonar ...
				indiceDeLectura+=cantidadDePasos;//avanza en la partitura
		
		return valorMusicalActualmenteLeyendose;
	}

	/**
	 * @return la cantidad de valores musicales que tiene la partitura
	 */
	public int size() {

		return valoresMusicales.size();
	}

	/**
	 * Cuenta no solo los valores musicales, sino también la cantidad de veces que puede sonar cada uno.
	 * Si se desea que cuente todos los pasos posibles el parametro debe ser 0.
	 * @param intervaloDeValoresASaltear indica la cantidad de valores que se saltea a medida que avanza en la partitura
	 * @return la cantidad de pasos que tiene la partitura avanzando de a intervaloDeValoresASaltear+1.
	 */
	public int cantidadDeMovimientos(int intervaloDeValoresASaltear) {
		
		int cantidadTotal=0, i=0;
		
		Iterator<ValorMusical> it=valoresMusicales.iterator();
		
		while (it.hasNext()){
			if(i==0) cantidadTotal+=it.next().getDuracion().getDuracion();
			else it.next();
			i++;
			if(i==intervaloDeValoresASaltear+1) i=0;
		}
			
		return cantidadTotal;	
	}
	
	  
}
