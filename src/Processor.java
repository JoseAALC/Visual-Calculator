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
		int verde, vermelho, azul;
		
		int x=0;
		// Ciclo que percorre a imagem inteira
		for (int i=0; i<sizey;i++){
			for(int j=0; j<sizex; j++){
				vermelho = getRed(matrix[x]);
				verde = getGreen(matrix[x]);
				azul = getBlue(matrix[x]);
				
				if(vermelho>=70 && verde>=50 && azul>=70){
					matrix[x] = makeColor(255,255,255);
					binaryMatrix[i][j]=0;
				}
				else{
					matrix[x] = makeColor(0,0,0);
					binaryMatrix[i][j]=1;
				}
				x++;	
			}
		}
		
		
		return binaryMatrix;
	
		
	}
	
	private int getRed(int color) { return (color >> 16) & 0xff; }
	private int getGreen(int color) { return (color >> 8) & 0xff; }
	private int getBlue(int color) { return color & 0xff; }
	private int makeColor(int red, int green, int blue) { return (255 << 24) | (red << 16) | (green << 8) | blue; }
	
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
