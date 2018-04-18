/**
 * @author JaiTorres13 & mariamarrero13
 *  Level 4 - the boss appears vertically 
 */

package rbadia.voidspace.main;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import rbadia.voidspace.graphics.MyGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;


public class Level4State extends Level3State {

	private static final long serialVersionUID = -2094575762243216079L;
	
	//stuff for boss
	protected Boss boss;
	int down = 0;
	protected int bossDamage = 0;
	protected static final int NEW_BOSS_DELAY = 500;
	 protected long lastBossTime;
	 protected Rectangle bossExplosion;
	 protected List<BossBullet> bossBullets;
	 protected long lastBulletBossTime;

	protected long lastBulletTime;

	
	// Constructors
	public Level4State(int level, MainFrame frame, GameStatus status, 
			MyLevelLogic gameLogic, InputHandler inputHandler,
			MyGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
	}
	//getter
	public Boss getBoss() 					{ return boss; 		}
	public List<BossBullet> getBossBullets() 			{ return bossBullets; 		}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		newBoss(this);
		lastBossTime = -NEW_BOSS_DELAY;
		bossBullets = new ArrayList<BossBullet>();
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		drawBoss();
		checkBullletBossCollisions();
		checkMegaManBossCollision();
		drawBossBullets();
		checkMegaManBossBulletsCollisions();
		
	}
	
	@Override
	public void doGettingReady() {
		setCurrentState(GETTING_READY);
		getGameLogic().drawGetReady();
		repaint();
		LevelLogic.delay(2000);
		//Changes music from "menu music" to "ingame music"
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/BossBattle.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}

	//if boss intersects megaman
		protected void checkMegaManBossCollision() {
			GameStatus status = getGameStatus();
			if(boss.intersects(megaMan)){
				status.setLivesLeft(status.getLivesLeft() - 3);
			}
		}
		
		//when megaman shoots boss, you get 500 points!
		protected void checkBullletBossCollisions() {
			GameStatus status = getGameStatus();
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(boss.intersects(bullet)){
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 500);
					damage=0;
					bossDamage++;
					// remove bullet
					if(bossDamage == 10) removeBoss(boss);
					bullets.remove(i);
					break;
				}
			}
		}
		
		
	int translate = 0;
	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();

		if((asteroid.getX() + asteroid.getPixelsWide() >  0) && (asteroid.getX() + asteroid.getPixelsWide() <= this.getWidth())) {

			//the asteroid will translate different ways
			switch(translate) {
			case 0:
				asteroid.translate(-asteroid.getSpeed() * asteroid.getSpeed() ,   asteroid.getSpeed() / 2);
				break;
			case 1:
				asteroid.translate(rand.nextInt(5) * 5,  asteroid.getSpeed()/2);
				break;
			case 2:
				asteroid.translate(0, asteroid.getSpeed() * asteroid.getSpeed());
				break;
			case 3:
				asteroid.translate(-asteroid.getSpeed() * 2, asteroid.getSpeed());
				break;
			case 4:
				asteroid.translate(asteroid.getSpeed(), (Math.abs(asteroid.getSpeed()) - rand.nextInt(10)));
				break;
			}

			getGraphicsManager().drawAsteroid(asteroid, g2d, this);
		}


		else {
			translate++;
			long currentTime = System.currentTimeMillis();
			if(translate == 4) translate = 0;
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				//the asteroid will be drawn in different locations
				switch(translate) {
				case 0:
					asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
							rand.nextInt(this.getHeight() - asteroid.getPixelsTall()) - 32);
					break;
				case 1:
					asteroid.setLocation(0, rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
					break;
				case 2:
					asteroid.setLocation(rand.nextInt(this.getWidth() - asteroid.getPixelsWide()), 0);
					break;
				case 3:
					asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
							rand.nextInt(this.getHeight() - asteroid.getPixelsTall()) - 32);
					break;
				case 4:
					asteroid.setLocation(0,0);
					break;

				}

			}
			else {
				// draw explosion
				translate++;
				if(translate == 4) translate = 0;
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
	}
	
	/*
	 * @author JaiTorres13
	 * method that draws the second asteroid
	 */
	int translate2 = 0;
	@Override
	protected void drawAsteroid2() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid2.getX() + asteroid2.getPixelsWide() >  0) && (asteroid2.getX() + asteroid2.getPixelsWide() <= this.getWidth())) {

			//the asteroid will translate different ways
			switch(translate2) {
			case 0:
				asteroid2.translate(rand.nextInt(5) * 5,  asteroid2.getSpeed()/2);
				break;
			case 1:
				asteroid2.translate(-asteroid2.getSpeed() * asteroid2.getSpeed() ,   asteroid2.getSpeed() / 2);
				break;
			case 2:
				asteroid2.translate(asteroid2.getSpeed(), (Math.abs(asteroid2.getSpeed()) - rand.nextInt(10)));
				break;
			case 3:
				asteroid2.translate(-asteroid2.getSpeed() * 2, asteroid2.getSpeed());
				break;
			case 4:
				asteroid2.translate(0, asteroid2.getSpeed() * asteroid2.getSpeed());
				break;
			}

			getGraphicsManager().drawAsteroid(asteroid2, g2d, this);
		}


		else {
			translate2++;
			long currentTime = System.currentTimeMillis();
			if(translate2 == 4) translate2 = 0;
			if((currentTime - lastAsteroid2Time) > NEW_ASTEROID_2_DELAY){
				//the asteroid will be drawn in different locations
				switch(translate2) {
				case 0:
					asteroid2.setLocation(0, rand.nextInt(this.getHeight() - asteroid2.getPixelsTall() - 32));
					break;
				case 1:
					asteroid2.setLocation(this.getWidth() - asteroid2.getPixelsWide(),
							rand.nextInt(this.getHeight() - asteroid2.getPixelsTall()) - 32);
					break;
				case 2:
					asteroid2.setLocation(0,0);
					break;
				case 3:
					asteroid2.setLocation(this.getWidth() - asteroid2.getPixelsWide(),
							rand.nextInt(this.getHeight() - asteroid2.getPixelsTall()) - 32);
					break;
				case 4:
					asteroid2.setLocation(rand.nextInt(this.getWidth() - asteroid2.getPixelsWide()), 0);
					break;

				}

			}
			else {
				// draw explosion
				translate2++;
				if(translate2 == 4) translate2 = 0;
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion2, g2d, this);
			}
		}	
	}
	
	/*
	 * @author JaiTorres13 & mariamarrero13
	 * draws the boss in screen
	 */
		protected void drawBoss() {
			Graphics2D g2d = getGraphics2D();
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBulletBossTime) > 500){
				lastBulletBossTime = currentTime;
				this.fireBossBullet();
			}
			if((boss.getY() + boss.getHeight() < this.getHeight()) && lowerBorderCollision() == false && down == 0) {
				boss.translate(0,boss.getSpeed());
				getMyGraphicsManager().drawBoss(boss, g2d, this);	
			}
		
			 if(lowerBorderCollision() == true){
				boss.translate(0,-boss.getSpeed());
				getMyGraphicsManager().drawBoss(boss, g2d, this);	
				down=1;
			}
				if((boss.getY() > 0) && upperBorderCollision() == false && down == 1) {
					boss.translate(0,-boss.getSpeed());
					getMyGraphicsManager().drawBoss(boss, g2d, this);	
				}
			
				 if(upperBorderCollision() == true){
					boss.translate(0,boss.getSpeed());
					getMyGraphicsManager().drawBoss(boss, g2d, this);	
					down=0;
				}	
		}


		/**
		 * @author JaiTorres13
		 * draws the platform in a specific pattern
		 */
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 20+ i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 );
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 + 140 - (i-k)*40 );
				k=k+2;
			}
		}
		return platforms;
	}
	
	@Override
	public boolean isLevelWon() {
		return (levelAsteroidsDestroyed >= 20 || bossDamage == 10);
	}
	
	public void removeBoss(Boss boss){
		// "remove" boss
		bossExplosion = new Rectangle(
				boss.x,
				boss.y,
				boss.width,
				boss.height);
		boss.setLocation(-boss.width, -boss.height);
		this.getSoundManager().playAsteroidExplosionSound();
	}
	
	/**
	 * Create a new boss
	 */
	public Boss newBoss(Level1State screen){
		int xPos = (int) (screen.getWidth() - Boss.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Boss.HEIGHT- 32));
		boss = new Boss(xPos, yPos);
		return boss;
	}
	
	
	// verify if the boss touch the upper side of screen
	public boolean upperBorderCollision(){
		if(boss.getY() < 1)
			return true;
		return false;
	}
	
	// verify if the boss touch the lower side of screen
	public boolean lowerBorderCollision(){
		if(boss.getY() + boss.getHeight() > this.getHeight() - 5)
			return true;
		return false;
	}
	
	//Bullet fire pose for boss
		protected boolean FireBoss(){
			Boss boss = this.getBoss();
			List<BossBullet> bossBullets = this.getBossBullets();
			for(int i=0; i<bossBullets.size(); i++){
				BossBullet bossbullet = bossBullets.get(i);
				if((bossbullet.getX() < boss.getX()) && 
						(bossbullet.getX() >= boss.getX() - boss.getWidth() - 60)){
					return true;
				}
			}
			return false;
		}
		
		//fires the boss bullet
		public void fireBossBullet(){
			BossBullet bossbullet = new BossBullet( (int)boss.getX()- BossBullet.WIDTH/2,
					boss.y + boss.width/2 - BossBullet.HEIGHT +2);
			bossBullets.add(bossbullet);
		}
		
		//draws the boss bullets
		protected void drawBossBullets() {
			Graphics2D g2d = getGraphics2D();
			for(int i=0; i<bossBullets.size(); i++){
				BossBullet bossbullet = bossBullets.get(i);
				getMyGraphicsManager().drawBossBullet(bossbullet, g2d, this);
				boolean remove =   this.moveBossBullet(bossbullet);
				if(remove){
					bossBullets.remove(i);
					i--;
				}
			}
		}
		
		//removes a life from megaman if the boss bullet touches megaman
		protected void checkMegaManBossBulletsCollisions() {
			GameStatus status = getGameStatus();
			for(int i=0; i<bossBullets.size(); i++){
				BossBullet bossbullet = bossBullets.get(i);
				if(megaMan.intersects(bossbullet)){
					// decrease megaman's life
					status.setLivesLeft(status.getLivesLeft() - 1);
					// remove bullet
					bossBullets.remove(i);
					break;
				}
	
			}
		}
		
		//moves bullet boss
		public boolean moveBossBullet(BossBullet bossbullet){
			if(bossbullet.getY() - bossbullet.getSpeed() >= 0){
				bossbullet.translate(-bossbullet.getSpeed(), 0);
				return false;
			}
			else{
				return true;
			}
		}
		
		
}
