package test.modelo;

import java.util.ArrayList;
import java.util.LinkedList;

import modelo.Cancion;
import modelo.Duracion;
import modelo.Partitura;
import modelo.ValorMusical;
import modelo.notas.*;
import modelo.notas.generico.Nota;

/**
 * Cancion de prueba para los test del paquete modelo.
 * Esta compuesta por la escala musical con acordes y diferentes duraciones y un silencio
 */
public class CancionDePrueba {
	
	Cancion cancion;
	Partitura partitura;
	
	public CancionDePrueba(){
		
		Duracion corchea=new Duracion(Duracion.CORCHEA);
		Duracion negra=new Duracion(Duracion.NEGRA);
		Duracion blanca=new Duracion(Duracion.BLANCA);
		Duracion redonda=new Duracion(Duracion.REDONDA);
		
		ArrayList<Nota> acordeDeDo=new ArrayList<Nota>();
		acordeDeDo.add(DO.getNota());
		acordeDeDo.add(MI.getNota());
		acordeDeDo.add(SOL.getNota());

		ValorMusical acordeDeDoCorchea = new  ValorMusical( corchea, acordeDeDo);
		ValorMusical reCorchea = new  ValorMusical( corchea, 	RE.getNota());
		ValorMusical miNegra   = new  ValorMusical( negra,  	MI.getNota());
		ValorMusical faNegra   = new  ValorMusical( negra, 		FA.getNota());
		ValorMusical solBlanca = new  ValorMusical( blanca, 	SOL.getNota());
		ValorMusical laBlanca  = new  ValorMusical( blanca, 	LA.getNota());
		ValorMusical siRedonda = new  ValorMusical( redonda, 	SI.getNota());
		ValorMusical silencioDeRedonda = new  ValorMusical( redonda, 	Silencio.getNota());

	
		ValorMusical [] arregloAuxFiguras={ acordeDeDoCorchea, reCorchea, miNegra, faNegra, solBlanca, laBlanca, siRedonda, silencioDeRedonda};
		LinkedList<ValorMusical> figuras=new LinkedList<ValorMusical>();
		
		for(int i=0; i<7; i++)figuras.add(arregloAuxFiguras[i]);

		partitura=new Partitura(figuras, 60);
		cancion=new Cancion(partitura);
	}

	public Cancion getCancion() {
		
		return cancion;
	}

	public Partitura getPartitura() {
		
		return partitura;
	}

}
