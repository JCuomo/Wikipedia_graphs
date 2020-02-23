package modelo;
import modelo.persistencia.Partida;

public class Jugador {

	private String nombre;
	private Partida partida;
	
	public Jugador(String nombre, Partida partida) {
		this.nombre = nombre;
		this.partida = partida;
	}
	
	public void jugar(KeyboardHero unKeyboardHero){
		//TODO
		//Como hago para que el jugador haga jugar?
		//El keyboarhero me pide un int para hacer run
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
	
	
}
