
public class SegmentedFile {
	private int binaryMatrix[][];
	private int[] representationToConvertToImage;
	
	public SegmentedFile(int[][] binaryMatrix, int[] representation) {
		super();
		this.binaryMatrix = binaryMatrix;
		this.representationToConvertToImage = representation;
	}

	public int[][] getBinaryMatrix() {
		return binaryMatrix;
	}

	public void setBinaryMatrix(int[][] binaryMatrix) {
		this.binaryMatrix = binaryMatrix;
	}

	public int[] getRepresentation() {
		return representationToConvertToImage;
	}

	public void setRepresentation(int[] representation) {
		this.representationToConvertToImage = representation;
	}
	


}
