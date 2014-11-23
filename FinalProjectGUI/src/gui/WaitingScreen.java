package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class WaitingScreen extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1164599519412930761L;

	private JButton playButton;
	public WaitingScreen(){
		super("Waiting Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
	}
	
	public JPanel setupGUI(){
		PanelBackground panel = new PanelBackground(1);
		
		/*Play Button, only Host should be able to click this, this will
		 * have to be modified later so that this functionality is made
		 * if(isHost){
		 * playButton.setEnabled(true);
		 * }
		 * else{
		 * playButton.setEnabled(false);
		 * }
		 */
	    playButton = new JButton("Play");
		playButton.setFont(new Font("Times New Roman",Font.BOLD,48));
		playButton.setBounds(280, 0, 100, 100);
		panel.add(playButton);
		
		
		
		
		
		return panel;
	}
	
	public static void main(String [] args){
		new WaitingScreen();
	}
}
