package configuracion;

import java.util.LinkedList;
import java.util.List;

import modelo.Instrumento;
import modelo.KeyboardHero;
import modelo.Nivel;
import modelo.excepciones.NoExisteLaCancion;

public abstract class JuegoArmado {
	
	protected  KeyboardHero 	kHero;
	protected  Instrumento 	instrumento = new Instrumento();;
	protected  List<Nivel> 	niveles = new LinkedList<Nivel>();
	protected  boolean 		juegoCreado = false;
	
	protected void crearJuego(){
		
		if(this.juegoCreado==false){
			this.afinarInstrumento();
			this.armarNiveles();
			kHero=new KeyboardHero(niveles, instrumento);
			try {
				kHero.setCancion(niveles.get(0).getCanciones().get(0));
			} catch (NoExisteLaCancion e) {
				System.err.println("No existe la cancion que se trató de cargar en JuegoArmado");
				e.printStackTrace();
			}
			juegoCreado=true;
		}
	}
	
	protected void armarNiveles() {
	}

	protected void afinarInstrumento() {		
	}

	public KeyboardHero getJuego(){
		
		crearJuego();		
		return kHero;
	}
	
	public Instrumento getInstrumento(){
		
		crearJuego();		
		return instrumento;
	}
	
	public List<Nivel> getNiveles(){
		
		crearJuego();		
		return niveles;
	}


}
