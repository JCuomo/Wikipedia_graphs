package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota FA de la escala musical*/
public final class FA extends Nota {
	
	private static FA Fa=new FA();

	/** no debe ser usado */
	private FA(){}
	
	public static synchronized Nota getNota() {
		
		return Fa;
	}
	
}
