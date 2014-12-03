package project2;

import java.util.Vector;

public class TimerThread extends Thread{
	private long startTime;
	private long lapTime;
	private long ns = 1000000000;
	public int numSecs;
	public int minRemaining;
	public int secsRemaining;
	public TimerThread(int numMinutes){
		this.numSecs = numMinutes * 60;
	}
	public void run(){
		startTime = System.nanoTime();
		
		while(!timerDone()){
			long tempTime = System.nanoTime();
			
			if((tempTime - startTime)/ns > 1){
				startTime +=ns;
				numSecs--;
				this.minRemaining = numSecs/60;
				this.secsRemaining = numSecs%60;
			}
		}
		
		
	}
	public boolean timerDone(){
		return 0 == this.numSecs;
	}
	public long getRunTime(){
		return (lapTime - startTime)/ns;
	}
	public static void main(String[] args){
		TimerThread timer = new TimerThread(1);
		timer.start();
	}
}
