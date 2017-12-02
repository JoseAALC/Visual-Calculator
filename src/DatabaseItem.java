import java.io.Serializable;

public class DatabaseItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Blob blob;
	private Character symbol;
	
	public DatabaseItem(Blob blob, Character symbol) {
		super();
		this.blob = blob;
		this.symbol = symbol;
	}
	public Blob getBlob() {
		return blob;
	}
	public void setBlob(Blob blob) {
		this.blob = blob;
	}
	public Character getSymbol() {
		return symbol;
	}
	public void setSymbol(Character symbol) {
		this.symbol = symbol;
	}
}
