import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

public class Processor {

	public Processor() {};
	
	public int[][] Segment(Image original) {
		//get Image Atributes
		int sizex = original.getWidth(null);
		int sizey = original.getHeight(null);
		
		int matrix[] = new int[sizex*sizey];
		int binaryMatrix[][] = new int[sizey][sizex];
		PixelGrabber pg = new PixelGrabber(original, 0, 0, sizex, sizey, matrix, 0, sizex);
		
		
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return null;
		}
		
		
		//vamos comecar
		
		
		
		
		
		
		return null;
	
		
	}
	
	
	public ArrayList<Blob> floodFill(int segmentedImage[][]) {
		int componentCount=0;
		int componentMarker=2;
		
		for(int i=0; i<segmentedImage.length; i++)
			for(int j=0; j<segmentedImage[i].length; j++){
				
				if(segmentedImage[i][j]==1){ //encontrou um, agora faz bloodfill
					
				}
			}
		
		return null;
		
	}
	
	
}
