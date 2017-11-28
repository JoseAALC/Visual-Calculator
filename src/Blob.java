
/**
 * Binary matrix object representative of an finded symbol
 * @author ze
 *
 */
public class Blob implements Comparable<Blob> {
	
	/**
	 * object matrix content filled with 1's and 0's
	 */
	private int content[][];
	/**
	 * corner points of the object
	 */
	private int p1x, p1y, p2x,p2y;
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

	public Blob(int[][] content, int p1x, int p1y, int p2x, int p2y, int iD, int size) {
		super();
		this.content = content;
		this.p1x = p1x;
		this.p1y = p1y;
		this.p2x = p2x;
		this.p2y = p2y;
		ID = iD;
		this.size = size;
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

	@Override
	public int compareTo(Blob o) {
		return ((Integer)(this.size)).compareTo(o.size);
	}
}
