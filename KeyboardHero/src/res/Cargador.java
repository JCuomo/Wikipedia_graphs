package res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Cargador{  
	
	public static BufferedImage getImagen(String file){  
	
	BufferedImage imagen = null;   
	java.net.URL imageURL = Cargador.class.getResource(file);  
try{ imagen = ImageIO.read(imageURL); }catch(java.io.IOException e){} 
	return imagen; 
	}  
	
	public static String getRuta(String file){  
		String s=Cargador.class.getResource(file).toString();
		return s;
	}
	public static ImageIcon getImageIcon(String file){  
		return 	new ImageIcon(Cargador.class.getResource(file));
	}

	public static File getCancion(String string) {
	try {return new File(Cargador.class.getResource(string).toURI());
	} catch (URISyntaxException e) {}
	return null;
	}

}  


