package gui;

import javax.swing.*;

public class WaitingScreen extends JFrame{
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
		
		
		
		return panel;
	}
	
	public static void main(String [] args){
		new WaitingScreen();
	}
}
