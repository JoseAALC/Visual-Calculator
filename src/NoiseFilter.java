import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.Collections;

public class NoiseFilter extends RGBProcessor {
	public NoiseFilter() {}
	
	
	public int[] medianFilter(int wx,int wy ,Image original) {
		int sizex = original.getWidth(null);
		int sizey = original.getHeight(null);
		
		int matrix[] = new int[sizex*sizey];

		PixelGrabber pg = new PixelGrabber(original, 0, 0, sizex, sizey, matrix, 0, sizex);
		
		
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return null;
		}
		
		
		int result[] = new int[matrix.length];
		System.arraycopy( matrix, 0, result, 0, matrix.length );
		for(int i = wx + wy*sizex;i<sizey*sizex - (wx + wy*sizex);i++) {
			ArrayList<Integer> reds = new ArrayList<Integer>();
			ArrayList<Integer> blues = new ArrayList<Integer>();
			ArrayList<Integer> greens = new ArrayList<Integer>();
			
			for(int j = i - wx/2;j<=i +wx/2;j++) {
				for(int k= -wy/2;k<wy/2;k++) {
					reds.add(getRed(matrix[j + k*sizex]));
					blues.add(getBlue(matrix[j+ k*sizex]));
					greens.add(getBlue(matrix[j + k*sizex]));
				}
			}
			Collections.sort(reds);
			Collections.sort(blues);
			Collections.sort(greens);
			result[i] = makeColor(median(reds),median(greens),median(blues));
			
		
		}
		
		
		
		return result;
		
	}
	
	
	public int[] meanFilter(int wx,int wy ,Image original) {
		int sizex = original.getWidth(null);
		int sizey = original.getHeight(null);
		
		int matrix[] = new int[sizex*sizey];

		PixelGrabber pg = new PixelGrabber(original, 0, 0, sizex, sizey, matrix, 0, sizex);
		
		
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return null;
		}
		
		int result[] = new int[matrix.length];
		System.arraycopy( matrix, 0, result, 0, matrix.length );
		for(int i = wx + wy*sizex;i<sizey*sizex - (wx + wy*sizex);i++) {
			int red = 0;
			int blue =0;
			int green = 0;
			
			for(int j = i - wx/2;j<=i +wx/2;j++) {
				for(int k= -wy/2;k<wy/2;k++) {
					red+=getRed(matrix[j + k*sizex]);
					blue+=getBlue(matrix[j+ k*sizex]);
					green+=getBlue(matrix[j + k*sizex]);
				}
			}
		
			result[i] = makeColor(red/9,green/9,blue/9);
			
		
		}
		
		
		
		return result;
		
	}
	
	
	
	
	
	private int median(ArrayList<Integer> arr) {
		int s = arr.size();
		if(s % 2 == 0)
			return (arr.get(s/2) + arr.get(s/2 + 1)) /2;
		return arr.get(s/2);
	}
}
	
	
	
