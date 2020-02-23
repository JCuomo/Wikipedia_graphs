package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota SOL de la escala musical*/
public final class SOL extends Nota {
	
	private static SOL Re=new SOL();

	/** no debe ser usado */
	private SOL(){}
	
	public static synchronized Nota getNota() {
		
		return Re;
	}
	
}
