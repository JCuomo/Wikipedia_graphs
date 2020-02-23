package control;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Reproductor implements Runnable{
	
	  boolean detenerEjecucion = false;
	  private File archivoMusica;
	  private boolean estaPausado;
	
	  public Reproductor(File archivo){
		  setCancion(archivo);
	  }
	  public void detener(){
		  detenerEjecucion=true;
	  }
	  public void pausar(){
		  estaPausado=true;
	  }
	  public void continuar(){
		  estaPausado=false;
	  }
	  public void setCancion(File nombre){
		  archivoMusica=nombre;
	  }
	
	  public void run() {
		  
		  try{
		  
			  byte tempBuffer[] = new byte[10000];
			  AudioInputStream flujoEntradaAudio= AudioSystem.getAudioInputStream(archivoMusica);
			  AudioFormat formatoAudio= flujoEntradaAudio.getFormat();
			  SourceDataLine sourceDataLine;	    
		      DataLine.Info dataLineInfo =new DataLine.Info(SourceDataLine.class,formatoAudio);
		      sourceDataLine =(SourceDataLine)AudioSystem.getLine(dataLineInfo);
		      sourceDataLine.open(formatoAudio);
		      int cnt; 
		      sourceDataLine.start();
		      while((cnt = flujoEntradaAudio.read(tempBuffer,0,tempBuffer.length)) != -1 && detenerEjecucion == false){
		    	  while(estaPausado) Thread.sleep(200);
		    	  if(cnt > 0){
			          sourceDataLine.write(tempBuffer, 0, cnt);
			          Cronometro.run();
		    	  }
		      }
		       
	
		      sourceDataLine.drain();
		      sourceDataLine.close();
		      detenerEjecucion = false;
		      
		    }catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
}