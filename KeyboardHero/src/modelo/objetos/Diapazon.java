package modelo.objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import control.Controlador;

import vista.ventanas.VentanaJuego;

import modelo.Instrumento;
import modelo.SistemaResonante;
import modelo.excepciones.NoExisteLaCancion;
import modelo.interfaces.*;
import modelo.notas.generico.Nota;

public class Diapazon implements Posicionable, ObjetoVivo {

	public static final int ESPACIO_ENTRE_CUERDAS = 20;
	public static final int ALTO_CUERDA = 600;
	public static final int ANCHO_CUERDA = 30;
	public static final int ANCHO_DIAPAZON = ANCHO_CUERDA*4+ESPACIO_ENTRE_CUERDAS*3;
	public static final int ALTO_DIAPAZON = ALTO_CUERDA;
	public static final int POSICION_CUERDA_2 = (VentanaJuego.ANCHO_VENTANA_GRANDE_JUEGO/4)-ESPACIO_ENTRE_CUERDAS/2-ANCHO_CUERDA;
	public static final int POSICION_CUERDA_1 = POSICION_CUERDA_2-ESPACIO_ENTRE_CUERDAS-ANCHO_CUERDA;
	public static final int POSICION_CUERDA_3 = (VentanaJuego.ANCHO_VENTANA_GRANDE_JUEGO/4)+ESPACIO_ENTRE_CUERDAS/2;
	public static final int POSICION_CUERDA_4 = POSICION_CUERDA_3+ESPACIO_ENTRE_CUERDAS+ANCHO_CUERDA;
	public static final int POSICION_Y = Cuerda.POSICION_Y;
	public static final int POSICION_X = POSICION_CUERDA_1;
	protected Instrumento instrumento;
	protected List<Cuerda> cuerdas;
	private HashMap<Nota, Cuerda> mapaNota_Cuerda;
	private Controlador controlador;
	private int alto;
	private int ancho;
	
	public Diapazon(int ancho, int alto, Controlador c){

		this.alto = alto;
		this.ancho = ancho;
		mapaNota_Cuerda=new HashMap<Nota, Cuerda>();
		this.controlador=c;
		setInstrumento(controlador.getkHero().getInstrumento());
	}
	
	public void setInstrumento(Instrumento instrumento){
		
		this.instrumento=instrumento;
		
		cuerdas=new ArrayList<Cuerda>();
		cuerdas.add(new Cuerda(POSICION_CUERDA_1));
		cuerdas.add(new Cuerda(POSICION_CUERDA_2));
		cuerdas.add(new Cuerda(POSICION_CUERDA_3));
		cuerdas.add(new Cuerda(POSICION_CUERDA_4));
		
		for(int i=0; i<instrumento.cantidaSistemasResonantes(); i++){
			SistemaResonante st=instrumento.getSistemasResonantes().get(i);
			for (Nota it_notas : st.getNotas()) 
				mapaNota_Cuerda.put(it_notas, cuerdas.get(i));
		}
	}
	
	public int getX() {	return POSICION_CUERDA_1; }

	public int getY() { return POSICION_Y; } 
	
	public Cuerda getCuerda(Nota unaNota){
		return mapaNota_Cuerda.get(instrumento.getSistemaResonante(unaNota).getNotas().get(0));
	}
	
	public List<Cuerda> getCuerdas(){
		return cuerdas;
	}
	
	
	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void vivir(){}

	public void tocar(ArrayList<Nota> notas) {

		try {
			if(!controlador.estaJuegoActivado())
				controlador.getkHero().avisarQueFallo();
		} catch (NoExisteLaCancion e) {	e.printStackTrace();}
		instrumento.tocar(notas);
		Iterator<Nota> it=notas.iterator();
		while (it.hasNext()) {
			Cuerda c=mapaNota_Cuerda.get(it.next());
			c.tocar();
		}
		
	}

	public void parar(ArrayList<Nota> notas) {
		
		instrumento.parar(notas);
		Iterator<Nota> it=notas.iterator();
		while (it.hasNext()) {
			Cuerda c=mapaNota_Cuerda.get(it.next());
			c.detener();
		}
	}
	
}
