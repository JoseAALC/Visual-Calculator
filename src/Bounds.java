/**
 * class that represents the boundaries of an object
 * @author ze
 *
 */
public class Bounds {
	private int lowerBond;
	private int upperBond;
	private int leftBond;
	private int rightBond;
	private int size;
	public Bounds(int lowerBond, int upperBond, int leftBond, int rightBond, int size) {
		super();
		this.lowerBond = lowerBond;
		this.upperBond = upperBond;
		this.leftBond = leftBond;
		this.rightBond = rightBond;
		this.size = size;
	}
	public int getLowerBond() {
		return lowerBond;
	}
	public void setLowerBond(int lowerBond) {
		this.lowerBond = lowerBond;
	}
	public int getUpperBond() {
		return upperBond;
	}
	public void setUpperBond(int upperBond) {
		this.upperBond = upperBond;
	}
	public int getLeftBond() {
		return leftBond;
	}
	public void setLeftBond(int leftBond) {
		this.leftBond = leftBond;
	}
	public int getRightBond() {
		return rightBond;
	}
	public void setRightBond(int rightBond) {
		this.rightBond = rightBond;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	

}
