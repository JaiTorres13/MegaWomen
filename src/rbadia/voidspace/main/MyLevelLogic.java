package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles general level logic and status.
 */
public class MyLevelLogic extends LevelLogic {

	protected Font originalFont;
	protected Font bigFont;
	protected Font biggestFont;
	private int mute = 0;

	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public MyLevelLogic(){
	}
	public int getMute(){return mute;}		

	/**
	 * Display initial game title screen.
	 */
	protected void drawInitialMessage() {

		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String gameTitleStr = "MegaWOMEN!!!";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > levelState.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.MAGENTA);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start the Level";
		strWidth = fm.stringWidth(newGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = (levelState.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Press <I> for Item Menu.";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Press <S> for Shop Menu.";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}
	public void handleKeysDuringInitialScreen(InputHandler ih, LevelState levelState) {
		super.handleKeysDuringInitialScreen(ih, levelState);
		if(ih.isMPressed()) {
			ih.reset();
			if (mute==0) {
				mute=1;
				MegaManMain.audioClip.close();
				}
			else {
				mute=0;
				MegaManMain.audioFile = new File("audio/menuScreen.wav");
				try {
					MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
					MegaManMain.audioClip.open(MegaManMain.audioStream);
					MegaManMain.audioClip.start();
					MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			};
		}
	}
	public void handleKeysDuringPlay(InputHandler ih, LevelState levelState) {

		super.handleKeysDuringPlay(ih,levelState);
		// Skips the level when user press N
		if(ih.isNPressed()){
			((MyLevel1State)levelState).setLevelAsteroidsDestroyed(10);
		}
		//Shuts on or off the music when user press M
		if(ih.isMPressed()) {
			ih.reset();
			if(getMute()==0) {
				MegaManMain.audioClip.close();
				mute=1;
				}
			else {
				mute=0;
				MegaManMain.audioFile = new File("audio/mainGame.wav");
				try {
					MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
					MegaManMain.audioClip.open(MegaManMain.audioStream);
					MegaManMain.audioClip.start();
					MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			};
		}
	}
}
