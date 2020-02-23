package modelo.objetos;

import java.util.Observable;

import control.Controlador;

import vista.VistaNota;
import modelo.interfaces.ObjetoVivo;
import modelo.interfaces.Posicionable;
import modelo.notas.generico.Nota;

public abstract class ObjetoNota extends Observable implements ObjetoVivo, Posicionable{

	protected int x;
	protected int y;
	protected Diapazon diapazon;
	private double duracion;
	private boolean estaEjecutandose=false;
	protected Controlador controlador;
	public static int VELOCIDAD_AVANCE=40;
	
	public ObjetoNota(){}
	
	public ObjetoNota(Diapazon diapazon, Nota nota, Controlador controlador){
		this.controlador=controlador;
		this.diapazon=diapazon;
		this.x=this.diapazon.getCuerda(nota).getX()+Diapazon.ANCHO_CUERDA/2-VistaNota.ANCHO_NOTA/2;
		this.y=this.diapazon.getCuerda(nota).getY();
	}
	
	public int getX() {	return x;}

	public int getY() {	return y;}

	public void vivir() {
		
		y +=VELOCIDAD_AVANCE;
		if(y+VistaNota.ALTO_NOTA >= this.diapazon.getAlto()+diapazon.getY() && duracion!=0){
			y-=VELOCIDAD_AVANCE;
			y+=(this.diapazon.getAlto()+diapazon.getY())-(y+VistaNota.ALTO_NOTA);
			estaEjecutandose=true;
		}
		if(estaEjecutandose){
			duracion--;
			controlador.activarJuego();
		}
		if(duracion<0){
			controlador.desactivarJuego();
			y+=200;
			controlador.agregarEliminableObjetoVivo(this);
		//	controlador.agregarEliminableDibujable(dibujable)
		}
	}

	public Diapazon getDiapazon() {
		return this.diapazon;
	}
	public void setDuracion(double duracionUltimoValorMusical){
		duracion=duracionUltimoValorMusical;
	}

	public void setDiapazon(Diapazon diapazon) {
		this.diapazon = diapazon;
	}

	public boolean estaEjecutandose() {
		return estaEjecutandose;
	}
}
