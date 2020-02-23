package test.modelo;

import modelo.Duracion;
import junit.framework.TestCase;

public class PruebasDuracion extends TestCase {
	
	
	/**Testea que las duraciones sean las esperadas */
	public void testDuracion(){
		
		int milisegundos = 10;
		
		Duracion.setDuracionPatron(milisegundos ); //fijo la duracion de la semicorchea en 10 milisegundos
		
		Duracion blanca	= new Duracion(Duracion.BLANCA);
		Duracion negra	= new Duracion(Duracion.NEGRA);
		
		
		
		assertSame(blanca.getDuracion(), Duracion.BLANCA * milisegundos );
		assertSame(negra.getDuracion(), Duracion.NEGRA * milisegundos);
		
		assertNotSame(blanca.getDuracion(), negra.getDuracion());

	}
	

}
