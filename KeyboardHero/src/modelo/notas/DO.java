package modelo.notas;

import modelo.notas.generico.Nota;


/**Clase de instancia única, representa la nota DO de la escala musical*/
public final class DO extends Nota {
	
	private static DO Do=new DO();

	/** no debe ser usado */
	private DO(){}
	
	public static synchronized Nota getNota() {
		
		return Do;
	}
	
}
