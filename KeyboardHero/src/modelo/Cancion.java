package modelo;
import java.util.Observable;

/**
 * Una cancion tiene una partitura y un archivo de musica correspondiente a la partitura.
 * Una cancion puede leerse, lo que implica ir avanzando en la partitura. Tambien puede espiarse que valores musicales
 * van a venir.
 */
public class Cancion extends Observable{

	//ATRIBUTOS
	private Partitura partitura;
	
	
	//CONSTRUCTORES
	public Cancion(Partitura partitura, String musica) {
		this.partitura = partitura;
	}
	
	public Cancion(Partitura partitura) {
		this.partitura = partitura;
	}

	//SETERS
	public void setPartitura(Partitura partitura) {
		this.partitura = partitura;
	}

	public void setMusica(String musica) {
	}
	
	/**Permite comenzar devuelta a leer la cancion*/
	public void reiniciar(){
		this.deleteObservers();
		this.partitura.reiniciar();
	}
	//GETERS
	public Partitura getPartitura() {
		return partitura;
	}

	/**
	 * @return el valor musical que se esta leyendo actualmente
	 */
	public ValorMusical getValorMusicalLeyendose() {
		
		ValorMusical unValorMusical=partitura.getValorMusicalActual();
		if(unValorMusical==Partitura.FIN_PARTITURA){
			setChanged();
			notifyObservers();
		}
		return unValorMusical;
	}
	
	/**
	 * Devuelve el valor musical ubicado, a partir de donde se estaba leyendo, la cantidad de valores + 1
	 * Si se saltea cero devuelve el inmediato posterior.
	 * Permite espiar valores ya leídos, como también sobre el que se esta parado utilizando parametros negativos.
	 * @param numeroDeValorMusical es la cantidad de valores que se necesita que pase de largo.
	 * @return el valor musical ubicado la cantidad que se saltea mas uno. Ó FIN_PARTITURA si en la posicion buscada ya se acabó.
	 */
	public ValorMusical espiarValorMusicalSalteando(int numeroDeValorMusical) {
		
		return partitura.espiarProximoValorMusicalSalteando(numeroDeValorMusical);
	}
	
	/**
	 * @return la cantidad de valores musicales que tiene la partitura
	 */
	public int size(){
		
		return partitura.size();
	}

	/**
	 * Cuenta no solo los valores musicales, sino también la cantidad de veces que puede sonar cada uno.
	 * Si se desea que cuente todos los pasos posibles el parametro debe ser 0.
	 * @param intervaloDeValoresASaltear indica la cantidad de valores que se saltea a medida que avanza en la cancion
	 * @return la cantidad de pasos que tiene la partitura avanzando de a intervaloDeValoresASaltear+1.
	 */
	public int cantidadDeMovimientos(int intervaloDeValoresASaltear) {
		
		return partitura.cantidadDeMovimientos(intervaloDeValoresASaltear);
	}

	public String getNombre(){
		
		return partitura.getNombre();
	}
	
	//OTROS METODOS
	/**
	 * Avanza en la cancion la cantidad de numero de pasos que se le pide luego de haber 
	 * finalizado el valor musical que se estaba leyendo
	 * @param numeroDePasos es la cantidad de valores musicales que avanza
	 * @return devuelve el valor musical que se esta leyendo (si no termino de sonar) o el proximo acorde a la cantidad de pasos que se le pide que avance
	 */
	public ValorMusical avanzar(int numerosDePasos){
		return this.partitura.avanzar(numerosDePasos);
	}

	public double getTempo() {
		
		return partitura.getTempo();
	}
}
