package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndOfGameScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439031398254320921L;
	private int countDownTimer = 10;
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
		
		/*
		 * Count down label
		 */
		
		
		
		/*
		 * Statistics,NEED TO SEE WHAT STATISTICS WILL BE DISPLAYED
		 */
		
		
		/*
		 * Quit Button
		 */
		
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Times New Roman",Font.BOLD,32));
		quitButton.setBounds(350, 450, 100, 50);
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Disconnect client from server
				System.exit(0);
			}
			
		});
		mainPanel.add(quitButton);
		return mainPanel;
	}
	
	public static void main(String [] args){
		new EndOfGameScreen();
	}
}
