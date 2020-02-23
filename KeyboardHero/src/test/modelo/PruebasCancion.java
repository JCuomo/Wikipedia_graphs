package test.modelo;

import java.util.List;

import modelo.Cancion;
import modelo.Duracion;
import modelo.notas.*;
import modelo.notas.generico.Nota;

import junit.framework.TestCase;

public class PruebasCancion extends TestCase {
	
	Cancion cancion;
	Nota primerNota, segundaNota, quintaNota;
	Nota Do, Mi, Sol, Si;
	List<Nota> notas;

	
	protected void setUp() {
		
		cancion=(new CancionDePrueba()).getCancion();

		Do=DO.getNota();
		Mi=MI.getNota();
		Sol=SOL.getNota();
		Si=SI.getNota();
	}

	/**La cancion que se reproduce es la cancion de prueba*/		
	public void testReproducir(){

		for(int i=0; i<Duracion.CORCHEA; i++)
			assertSame(cancion.avanzar(2).getNotas().get(0), Do);
		
		for(int i=0; i<Duracion.NEGRA; i++)
			assertSame(cancion.avanzar(2).getNotas().get(0), Mi);
		
		for(int i=0; i<Duracion.BLANCA; i++)
			assertSame(cancion.avanzar(2).getNotas().get(0), Sol);
		
		for(int i=0; i<Duracion.REDONDA; i++)
			assertSame(cancion.avanzar(2).getNotas().get(0), Si);	
	}
	
}
