import java.util.ArrayList;

public class Line {
	private double b,m;
	

	public Line(ArrayList<Point> points) {
		double m = 1;
		double b = 1;
		double step = 	0.00005;
		double delta = Math.pow(10, -12);
		double beforeRss = -1;
		
		while(true) {
			double actualRss = RSS(points,b,m);
			if( Math.abs(beforeRss-actualRss) < delta)
				break;
			b = step*derivative1(points, b, m);
			m = step*derivative2(points, b, m);
		}
		System.out.println("Convergi! m= "+m);
		this.b = b;
		this.m = m;
	}
	
	
	public Point getCenter(int x) {
		return new Point(x,(int) (b + m*x)); 
	}
	




	private double RSS(ArrayList<Point> points,double b,double m) {
		double sum =0;
		
		
		for(int i=0;i<points.size();i++) {
			int x = points.get(i).getX();
			int y = points.get(i).getY();
			sum+= Math.pow(y - (b + m*x) , 2);
		}
		
		return sum;
		
	}
	
	
	
	
	
	
	
	
	
	public double getB() {
		return b;
	}








	public void setB(double b) {
		this.b = b;
	}








	public double getM() {
		return m;
	}








	public void setM(double m) {
		this.m = m;
	}








	private double derivative1(ArrayList<Point> points,double b,double m) {
		double sum = 0;
		for(int i=0;i<points.size();i++) {
			int x = points.get(i).getX();
			int y = points.get(i).getY();
			sum+= (-2*(y -(b + m*x)));
			
		}
		return sum;	
	}
	
	private double derivative2(ArrayList<Point> points,double b,double m) {
		double sum = 0;
		for(int i=0;i<points.size();i++) {
			int x = points.get(i).getX();
			int y = points.get(i).getY();
			sum+= (-2*x*(y -(b + m*x)));
			
		}
		return sum;
	}
}
