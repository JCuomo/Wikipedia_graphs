package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import modelo.notas.generico.Nota;

/**
 * ValorMusical representa los valores en las partituras. Esto es una figura o un silencio.
 * Soporta tener varias notas y una sola duracion.
 * Un valor puede sonar la cantidad de veces que su duracion lo indica respecto del patron que se haya elegido.
 * Por ejemplo, una negra de DO va a poder sonar la cantidad de veces que entre la duracion patron en la negra.
 */
public class ValorMusical {

	public static final int PATRON = Duracion.SEMICORCHEA;// es la medida patron en tiempos de otra duracion.
	/** Duracion asociada al valor musical */
	protected Duracion duracion;
	/** notas asociadas al valor musical */
	protected ArrayList<Nota> notas;
	/**al alcanzar la duracion asociada muere este ValorMusical.*/
	protected int unidadesDeTiempoRecorridas;
	private long momentoEjecucion;
	
	// CONSTRUCTORES
	/** crea un valor musical con duracion pero sin notas*/
	public ValorMusical(Duracion duracion) {

		this(duracion, 0);
		this.duracion = duracion;
	}
	public ValorMusical(Duracion duracion, long momento) {

		this();
		this.duracion = duracion;
		this.momentoEjecucion=momento;
	}
	
	/** crea un valor musical con la duracion y con solo una notas*/
	public ValorMusical(Duracion duracion, Nota unaNota) {
	
		this(duracion);
		this.notas.add(unaNota);
	}
	
	/** crea un valor musical con la duracion y con solo dos notas*/
	public ValorMusical(Duracion duracion, Nota unaNota, Nota otraNota) {

		this(duracion);
		this.notas.add(unaNota);
		this.notas.add(otraNota);
	}
	
	/** crea un valor musical con la duracion pasada y con todas las notas de la coleccion*/
	public ValorMusical(Duracion duracion, Collection<? extends Nota> variasNotas) {
		
		this(duracion);
		this.notas.addAll(variasNotas);
		this.duracion = duracion;
	}
	
	public ValorMusical(Duracion duracion, Collection<? extends Nota> variasNotas, long momento) {
		
		this(duracion, momento);
		this.notas.addAll(variasNotas);
		this.duracion = duracion;
	}

	/** crea un valor musical sin duracion y sin notas */
	public ValorMusical() {
		
		this.unidadesDeTiempoRecorridas = 0;
		this.duracion = null;
		this.notas = new ArrayList<Nota>();
	}

	// SETERS
	public void setDuracion(Duracion duracion) {
	
		this.duracion = duracion;
	}

	// GETERS
	public Duracion getDuracion() {
	
		return duracion;
	}

	public List<Nota> getNotas(){

		return notas;
	}

	// OTROS METODOS

	/**
	 * Hace sonar al valor musical. 
	 * Puede sonar la cantidad de veces que entre la unidad patron en la duracion del valor musical
	 * @param unidadesDelTiempo Cantida de veces que suena
	 * @return true/false si puede seguir sonando o si es la ultima vez que debio sonar
	 */
	public boolean sonar(int unidadesDelTiempo) {
	
		unidadesDeTiempoRecorridas += unidadesDelTiempo;
		if (unidadesDeTiempoRecorridas < duracion.getDuracion()) return true;
		return false;
	}

	/**
	 * @return true/false dependiendo de si el valor musical sigue sonando o si ya 
	 * sonó la cantidad de veces que entra la duracion patron en la duracion del valor musical
	 */
	public boolean estaSonando() {
		/*
		 * devuelve true si la nota esta dentro de su rango de duracion menos
		 * uno. Es decir si esta en su ultimo instante de vida devuelve false.
		 * Aclaracion: instanteDeVida = duracion de una semicorchea. 
		 * Soporta reproducir hasta corcheas. Ejemplo: una negra nace
		 * (1° instante vivo), 2° y 3° instante sigue sonando, al 4° y
		 * ultimo antes de que muera la negra deja de sonar.
		 */
		return (unidadesDeTiempoRecorridas < duracion.getDuracion());
	}

	public long getMomentoEjecucion() {
		// TODO Auto-generated method stub
		return momentoEjecucion;
	}

	public void resetear(){
		unidadesDeTiempoRecorridas=0;
	}
}
