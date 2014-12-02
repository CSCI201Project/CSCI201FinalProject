package gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {

	private AudioInputStream inputStream;
	private Clip clip;
	
	/*NOT ALL FILE TYPES ARE SUPPORTED, WAV WORKS FOR SURE*/
	public SoundPlayer(String soundFile){
		try {
			this.inputStream = AudioSystem.getAudioInputStream(new File(soundFile));
			this.clip = AudioSystem.getClip();
			this.clip.open(this.inputStream);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio file " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Sound Player could not get audio file " + e.getMessage());
		} catch (LineUnavailableException e) {
			System.out.println("Line unavailbalbe exception " + e.getMessage());
		}
	}
	
	/*continuously plays a audio clip until the clip is stopped*/
	public void playSoundContinuously(){
		clip.loop(Clip.LOOP_CONTINUOUSLY); // looping as long as this thread is alive
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
	public void playSoundOnce(){
		clip.loop(0);
	}
	
	/*plays the audio file numberOfPlaysTimes*/
	public void playSoundXtimes(int numberOfPlays){
		clip.loop(numberOfPlays - 1);
	}
	
	/*stops the audio file*/
	public void stopSound(){
		clip.stop();
	}
	
	public void closeAudioFile(){
		clip.close();
	}
}
