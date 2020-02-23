package vista.ventanas;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import res.Cargador;

import modelo.interfaces.SuperficieDeDibujo;
import control.Controlador;
/*
 * ESta clase representa la superficie de dibujo, tipicamente será el formulario
 * principal de la aplicación y donde se dibujará la vista.
 * Esta clase utiliza la tecnica de doble buffering para evitar los efectos de flicking
 */
@SuppressWarnings("serial")
public class Ventana extends Frame implements SuperficieDeDibujo{

	protected Image fondo;
 /*Esta es la imagen en que se realiza todo el dibujo utilizando la tecnica de doble buffering.*/
    protected Image imagen;
	protected Controlador controlador;
	
	public Ventana(int ancho,int alto, Controlador unControlador){

		this(unControlador);
		setSize(ancho, alto);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		limpiar();
	}
	
	public Ventana(Controlador unControlador){
		this.controlador = unControlador;
		limpiar();
	}
	
	public void setFondo(String unFondo){
		try {
			this.fondo = ImageIO.read(new File(unFondo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// es llamado internamente por el metodo repaint() de la clase Frame
	public void update(Graphics g) {
		paint(g);
	}
    
	public void paint(Graphics g) {
		limpiar();
		imagen.getGraphics().drawImage(this.fondo, 0,0, null);
		g.drawImage(this.imagen, 0, 0, null);
	}

	public void limpiar(){
		if(this.imagen == null)
			this.imagen = this.createImage(getSize().width, getSize().height);
	}
	
	public Object getBuffer(){
		limpiar();
		return this.imagen.getGraphics();
	}	
	
	public void actualizar(){
		this.repaint();
	}


	
	
}
