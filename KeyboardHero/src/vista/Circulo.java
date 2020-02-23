package vista;

import java.awt.Graphics;

import modelo.interfaces.SuperficieDeDibujo;

public class Circulo  extends Figura {

	private int radio;
	
	public Circulo(int radio){
		this.radio = radio;
	}
	
	public void dibujar(SuperficieDeDibujo superfice) {
		Graphics grafico = (Graphics)superfice.getBuffer();
		grafico.setColor(getColor());
		grafico.fillOval(getPosicionable().getX() , getPosicionable().getY(), this.radio, this.radio);
	}

}
