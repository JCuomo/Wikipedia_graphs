package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import modelo.notas.generico.Nota;

/**
 * Un sistema resonante representa aquello que permite hacer sonar notas en un instrumento.
 * Se le pueden asociar notas.
 * Se puede ejecutar y detener la ejecucion.
 * Es componente esencial de un instrumento.
 *
 */
public class SistemaResonante {
	
	//ATRIBUTOS
	/**Define si el sistema resonante se esta ejecutando o no*/
	private boolean estado;
	/**Son las notas que el sistema resonante puede hacer sonar*/
	private ArrayList<Nota> notasAsociadas;
	
	//CONSTRUCTORES
	/** Crea un sistema resonante sin notas asociadas */
	public SistemaResonante(){
	
		this.estado=false;
		this.notasAsociadas=new ArrayList<Nota>();
	}

	/**
	 * Crea un sistema resonante con notas
	 * @param notas son aquellas a las cuales el sistema resonante va a hacer sonar cuando se ejecute
	 */
	public SistemaResonante(Collection<Nota> notas){
		
		this();
		Iterator<Nota> it=notas.iterator();
		while (it.hasNext()) {
			this.agregarNota(it.next());
		}
	}

	//SETERS
	public void agregarNota(Nota unaNota){
		
		notasAsociadas.add(unaNota);	
	}

	//GETERS
	//OTROS METODOS
	public void ejecutar(){
		
		estado=true;
	}

	public void detenerEjecucion(){
		
		estado=false;
	}
	
	public boolean estaEjecutandose(){
		
		return estado;
	}
	
	/**
	 * @param unaNota
	 * @return true/false dependiendo de si el sistema resonante tiene unaNota asociada
	 */
	public boolean tieneLaNota(Nota unaNota){
	//se usa unicamente para el test unitario	
		Iterator<Nota> it=notasAsociadas.iterator();
		
		while(it.hasNext()){
			if(it.next()==unaNota){
				return true;
			}
		}
		return false;
	}

	public List<Nota> getNotas() {
		// TODO Auto-generated method stub
		return notasAsociadas;
	}
}
