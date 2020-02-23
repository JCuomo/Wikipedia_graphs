package control;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import modelo.interfaces.KeyPressedObservador;
import modelo.notas.DO;
import modelo.notas.FA;
import modelo.notas.LA;
import modelo.notas.MI;
import modelo.notas.RE;
import modelo.notas.SI;
import modelo.notas.SOL;
import modelo.notas.generico.Nota;
import modelo.objetos.Diapazon;


public class EscuchadorDeKeyPress implements KeyPressedObservador {

	private Diapazon diapazon;

	ArrayList<Nota> Do_Si, Re_Fa, Mi_La, Sol;

	private Controlador controlador;
	
	public EscuchadorDeKeyPress(Controlador controlador ){
		this.diapazon = controlador.getDiapazon();
		this.controlador = controlador;
		Do_Si=new ArrayList<Nota>();
		Do_Si.add(DO.getNota());
		Do_Si.add(SI.getNota());
		Re_Fa=new ArrayList<Nota>();
		Re_Fa.add(RE.getNota());
		Re_Fa.add(FA.getNota());
		Mi_La=new ArrayList<Nota>();
		Mi_La.add(MI.getNota());
		Mi_La.add(LA.getNota());
		Sol=new ArrayList<Nota>();
		Sol.add(SOL.getNota());
	}
	public void keyPressed(KeyEvent event) {
		
		switch (event.getKeyCode()) {
		
			case KeyEvent.VK_Q:
				diapazon.tocar(Do_Si);
				break;
				
			case KeyEvent.VK_D:
				diapazon.tocar(Mi_La);
				break;
				
			case KeyEvent.VK_K:
				diapazon.tocar(Re_Fa);
				break;
			
			case KeyEvent.VK_P:
				diapazon.tocar(Sol);
				break;
				
			case KeyEvent.VK_F2:
			if(	controlador.getVentanaJuego()!=null)
				controlador.getVentanaJuego().acomodarPantalla();
				break;
				
			case KeyEvent.VK_F1:
				if(controlador.estaEnEjecucion())
					controlador.detener();
				else controlador.continuar();
				break;

			case KeyEvent.VK_ESCAPE:
				controlador.finalizarJuego();
				break;
						
			case KeyEvent.VK_T:
				Cronometro.printTime();
				break;
				
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
		switch (event.getKeyCode()) {
		
			case KeyEvent.VK_Q:
				diapazon.parar(Do_Si);
				break;
				
			case KeyEvent.VK_D:
				diapazon.parar(Mi_La);
				break;
				
			case KeyEvent.VK_K:
				diapazon.parar(Re_Fa);
				break;
			
			case KeyEvent.VK_P:
				diapazon.parar(Sol);
				break;
					
			default:
				break;
	}
		
	}
}

