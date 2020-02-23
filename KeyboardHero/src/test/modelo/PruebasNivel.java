package test.modelo;

import java.util.LinkedList;

import modelo.Cancion;
import modelo.Nivel;
import modelo.Partitura;
import modelo.Puntaje;
import modelo.Nivel.NumeroNivel;
import modelo.excepciones.NoExisteLaCancion;
import junit.framework.TestCase;

public class PruebasNivel extends TestCase {
	
	Cancion cancionUno, cancionDos;
	Nivel nivelUno;
	LinkedList<Cancion> listaDeCanciones;

	protected void setUp(){
		
		cancionUno=new CancionDePrueba().getCancion();
		cancionDos=new CancionDePrueba().getCancion();
		listaDeCanciones=new LinkedList<Cancion>();
		listaDeCanciones.add(cancionUno);
		listaDeCanciones.add(cancionDos);
		nivelUno=new Nivel(NumeroNivel.NIVEL_UNO, listaDeCanciones);
		cancionUno.addObserver(nivelUno);
		cancionDos.addObserver(nivelUno);
		try{
			nivelUno.asociarPuntaje(cancionUno, new Puntaje(cancionUno.size()));
			nivelUno.asociarPuntaje(cancionDos, new Puntaje(cancionDos.size()));
		}catch (NoExisteLaCancion e) {
//No entra en el catch porque cancion esta asociada al nivel
		}
		
	}

	/**Testea que una cancion pueda estar o no disponible*/
	public void testCancionDisponible(){
		
		try {
			assertTrue(nivelUno.cancionDisponible(cancionUno));
			assertFalse(nivelUno.cancionDisponible(cancionDos));
		} catch (NoExisteLaCancion e) {
//No entra en el catch porque cancion esta asociada al nivel	
		}
	}
	
	/**Testea que funcione bien el aumento de puntos*/
	public void testMovimientoCorrecto(){

		try{
			assertTrue(nivelUno.getPuntaje()==0);
			while(cancionUno.getValorMusicalLeyendose()!=Partitura.FIN_PARTITURA){
				cancionUno.avanzar(1);
				nivelUno.movimientoCorrectoEnCancion(cancionUno);
			}
			assertTrue(nivelUno.getPuntaje()> 0);
		}catch (NoExisteLaCancion e) {
//No entra en el catch porque cancion esta asociada al nivel	
		}
	}
	
	/**Testea que un nivel pueda o no ser completado*/
	public void testCompletado(){
		
		try{
			for(int i=0; i<cancionUno.size(); i++)
				nivelUno.movimientoCorrectoEnCancion(cancionUno);
			assertFalse(nivelUno.completado());
			cancionUno.reiniciar();//como son las dos la misma cancion, tengo que reiniciarla
			for(int i=0; i<cancionDos.size(); i++)
				nivelUno.movimientoCorrectoEnCancion(cancionDos);
			assertTrue(nivelUno.completado());
		}catch (NoExisteLaCancion e) {
//No entra en el catch porque cancion esta asociada al nivel		
		}
	}
	
	public void testExisteLaCancion() {

		try {
			nivelUno.getPuntajeDeCancion(new Cancion(null));
		    fail("Puedo extraer de una cuenta sin saldo.");
		}catch (NoExisteLaCancion expected) {
		    assertTrue(true);  
		  }
	}

	
}
