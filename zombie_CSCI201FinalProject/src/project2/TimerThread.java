package project2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class TimerThread extends Thread{
	private int totalTime;
	private int numMinutes;
	private Timer timer;
	
	public TimerThread(int numMinutes){
		this.numMinutes = numMinutes;
		convertToMilliSecs();
	}
	public void run(){
		ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  //performs this after timer
		      }
		};
		timer = new Timer(totalTime, taskPerformer);
		timer.setRepeats(false);
		timer.start(); 
		while(timer.isRunning()){
			//if(timer.)
		}
		
	}
	public void convertToMilliSecs(){
		int minute = 60000; //milliseconds
		this.totalTime = minute * this.numMinutes;
	}
	public void endSimulation(){
	}
	public static void main(String[] args ){
		TimerThread tt = new TimerThread(2);
		tt.start();
	}
}
