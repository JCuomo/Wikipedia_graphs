package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressedController extends KeyAdapter implements KeyListener {
	
	private Controlador controlador;
	
	public KeyPressedController(Controlador controlador){
		this.controlador = controlador;
	}

	public void keyPressed(KeyEvent e) {
		this.controlador.despacharKeyPress(e);
	}

	public void keyReleased(KeyEvent e) {
		this.controlador.despacharKeyReleased(e);
	}

}
