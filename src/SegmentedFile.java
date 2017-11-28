import java.awt.Image;
public class SegmentedFile {
	private int binaryMatrix[][];
	private Image representation;
	
	public SegmentedFile(int[][] binaryMatrix, Image representation) {
		super();
		this.binaryMatrix = binaryMatrix;
		this.representation = representation;
	}

	public int[][] getBinaryMatrix() {
		return binaryMatrix;
	}

	public void setBinaryMatrix(int[][] binaryMatrix) {
		this.binaryMatrix = binaryMatrix;
	}

	public Image getRepresentation() {
		return representation;
	}

	public void setRepresentation(Image representation) {
		this.representation = representation;
	}
	


}
