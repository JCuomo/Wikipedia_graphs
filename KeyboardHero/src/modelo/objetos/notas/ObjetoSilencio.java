package modelo.objetos.notas;

import control.Controlador;
import vista.VistaNota;
import modelo.notas.DO;
import modelo.notas.generico.Nota;
import modelo.objetos.Diapazon;
import modelo.objetos.ObjetoNota;

public class ObjetoSilencio extends ObjetoNota {

	public ObjetoSilencio(Diapazon diapazon, Nota nota, Controlador c){
		
		this.diapazon=diapazon;
		this.x=this.diapazon.getCuerda(DO.getNota()).getX()+Diapazon.ANCHO_CUERDA/2-VistaNota.ANCHO_NOTA/2;
		this.y=this.diapazon.getCuerda(DO.getNota()).getY();
		this.controlador=c;
	}
	
	

}
