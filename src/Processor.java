import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.Collections;

public class Processor {

	public Processor() {};
	
	
	
	public SegmentedFile Segment(Image original) {
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
		
		
		
		return new SegmentedFile(binaryMatrix,matrix);
	
		
	}
	
	//maybe using median filter
	public Image cleanNoise(Image original) {
		return original;
		
	}
	//maybe using nearest-neighbor algorithm
	public Image resize(Image original) {
		return original;
		
	}
	/**
	 * 
	 * @param binaryImage
	 * @return a list of Blobs finded on the floodFill method
	 */
	public ArrayList<Blob> floodFill(int binaryImage[][]) {
		int componentCount=0;
		int componentMarker=2;
		ArrayList<Blob> blobList= new ArrayList<Blob>();
		for(int i=0; i<binaryImage.length; i++)
			for(int j=0; j<binaryImage[i].length; j++){
				
				if(binaryImage[i][j]==1){ //encontrou um, agora faz floodfill
					
					Bounds bounds =dfsMarker(binaryImage,i,j,1,componentMarker);
					
					int blobMatrix[][] = copyMatrix(bounds, binaryImage);
					Blob blob = new Blob(blobMatrix,bounds,componentMarker);
					componentMarker++;
					componentCount++; // why are you interested in the number of components?
					blobList.add(blob);
				}
			}
		
		return removeFalseBlobs(blobList);
		
	}
	
	/**
	 * removes the Blobs that are noised confused with symbols
	 * @param listOfBlobs
	 * @return Blob list free of noise
	 */
	private ArrayList<Blob> removeFalseBlobs(ArrayList<Blob> listOfBlobs){
		//My idea is that, the bigger difference between sizes of blobs corresponds to the break point
		//between blobs and noises.
		//this is only an idea I can be wrong, right?
		Collections.sort(listOfBlobs);
		int maxDifference = -1;
		int maxDifferenceIndex = -1;
		for(int i=0;i<listOfBlobs.size()-1;i++) {
			int difference = listOfBlobs.get(i+1).getSize() - listOfBlobs.get(i).getSize();
			if(maxDifference < difference) {
				maxDifference = difference;
				maxDifferenceIndex = i+1;
			}
		
		}
			
		
		return (ArrayList<Blob>)listOfBlobs.subList(maxDifferenceIndex, listOfBlobs.size());
		
	}
	
	/**
	 * method to get the matrix to copy to the blob
	 * @param bounds
	 * @param matrix
	 * @return
	 */
	private int[][] copyMatrix(Bounds bounds,int matrix[][]) {
		int blobMatrix [][] = new int[bounds.getRightBond()][bounds.getLowerBond()] ;
		for(int i =bounds.getLeftBond(); i<=bounds.getRightBond();i++) 
			for(int j=bounds.getUpperBond();j<=bounds.getLowerBond();j++)
				blobMatrix[i][j] = matrix[i][j];
		return blobMatrix;
	}
	
	/**
	 * DFS responsible by the floodFill of each component
	 * @param binaryImage the matrix with binary values
	 * @param x the actual coordinate in x axis to analise
	 * @param y the actual coordinate on y's axis to analise
	 * @param conditionMarker number must be equal to this to change to marker
	 * @param marker that will mark the numbers that must change
	 */ 
	private Bounds dfsMarker(int binaryImage[][],int x, int y,int conditionMarker,int marker) {
		//exceed matrix check
		if(x >= binaryImage.length || x <0 || y >= binaryImage[0].length || y<0)
			return new Bounds( -1,binaryImage[0].length,binaryImage[0].length,-1,0);
		//If the bit must me marked
		if(binaryImage[x][y]==conditionMarker) {
			binaryImage[x][y] = marker;
			
			
			
			Bounds b1,b2,b3,b4,b5,b6,b7,b8;
			//eight directions cheking 1's
			b1 =dfsMarker(binaryImage,x-1,y,conditionMarker,marker);
			b2 = dfsMarker(binaryImage,x+1,y,conditionMarker,marker);
			b3 =dfsMarker(binaryImage,x,y+1,conditionMarker,marker);
			b4 =dfsMarker(binaryImage,x,y-1,conditionMarker,marker);
			//diagonals
			//b5=dfsMarker(binaryImage,x-1,y+1,conditionMarker,marker);
			//b6=dfsMarker(binaryImage,x+1,y+1,conditionMarker,marker);
			//b7=dfsMarker(binaryImage,x-1,y-1,conditionMarker,marker);
			//b8=dfsMarker(binaryImage,x+1,y-1,conditionMarker,marker);
			
			int size =1 +b1.getSize() + b2.getSize() + b3.getSize() + b4.getSize();
			int lb = Math.max(b1.getLowerBond(),Math.max(b2.getLowerBond(),Math.max(b3.getLowerBond(),Math.max(b4.getLowerBond(),y))));
			int ub = Math.min(b1.getUpperBond(), Math.min(b2.getUpperBond(),Math.min(b3.getUpperBond(),Math.min(b4.getUpperBond(),y))));
			int leb = Math.min(b1.getLeftBond(), Math.min(b2.getLeftBond(), Math.min(b3.getLeftBond(), Math.min(b4.getLeftBond(), x))));
			int rb = Math.max(b1.getRightBond(), Math.max(b2.getRightBond(), Math.max(b3.getRightBond(), Math.max(b4.getRightBond(), x))));
			
			return new Bounds(lb, ub, leb, rb,size);
				
					
		}
		return new Bounds( -1,binaryImage[0].length,binaryImage[0].length,-1,0);
	}
	
	
	
	private int getRed(int color) { return (color >> 16) & 0xff; }
	private int getGreen(int color) { return (color >> 8) & 0xff; }
	private int getBlue(int color) { return color & 0xff; }
	private int makeColor(int red, int green, int blue) { return (255 << 24) | (red << 16) | (green << 8) | blue; }
}
