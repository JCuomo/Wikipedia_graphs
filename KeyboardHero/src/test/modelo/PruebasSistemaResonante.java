package test.modelo;

import junit.framework.TestCase;
import modelo.SistemaResonante;
import modelo.notas.FA;
import modelo.notas.RE;
import modelo.notas.generico.Nota;

public class PruebasSistemaResonante extends TestCase {
	
	Nota Fa, Re;
	private SistemaResonante cuerda1;
	
	protected void setUp(){
		
		Fa=FA.getNota();
		Re=RE.getNota();
		
		cuerda1=new SistemaResonante();
	}
	
	/**Testea que se puedan agregar notas*/
	public void testAgregarNota(){
		
		cuerda1.agregarNota(Fa);
		cuerda1.agregarNota(Re);

		assertTrue(cuerda1.tieneLaNota(Fa));
		assertTrue(cuerda1.tieneLaNota(Re));
	}
	
	/**Testea que se puedan ejecutar notas*/
	public void testEjecutar(){
		
		cuerda1.ejecutar();
		assertTrue(cuerda1.estaEjecutandose());
	}
	
	/**Testea que se pueda detener la ejecucion de notas*/
	public void testDetenerEjecucion(){
		
		cuerda1.ejecutar();
		cuerda1.detenerEjecucion();
		assertFalse(cuerda1.estaEjecutandose());
	}
}
