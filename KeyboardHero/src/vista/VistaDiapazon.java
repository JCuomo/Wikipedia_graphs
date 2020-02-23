package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modelo.interfaces.Dibujable;
import modelo.interfaces.Posicionable;
import modelo.interfaces.SuperficieDeDibujo;
import modelo.objetos.Cuerda;
import modelo.objetos.Diapazon;


public class VistaDiapazon extends Figura implements Dibujable{
	
	private static final int ANCHO_LINEA_EJECUCION = 2;
	private Posicionable posicionable;
	private Diapazon diapazon;
	List<VistaCuerda> vistaCuerdas = new ArrayList<VistaCuerda>();
	private boolean vistasCuerdasCreadas=false;
	
	public VistaDiapazon(){	}
	
	public void dibujar(SuperficieDeDibujo superficeDeDibujo) {
		
		Graphics grafico = (Graphics)superficeDeDibujo.getBuffer();
		grafico.setColor(getColor());
		if(vistasCuerdasCreadas==false){
			for (int i = 0; i < diapazon.getCuerdas().size(); i++) 	vistaCuerdas.add(new VistaCuerda(0, 0));
			vistasCuerdasCreadas=true;
		}
		Iterator<Cuerda> it_cuerdas=diapazon.getCuerdas().iterator();
		Iterator<VistaCuerda> it_vistasCuerdas=vistaCuerdas.iterator();
		
		while (it_cuerdas.hasNext()) {
			VistaCuerda unaVistaCuerda=it_vistasCuerdas.next();
			unaVistaCuerda.setSize(Diapazon.ANCHO_CUERDA, Diapazon.ALTO_CUERDA);

			unaVistaCuerda.setPosicionable(it_cuerdas.next());
			unaVistaCuerda.dibujar(superficeDeDibujo);
		}
		setColor(Color.WHITE);
		grafico.fillRect(this.getPosicionable().getX(), this.getPosicionable().getY()+Diapazon.ALTO_DIAPAZON-VistaNota.ALTO_NOTA-ANCHO_LINEA_EJECUCION, Diapazon.ANCHO_DIAPAZON, ANCHO_LINEA_EJECUCION);

	}

	public Posicionable getPosicionable() {
		return posicionable;
	}

	public void setPosicionable(Posicionable posicionable) {
		this.posicionable = posicionable;
		this.diapazon=(Diapazon)posicionable;
	}
}
