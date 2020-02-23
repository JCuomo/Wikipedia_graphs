package modelo;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import modelo.Puntaje.Resultado;
import modelo.excepciones.NoExisteLaCancion;
import res.Cargador;
import control.Reproductor;


/**
 * KeyboardHero permite adminitrar las canciones y su reproduccion.
 * Tiene niveles con canciones, un instrumento, y una dificultad.
 * Automaticamente avanza en una cancion y comprueba que se este tocando correctamente el instrumento, 
 * a fin de obtener un resultado cuando finalice la cancion.
 */
public class KeyboardHero{
	
	
	//ATRIBUTOS

	/** Es el nivel en el que esta la cancion que se esta interpretando */
	private Nivel nivelActual;
	/** Son todos los niveles que tiene*/
	private List<Nivel> niveles;
	/** Es la cancion que actualmente se esta interpretando*/
	private Cancion cancion;
	/** Es el instrumento con el cual se interpreta la cancion*/
	public  Instrumento instrumento;
	/** Es la dificultad en la que se esta interpretando la cancion*/
	private Dificultad dificultad;
	private static final String RUTA_CANCIONES = "\\res\\canciones\\";
	
	private Reproductor reproductor=new Reproductor(null);
	private Thread hiloMusica=new Thread(reproductor);

	//CONSTRUCTORES
	/**
	 * Comienza en NIVEL_UNO
	 * @param unosNiveles son todos los niveles que tiene
	 * @param unaCancion que se va a interpretar (debe pertenecer a NIVEL_UNO)
	 * @param unInstrumento con el cual se va a interpretar
	 * @param unaDificultad en la cual se va a interpretar
	 * @throws NoExisteLaCancion si el parametro Cancion no pertenece a NIVEL_UNO	
	 */
	public KeyboardHero(List<Nivel> unosNiveles, Cancion unaCancion, Instrumento unInstrumento, Dificultad unaDificultad) throws NoExisteLaCancion{
		
		this(unosNiveles, unInstrumento);
		this.setDificultad(unaDificultad);
		this.setCancion(unaCancion);
	}
	

	/**
	 * Crea un juego con niveles y un instrumento. Necesario setear la dificultad y la cancion para poder utilizarlo.
	 * @param unosNiveles
	 * @param unInstrumento
	 */
	public KeyboardHero(List<Nivel> unosNiveles, Instrumento unInstrumento){
		
		this();
		this.niveles=unosNiveles;
		this.setNivel(niveles.get(0));
		this.setInstrumento(unInstrumento);
	}
	
	/** Crea un KeyboarHero sin niveles, sin cancion, con un instrumento sin notas asociadas, y en dificultad FACIL*/
	public KeyboardHero(){
		
		this.nivelActual=null;
		this.niveles=null;
		this.cancion=null;
		this.dificultad=new Dificultad(Dificultad.FACIL);
		this.instrumento=new Instrumento();
	}
	
	//SETERS
	public void setDificultad(Dificultad nuevaDificultad){
		
		dificultad=nuevaDificultad;
		if(cancion!=null){
			cancion.deleteObservers();
			cancion.addObserver(dificultad);
			if(nivelActual!=null)
				try {
					nivelActual.asociarPuntaje(cancion, new Puntaje(dificultad.cantidadDeMovimientos(cancion)));
				} catch (NoExisteLaCancion e) {
					e.printStackTrace();
				}
		}
	}
	
	public void setInstrumento(Instrumento nuevoInstrumento){
		
		instrumento= nuevoInstrumento;
	}
	
	public void setNivel(Nivel nuevoNivel){
		
		this.nivelActual=nuevoNivel;
	}
	
	/** Luego de setear la cancion avisa automaticamente a la dificultad que se empieza con una nueva cancion
	 * @throws NoExisteLaCancion  si se intenta setear una cancion que no pertenece al nivel*/
	public void setCancion(Cancion nuevaCancion) throws NoExisteLaCancion{
		
		cancion=nuevaCancion;
		cancion.reiniciar();
		dificultad.nuevaCancion();//avisa a dificultad que se empieza una cancion 
		cancion.addObserver(dificultad);
		nivelActual.asociarPuntaje(cancion, new Puntaje(dificultad.cantidadDeMovimientos(cancion)));
		this.reproductor.setCancion(new File(RUTA_CANCIONES+cancion.getNombre()+".wav"));
		
	}
	
	//GETERS
	/**
	 * @return el valor musical que va a venir
	 */
	public ValorMusical espiarProximoValorMusical(){
		
		return dificultad.espiarProximoValorMusical(cancion);

	}
	
	public Nivel getNivel() {

		return nivelActual;
	}

	/**
	 * @param cantidad es la cancion sobre la cual espía
	 * @return el valor musical que va a venir salteandose lo indicado
	 */
	public ValorMusical espiarValorMusicalSalteando(int cantidad){
		return dificultad.espiarValorMusicalSalteando(cancion, cantidad);

	}
	
	/**
	 * @param cancionUno es la cancion a la que se le pide el valor musical actual
	 * @return el valor musical que se esta leyendo
	 */
	public ValorMusical getValorMusicalLeyendose(){
		
		return dificultad.getValorMusicalLeyendose(cancion);
	}
	
	/**
	 * @return el resultado de la cancion. Puede ser GANO ó PERDIO
	 */
	public Resultado getResultadoCancion(){
		
		try {
			return nivelActual.getResultadoDeCancion(cancion);
		} catch (NoExisteLaCancion e) {
//No debería entrar nunca en este catch ya que la excepcion se hubiese lanzado antes
			e.printStackTrace();
		}
		return null;//Nunca devuelve null
	}

	/**
	 * @return el resultado del juego. Puede ser GANO ó INCONCLUSO
	 */
	public Resultado getResultado(){
		
		try{
			Iterator<Nivel> it=niveles.iterator();
			while(it.hasNext()){
				if( !it.next().completado() )
					return Resultado.FALLASTE;
			}
			return Resultado.GANASTE;
		}
		catch (NoExisteLaCancion e) {
			//No debería entrar nunca en este catch ya que la excepcion se hubiese lanzado antes
		}
		return null;//nunca devuelve null
	}
	
	/**
	 * @return el puntaje de la cancion que se estaba interpretando actualmente
	 */
	public float getPuntajeCancion(){
		
		try {
			return nivelActual.getPuntajeDeCancion(cancion).getPuntaje();
		} catch (NoExisteLaCancion e) {
//No debería entrar nunca en este catch ya que la excepcion se hubiese lanzado antes
			e.printStackTrace();
		}
		return 0;//nunca devulve esto
	}
	
	//OTROS METODOS
	/**
	 * @return el proximo valor musical ó el actual si todavía no se terminó de leer
	 */
	public ValorMusical reproducir(){
		
		return dificultad.reproducirCancion(cancion);
	}
	
	/**
	 * avanza en la cancion y comprueba que se este interpretando correctamente con el instrumento
	 * Se debe comprobar que la cancion no haya finalizado antes de llamar a este método.
	 * @param cantidadDeMovimientos es la cantidad de pasos que avanza en la cancion realizando las comprobaciones necesarias.
	 */
	public void run(int cantidadDeMovimientos){
		try{
			ValorMusical proximoValorMusical;
			while(cantidadDeMovimientos!=0){
				//avanza un instante de juego (una semicorchea)
				proximoValorMusical = this.reproducir();
			//	setDuracion(proximoValorMusical.getDuracion().getDuracion());
				//si se estan tocando las notas que corresponde...
				if(instrumento.estaEjecutandose(proximoValorMusical.getNotas())) avisarQueAcerto();
				else avisarQueFallo();
				cantidadDeMovimientos-=1;
			}
		}catch (NoExisteLaCancion e) {
//No debería entrar nunca en este catch ya que la excepcion se hubiese lanzado antes
		}
	}
	
	public boolean terminoCancion() {

			return dificultad.terminoCancion(cancion);
	}
	
	/**
	 * @param unNivel sobre el cual se pregunta su estado
	 * @return true/false dependiendo si el unNivel esta disponible para ser usado o no
	 */
	public boolean nivelHabilitado(int unNivel){
		if(unNivel==1) return true;
		try {
			return niveles.get(unNivel-2).completado();
		} catch (NoExisteLaCancion e) {
//No debería entrar nunca en este catch ya que la excepcion se hubiese lanzado antes
			e.printStackTrace();
		}
		return false;//no sale por acá

	}

	public Instrumento getInstrumento() {

		return instrumento;
	}


	public List<Nivel> getNiveles() {
		return niveles;
	}
	public void avisarQueFallo() throws NoExisteLaCancion{
		nivelActual.movimientoIncorrectoEnCancion(cancion);
	}
	private void avisarQueAcerto() throws NoExisteLaCancion{
		nivelActual.movimientoCorrectoEnCancion(cancion);	
	}

	public void reproducirMusica(){
		hiloMusica.start();
	}
	
	public void detener() {
		reproductor.pausar();
	}
	
	public void continuar() {
		reproductor.continuar();
	}

	public void terminarMusica() {
		reproductor.detener();
		reproductor=new Reproductor(Cargador.getCancion(RUTA_CANCIONES+cancion.getNombre()+".wav"));
		hiloMusica=new Thread(reproductor);
	}

	public void resetear() {
		cancion.reiniciar();
	}
}



