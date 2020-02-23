package modelo.objetos;

import vista.ventanas.VentanaJuego;
import modelo.Puntaje.Resultado;
import modelo.interfaces.ObjetoVivo;
import modelo.interfaces.Posicionable;

public class Resultados implements ObjetoVivo, Posicionable{
	
	public static final int FONT_SIZE = 30;
	public static final int ESPACIO_ENTRE_LINEAS = FONT_SIZE*2;
	
	public static final int POSICION_PUNTAJE_CANCION_X   = VentanaJuego.ANCHO_VENTANA_GRANDE_JUEGO/4;
	public static final int POSICION_PUNTAJE_NIVEL_X     = POSICION_PUNTAJE_CANCION_X;
	public static final int POSICION_RESULTADO_CANCION_X = POSICION_PUNTAJE_CANCION_X;
	public static final int POSICION_RESULTADO_NIVEL_X   = POSICION_PUNTAJE_CANCION_X;
	public static final int POSICION_RESULTADO_JUEGO_X   = POSICION_PUNTAJE_CANCION_X;
	
	public static final int POSICION_PUNTAJE_CANCION_Y   = VentanaJuego.ALTO_VENTANA_JUEGO/8;
	public static final int POSICION_PUNTAJE_NIVEL_Y     = POSICION_PUNTAJE_CANCION_Y   +ESPACIO_ENTRE_LINEAS;
	public static final int POSICION_RESULTADO_CANCION_Y = POSICION_PUNTAJE_NIVEL_Y     +ESPACIO_ENTRE_LINEAS;
	public static final int POSICION_RESULTADO_NIVEL_Y   = POSICION_RESULTADO_CANCION_Y +ESPACIO_ENTRE_LINEAS;
	public static final int POSICION_RESULTADO_JUEGO_Y   = POSICION_RESULTADO_NIVEL_Y   +ESPACIO_ENTRE_LINEAS;

	private float puntajeCancion;
	private float puntajeNivel;
	private Resultado resultadoCancion;
	private boolean resultadoNivel;
	private Resultado resultadoJuego;

	public void vivir() {}	
	public int getX() {	return 0;}
	public int getY() {	return 0;}
	
	public float getPuntajeCancion() {
		return puntajeCancion;
	}
	public void setPuntajeCancion(float puntajeCancion) {
		this.puntajeCancion = puntajeCancion;
	}
	public float getPuntajeNivel() {
		return puntajeNivel;
	}
	public void setPuntajeNivel(float puntajeNivel) {
		this.puntajeNivel = puntajeNivel;
	}
	public Resultado getResultadoCancion() {
		return resultadoCancion;
	}
	public void setResultadoCancion(Resultado resultadoCancion) {
		this.resultadoCancion = resultadoCancion;
	}
	public boolean getResultadoNivel() {
		return resultadoNivel;
	}
	public void setResultadoNivel(boolean resultadoNivel) {
		this.resultadoNivel = resultadoNivel;
	}
	public Resultado getResultadoJuego() {
		return resultadoJuego;
	}
	public void setResultadoJuego(Resultado resultadoJuego) {
		this.resultadoJuego = resultadoJuego;
	}
	
	

}
