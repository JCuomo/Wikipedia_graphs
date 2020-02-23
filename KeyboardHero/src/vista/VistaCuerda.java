package vista;

import java.awt.Color;
import java.awt.Graphics;
import modelo.interfaces.SuperficieDeDibujo;
import modelo.objetos.Cuerda;


public class VistaCuerda extends Figura {

	private int alto;
	private int ancho;

	public VistaCuerda(int ancho, int alto){
		this.alto = alto;
		this.ancho = ancho;
	}

	public void setSize(int ancho, int alto){
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public void dibujar(SuperficieDeDibujo superfice) {
		Graphics grafico = (Graphics)superfice.getBuffer();
		if(((Cuerda)getPosicionable()).estaEjecutandose())
			grafico.setColor(Color.ORANGE);
		else grafico.setColor(Color.LIGHT_GRAY);
		grafico.fillRect(this.getPosicionable().getX(), this.getPosicionable().getY(), this.ancho, this.alto);
	}
}
