package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota SI de la escala musical*/
public final class SI extends Nota {
	
	private static SI Si=new SI();

	/** no debe ser usado */
	private SI(){}
	
	public static synchronized Nota getNota() {
		
		return Si;
	}
	
}

