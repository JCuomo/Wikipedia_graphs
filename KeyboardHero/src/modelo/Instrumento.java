package modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import modelo.notas.Silencio;
import modelo.notas.generico.Nota;


/**
 * Un instrumento tiene un/os sistema/s resonante/s asociados.
 * Un instrumento puede tocar y dejar de tocar las notas que tenga asociadas.
 */
public class Instrumento {
	
	//ATRIBUTOS
	/** sistemas resonantes asociados*/
//	private HashMap<Nota,SistemaResonante> sistemasResonantes;
private List<SistemaResonante> sistemasResonantes;
	//CONSTRUCTORES
	/** crea un instrumento sin sistemas resonantes*/
	public Instrumento(){
		
		sistemasResonantes= new ArrayList<SistemaResonante>();//new HashMap<Nota, SistemaResonante>(); 
	}

	//SETERS
	
	/**
	 * @param unaNota sera la nota que se asociara al sistema resonante
	 * @param sistemaResonante es el sistema resonante que asociara unaNota
	 */
	public void asociarNotaASistemaResonante(Nota unaNota, SistemaResonante sistemaResonante ){
		sistemaResonante.agregarNota(unaNota);
		if(sistemasResonantes.contains(sistemaResonante)) return;
		sistemasResonantes.add(sistemaResonante);
		//sistemasResonantes.put(unaNota, sistemaResonante);
	}
	
	//GETERS
	/**
	 * @param nota es la nota de la cual se quiere saber el sistema resonante
	 * @return el sistema resonante asociado a la nota
	 */
	public SistemaResonante getSistemaResonante(Nota nota) {
		Iterator<SistemaResonante> it=sistemasResonantes.iterator();
		while (it.hasNext()) {
			SistemaResonante sr=it.next();
				if(sr.tieneLaNota(nota)) return sr;
		}
		return null;
			//return sistemasResonantes.get(nota);
	}
	
	/**
	 * @return los sistemas resonantes en un mapa donde las claves son las notas que estan
	 * asociadas a diferentes sistemas resonantes
	 */
	public /*HashMap<Nota, SistemaResonante>*/ List<SistemaResonante> getSistemasResonantes() {
	
		return sistemasResonantes;
	}
	
	//OTROS METODOS	
	/**
	 * Ejecuta los sistemas resonantes que estan asociados a las notas
	 * @param notas son las notas que se tocaran. Si es un silencio no se hace nada.
	 * Prevalece un silencio por sobre el resto de las notas, i.e. no se hace nada si hay un silencio,
	 * aunque no debería pasar que haya notas mezcladas con silencios.
	 */
	public void tocar(Collection<Nota> notas){
			
		Iterator<Nota> it=notas.iterator();
		if(notas.contains(Silencio.getNota())) return;
		while (it.hasNext()){
			Nota nota=it.next();
			for(int i=0; i<sistemasResonantes.size(); i++){
				SistemaResonante st=sistemasResonantes.get(i);
				if(st.tieneLaNota(nota)){
					st.ejecutar();
					break;
				}
			}
		}	
			//sistemasResonantes.get(it.next()).ejecutar();
	}	
	
	/** Deja de ejecutar todos los sistemas resonantes */
	public void parar() {
			
			Iterator<SistemaResonante> it=sistemasResonantes.iterator();
			while (it.hasNext()) it.next().detenerEjecucion();
		}

	/**
	 * detiene la ejecucion del sistema resonante asociado a las notas, 
	 * lo que implica la detencion de la ejecucion de las demas notas asociadas a esos sistemas resonantes.
	 * @param notas son las que se dejaran de tocar. Si al menos una es un silencio, no se hace nada.
	 * Evitar mezclar notas de la escala musical con silencio, esta conceptualmente mal.
	 */
	public void parar(List<Nota> notas) {

		if(notas.contains(Silencio.getNota())) return;
		Iterator<Nota> it=notas.iterator();
		while (it.hasNext())
			getSistemaResonante(it.next()).detenerEjecucion();
			//sistemasResonantes.get(it.next()).detenerEjecucion();
	}

	/**
	 * @param unaNota es la que uno quisiera saber si se esta tocando o no.
	 * @return true/false dependiendo si el sistema resonante asociado a unaNota se esta ejecutando
	 */
	public boolean estaTocandose(Nota unaNota){
//sirve solo para el test unitario
		if(unaNota.esSilencio()){//si es un silencio...
			Iterator<SistemaResonante> it=sistemasResonantes.iterator();
			while(it.hasNext()){//si se esta ejecutando alguna nota devuelve false
				if(it.next().estaEjecutandose()) return false;
			}
			return true;//si no se esta ejecutando ninguna devuelve true
		}
		return  this.getSistemaResonante(unaNota).estaEjecutandose();
	}
	
	/**
	 * @param notas son las que uno quisiera saber si se estan tocando o no.
	 * @return true/false dependiendo si los sistemas resonantes asociados a notas se estas ejecutando.
	 */
	public boolean estaEjecutandose(Collection<Nota> notas) {

		Iterator<Nota> it=notas.iterator();
		while (it.hasNext()) {
			if(!this.estaTocandose(it.next()))//si al menos una no se esta ejecutando..
				return false;
		}
		return true;//si todas las notas se estan ejecutando devuelve true
	}

	public int cantidaSistemasResonantes() {
		return sistemasResonantes.size();
		//return ((HashSet)sistemasResonantes.values()).size();
	}
	
}
		 
		
		