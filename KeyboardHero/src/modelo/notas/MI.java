package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota MI de la escala musical*/
public final class MI extends Nota {
	
	private static MI Mi=new MI();

	/** no debe ser usado */
	private MI(){}
	
	public static synchronized Nota getNota() {
		
		return Mi;
	}
	
}
