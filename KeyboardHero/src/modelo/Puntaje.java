package modelo;

/**
 * Puntaje tiene la responsabilidad de tener un registro de puntos asociado a algo que tenga pasos y 
 * que en cada uno de ellos se necesite sumar o no puntaje.
 * Los puntos que maneja los contabiliza en funcion de la cantidad de pasos que se puedan realizar.
 * Siendo el puntaje el porcentaje de pasos realizados con suma de puntos.
 * El resultado dependera del porcentaje minimo para ganar.  
 */
public class Puntaje {
	
	/** Se gana completando exitosamente el 50% */
	private static int PORCENTAJE_MINIMO_PARA_GANAR = 50;
	public enum Resultado{GANASTE, PERDISTE, FALLASTE};

	//ATRIBUTOS
	/** es el % de aciertos respecto de la cantidad de pasos*/
	private float porcentajeDeAciertos;
	/** es el % correspodiente para realizar la suma de puntos*/
	private float porcentajeASumar;
	
	//CONSTRUCTORES
	/**
	 * @param cantidadDeMovimietosTotales son los pasos totales que debe tener asociado lo que utilize esta clase
	 */
	public Puntaje(int cantidadDeMovimietosTotales){
		
		this.porcentajeDeAciertos=0;
		this.porcentajeASumar=(float)100/cantidadDeMovimietosTotales;
	}
	
	//SETERS
	/**
	 * @param porcentajeMinimo fija el requerimiento para ganar o perder
	 */
	static public void porcentajeMinimoParaGanar(int porcentajeMinimo){
		
		Puntaje.PORCENTAJE_MINIMO_PARA_GANAR=porcentajeMinimo;
		
	}
	//GETERS
	public float getPuntaje(){
		
		return porcentajeDeAciertos;
	}
	
	public Resultado getResultado() {
		
		if(this.getPuntaje()>PORCENTAJE_MINIMO_PARA_GANAR)	return Resultado.GANASTE;
		
		return Resultado.PERDISTE;
	}
	
	//OTROS METODOS
	/**
	 * Suma el porcentaje correspondiente a la cantidad de pasos
	 */
	public void sumarPuntos(){

		porcentajeDeAciertos+=porcentajeASumar;
	}
		public void restarPuntos() {
		porcentajeDeAciertos-=porcentajeASumar;
	}
	
}