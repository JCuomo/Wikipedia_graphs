package modelo.persistencia;

import modelo.Nivel;
import modelo.Puntaje;

public class Partida {
	
	private Nivel nivel;
	private Puntaje puntaje;
	
	public Partida(Nivel unNivel, Puntaje unPuntaje){
		this.nivel = unNivel;
		this.puntaje = unPuntaje;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Puntaje getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Puntaje puntaje) {
		this.puntaje = puntaje;
	}
	
	public void guardarPartida(){
		//TODO
		//Usar persistencia
	}
}
