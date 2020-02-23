package control;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Cancion;
import modelo.KeyboardHero;
import modelo.ValorMusical;
import modelo.excepciones.NoExisteLaCancion;
import modelo.interfaces.Dibujable;
import modelo.interfaces.KeyPressedObservador;
import modelo.interfaces.ObjetoVivo;
import modelo.interfaces.SuperficieDeDibujo;
import modelo.notas.generico.Nota;
import modelo.objetos.Diapazon;
import modelo.objetos.IndicadorNota;
import modelo.objetos.ObjetoNota;
import modelo.objetos.Resultados;
import vista.VistaDiapazon;
import vista.VistaJuego;
import vista.VistaNota;
import vista.VistaPuntaje;
import vista.VistaPuntos;
import vista.ventanas.Ventana;
import vista.ventanas.VentanaCanciones;
import vista.ventanas.VentanaJuego;
import vista.ventanas.VentanaPrincipal;
import vista.ventanas.VentanaPuntaje;

public class Controlador implements Runnable{
	
	private List<KeyPressedObservador> keyPressedObservadores;
	private List<ObjetoVivo> objetosVivos;
	private List<Dibujable> dibujables;
	private List<ObjetoVivo> proximosEliminadosOV;
	private List<Dibujable> proximosEliminadosD;

	
	
	protected SuperficieDeDibujo superficieDeDibujo;	
	private Diapazon diapazon;
	private KeyboardHero kHero;

	private Ventana ventanaJuego;
	private VentanaPrincipal ventanaPrincipal;
	private Ventana ventanaPuntaje;
	private Ventana ventanaCanciones;
	private VistaPuntos vistaPuntos;
	
	//lleva la cuenta de cuanto tiempo hay que dejar pasar antes de empezar a mostrar otro valor musical
	double duracionUltimoValorMusical=0;
	//lleva la cuenta de cuantos valores musicales se estan mostrando por pantalla
	int cantidadQueEstaDibujando=0;
	private int cantidadDeMovimientos;
	//determina si es necesario repintar el diapazon
	private boolean diapazonDibujado=false;
	//determina si el juego termino o no
	private boolean juegoVigente=true;
	//determina si el juego esta en pausa o no
	private boolean estaEnEjecucion;
	//determina si el juego (keyboardHero) tiene que avanzar
	private boolean juegoActivado=false;
	//sirve para saber si ya se leyó el 1° valor musical de la partitura
	private boolean primerValorLeido=false;

	
	public Controlador(){
		resetear();
		this.keyPressedObservadores = new ArrayList<KeyPressedObservador>();
		cantidadDeMovimientos=1;
	}
	/* Instancia minima mediante el cual se debe iniciar el juego*/
	public void run(){
		if(ventanaJuego==null) ventanaJuego=new VentanaJuego(this); 
		resetear();
		resetearJuego();
		setSuperficieDeDibujo(ventanaJuego);
		superficieDeDibujo.limpiar();
		ventanaJuego.setVisible(true);
		comenzar();
		cerrarVentanas();
		puntaje();
	}
	/*Abre la ventana con los puntajes del ultimo juego*/
	public void puntaje(){
		if(ventanaPuntaje==null) ventanaPuntaje=new VentanaPuntaje(this); 
		setSuperficieDeDibujo(ventanaPuntaje);
		resetear();
		resetearPuntaje();
		superficieDeDibujo.limpiar();
		((VentanaPuntaje)superficieDeDibujo).modoPuntaje();
		ventanaPuntaje.setVisible(true);
		dibujar();
		superficieDeDibujo.actualizar();

	}
	/*Abre la ventana con las canciones para elegir*/
	public void canciones(){
		ventanaCanciones=new VentanaCanciones(this, kHero.getNivel()); 
		resetear();
		setSuperficieDeDibujo(ventanaCanciones);
		superficieDeDibujo.limpiar();
		dibujar();
		ventanaCanciones.setVisible(true);
	}
	/*Abre la ventana principal de donde se comienza el juego*/
	public void principal(){
		ventanaPrincipal=new VentanaPrincipal(this); 
		resetear();
		ventanaPrincipal.setVisible(true);
	}
	/*Devuelve el estado original al juego*/
	private void resetear() {
		Cronometro.resetear();
		dibujables=new ArrayList<Dibujable>();
		objetosVivos=new ArrayList<ObjetoVivo>();
		proximosEliminadosOV=new ArrayList<ObjetoVivo>();
		proximosEliminadosD=new ArrayList<Dibujable>();
		diapazonDibujado=false;
	}
	/*Setea una nueva cancion para jugar*/
	public void nuevaCancion(int i) {

		try {
			Cancion cancion=kHero.getNivel().getCanciones().get(i);
			kHero.setCancion(cancion);
			new Thread(this).start();
		} catch (NoExisteLaCancion e) {e.printStackTrace();	}
	}
	/*Inicia el juego*/
	private void comenzar(){
		
		try{
			kHero.reproducirMusica();
			Thread.sleep(500);//se asegura que empiece la musica (que inicia el cornometro) antes de seguir jugando
			agregarDiapazon();
			while(!kHero.terminoCancion() && juegoVigente){
				
				while(!estaEnEjecucion)	Thread.sleep(200);
				simular();
				dibujar();
				if(juegoActivado==true)	kHero.run(cantidadDeMovimientos);	
				duracionUltimoValorMusical-=1;
				Thread.sleep(50);
			}
			this.finalizarJuego();
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public  void agregarObjetoVivo(ObjetoVivo objetoVivo){
		objetosVivos.add(objetoVivo);
	}
	
	public void removerObjetoVivo(ObjetoVivo objetoVivo){
		objetosVivos.remove(objetoVivo);
	}
	
	public  void agregarEliminableObjetoVivo(ObjetoVivo objetoVivo){
		proximosEliminadosOV.add(objetoVivo);
	}
	
	public void removerEliminableObjetoVivo(ObjetoVivo objetoVivo){
		proximosEliminadosOV.remove(objetoVivo);
	}
	
	public  void agregarEliminableDibujable(Dibujable dibujable){
		proximosEliminadosD.add(dibujable);
	}
	
	public void removerEliminableDibujable(Dibujable dibujable){
		proximosEliminadosD.remove(dibujable);
	}

	public void agregarDibujable(Dibujable unDibujable){
		dibujables.add(unDibujable);
	}
	
	public void removerDibujable(Dibujable unDibujable){
		dibujables.remove(unDibujable);
	}
	/*Devuelve la instancia del KeyboardHero sobre la cual se esta jugando*/
	public KeyboardHero getkHero() {
		return kHero;
	}
	/*Setea la instancia del KeyboardHero sobre la cual se va a jugar*/
	public void setkHero(KeyboardHero kHero) {
		this.kHero = kHero;
		agregarDiapazon();
	}
	/*Dibuja los Dibujables sobre la superficie de dibujo*/
	protected synchronized void dibujar() {
		this.superficieDeDibujo.limpiar();

		Iterator<Dibujable> iterador = dibujables.iterator();
		while(iterador.hasNext())
			((Dibujable)iterador.next()).dibujar(this.superficieDeDibujo);
		this.superficieDeDibujo.actualizar();
	}
	/*Modela el proximo estado de la vista, previo al dibujado*/
	protected void simular() {
		this.superficieDeDibujo.limpiar();
		this.agregarNuevosObjetos();
		this.cobrarVida();
	}
	/* Agrega los objetos vivos en el momento adecuado*/
	private void agregarNuevosObjetos(){	
		
		ValorMusical vM;
		agregarDiapazon();
		vistaPuntos.setPuntos(kHero.getPuntajeCancion());
		if(duracionUltimoValorMusical>0) return;
		if(primerValorLeido==false) vM=kHero.getValorMusicalLeyendose();
		else vM=kHero.espiarValorMusicalSalteando(cantidadQueEstaDibujando);
		if(vM==null) return;
		if(Cronometro.pasaron(vM.getMomentoEjecucion())){
			if(primerValorLeido==false) cantidadQueEstaDibujando--;
			primerValorLeido=true;
			this.agregarValorMusical(vM);
		}			
	}

	private void agregarDiapazon(){
		
		if(diapazonDibujado==false){
		if(diapazon==null) diapazon = new Diapazon((VentanaJuego.ANCHO_VENTANA_GRANDE_JUEGO/2)-Diapazon.ANCHO_DIAPAZON, Diapazon.ALTO_DIAPAZON , this);
		this.agregarObjetoVivo(diapazon);
		VistaDiapazon vistaDiapazon = new VistaDiapazon();
		vistaDiapazon.setPosicionable(diapazon);		
		this.agregarDibujable(vistaDiapazon);
		diapazonDibujado=true;
		}
	}
	
	protected synchronized void agregarValorMusical(ValorMusical vM){
		cantidadQueEstaDibujando++;
		duracionUltimoValorMusical=vM.getDuracion().getDuracion();
		Iterator<Nota> it=vM.getNotas().iterator();
		while (it.hasNext()){
			Nota unaNota=it.next();
			ObjetoNota unObjetoNota=IndicadorNota.getObjeto(diapazon, unaNota, this);
			unObjetoNota.setDuracion(duracionUltimoValorMusical);
			this.agregarObjetoVivo(unObjetoNota);
			VistaNota vistaNota = new VistaNota(unaNota, duracionUltimoValorMusical);
			vistaNota.setPosicionable(unObjetoNota);
			this.agregarDibujable(vistaNota);
		}
	}
	/*Invocar luego de agregar los objetos vivos, pervio al dibujado*/
	protected  void cobrarVida(){
		
		Iterator<ObjetoVivo> iterador = objetosVivos.iterator();
		while(iterador.hasNext())
			((ObjetoVivo)iterador.next()).vivir();
		for (int i=0; i<proximosEliminadosOV.size(); i++) {
			if(i==0)cantidadQueEstaDibujando--;
			objetosVivos.remove(1);
			dibujables.remove(3);
		}
		proximosEliminadosOV.clear();
	}
	

	public SuperficieDeDibujo getSuperficieDeDibujo() {
		return superficieDeDibujo;
	}

	public void setSuperficieDeDibujo(SuperficieDeDibujo superficieDeDibujo) {
		this.superficieDeDibujo = superficieDeDibujo;
	}

	public void despacharKeyPress(KeyEvent event){
		for (KeyPressedObservador observador : this.keyPressedObservadores){
			observador.keyPressed(event);
		}
	}
	public void despacharKeyReleased(KeyEvent event){
		for (KeyPressedObservador observador : this.keyPressedObservadores){
			observador.keyReleased(event);
		}
	}

	public void agregarKeyPressObservador(KeyPressedObservador unKeyPressedObservador){
		this.keyPressedObservadores.add(unKeyPressedObservador);
	}
	
	public void removerKeyPressObservador(KeyPressedObservador unKeyPressedObservador){
		this.keyPressedObservadores.remove(unKeyPressedObservador);
	}

	public void resetearPuntaje() {
		
		Resultados resultados=new Resultados();
		this.agregarObjetoVivo(resultados);
		VistaPuntaje vistaPuntaje = new VistaPuntaje(this);
		vistaPuntaje.setPosicionable(resultados);
		vistaPuntaje.setResultados(resultados);
		
		resultados.setPuntajeCancion(kHero.getPuntajeCancion());
		resultados.setPuntajeNivel(kHero.getNivel().getPuntaje());
		resultados.setResultadoCancion(kHero.getResultadoCancion());
try {   resultados.setResultadoNivel(kHero.getNivel().completado());} catch (NoExisteLaCancion e){e.printStackTrace();}
		resultados.setResultadoJuego(kHero.getResultado());

		this.agregarDibujable(vistaPuntaje);
		this.cobrarVida();
		
	}
	public void resetearJuego() {
			
		primerValorLeido=false;
		diapazonDibujado=false;
		juegoActivado=false;
		
		juegoVigente=true;
		estaEnEjecucion=true;

		duracionUltimoValorMusical=0;
		cantidadQueEstaDibujando=0;
	
		VistaJuego fondoJuego= new VistaJuego();
		this.agregarDibujable(fondoJuego);
		vistaPuntos = new VistaPuntos(0);
		this.agregarDibujable(vistaPuntos);
	}
	/*Termina el juego*/
	public void finalizarJuego() {
		juegoVigente=false;
		kHero.terminarMusica();
		
	}
	/*Pausa el juego, no es recomendable su uso dado al estado luego que se continua (se desincroniza la musica con la vista)*/
	public void detener(){
		this.estaEnEjecucion = false;
	}
	/*Continua el juego luego de una pausa*/
	public void continuar() {
		this.estaEnEjecucion=true;		
	}
	/*cierra TODAS las ventanas abiertas*/
	public void cerrarVentanas() {
		if(ventanaCanciones!=null)
			ventanaCanciones.dispose();	
		if(ventanaJuego!=null)
			ventanaJuego.dispose();	
		if(ventanaPrincipal!=null)
			ventanaPrincipal.dispose();	
		if(ventanaPuntaje!=null)
			ventanaPuntaje.dispose();	
	}
	
	public boolean estaEnEjecucion() {
		return estaEnEjecucion;
	}
	
	public VentanaJuego getVentanaJuego() {
		return (VentanaJuego)ventanaJuego;
	}
	
	public Diapazon getDiapazon() {
		return diapazon;
	}
	
	public boolean estaJuegoActivado(){
		return juegoActivado;
	}
	/*Hace arrancar el keyboardHero*/
	public void activarJuego() {
		juegoActivado=true;
	}
	/*Detiene el keyboardHero*/
	public void desactivarJuego() {
		juegoActivado=false;
	}
}
