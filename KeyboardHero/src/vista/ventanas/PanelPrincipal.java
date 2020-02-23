package vista.ventanas;

import java.awt.Button;
import java.util.List;
import res.Cargador;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Dificultad;
import modelo.KeyboardHero;
import modelo.Nivel;
import control.Controlador;

@SuppressWarnings("serial")
public class PanelPrincipal extends JPanel{
	
	private static final String[] dificultades = { "FACIL","NORMAL","DIFICIL" };
	
	private static final int ANCHO_CANCIONES = 100;
	private static final int ALTO_CANCIONES = 30;
	private static final int ANCHO_APLICAR = 100;
	private static final int ALTO_APLICAR = 30;
	private static final int ANCHO_DIFICULTADES = 90;
	private static final int ALTO_DIFICULTADES = 30;

	private static final int POSICION_CANCIONES_X =VentanaPrincipal.ANCHO/2;
	private static final int POSICION_PRIMERA_CANCION_Y =  VentanaPrincipal.ALTO/2 - ALTO_CANCIONES /2;
	private static final int DISTANCIA_CANCIONES = ALTO_CANCIONES*2;

	private static final int POSICION_DIFICULTADES_X = VentanaPrincipal.ANCHO/2-ANCHO_APLICAR/2;
	private static final int POSICION_DIFICULTADES_Y = 100;

	private static final int POSICION_OK_X = POSICION_DIFICULTADES_X+ANCHO_DIFICULTADES+10;
	private static final int POSICION_OK_Y = POSICION_DIFICULTADES_Y;
	
	private static final String FONDO = "./res/imagenes\\fondoPrincipal.jpg";

	Controlador controlador;
	JComboBox comboDificultades=new JComboBox(dificultades);
	KeyboardHero kHero;
	
	public PanelPrincipal (Controlador unControlador){
		super();
		JLabel etiqueta = new JLabel(new ImageIcon(FONDO));
		JButton botonAplicar = new JButton ("Aplicar");
		controlador=unControlador;
		kHero=controlador.getkHero();

		KeyboardHero kHero=unControlador.getkHero();
		List<Nivel> niveles=kHero.getNiveles();
		
		add(etiqueta);
		etiqueta.add(botonAplicar);
		etiqueta.add(comboDificultades);
		
		int cantidadNiveles=niveles.size();
		for (int i = 0; i < cantidadNiveles; i++) {
			final int i_aux=i;
			Button botonNivel=new Button("NIVEL "+ Integer.toString(i+1));
			if(!kHero.nivelHabilitado(i+1)) botonNivel.setEnabled(false);
			int posicionY=POSICION_PRIMERA_CANCION_Y-(ALTO_CANCIONES*cantidadNiveles+DISTANCIA_CANCIONES*(cantidadNiveles-1))/2+i*DISTANCIA_CANCIONES;
			botonNivel.setBounds (POSICION_CANCIONES_X, posicionY, ANCHO_CANCIONES, ALTO_CANCIONES);
			botonNivel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					controlador.cerrarVentanas();
					controlador.getkHero().setNivel(controlador.getkHero().getNiveles().get(i_aux));
					controlador.canciones();
				}
			});
			etiqueta.add(botonNivel);
		}
		
		comboDificultades.setBounds(POSICION_DIFICULTADES_X, POSICION_DIFICULTADES_Y, ANCHO_DIFICULTADES, ALTO_DIFICULTADES);
		comboDificultades.setSelectedIndex(1);		
		botonAplicar.setBounds(POSICION_OK_X, POSICION_OK_Y, ANCHO_APLICAR, ALTO_APLICAR);
		
		botonAplicar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
	try {	controlador.getkHero().setDificultad(new Dificultad((Class.forName("modelo."+"Dificultad").getField(comboDificultades.getSelectedItem().toString()).getInt(new Dificultad(Dificultad.DIFICIL)))));
	} catch (Exception e1) {e1.printStackTrace();}
			}
		});
		
		
	}
}
