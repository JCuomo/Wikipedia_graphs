package modelo.notas.generico;

/**Una Nota representa la nota musical, soportando la nota muda que es el silencio.
 * Sirve para, junto con otros elementos, armar valores musicales y componer una partitura.
 * Se utiliza una única instancia.
 * Throw: CloneNotSupportedException si se intenta clonar.
 * Las clases derivadas deben ser implementadas con el patron Singleton o similar, impidiendo que haya
 * mas de una instancia de cada clase.
 */
public abstract class Nota {

	//ATRIBUTOS
	//CONSTRUCTOR
	/** no debe ser usado */
	protected Nota(){}
	
	//SETERS
	//GETERS
	/**Redefinir en las clases heredades.
	 * Debe devolver la única instancia de la clase
	 */
	public static synchronized Nota getNota(){

		return null;
	}
	
	//OTROS METODOS
	/**
	 * @return true/false dependiendo si la nota es muda o es de la escala musical
	 */
	public boolean esSilencio() {
		
		return false;
	}
	
	public Object clone() throws CloneNotSupportedException {
		
		throw new CloneNotSupportedException();
	}
}
