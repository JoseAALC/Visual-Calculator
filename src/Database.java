import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * class that will save the blobs to test correlation
 * @author ze
 *
 */
public class Database implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<DatabaseItem> blobs;
	
	public Database() {
		blobs = new ArrayList<DatabaseItem>();
	}
	
	public int getSize(){
		return blobs.size();
	}
	
	public void removeBlob(char c) throws IOException{
		for(int i=0; i<blobs.size(); i++){
			if(blobs.get(i).getSymbol().equals(c)){
				blobs.remove(i);
				FileOutputStream fot = new FileOutputStream("database.ser", false);
				ObjectOutputStream oos = new ObjectOutputStream(fot);
		    	oos.writeObject(this);
		    	oos.close();
				break;
			}
		}
	}
	
	/**
	 *  adds an blob to database
	 * @param blob to be added to database
	 * @throws IOException 
	 */
	public void addBlob(Blob blob,Character symbol) throws IOException {
		blobs.add(new DatabaseItem(blob,symbol));
		FileOutputStream fot = new FileOutputStream("database.ser", false);
		ObjectOutputStream oos = new ObjectOutputStream(fot);
    	oos.writeObject(this);
    	oos.close();
	}
	/**
	 * 
	 * @param blob1 
	 * @param blob2
	 * @return Calculates the correlation between two blobs
	 */
	private int correlationCompute(Blob blob1,Blob blob2) {
		int blob1matrix[][] = blob1.getContent();
		int blob2matrix[][] = blob2.getContent();
		
		int corr =0;
		for(int i=0;i<blob1matrix.length;i++)
			for(int j =0;j<blob1matrix[i].length;j++)
				corr+= correlationBits(blob1matrix[i][j],blob2matrix[i][j]);
				
		
		return corr;
		
	}
	//computes (p and q) or (not p and not q)
	private int correlationBits(int p,int q) {
		return ((p & q) | ( (~p) & (~q))) & 1;
	}
	/**
	 * calculates the blob with better correlation
	 * @param test
	 * @return
	 */
	public Blob correlation(Blob test) {
		int max =-1;
		int maxindex = -1;
		for(int i=0;i<blobs.size();i++) {
			int corr = correlationCompute(test,blobs.get(i).getBlob());
			if( corr > max) {
				max = corr;
				maxindex = i;
			}
		}
		return blobs.get(maxindex).getBlob();
	}
	
	
	
}
