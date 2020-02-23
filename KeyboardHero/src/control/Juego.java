package control;

import modelo.Dificultad;
import modelo.KeyboardHero;
import configuracion.Juego_Guitarra_4Cuerdas_2Niveles;

public class Juego {
public static void main(String[] args) {
	
	
		Controlador controlador = new Controlador();
		KeyboardHero juego=new Juego_Guitarra_4Cuerdas_2Niveles().getJuego();
		juego.setDificultad(new Dificultad(Dificultad.DIFICIL));
		controlador.setkHero(juego);
		controlador.agregarKeyPressObservador(new EscuchadorDeKeyPress(controlador));
		controlador.principal();
}
}
