package vista.ventanas;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;

import modelo.objetos.Resultados;
import control.Controlador;

@SuppressWarnings("serial")
public class VentanaPuntaje extends Ventana {

	public static final int ANCHO_VENTANA_PUNTAJE = 790;
	public static final int ALTO_VENTANA_PUNTAJE = 500;
	
	private static final int POSICION_INCICIO_X = 10 ;
	private static final int POSICION_INICIO_Y = 10;
	private static final int ANCHO_INICIO = 100;
	private static final int ALTO_INICIO = 25;
	
	private static final int POSICION_CANCIONES_X = Resultados.POSICION_RESULTADO_CANCION_X+360;
	private static final int POSICION_CANCIONES_Y = Resultados.POSICION_RESULTADO_CANCION_Y-53;
	private static final int ANCHO_CANCIONES = 70;
	private static final int ALTO_CANCIONES = 25;

	public VentanaPuntaje(Controlador unControlador) {		
		super(ANCHO_VENTANA_PUNTAJE, ALTO_VENTANA_PUNTAJE, unControlador);
		modoPuntaje();
	}
	
	public void modoPuntaje() {
		
		this.removeAll();
		this.limpiar();
		this.actualizar();
		this.setSize(ANCHO_VENTANA_PUNTAJE, ALTO_VENTANA_PUNTAJE);
		this.setTitle("Puntaje");
		this.setBackground(Color.black);
		
		JLabel etiqueta = new JLabel ();
		
		Button botonInicio=new Button("Menu Principal");
		Button botonCanciones=new Button("Canciones");

		botonInicio.setBounds (POSICION_INCICIO_X, POSICION_INICIO_Y, ANCHO_INICIO, ALTO_INICIO);
		botonCanciones.setBounds (POSICION_CANCIONES_X, POSICION_CANCIONES_Y, ANCHO_CANCIONES, ALTO_CANCIONES);
				
		
		
		botonCanciones.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				controlador.cerrarVentanas();

				controlador.canciones();
				}
		});
		botonInicio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				controlador.cerrarVentanas();

				controlador.principal();
			}
		});
		
		etiqueta.add(botonInicio);
		etiqueta.add(botonCanciones);
		
		add(etiqueta);
		
	}
	
}
