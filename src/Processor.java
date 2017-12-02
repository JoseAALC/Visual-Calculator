import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Processor extends RGBProcessor {

	public Processor() {};
	
	
	/**
	 * Generates an binary matrix using an image
	 * @param original, image used to generate the binary matrix
	 * @return an segmetedFile with a binary matrix and an imagematrix
	 */
	public SegmentedFile segment(Image original) {
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
				
				if(vermelho>=50 && verde>=100  && azul>=100){
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
	
	//maybe using nearest-neighbor algorithm
	public int[] resize(int image[][],int mat[],double sf,double sf2) {
		
		
		int scaledWidth = (int)(sf*image[0].length);
		int scaledHeight = (int)(sf2*image.length);
		int matrix[] = new int[scaledWidth*scaledHeight];
		for (int i = 0; i < scaledHeight; i++) {
			 for (int j = 0; j < scaledWidth; j++) {
				 int y = (int) (Math.min( Math.round(i / sf2), image.length )*image[0].length);
				 int x = (int) Math.min( Math.round(j / sf), image[0].length );
				 matrix[(int)((scaledWidth * i) + j)] =mat[(y + x)];
			 }
		}
		return matrix;
		
	}
	/**
	 * 
	 * @param binaryImage
	 * @return a list of Blobs finded on the floodFill method
	 */
	public ArrayList<Blob> floodFill(int binaryImage[][]) {
		int componentMarker=2;
		ArrayList<Blob> blobList= new ArrayList<Blob>();
		for(int i=0; i<binaryImage.length; i++)
			for(int j=0; j<binaryImage[i].length; j++){
				
				if(binaryImage[i][j]==1){ //encontrou um, agora faz floodfill
					
					Bounds bounds =dfsMarker(binaryImage,j,i,1,componentMarker);
					
					int blobMatrix[][] = copyMatrix(bounds, binaryImage);
					Blob blob = new Blob(blobMatrix,bounds,componentMarker);
					componentMarker++;
					blobList.add(blob);
				}
			}
		
		return (blobList);
		
	}
	
	/**
	 * removes the Blobs that are noised confused with symbols
	 * @param listOfBlobs
	 * @return Blob list free of noise
	 */
	public ArrayList<Blob> removeFalseBlobs(ArrayList<Blob> listOfBlobs){

		Collections.sort(listOfBlobs);
		int index = -1;
		for(int i=0;i<listOfBlobs.size()-1;i++) {
			if(listOfBlobs.get(i+1).getSize()*(1.0)/4 > listOfBlobs.get(i).getSize()) {
				index = i+1;
			}
		
		}
		
		ArrayList<Blob> result = new ArrayList<Blob>();
		for(int i =Math.max(index,0);i<listOfBlobs.size();i++)
			result.add(listOfBlobs.get(i));
			
		
		return result;
		
	}
	
	/**
	 * method to get the matrix to copy to the blob
	 * @param bounds
	 * @param matrix
	 * @return
	 */
	private int[][] copyMatrix(Bounds bounds,int matrix[][]) {
	
		int blobMatrix [][] = new int[bounds.getLowerBond() - bounds.getUpperBond()+1][bounds.getRightBond()-bounds.getLeftBond()+1];

		for(int i =bounds.getUpperBond(); i<=bounds.getLowerBond();i++) 
			for(int j=bounds.getLeftBond();j<=bounds.getRightBond();j++) {

				blobMatrix[i-bounds.getUpperBond()][j-bounds.getLeftBond()] = matrix[i][j];
			}
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
		
		LinkedList<Point> points = new LinkedList<Point>();
		
		points.addFirst(new Point(x,y));
		int size=0,lb= -1 ,leb = binaryImage[0].length,ub = binaryImage.length,rb= -1;
		while(!points.isEmpty()) {
			Point p = points.removeFirst();
			x = p.getX();
			y = p.getY();
		
		
		
		
			if(x >= binaryImage[0].length || x <0 || y >= binaryImage.length || y<0){
				continue;
			}
			//If the bit must me marked
			if(binaryImage[y][x]==conditionMarker) {
				binaryImage[y][x] = marker;
			
		
				//four directions cheking 1's
				points.addFirst(new Point(x-1,y));
				points.addFirst(new Point(x+1,y));
				points.addFirst(new Point(x,y+1));
				points.addFirst(new Point(x,y-1));

				size +=1;
				lb = Math.max(lb,y);
				ub = Math.min(ub,y);
				leb = Math.min(leb,x);
				rb = Math.max(rb,x);
		
			
				
					
			}
		}
		
			return new Bounds(lb, ub, leb, rb,size);
	}
	
	
	public SegmentedFile eliminateLateraShadow(SegmentedFile seg) {
		int binaryImage[][] = seg.getBinaryMatrix();
		int matrix[] = seg.getRepresentation();
		for(int i=0;i<binaryImage.length;i++) {
			if(binaryImage[i][0]==1)
				dfsMarker(binaryImage, 0, i, 1, 0);
		}
		
		for(int i=0;i<binaryImage.length;i++) {
			if(binaryImage[i][binaryImage[0].length-1]==1)
				dfsMarker(binaryImage, 0, i, 1, 0);
		}
		
		for(int i=0;i<binaryImage[0].length;i++) {
			if(binaryImage[0][i]==1)
				dfsMarker(binaryImage, i, 0, 1, 0);
		}
		
		for(int i=0;i<binaryImage[0].length;i++) {
			if(binaryImage[binaryImage.length-1][i]==1)
				dfsMarker(binaryImage, i, 0, 1, 0);
		}
		
		for(int j=0; j<binaryImage[0].length;j++) {
			for(int i=0;i<binaryImage.length;i++) {

				matrix[j + i*(binaryImage[0].length)] = ((binaryImage[i][j]==0) ? makeColor(255, 255, 255):makeColor(0, 0, 0));
			}
			
		}
		
		return new SegmentedFile(binaryImage, matrix);
	}
	
	
}
