package vista.ventanas;

import java.awt.Button;
import java.util.List;

import javax.swing.JLabel;

import modelo.Cancion;
import modelo.Nivel;
import modelo.excepciones.NoExisteLaCancion;
import control.Controlador;

@SuppressWarnings("serial")
public class VentanaCanciones extends Ventana {

	public static final int ANCHO = 400;
	public static final int ALTO = 400;
	
	private static final int ANCHO_CANCIONES = 300;
	private static final int ALTO_CANCIONES = 25;

	private static final int POSICION_CANCIONES_X = ANCHO/2- ANCHO_CANCIONES/2;
	private static final int POSICION_PRIMER_CANCION_Y =  ALTO/2 - ALTO_CANCIONES /2;
	private static final int DISTANCIA_CANCIONES = ALTO_CANCIONES*2;
	private static final String FONDO = "./res/imagenes\\fondoCanciones.jpg";
	
	public VentanaCanciones(Controlador unControlador, Nivel nivel) {
		
		this(unControlador, unControlador.getkHero().getNiveles().indexOf(nivel));
	}

	public VentanaCanciones(Controlador unControlador, int numeroNivel) {
		
		super(ANCHO, ALTO, unControlador);
		setFondo(FONDO);
		Nivel nivel=controlador.getkHero().getNiveles().get(numeroNivel);
		JLabel etiqueta = new JLabel ();
		final List<Cancion> canciones=nivel.getCanciones();
		List<String> nombres=nivel.getNombresCanciones();
		int cantidadCanciones=canciones.size();
		for (int i = 0; i < cantidadCanciones; i++) {
			final int i_aux=i;
			Button botonCancion=new Button(nombres.get(i));
			try {if(!nivel.cancionDisponible(canciones.get(i)))botonCancion.setEnabled(false);} catch (NoExisteLaCancion e) {e.printStackTrace();}
			int posicionY=POSICION_PRIMER_CANCION_Y-(ALTO_CANCIONES*cantidadCanciones+DISTANCIA_CANCIONES*(cantidadCanciones-1))/2+i*DISTANCIA_CANCIONES;
			botonCancion.setBounds (POSICION_CANCIONES_X, posicionY, ANCHO_CANCIONES, ALTO_CANCIONES);
			botonCancion.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					controlador.cerrarVentanas();
					controlador.nuevaCancion(i_aux);
				}
			});
			etiqueta.add(botonCancion);
		}
		add(etiqueta);
	}
}
