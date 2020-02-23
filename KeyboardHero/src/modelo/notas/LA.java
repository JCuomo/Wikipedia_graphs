package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa la nota LA de la escala musical*/
public final class LA extends Nota {
	
	private static LA La=new LA();
	
	/** no debe ser usado */
	private LA(){}
	
	public static synchronized Nota getNota() {
		
		return La;
	}
	
}
