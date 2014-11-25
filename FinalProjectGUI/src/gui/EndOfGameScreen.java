package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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
	private JLabel countDownLabel = new JLabel();
	private PanelBackground mainPanel;
	
	public EndOfGameScreen(){
		super("End Of Game Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
		this.updateCountDownTimer();
	}
	
	private JPanel setupGUI(){
	    mainPanel = new PanelBackground(1);
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
		/*Statistics Panel*/
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setLayout(new BoxLayout(statisticsPanel,BoxLayout.Y_AXIS));
		statisticsPanel.setBounds(280, 200, 250, 300);
		mainPanel.add(statisticsPanel);
		
		/*
		 * Quit Button
		 */
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Times New Roman",Font.BOLD,32));
		quitButton.setBounds(350, 500, 100, 50);
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
	
	private void updateCountDownTimer(){
		this.countDownLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		this.countDownLabel.setBounds(300, 100, 400, 50);
		this.mainPanel.add(this.countDownLabel);
		for(int i = 0; i <= 10; i++){
			this.countDownLabel.setText(this.countDownTimer-- + "S until the game \n goes back to the start up screen");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args){
		new EndOfGameScreen();
	}
	
}
