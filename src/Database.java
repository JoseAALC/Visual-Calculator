import java.util.ArrayList;

public class Database {
	private ArrayList<Blob> blobs;
	/**
	 * 
	 * @param blob1 
	 * @param blob2
	 * @return the correlaction number
	 */
	private int correlationCompute(Blob blob1,Blob blob2) {
		return 0;
		
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
			int corr = correlationCompute(test,blobs.get(i));
			if( corr > max) {
				max = corr;
				maxindex = i;
			}
		}
		return blobs.get(maxindex);
	}
	
	
	
}
