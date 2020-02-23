package test.modelo;
import java.util.ArrayList;
import java.util.List;

import modelo.Instrumento;
import modelo.SistemaResonante;
import modelo.notas.*;
import modelo.notas.generico.Nota;
import junit.framework.TestCase;

public class PruebasIntrumento extends TestCase {
	
	Instrumento guitarra;
	List<Nota> doMayor, silencio;

	Nota Do, Re, Mi, Fa, Sol;

	SistemaResonante cuerdaDelDO;
	SistemaResonante cuerdaDelRE;
	SistemaResonante cuerdaDelMI;
	SistemaResonante cuerdaDelFA;
	SistemaResonante cuerdaDelSOL;

	protected void setUp(){
		
		guitarra = new Instrumento();
		
		Do=DO.getNota();
		Re=RE.getNota();
		Mi=MI.getNota();
		Fa=FA.getNota();
		Sol=SOL.getNota();
		
		doMayor=new ArrayList<Nota>();
		doMayor.add(Do);
		doMayor.add(Mi);
		doMayor.add(Sol);
		
		silencio=new ArrayList<Nota>();
		silencio.add(Silencio.getNota());
		
				
		guitarra.asociarNotaASistemaResonante(Do, new SistemaResonante() );
		guitarra.asociarNotaASistemaResonante(Re, new SistemaResonante() );
		guitarra.asociarNotaASistemaResonante(Mi, new SistemaResonante() );
		guitarra.asociarNotaASistemaResonante(Fa, guitarra.getSistemaResonante(Do)  );
		guitarra.asociarNotaASistemaResonante(Sol, guitarra.getSistemaResonante(Re) );
				
		cuerdaDelDO=guitarra.getSistemaResonante(Do);
		cuerdaDelRE=guitarra.getSistemaResonante(Re);
		cuerdaDelMI=guitarra.getSistemaResonante(Mi);
		cuerdaDelFA=guitarra.getSistemaResonante(Fa);
		cuerdaDelSOL=guitarra.getSistemaResonante(Sol);
	}

	/**Testea que las asociaciones se realicen segun lo esperado */
	public void testAsociarSistemaResonanteANota(){

		assertSame(cuerdaDelDO, cuerdaDelFA);
		assertSame(cuerdaDelRE, cuerdaDelSOL);
		
		assertNotSame(cuerdaDelDO, cuerdaDelRE);
		assertNotSame(cuerdaDelMI, cuerdaDelRE);
		
	}
	/** Testea que se pueda tocar una nota*/
	public void testTocarAcorde(){

		guitarra.tocar(doMayor);

		assertTrue(guitarra.estaTocandose(Do));
		assertTrue(cuerdaDelDO.estaEjecutandose());
		
		assertTrue(guitarra.estaTocandose(Mi));
		assertTrue(cuerdaDelDO.estaEjecutandose());
		
		assertTrue(guitarra.estaTocandose(Sol));
		assertTrue(cuerdaDelDO.estaEjecutandose());
	}
	
	/** Testea especialmente que pasa cuando se toca un silencio*/
	public void testTocarSilencio(){

		guitarra.tocar(silencio);

		assertTrue(guitarra.estaEjecutandose(silencio));	
	}
	
	/**Testea que se pueda para de tocar una nota*/
	public void testParar(){

		guitarra.tocar(doMayor);
		guitarra.parar(doMayor);
	
		assertFalse(guitarra.estaTocandose(Do));
		assertFalse(guitarra.estaTocandose(Mi));
		assertFalse(guitarra.estaTocandose(Sol));
	}
	
	/**Testea el comportamiento cuando es una nota muda*/
	public void testPararSilencio(){

		guitarra.tocar(doMayor);
		guitarra.parar(silencio);
	
		assertTrue(guitarra.estaTocandose(Do));
		assertTrue(guitarra.estaTocandose(Mi));
		assertTrue(guitarra.estaTocandose(Sol));
	}
}
