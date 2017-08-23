import processing.core.*;

public class HolaMidi extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("HolaMidi");
	}
	
	public void settings() {
		size(500, 500);
	}
	
	public void setup() {
		background(255);
	}

	public void draw() {
		ellipse(width/2, height/2, 100, 100);
	}
}
