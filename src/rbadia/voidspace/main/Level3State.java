/**
 * @author JaiTorres13 & mariamarrero13
 * level 3 where the asteroids moves in different directions
 */

package rbadia.voidspace.main;
import java.awt.Graphics2D;
import java.util.List;

import rbadia.voidspace.graphics.MyGraphicsManager;

import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;

import rbadia.voidspace.model.Asteroid;

import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;


public class Level3State extends MyLevel2State {
private static final long serialVersionUID = -2094575762243216079L;
	// Constructors
	public Level3State(int level, MainFrame frame, GameStatus status, 
			MyLevelLogic gameLogic, InputHandler inputHandler,
			MyGraphicsManager myGraphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, myGraphicsMan, soundMan);
		setMyGraphicsManager(myGraphicsMan);
	}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}

	
	int translate = 0;
	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid.getX() + asteroid.getPixelsWide() >  0) && (asteroid.getX() + asteroid.getPixelsWide() <= this.getWidth())) {
			//different trajectories for asteroid
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
				//different locations for asteroid
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
	
	
	/**
	 * @author JaiTorres13
	 * draws second asteroid
	 */
	int translate2 = 0;
	@Override
	protected void drawAsteroid2() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid2.getX() + asteroid2.getPixelsWide() >  0) && (asteroid2.getX() + asteroid2.getPixelsWide() <= this.getWidth())) {


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
	
	
	/**
	 * @author JaiTorres13
	 * draws the platform in a specific pattern
	 */
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);			
		}	
		return platforms;
	}
	
	
	@Override
	public boolean isLevelWon() {
		return (levelAsteroidsDestroyed >= 10 );
	}

}
