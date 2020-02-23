package test.modelo;

import java.util.LinkedList;

import modelo.Cancion;
import modelo.Dificultad;
import modelo.Instrumento;
import modelo.KeyboardHero;
import modelo.Nivel;
import modelo.SistemaResonante;
import modelo.Nivel.NumeroNivel;
import modelo.excepciones.NoExisteLaCancion;
import modelo.notas.DO;
import modelo.notas.FA;
import modelo.notas.LA;
import modelo.notas.MI;
import modelo.notas.RE;
import modelo.notas.SI;
import modelo.notas.SOL;
import modelo.notas.generico.Nota;

public class JuegoDePrueba {
	
	KeyboardHero juego;
	Nivel nivelUno;
	Instrumento guitarra;
	Dificultad media;
	Cancion cancion;
	Nota Do, Re, Mi, Fa, Sol, La, Si;
	SistemaResonante cuerda1, cuerda2, cuerda3;
	
	public JuegoDePrueba(){ 

		Do=DO.getNota();
		Re=RE.getNota();
		Mi=MI.getNota();
		Fa=FA.getNota();
		Sol=SOL.getNota();
		La=LA.getNota();
		Si=SI.getNota();
		
		cuerda1=new SistemaResonante();
		cuerda2=new SistemaResonante();
		cuerda3=new SistemaResonante();		
		
		guitarra=new Instrumento();
		
		guitarra.asociarNotaASistemaResonante( Do , 	cuerda1);
		guitarra.asociarNotaASistemaResonante( Re , 	cuerda2);
		guitarra.asociarNotaASistemaResonante( Mi , 	cuerda3);
		guitarra.asociarNotaASistemaResonante( Fa ,		cuerda1);
		guitarra.asociarNotaASistemaResonante( Sol,		cuerda2);
		guitarra.asociarNotaASistemaResonante( La , 	cuerda3);
		guitarra.asociarNotaASistemaResonante( Si , 	cuerda1);

		cancion=(new CancionDePrueba()).getCancion();
		LinkedList<Cancion> cancionesDelNivelUno= new LinkedList<Cancion>();
		cancionesDelNivelUno.add(cancion);
		nivelUno=new Nivel(NumeroNivel.NIVEL_UNO, cancionesDelNivelUno);
		LinkedList<Nivel> niveles=new LinkedList<Nivel>();
		niveles.add(nivelUno);
		
		media=new Dificultad(Dificultad.NORMAL);
		
		try {
			juego=new KeyboardHero(niveles, cancion, guitarra, media);
		} catch (NoExisteLaCancion e) {
//No entra en el catch porque cancion esta asociada
		}	
		
		cancion.addObserver(nivelUno);
		cancion.addObserver(media);
		
	}
	public KeyboardHero getJuego(){
		
		return juego;

}

	public Instrumento getGuitarra() {
		return guitarra;
	}}
