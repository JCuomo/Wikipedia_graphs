package modelo;

/**
 *Duracion modela el tiempo que duran los tiempos musicales (redonda, blanca, negra, etc.)
 *Hay una duracion patrón que corresponde al tiempo musical de menor duracion, y se le puede setear el tiempo que dura. 
 */
public class Duracion {
	
	public static final int SEMICORCHEA = 1;//patron
	public static final int CORCHEA 	= 2*SEMICORCHEA;
	public static final int NEGRA 		= 4*SEMICORCHEA;
	public static final int BLANCA 		= 8*SEMICORCHEA;
	public static final int REDONDA 	= 16*SEMICORCHEA;

	/**Es la duracion de la Duracion Patron en milisegundos	 */
	static private double duracionPatron=1;
	
	/**Es la duracion en milisegundos	 */
	private double tiempo;//en milisegundos

	//CONSTRUCTORES
	/**
	 * Se debe pasar como parametro una constante de esta clase.
	 * ej: new Duracion(Duracion.CORCHEA);
	 * @param unaDuracion multiplicado por la duracion patron es el tiempo que durara.
	 */
	public Duracion(int unaDuracion) {
		
		this.tiempo = unaDuracion;
	}
	
	//SETERS
	public void setDuracion(int unaDuracion) {
		
		this.tiempo = unaDuracion;
	}
	
	static public void setDuracionPatron(double d){
		
		duracionPatron=d;
	}
	
	//GETERS
	public double getDuracion() {
		
		return tiempo;
	}

	public static double getDuracionPatron() {
		return duracionPatron;
	}
}
