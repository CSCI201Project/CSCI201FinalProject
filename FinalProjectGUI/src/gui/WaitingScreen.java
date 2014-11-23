package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WaitingScreen extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1164599519412930761L;

	private JButton playButton;
	private JComboBox<String> timeLimitCB;
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
		panel.setLayout(null);
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
		playButton.setBounds(300, 0, 200, 75);
		playButton.setOpaque(false);
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Start gamex
				
			}
			
		});
		panel.add(playButton);
		
		/*Time Limit Label*/
		JLabel timeLimitLabel = new JLabel("Time Limit:");
		timeLimitLabel.setForeground(Color.WHITE);
		timeLimitLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		timeLimitLabel.setBounds(295, 100, 150, 50);
		panel.add(timeLimitLabel);
		
		/*Time Limit Combo Box*/
		 timeLimitCB = new JComboBox<String>();
		 /*Note: Not sure yet of maximum time being allowed, just going up to ten for now*/
		 for(int i = 1; i <= 10;i++){
			 timeLimitCB.addItem(i + " Minutes");
		 }
		 timeLimitCB.setFont(new Font("Times New Roman",Font.BOLD,15));
		 timeLimitCB.setBounds(400, 100, 150, 50);
		 panel.add(timeLimitCB);
		
		
		return panel;
	}
	
	public static void main(String [] args){
		new WaitingScreen();
	}
}
