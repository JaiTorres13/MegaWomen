package rbadia.voidspace.model;

public class Floor extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;

	public Floor(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
	}
}
