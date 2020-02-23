package vista.ventanas;

import control.Controlador;
import control.KeyPressedController;

@SuppressWarnings("serial")
public class VentanaJuego extends Ventana {
	
	public static int ANCHO_VENTANA_GRANDE_JUEGO = 1240;
	public static int ANCHO_VENTANA_CHICA_JUEGO = 420;
	public static final int ALTO_VENTANA_JUEGO = 670;
	

	public VentanaJuego(Controlador unControlador) {
		super(ANCHO_VENTANA_GRANDE_JUEGO, ALTO_VENTANA_JUEGO, unControlador);
		this.addKeyListener(new KeyPressedController(controlador));
		this.setTitle("Keyboard Hero");
	}

	public  void acomodarPantalla() {
		if(this.getSize().width==ANCHO_VENTANA_GRANDE_JUEGO)
			this.setSize(ANCHO_VENTANA_CHICA_JUEGO, ALTO_VENTANA_JUEGO);
		else
			this.setSize(ANCHO_VENTANA_GRANDE_JUEGO, ALTO_VENTANA_JUEGO);

	}

}
	

