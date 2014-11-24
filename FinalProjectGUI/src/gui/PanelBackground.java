package gui;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelBackground extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244066760150399321L;
	private Image backGroundImage; 
	public PanelBackground(int backGroundNumber){
		/*creates a custom JPanel with a background.
		 * the background chosen depends on the number passed into the constructor
		 * 0 => Login Screen
		 * 1 => Waiting Screen
		 * 2 => Start Up Screen
		 * 3 => End Of Game Screen
		 * default => no background
		 */
		switch(backGroundNumber){
		case 0:
		    ImageIcon loginBackground = new ImageIcon("bloodWallPaper.jpg");
			this.setBackGround(loginBackground);
			break;
		case 1:
			ImageIcon waitingScreenBackGround = new ImageIcon("darkForest.jpg");
			this.setBackGround(waitingScreenBackGround);
			break;
		case 2:
			ImageIcon startUpScreenBackGround = new ImageIcon("noWayOut.jpg");
			this.setBackGround(startUpScreenBackGround);
			break;
		case 3:
			
			break;
			default://do nothing
		}
		
	}
	
	/*gets the imageIcon chosen, scales it to the size of the JFrame(800,600), and sets
	 * the panels private image variable to the scaled image
	 */
	public void setBackGround(ImageIcon backGround){
		Image image = backGround.getImage();
		Image newImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		backGround = new ImageIcon(newImage);
		this.backGroundImage = backGround.getImage();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.backGroundImage, 0, 0, null);
	}
}
