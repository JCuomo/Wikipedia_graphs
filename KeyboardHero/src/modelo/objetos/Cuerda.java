package modelo.objetos;

import modelo.interfaces.*;

/* Representa a la cuerda por donde bajan las notas, componen a un diapazon */
public class Cuerda implements Posicionable, ObjetoVivo{
	
	public static final int POSICION_Y = 50;
	private int x;
	private boolean estaEjecutandose=false;
	
	public Cuerda(int x){
		this.x = x;
	}
	public int getX() {return x;}

	public int getY() {return POSICION_Y;}

	public void vivir() {}

	public void tocar() {
		estaEjecutandose=true;
	}
	public void detener(){
		estaEjecutandose=false;
	}
	public boolean estaEjecutandose() {
		return estaEjecutandose;
	}
	
}
