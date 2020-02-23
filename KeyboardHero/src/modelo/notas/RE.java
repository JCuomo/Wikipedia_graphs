package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota RE de la escala musical*/
public final class RE extends Nota {
	
	private static RE Re=new RE();

	/** no debe ser usado */
	private RE(){}
	
	public static synchronized Nota getNota() {
		
		return Re;
	}
	
}
