package test.modelo;


import modelo.Puntaje;
import junit.framework.TestCase;

public class PruebasPuntaje extends TestCase {
	
	private static final int CINCUENTA_PORCIENTO = 50;
	Puntaje puntos;
	private int cantidadDeMovimientos;
	
	protected void setUp(){
		cantidadDeMovimientos=230;
		puntos=new Puntaje(cantidadDeMovimientos);
	}

	/**Testea que se puedan sumar los puntos correctamente, y que sea acorde al porcentaje de movimientos correctos*/
	public void testSumarPuntos(){
		
		for(int i=0; i<cantidadDeMovimientos/2; i++) puntos.sumarPuntos();
		float puntosAcumulados=puntos.getPuntaje();
		
		assertTrue(puntosAcumulados < CINCUENTA_PORCIENTO+1 && puntosAcumulados > CINCUENTA_PORCIENTO-1);
	}


}
