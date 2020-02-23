package configuracion;

import java.util.LinkedList;

import res.Cargador;

import modelo.Cancion;
import modelo.Nivel;
import modelo.Partitura;
import modelo.SistemaResonante;
import modelo.Nivel.NumeroNivel;
import modelo.notas.*;

public class Juego_Guitarra_4Cuerdas_2Niveles extends JuegoArmado {
	
	public Juego_Guitarra_4Cuerdas_2Niveles(){
		
		super();
	}
	
	protected void armarNiveles() {
		
		LinkedList<Cancion> cancionesDelNivelUno= new LinkedList<Cancion>();
		LinkedList<Cancion> cancionesDelNivelDos= new LinkedList<Cancion>();

		try{
			cancionesDelNivelUno.add(new Cancion(new Partitura("./res/partituras/Cancion1.xml")));
			cancionesDelNivelUno.add(new Cancion(new Partitura("./res/partituras/Cancion2.xml")));
			cancionesDelNivelDos.add(new Cancion(new Partitura("./res/partituras/Cancion3.xml")));
		}
		catch(Exception e){
			e.printStackTrace();
			System.err.println("No pudo cargar las partituras XML");
		}
		
		niveles.add(new Nivel(NumeroNivel.NIVEL_UNO, cancionesDelNivelUno));
		niveles.add(new Nivel(NumeroNivel.NIVEL_DOS, cancionesDelNivelDos));
	}

	protected void afinarInstrumento() {
		
		SistemaResonante cuerda1=new SistemaResonante();
		SistemaResonante cuerda2=new SistemaResonante();
		SistemaResonante cuerda3=new SistemaResonante();	
		SistemaResonante cuerda4=new SistemaResonante();	

		instrumento.asociarNotaASistemaResonante( DO.getNota() , 	cuerda1);
		instrumento.asociarNotaASistemaResonante( RE.getNota() , 	cuerda2);
		instrumento.asociarNotaASistemaResonante( MI.getNota() , 	cuerda3);
		instrumento.asociarNotaASistemaResonante( FA.getNota() ,	cuerda2);
		instrumento.asociarNotaASistemaResonante( SOL.getNota(),	cuerda4);
		instrumento.asociarNotaASistemaResonante( LA.getNota() , 	cuerda3);
		instrumento.asociarNotaASistemaResonante( SI.getNota() , 	cuerda1);
	}
	
	

}
