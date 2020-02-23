package vista;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import modelo.interfaces.SuperficieDeDibujo;
import modelo.notas.generico.Nota;
import modelo.objetos.ObjetoNota;

	public class VistaNota  extends Figura {
	
		private static final int RADIO_NOTA = 30;

		public static final int ANCHO_NOTA = RADIO_NOTA;
		public static final int ALTO_NOTA = RADIO_NOTA;
		private static final int ANCHO_COLA = RADIO_NOTA/2;
		private static final int ARCO_COLA = 30;
		private String nombreNota;
		private int largoCola;
		
		public VistaNota(Nota unaNota, double duracionNota){
			this.largoCola=(int)(duracionNota*ObjetoNota.VELOCIDAD_AVANCE);
			nombreNota=unaNota.getClass().getCanonicalName().replaceAll("modelo.notas.", "");
			setColor(Color.RED);
		}
		
		public VistaNota() {
			
		}

		public void dibujar(SuperficieDeDibujo superfice) {
		if(	((ObjetoNota)this.getPosicionable()).estaEjecutandose() )
			largoCola-=ObjetoNota.VELOCIDAD_AVANCE;

			if(nombreNota.matches("Silencio")) return;
			Graphics grafico = (Graphics)superfice.getBuffer();
			grafico.setColor(getColor());
			grafico.fillOval(getPosicionable().getX() , getPosicionable().getY(), RADIO_NOTA, RADIO_NOTA);
			grafico.fillRoundRect(getPosicionable().getX()+RADIO_NOTA/2-ANCHO_COLA/2 , getPosicionable().getY()-largoCola+RADIO_NOTA/2, ANCHO_COLA, largoCola, ARCO_COLA, ARCO_COLA);
			grafico.setColor(Color.BLACK);
			grafico.setFont(new Font("Arial", Font.BOLD, 15));
			grafico.drawString(nombreNota, getPosicionable().getX()+2, getPosicionable().getY()+RADIO_NOTA/2+7);
		}
	
	}
