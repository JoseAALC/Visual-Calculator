import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Binary matrix object representative of an finded symbol
 * @author ze
 *
 */
public class Blob implements Comparable<Blob>, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * object matrix content filled with 1's and 0's
	 */
	private int content[][];
	/**
	 * corner points of the object
	 */
	private int p1x, p1y, p2x, p2y;
	/*
	 * number of the Blob
	 */
	private int ID;
	/*
	 * Number of pixels
	 */
	private int size;


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Blob(int[][] content, int p1x, int p1y, int p2x, int p2y, int size,int iD) {
		this.content = content;
		this.p1x = p1x;
		this.p1y = p1y;
		this.p2x = p2x;
		this.p2y = p2y;
		ID = iD;
		this.size = size;
	}

	public Blob(int[][] content,Bounds bounds,int ID) {
		this(content,bounds.getLeftBond(),bounds.getUpperBond(),bounds.getRightBond(),bounds.getLowerBond(),bounds.getSize(),ID);

	}

	public int[] getMatrixImage() {
		int matrix[] = new int[content.length*content[0].length];
		for(int j=0; j<content[0].length;j++) {
			for(int i=0;i<content.length;i++) {
				matrix[j + i*(p2x-p1x)] = ((content[i][j]==0) ? makeColor(255, 255, 255):makeColor(0, 0, 0));
			}
		}

		return matrix;
	}



	public int[][] getContent() {
		return content;
	}

	public void setContent(int[][] content) {
		this.content = content;
	}

	public int getP1x() {
		return p1x;
	}

	public void setP1x(int p1x) {
		this.p1x = p1x;
	}

	public int getP1y() {
		return p1y;
	}

	public void setP1y(int p1y) {
		this.p1y = p1y;
	}

	public int getP2x() {
		return p2x;
	}

	public void setP2x(int p2x) {
		this.p2x = p2x;
	}

	public int getP2y() {
		return p2y;
	}

	public void setP2y(int p2y) {
		this.p2y = p2y;
	}

	public Point calculateMassCenter() {
		return new Point(p1x + (p2x-p1x)/2,p1y + (p2y-p1y)/2);
	}
	
	public void printBlob(){
		for(int i=0; i<content.length; i++){
			for(int j=0; j< content[0].length; j++)
				System.out.print(content[i][j]);
			System.out.println();
		}
	}
	
	public static ArrayList<Blob> sortByPosition(ArrayList<Blob> blobList){
		Collections.sort(blobList, new Comparator<Blob>() {
            @Override
            public int compare(Blob b1, Blob b2) {
            	if(b1.p1x < b2.p1x)
        			return -1;
        		else if(b1.p1x > b2.p1x)
        			return 1;
        		else
        			return 0;
            }
        });
		//blobList.get(0).printBlob();
		return blobList;
	}
	
	/*
	public static int compareByPosition(Blob b1, Blob b2){
		if(b1.p1x < b2.p1x)
			return -1;
		else if(b1.p1x > b2.p1x)
			return 1;
		else
			return 0;
	}*/
	
	@Override
	public int compareTo(Blob o) {
		/*if(this.p1x < o.p1x)
			return -1;
		else if(this.p1x > o.p1x)
			return 1;
		else
			return 0;
		 */
		return ((Integer)(this.size)).compareTo(o.size);
	}

	protected int makeColor(int red, int green, int blue) { return (255 << 24) | (red << 16) | (green << 8) | blue; }
}
