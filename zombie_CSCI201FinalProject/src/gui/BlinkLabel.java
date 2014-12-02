package gui;
/* 
 * CREDIT GOES TO http://www.cpe.ku.ac.th/~jim/219141/samples/BlinkLabel.java FOR IMPLEMENTATION
 */
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 * A blinkable label.
 * This class adds blinking behavior to a JLabel.
 * Use setBlinking(true) to turn blinking on.
 * 
 * How it works:
 * 1. it uses a Timer and TimerTask to perform blinking.
 * 2. the TimerTask toggles the foreground color between the
 *    original foreground color and the background color.  This makes 
 *    the label text appear and disappear (when foreground == background).
 * 
 * 
 */
public class BlinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private static final int BLINKING_RATE = 1000; // period in milliseconds

	private boolean blinking = false;
	private Timer timer = null;

	/**
	 * Create a new blinkable label with given text string.
	 * @param text a string to display on the label.
	 */
	public BlinkLabel(String text) {
		super(text);	
	}

	/**
	 * Turn the blinking on or off.
	 * @param blinking is true to turn blinking on
	 */
	public void setBlinking(boolean blinking) {
		if ( this.blinking == blinking ) return; // nothing to do
		this.blinking = blinking;
		// if there is a timer running then cancel it.
		// there should only be a timer if the label is blinking.
		if ( timer != null ) {
			timer.cancel();
			timer = null;
		}
		// to make the label blink, schedule a BlinkTask.
		if ( blinking ) {
			timer = new Timer();
			// parameters are: (TimerTask, delay, period)
			timer.scheduleAtFixedRate(new BlinkTask(this), 0, BLINKING_RATE);	
		}
	}

	/**
	 * Get the blinking state of the label.
	 * @return true if label is in blinking state
	 */
	public boolean getBlinking( ) {
		return this.blinking;
	}

	/**
	 * A task to control blinking behavior.
	 */
	class BlinkTask extends TimerTask {
		private JLabel label;
		

		public BlinkTask(JLabel label) {
			this.label = label;
		}

		/**
		 * Reverse the visibility of label.
		 */
		public void run( ) {
			if(label.isVisible()){
				label.setVisible(false);
			}
			else{
				label.setVisible(true);
			}
		}
		
		/**
		 * When the TimerTask is canceled, restore the original
		 * foreground color.  This ensures the label is visible.
		 */
		public boolean cancel() {
			label.setVisible(true);
			return true; // success
		}
	}
}