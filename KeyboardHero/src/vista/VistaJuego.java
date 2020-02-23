package vista;

import java.awt.Graphics;
import java.awt.Image;

import modelo.interfaces.SuperficieDeDibujo;
import res.Cargador;

public class VistaJuego extends Figura {
	Image fondo;
	private final static String FONDO="/res/imagenes\\fondoJuego.jpg";
	public VistaJuego(){
		 fondo = Cargador.getImagen(FONDO);
	}
	
	public void dibujar(SuperficieDeDibujo superfice) {
		
Graphics grafico = (Graphics)superfice.getBuffer();
grafico.drawImage(fondo, 0, 0, null);
	}
}
