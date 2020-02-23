package vista.ventanas;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import control.Controlador;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame{
	public static final int ANCHO = 550;
	public static final int ALTO = 500;
	
	public VentanaPrincipal(Controlador unControlador){
	
		super("Keyboard Hero");
		setSize(ANCHO, ALTO);
		setResizable(false);
		add(new PanelPrincipal(unControlador));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);
	}
}