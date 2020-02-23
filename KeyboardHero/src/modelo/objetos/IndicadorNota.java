package modelo.objetos;

import control.Controlador;
import modelo.notas.*;
import modelo.notas.generico.Nota;
import modelo.objetos.notas.ObjetoDO;
import modelo.objetos.notas.ObjetoFA;
import modelo.objetos.notas.ObjetoLA;
import modelo.objetos.notas.ObjetoMI;
import modelo.objetos.notas.ObjetoRE;
import modelo.objetos.notas.ObjetoSI;
import modelo.objetos.notas.ObjetoSOL;
import modelo.objetos.notas.ObjetoSilencio;

public class IndicadorNota {
	
	static int posicionX=0;
	static int posicionY=0;
	

	public static ObjetoNota getObjeto(Diapazon diapazon, Nota unaNota, Controlador c){
		
		if(unaNota==DO.getNota())
			return new ObjetoDO(diapazon, unaNota, c);
		if(unaNota==RE.getNota())
			return new ObjetoRE(diapazon, unaNota, c);
		if(unaNota==MI.getNota())
			return new ObjetoMI(diapazon, unaNota, c);
		if(unaNota==FA.getNota())
			return new ObjetoFA(diapazon, unaNota, c);
		if(unaNota==SOL.getNota())
			return new ObjetoSOL(diapazon, unaNota, c);
		if(unaNota==LA.getNota())
			return new ObjetoLA(diapazon, unaNota, c);
		if(unaNota==SI.getNota())
			return new ObjetoSI(diapazon, unaNota, c);
		if(unaNota==Silencio.getNota())
			return new ObjetoSilencio(diapazon, unaNota, c);
		return null;
	}
	public static void setPosicionXY(int nuevaPosicionX, int nuevaPosicionY){
		
		posicionX=nuevaPosicionX;
		posicionY=nuevaPosicionY;
	}

}