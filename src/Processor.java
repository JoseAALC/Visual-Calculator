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
	
	public ArrayList<Blob> floodFill(int binaryImage[][]) {
		int componentCount=0;
		int componentMarker=2;
		
		for(int i=0; i<binaryImage.length; i++)
			for(int j=0; j<binaryImage[i].length; j++){
				
				if(binaryImage[i][j]==1){ //encontrou um, agora faz floodfill
					dfsMarker(binaryImage,i,j,1,componentMarker);
					componentMarker++;
					componentCount++; // why are you interested in the number of components?
				}
			}
		
		//we will still need to save each blob
		
		return null;
		
	}
	
	/**
	 * DFS responsible by the floodFill of each component
	 * @param binaryImage the matrix with binary values
	 * @param x the actual coordinate in x axis to analise
	 * @param y the actual coordinate on y's axis to analise
	 * @param conditionMarker number must be equal to this to change to marker
	 * @param marker that will mark the numbers that must change
	 */
	private void dfsMarker(int binaryImage[][],int x, int y,int conditionMarker,int marker) {
		//exceed matrix check
		if(x >= binaryImage.length || x <0 || y >= binaryImage[0].length || y<0)
			return;
		//If the bit must me marked
		if(binaryImage[x][y]==conditionMarker) {
			binaryImage[x][y] = marker;
			//eight directions cheking 1's
			dfsMarker(binaryImage,x-1,y,conditionMarker,marker);
			dfsMarker(binaryImage,x+1,y,conditionMarker,marker);
			dfsMarker(binaryImage,x,y+1,conditionMarker,marker);
			dfsMarker(binaryImage,x,y-1,conditionMarker,marker);
			//diagonals
			dfsMarker(binaryImage,x-1,y+1,conditionMarker,marker);
			dfsMarker(binaryImage,x+1,y+1,conditionMarker,marker);
			dfsMarker(binaryImage,x-1,y-1,conditionMarker,marker);
			dfsMarker(binaryImage,x+1,y-1,conditionMarker,marker);
		}
	}
	
	
}
