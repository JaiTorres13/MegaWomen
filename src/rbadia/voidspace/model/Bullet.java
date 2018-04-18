package rbadia.voidspace.model;

/**
 * Represents a bullet fired by a ship.
 */
public class Bullet extends GameObject {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	public static final  int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	public Bullet(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(12);
	}
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
