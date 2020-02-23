package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import modelo.interfaces.Dibujable;
import modelo.interfaces.Posicionable;
import modelo.interfaces.SuperficieDeDibujo;


public class VistaPuntos implements Dibujable{
	
	private static final int POSICION_PUNTOS_X = 50;
	private static final int POSICION_PUNTOS_Y = 100;
	float puntos;

	public VistaPuntos(float puntos){
		setPuntos(puntos);
	}
	
	public void setPuntos(float puntos){
		this.puntos=puntos;
	}
	
	public void dibujar(SuperficieDeDibujo superfice) {
		
		Graphics grafico = (Graphics)superfice.getBuffer();
		grafico.setColor(Color.ORANGE);
		grafico.setFont(new Font("Arial", Font.BOLD, 40));
		grafico.drawString(Integer.toString((int)puntos), POSICION_PUNTOS_X, POSICION_PUNTOS_Y);
				}

	public Posicionable getPosicionable() {	return null; }

	public void setPosicionable(Posicionable posicionable) {}

}

