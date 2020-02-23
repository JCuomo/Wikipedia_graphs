package test.modelo;

import java.util.List;

import junit.framework.TestCase;
import modelo.Partitura;
import modelo.notas.*;
import modelo.notas.generico.Nota;

/**Utiliza cancion de prueba*/
public class PruebasPartitura extends TestCase {
	
	CancionDePrueba cancion;
	Partitura partitura;
	List<Nota> primerasNotas, segundasNotas, quintasNotas;
	Nota Do, Re, Sol, Fa, Si, Mi;
	double duracionDelPrimerValorMusical;
	double duracionDelSegundoValorMusical;

	
	protected void setUp() {
		
		cancion=new CancionDePrueba();
		partitura=cancion.getPartitura();
	
		Do=DO.getNota();
		Re=RE.getNota();
		Sol=SOL.getNota();
		Fa=FA.getNota();
		Si=SI.getNota();
		Mi=MI.getNota();
		
		duracionDelPrimerValorMusical=partitura.getValorMusicalActual().getDuracion().getDuracion();
		duracionDelSegundoValorMusical=partitura.espiarProximoValorMusicalSalteando(0).getDuracion().getDuracion();

	}

	/**Testea que una partitura pueda ser leída correctamente*/
	public void testLeer(){
		//comprueba que se reproduce la misma nota la cantidad de veces que dura.		
		for(int i=0; i<duracionDelPrimerValorMusical; i++){
			
			primerasNotas=partitura.avanzar(1).getNotas();
			assertSame(primerasNotas.get(0), Do);
			assertSame(primerasNotas.get(1), Mi);
			assertSame(primerasNotas.get(2), Sol);
		}
		//al avanzar 5 lo que hace es luego de reproducir la 2° nota se saltea 5 menos 1 notas(mi, fa, sol, la)
		for(int i=0; i<duracionDelSegundoValorMusical; i++){
			primerasNotas=partitura.avanzar(5).getNotas();
			assertSame(primerasNotas.get(0), Re);
		}
		assertSame(partitura.avanzar(1).getNotas().get(0), Si);
		
	}
	
	/**Testea que se puedan espiar los valores musicales que estan por leerse*/
	public void testEspiarValorMusical(){
	
		for(int i=1; i<duracionDelPrimerValorMusical+duracionDelSegundoValorMusical; i++)
			partitura.avanzar(1);
		
		quintasNotas=partitura.espiarProximoValorMusicalSalteando(2).getNotas();
		assertSame(quintasNotas.get(0), Sol);
	}
}
