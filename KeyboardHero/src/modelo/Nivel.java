package modelo;
import java.util.*;
import modelo.Puntaje.Resultado;
import modelo.excepciones.NoExisteLaCancion;


/**
 * Un nivel administra cancion y lsa habilita a medida que se las va aprobando.
 */
public class  Nivel implements Observer {
	
	//ATRIBUTOS
	/**Las cancion que tiene asociadas */
	protected List<Cancion> canciones;
	/** Los puntajes de cada cancion (tiene que haber correspondencia de indices con "canciones")*/
	protected List<Puntaje> puntajes;
	/** Son los puntos acumulados de cada cancion*/
	protected int puntajeTotal;
	/**Es el porcentaje necesario para pasar el nivel*/
	private int porcentajeParaAprobar;
	/** Es un mapa con los porcentajes necesarios para aprobar cada nivel*/
	static private HashMap<NumeroNivel, Integer> Nivel_Porcentaje=new HashMap<NumeroNivel, Integer>();
	
	public enum NumeroNivel{NIVEL_UNO, NIVEL_DOS, NIVEL_TRES};
	
	private static final int EXIGENCIA_NIVEL_UNO = 60;
	private static final int EXIGENCIA_NIVEL_DOS = 70;
	private static final int EXIGENCIA_NIVEL_TRES= 80; 
	
	//CONSTRUCTORES
	/**
	 * Crea un nivel con la lista de cancion y con la exigencia del nivel pasado.
	 * ej: new Nivel(nivel.NIVEL_UNO, listaDeCanciones);
	 * @param unNivel debe ser del tipo "nivel" (NIVEL_UNO, NIVEL_DOS, NIVEL_TRES)
	 * @param listaDeCanciones
	 */
	public Nivel(NumeroNivel unNivel, List<Cancion> listaDeCanciones) {
		
		this();
		this.porcentajeParaAprobar = Nivel_Porcentaje.get(unNivel);
		this.canciones=listaDeCanciones;
		for(int i=0; i<canciones.size(); i++) puntajes.add(new Puntaje(100));
	}
	
	/** Crea un nivel sin canciones, con la exigencia del NIVEL_UNO */
	public Nivel() {
		Nivel.armarMapaPorcentajes();
		this.porcentajeParaAprobar 	= Nivel_Porcentaje.get(NumeroNivel.NIVEL_UNO);
		this.puntajes=new ArrayList<Puntaje>();
		this.canciones=new ArrayList<Cancion>();
		this.puntajeTotal = 0;
	}

	private static void armarMapaPorcentajes(){
		Nivel_Porcentaje.put(NumeroNivel.NIVEL_UNO, EXIGENCIA_NIVEL_UNO);
		Nivel_Porcentaje.put(NumeroNivel.NIVEL_DOS, EXIGENCIA_NIVEL_DOS);
		Nivel_Porcentaje.put(NumeroNivel.NIVEL_TRES, EXIGENCIA_NIVEL_TRES);
	}
	
	//SETERS
	/**
	 * Agrega una cancion en la posicion indicada,
	 * desplazando a derecha las canciones que ubiese a partir de la posicion.
	 * @param cancion 
	 * @param posicionDeCancion
	 */
	public void agregarCancion(Cancion cancion, int posicionDeCancion) {	
		canciones.add(posicionDeCancion, cancion);
		puntajes.add(posicionDeCancion, new Puntaje(cancion.size()));
	}
	
	/**
	 * Agrega una cancion en la ultima posicion
	 * @param cancion
	 */
	public void agregarCancion(Cancion cancion) {
		
		this.agregarCancion(cancion, canciones.size());
	}
	
	//GETERS
	public List<Cancion> getCanciones(){
		
		return canciones;
	}
	
	public List<String> getNombresCanciones(){
		
		List<String> nombres=new LinkedList<String>();
		Iterator<Cancion> it=canciones.iterator();
		while (it.hasNext()) 
			nombres.add(it.next().getNombre());
		return nombres;
	}
	
	/**
	 * @param cancion
	 * @return el puntaje de la cancion
	 * @throws NoExisteLaCancion cuando se pide el puntaje de una cancion que no pertence al nivel 
	 */
	public Puntaje getPuntajeDeCancion(Cancion cancion) throws NoExisteLaCancion{
	
		try{
			return puntajes.get(canciones.indexOf(cancion));	
		}
		catch (IndexOutOfBoundsException e) {
			
			throw new NoExisteLaCancion();
		}
	}
	
	
	/**
	 * @return los puntos acumulados del nivel
	 */
	public int getPuntaje() {
		
		return puntajeTotal;
	}

	/**
	 * @param cancion
	 * @return el resultado de la cancion.Puede ser GANO ó PERDIO
	 * @throws NoExisteLaCancion si se pide por el puntaje de una cancion que no pertenece al nivel
	 */
	public Resultado getResultadoDeCancion(Cancion cancion) throws NoExisteLaCancion {

		try{
			return this.getPuntajeDeCancion(cancion).getResultado();
		}
		catch (NullPointerException e) {
			throw new NoExisteLaCancion();
		}
	}

	//OTROS METODOS	
	/**
	 * Una cancion esta disponible si la anterior esta aprobada
	 * @param unaCancion
	 * @return true/false dependiendo si la cancion por la que se pregunta esta disponible.
	 * @throws NoExisteLaCancion si se pregunta por una cancion que no pertenece al nivel
	 */
	public boolean cancionDisponible(Cancion unaCancion) throws NoExisteLaCancion{

		try{
			int indice=canciones.indexOf(unaCancion);
			return puntosSuficientes(puntajes.get(indice-1).getPuntaje());
		}
		catch (IndexOutOfBoundsException e) {
			return true;
		}
		catch (NullPointerException e) {
			throw new NoExisteLaCancion();
		}
	}
	
	/**
	 * Se completó un nivel cuando todas las canciones estan aprobadas
	 * @return true/false dependiendo si el nivel se completo o no
	 * @throws NoExisteLaCancion no lanza esta excepcion.
	 */
	public boolean completado() throws NoExisteLaCancion{

		Cancion ultimaCancion=canciones.get(canciones.size()-1);
		float puntosUltimaCancion=this.getPuntajeDeCancion(ultimaCancion).getPuntaje();
		return this.puntosSuficientes(puntosUltimaCancion);
	}
	
	/**
	 * Los puntos son suficientes cuando son iguales o mayores a los necesarios para aprobar una cancion.
	 * @param puntos son por los que se pregunta si son suficientes
	 * @return true/false dependiendo si los puntos son suficientes para aprobar una cancion
	 */
	private boolean puntosSuficientes(float puntos){
		
		return (puntos >= porcentajeParaAprobar);
	}

	/**
	 * Avisa al nivel que se interpreto correctamente un movimiento de la cancion.
	 * Nivel es el que administra los puntajes de las canciones y de si mismo.
	 * @param cancion
	 * @throws NoExisteLaCancion si se pasa una cancion que no pertenece al nivel
	 */
	public void movimientoCorrectoEnCancion(Cancion cancion) throws NoExisteLaCancion {
		
		this.getPuntajeDeCancion(cancion).sumarPuntos();
	}
	
		public void movimientoIncorrectoEnCancion(Cancion cancion) throws NoExisteLaCancion {
		
		this.getPuntajeDeCancion(cancion).restarPuntos();
	}
	
	
	/**
	 * Cuando se empieza a interpretar una cancion se le debe asociar un puntaje.
	 * @param cancion
	 * @param puntaje
	 * @throws NoExisteLaCancion si la cancion no pertenece al nivel
	 */
	public void asociarPuntaje(Cancion cancion, Puntaje puntaje) throws NoExisteLaCancion {

		try{
			int indice=canciones.indexOf(cancion);
			puntajes.add(indice, puntaje);
		}
		catch (IndexOutOfBoundsException e) {
			throw new NoExisteLaCancion();
		}
		
	}
	
	public void update(Observable cancion, Object arg1) {
//cuando una cancion finaliza, si fue realizada correctamente se suman los puntos
		float puntos;
		try {
			puntos = this.getPuntajeDeCancion((Cancion)cancion).getPuntaje();
			if(this.puntosSuficientes(puntos)) puntajeTotal+=puntos;
		} catch (NoExisteLaCancion e) {
			e.printStackTrace();
		}
	}

}