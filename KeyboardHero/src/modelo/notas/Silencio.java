package modelo.notas;

import modelo.notas.generico.Nota;

/**Clase de instancia única, representa una nota muda*/
public final class Silencio extends Nota {

	private static Silencio silencio=new Silencio();

	private Silencio(){}
	
	public static synchronized Nota getNota() {
		
		return silencio;
	}
	
	public boolean esSilencio() {
		
		return true;
	}
}
