package test.modelo;

import java.util.List;

import junit.framework.TestCase;
import modelo.Cancion;
import modelo.Dificultad;
import modelo.Duracion;
import modelo.notas.DO;
import modelo.notas.LA;
import modelo.notas.SI;
import modelo.notas.generico.Nota;

public class PruebasDificultad extends TestCase {
	 
	private Dificultad facil;
	private Dificultad dificil;
	private Cancion cancion;
	List<Nota> notas;

	
	protected void setUp(){
	
		cancion=(new CancionDePrueba()).getCancion();
		facil=new Dificultad(Dificultad.FACIL);
		dificil=new Dificultad(Dificultad.DIFICIL);
		cancion.addObserver(facil);
	}
	
	/** Testea que en la reproduccion saltee la cantidad de notas correctas */
	public void testReproducirCancion(){

		if(!facil.terminoCancion(cancion)){

			notas=facil.reproducirCancion(cancion).getNotas();
			assertEquals(notas.get(0), DO.getNota());
			facil.reproducirCancion(cancion).getNotas();//termina de sonar el DO (corchea)
			
			for(int i=0; i<Duracion.BLANCA-1; i++){
				notas=facil.reproducirCancion(cancion).getNotas();//suena toda la nota LA (blanca)
				assertEquals(notas.get(0), LA.getNota());//menos una vez (paso en el que decide cuantas notas saltear
			}
		}
		
		if(!dificil.terminoCancion(cancion)){
			
			notas=dificil.reproducirCancion(cancion).getNotas();//terminar de sonar el La
			assertEquals(notas.get(0), LA.getNota());
			
			notas=dificil.reproducirCancion(cancion).getNotas();//suena SI (redonda)
			assertEquals(notas.get(0), SI.getNota());
		}
	}
}