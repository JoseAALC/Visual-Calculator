

public abstract class RGBProcessor {
	
	
	
	protected int getRed(int color) { return (color >> 16) & 0xff; }
	protected int getGreen(int color) { return (color >> 8) & 0xff; }
	protected int getBlue(int color) { return color & 0xff; }
	protected int makeColor(int red, int green, int blue) { return (255 << 24) | (red << 16) | (green << 8) | blue; }
	
}

