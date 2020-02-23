package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import modelo.interfaces.SuperficieDeDibujo;
import modelo.objetos.Resultados;
import res.Cargador;
import control.Controlador;
import control.Reproductor;

public class VistaPuntaje extends Figura {
	private static final String FONDO = "/res/imagenes\\fondoPuntaje.jpg";
	private Resultados resultados;
	private Controlador controlador;

	public VistaPuntaje(Controlador controlador){
		super.setColor(Color.BLACK);
		this.controlador=controlador;
	}
	
	public void setResultados(Resultados resultado){
		resultados=resultado;
	}
	public void dibujar(SuperficieDeDibujo superfice) {
		
		Graphics grafico = (Graphics)superfice.getBuffer();
		grafico.drawImage(Cargador.getImagen(FONDO), 0, 0, null);
	
		switch (controlador.getkHero().getResultadoCancion()) {
		case GANASTE: new Thread(new Reproductor(new File("/res/efectoSonido/aplausos.wav"))).start();break;
			
		case PERDISTE: new Thread(new Reproductor(new File("/res/efectoSonido/risaMaligna.wav"))).start();break;
	
		default:
			break;
		}
		grafico.setColor(Color.orange);
		grafico.setFont(new Font(Font.DIALOG, Font.BOLD, Resultados.FONT_SIZE));
		grafico.drawString("Puntaje Cancion:   "+String.valueOf(resultados.getPuntajeCancion()), Resultados.POSICION_PUNTAJE_CANCION_X, Resultados.POSICION_PUNTAJE_CANCION_Y);
		grafico.drawString("Puntaje Nivel:         "+String.valueOf(resultados.getPuntajeNivel()), Resultados.POSICION_PUNTAJE_NIVEL_X, Resultados.POSICION_PUNTAJE_NIVEL_Y);
	
		grafico.drawString(String.valueOf(resultados.getResultadoCancion()+" LA CANCIÓN"), Resultados.POSICION_RESULTADO_CANCION_X, Resultados.POSICION_RESULTADO_CANCION_Y);
		grafico.drawString(String.valueOf(resultados.getResultadoJuego()+" EL JUEGO"),     Resultados.POSICION_RESULTADO_JUEGO_X,   Resultados.POSICION_RESULTADO_JUEGO_Y);
		if(resultados.getResultadoNivel())		grafico.drawString("COMPLETASTE EL NIVEL", Resultados.POSICION_RESULTADO_NIVEL_X,   Resultados.POSICION_RESULTADO_NIVEL_Y);
		else						         grafico.drawString("NO COMPLETASTE EL NIVEL", Resultados.POSICION_RESULTADO_NIVEL_X,   Resultados.POSICION_RESULTADO_NIVEL_Y);


	}
}
