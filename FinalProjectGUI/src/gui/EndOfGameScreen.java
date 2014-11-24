package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class EndOfGameScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439031398254320921L;

	public EndOfGameScreen(){
		super("End Of Game Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
	}
	
	private JPanel setupGUI(){
		PanelBackground mainPanel = new PanelBackground(1);
		mainPanel.setLayout(null);
		
		/*Added winnner label*/
		JLabel teamWonLabel = new JLabel("____ win!");
		teamWonLabel.setForeground(Color.WHITE);
		teamWonLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		teamWonLabel.setBounds(300, 0, 275, 50);
		mainPanel.add(teamWonLabel);
		
		return mainPanel;
	}
	
	public static void main(String [] args){
		new EndOfGameScreen();
	}
}
