package test.modelo;

import junit.framework.TestCase;
import modelo.Duracion;
import modelo.ValorMusical;
import modelo.notas.DO;
import modelo.notas.Silencio;
import modelo.notas.generico.Nota;

public class PruebasValorMusical extends TestCase {
	
	private ValorMusical doNegra;
	private ValorMusical silencioDeNegra;	
	
	protected void setUp(){
		
		Duracion negra=new Duracion(Duracion.NEGRA);
		Nota Do=DO.getNota();
		Nota silencio=Silencio.getNota();
		
		doNegra=new ValorMusical(negra, Do);
		silencioDeNegra=new ValorMusical(negra, silencio );		
	}

	/**Testea que suenen la cantidad de veces que debería */
	public void testSonar(){
		
		assertTrue(doNegra.sonar(1));
		assertTrue(doNegra.sonar(2));
		assertFalse(doNegra.sonar(1));//4° vez que suena devuelve false indicando que se tiene que dejar de tocar
		assertFalse(doNegra.sonar(1));//luego sigue devolviendo false

		assertTrue(silencioDeNegra.sonar(1));
		assertTrue(silencioDeNegra.sonar(2));
		assertFalse(silencioDeNegra.sonar(1));
	}

}
