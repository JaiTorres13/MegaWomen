/**
 * @author JaiTorres13
 * boss for game
 */
package rbadia.voidspace.model;

public class Boss extends GameObject {
private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	public static final int Y_OFFSET = 325; // initial y distance of the ship from the bottom of the screen 
	
	public static final int WIDTH = 75;
	public static final int HEIGHT = 75;
	//public static final int speed = DEFAULT_SPEED;
	
	public Boss(int xPos, int yPos){
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}
	
	public int gtInitialYOffset() {
		return Y_OFFSET;
	}
	
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
}
