package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;

public class MyGraphicsManager extends GraphicsManager {
	private BufferedImage megaManImgL;
	private BufferedImage megaFallLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage bossImg;
	private BufferedImage megaBackg;
	private BufferedImage bossbulletImg;
	public MyGraphicsManager(){
		// load images
		super();
		try {
			this.megaManImgL = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Left.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallLeft.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireLeft.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossMegaMan.png"));
			this.megaBackg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaBack.png"));
			this.bossbulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bossbullet.png"));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}


	public void drawMegaManL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManImgL, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawBoss (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossImg, boss.x, boss.y, observer);	
	}


	public void drawMegaBackg (Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaBackg, 0, 0, observer);	
	}
	
	/**
	 * @author JaiTorres13
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bossbullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossBullet(BossBullet bossbullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossbulletImg, bossbullet.x, bossbullet.y, observer);
	}
}
