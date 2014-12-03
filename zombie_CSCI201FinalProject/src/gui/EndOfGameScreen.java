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

import networking.ZombieGameClient;
import networking.ZombieGameServer;

public class EndOfGameScreen extends JFrame {
	private static final long serialVersionUID = -2439031398254320921L;
	private int countDownTimer = 10;
	private JLabel countDownLabel = new JLabel();
	private PanelBackground mainPanel;
	
	private boolean isHost;
	private ZombieGameServer server;
	private ZombieGameClient client;
	
	public EndOfGameScreen(boolean survivorsWin) {
		super("End Of Game Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI(survivorsWin));
		this.setVisible(true);
		this.startCountDownTimer();
	}
	
	private JPanel setupGUI(boolean survivorsWin) {
	    mainPanel = new PanelBackground(3);
		mainPanel.setLayout(null);
		
		/*Winner label that will eventually display team that won the game*/
		JLabel teamWonLabel;
		
		if(survivorsWin) teamWonLabel = new JLabel("Survivors win!");
		else teamWonLabel = new JLabel("Zombies win!");
		
		teamWonLabel.setForeground(Color.WHITE);
		teamWonLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		teamWonLabel.setBounds(300, 0, 275, 50);
		mainPanel.add(teamWonLabel);
			
		
		/*
		 * Statistics,NEED TO SEE WHAT STATISTICS WILL BE DISPLAYED
		 * NOT COMPLETELY IMPLEMENTED YET
		 */
		/*Statistics Panel*/
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setLayout(new BoxLayout(statisticsPanel,BoxLayout.Y_AXIS));
		statisticsPanel.setBounds(280, 200, 250, 300);
		statisticsPanel.setOpaque(false);
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
	
	

	/* Count down label
	*Starts a count down for the players to be returned to the lobby, updates
	 * the label each second and decrements the value by 1 each time
	 */
	private void startCountDownTimer(){
		this.countDownLabel.setForeground(Color.WHITE);
		this.countDownLabel.setFont(new Font("Times New Roman",Font.BOLD,25));
		this.countDownLabel.setBounds(300, 100, 400, 75);
		this.mainPanel.add(this.countDownLabel);
		for(int i = 0; i <= 10; i++){
			this.countDownLabel.setText("<html>" + this.countDownTimer-- + " S until the game <br> goes back to the start up screen</html>");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*At This point, the counter is 0, everyone that is still connected should be taken back
		 * to the login screen so that they can play again
		 */
		this.setVisible(false);
		
		StartUpScreen sus = new StartUpScreen(this.isHost);
		if(this.isHost) sus.setServer(this.server);
		else sus.setClient(this.client);
	}
	
	public void setServer(ZombieGameServer zgs) {
		this.isHost = true;
		this.server = zgs;
		this.server.setFrame(this);
	}
	public void setClient(ZombieGameClient zgc) {
		this.isHost = false;
		this.client = zgc;
		this.client.setFrame(this);
	}
	
	public static void main(String [] args){
		EndOfGameScreen eogs = new EndOfGameScreen(false);
//		eogs.setServer(new ZombieGameServer(80));
//		eogs.setClient(new ZombieGameClient("localhost", 80));
	}
	
}
