
package modelo;

import java.util.Observable;
import java.util.Observer;


/**
 * Dificultad permite simplificar partituras acotando la lectura a valores musicales espaciados, i.e. salteandose valores.
 * 
 */
public class Dificultad implements Observer{
	
	//ATRIBUTOS
	/**Es la cantidad de valores musicales que ignora entre lecturas*/
	private int intervaloDeValoresASaltear;
	/** indicador de que una cancion termino*/
	private boolean finCancion;
	
	private static final int NOTAS_SALTEADAS_EN_FACIL = 4;
	private static final int NOTAS_SALTEADAS_EN_NORMAL = 2;
	private static final int NOTAS_SALTEADAS_EN_DIFICIL = 0;
	
	public static final int FACIL = NOTAS_SALTEADAS_EN_FACIL;
	public static final int NORMAL = NOTAS_SALTEADAS_EN_NORMAL;
	public static final int DIFICIL = NOTAS_SALTEADAS_EN_DIFICIL;

	
	//CONSTRUCTOR
	/**
	 * Debe pasarse como parametro una constante de esta clase, 
	 * ej: new Dificultad(Dificultad.FACIL);
	 * @param intervaloDeValores es la cantidad de valores musicales que se saltea al avanzar en la cancion
	 */
	public Dificultad(int intervaloDeValores){
		
		finCancion=false;
		this.intervaloDeValoresASaltear=intervaloDeValores;	
	}
	
	//SETERS
	//GETERS
	/**
	 * @param cancion es la cancion a la que se le pide el valor musical actual
	 * @return el valor musical que se esta leyendo
	 */
	public ValorMusical getValorMusicalLeyendose(Cancion cancion) {

		return cancion.getValorMusicalLeyendose();
	}
	
	//OTROS METODOS
	
	/**
	 * @param unaCancion es la cancion en la cual avanza
	 * @return proximo valor musical o el mismo sino termino de sonar.
	 */
	public ValorMusical reproducirCancion(Cancion unaCancion){

		return unaCancion.avanzar(intervaloDeValoresASaltear+1);
	}
		
	/**
	 * @param cancion es la cancion sobre la cual espía
	 * @return el valor musical que va a venir
	 */
	public ValorMusical espiarProximoValorMusical(Cancion cancion) {
		
		return cancion.espiarValorMusicalSalteando(intervaloDeValoresASaltear);
	}
	
	/**
	 * @param cancion es la cancion sobre la cual espía
	 * @param cantidad es la cual se saltea aparte de saltear los que corresponden por la dificultadS
	 * @return el valor musical que va a venir
	 */
	
	public ValorMusical espiarValorMusicalSalteando(Cancion cancion, int cantidad) {
		
		return cancion.espiarValorMusicalSalteando(intervaloDeValoresASaltear+cantidad);
	}
	
	/**
	 * @param cancion es la cancion sobre la cual se pergunta si termino
	 * @return true/false dependiendo si termino o no la cancion
	 */
	public boolean terminoCancion(Cancion cancion) {
//si al espiar llega a ver que termina la cancion va a ser notificado por Cancion que se acabo.	
		cancion.getValorMusicalLeyendose();
		return finCancion;
	}
	
	public void update(Observable o, Object arg) {
		
		finCancion=true;
	}

	/**
	 * se invoca este metodo para avisar que se va a comenzar a reproducir una nueva cancion
	 * Es NECESARIO avisar.
	 */
	public void nuevaCancion() {

		finCancion=false;
	}

	/**
	 * @param cancion es la cancion sobre la cual se calcula la cantidad de movimientos que tiene
	 * @return la cantidad de pasos que tiene la cancion reproduciendose por medio de esta dificultad
	 */
	public int cantidadDeMovimientos(Cancion cancion) {

		return cancion.cantidadDeMovimientos(intervaloDeValoresASaltear);
	}

}
