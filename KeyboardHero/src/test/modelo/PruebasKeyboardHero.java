package test.modelo;

import junit.framework.TestCase;
import modelo.Duracion;
import modelo.Instrumento;
import modelo.KeyboardHero;
import modelo.ValorMusical;
import modelo.Puntaje.Resultado;
import modelo.notas.RE;

public class PruebasKeyboardHero extends TestCase {
	
	KeyboardHero juego;
	Instrumento guitarra;
	
	protected void setUp(){
		
		JuegoDePrueba j=new JuegoDePrueba();
		
		juego=j.getJuego();
		guitarra=j.getGuitarra();
	}
	/**Testea que una cancion pueda ser interpretada correctamente*/
	public void testCancionBienInterpretada(){
		//miro cual es la 1° nota		
		ValorMusical valorMusical=juego.getValorMusicalLeyendose();

		while(!juego.terminoCancion()){
			
			if(valorMusical.estaSonando()){//si deberia tocar la nota...				
				guitarra.tocar(valorMusical.getNotas());//toco la nota
			}
			else{//si no deberia tocar la nota
				guitarra.parar(valorMusical.getNotas());//paro de tocar
				valorMusical=juego.getValorMusicalLeyendose();	//miro cual es la nota que viene
				if(!juego.terminoCancion()) guitarra.tocar(valorMusical.getNotas());//toco la nota
			}
			juego.run(1);
		}
		assertEquals(Resultado.GANASTE, juego.getResultadoCancion());
		assertEquals(Resultado.GANASTE, juego.getResultado());
		}
	
	/** Testea que una cancion pueda ser interpretada incorrectamente*/
	public void testCancionMalInterpretada(){
//se toca siempre Re
		ValorMusical valorMusical=juego.getValorMusicalLeyendose();
		ValorMusical Re=new ValorMusical(new Duracion(Duracion.NEGRA), RE.getNota());

		while(!juego.terminoCancion()){
			
			if(valorMusical.estaSonando()){//si deberia tocar la nota...				
				guitarra.tocar(Re.getNotas());//toco la nota
			}
			else{//si no deberia tocar la nota
				guitarra.parar();//paro de tocar
				valorMusical=juego.getValorMusicalLeyendose();	//miro cual es la nota que viene
				if(!juego.terminoCancion()) guitarra.tocar(Re.getNotas());//toco la nota
			}
			juego.run(1);
		}
		assertEquals(Resultado.PERDISTE, juego.getResultadoCancion());
		assertEquals(Resultado.FALLASTE, juego.getResultado());//el juego no se pierde, se gana o esta inconcluso
		}
}	
