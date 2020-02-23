package control;

public class Cronometro{
	
	private static final long DELTA = 0;
	private static long tiempo=0; 
	private static long tiempoDetencion=0;
	private static long tiempoDetenido=0;
	private static long tiempoContinuacion=0;

	   public static void run() {
		  if(tiempo==0)tiempo = System.currentTimeMillis();
	   }

	   public static void resetear(){
		  tiempo = 0;
		  tiempoDetencion = 0;
		  tiempoDetenido = 0; 
		  tiempoContinuacion = 0;
	   }
	   public static long getTiempo() {
	     
	      return (System.currentTimeMillis()-tiempo-tiempoDetenido);   
	   }
	   
	   public static boolean van(long i){
		   
		   return (getTiempo() >= (i-DELTA) && getTiempo() <= (i+DELTA));
	   }
	   
	   public static boolean pasaron(long i){
		   return getTiempo() >= i;
	   }
	   
	   public static void detener(){
		 tiempoDetencion=System.currentTimeMillis();
	   }
	   
	   public static void continuar(){
		 tiempoContinuacion=System.currentTimeMillis();
		 tiempoDetencion+=tiempoContinuacion-tiempoDetencion;
	   }

	   public static void printTime() {
		System.out.println(getTiempo());		
	   }
}
