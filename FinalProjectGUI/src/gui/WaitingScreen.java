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
	private JPanel connectedPlayersPanel;
	
	
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
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Start game
				
			}
			
		});
		panel.add(playButton);
		
		/*Time Limit Label*/
		JLabel timeLimitLabel = new JLabel("Time Limit:");
		timeLimitLabel.setForeground(Color.WHITE);
		timeLimitLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		timeLimitLabel.setBounds(295, 100, 150, 50);
		panel.add(timeLimitLabel);
		
		/*Time Limit Combo Box
		 * Similar to the play button, only the host should be able to select an item
		 * from the combo box, the combo box should only be enable if the connected client
		 * is the host*/
		 timeLimitCB = new JComboBox<String>();
		 /*Note: Not sure yet of maximum time being allowed, just going up to ten for now*/
		 for(int i = 1; i <= 10;i++){
			 timeLimitCB.addItem(i + " Minutes");
		 }
		 timeLimitCB.setFont(new Font("Times New Roman",Font.BOLD,15));
		 timeLimitCB.setBounds(400, 100, 150, 50);
		 panel.add(timeLimitCB);
		
		 /*Label to indicate that server is waiting for players to connect*/
		 JLabel waitingForPlayersLabel = new JLabel("Waiting for other players to connect. . .");
		 waitingForPlayersLabel.setForeground(Color.WHITE);
		 waitingForPlayersLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		 waitingForPlayersLabel.setBounds(250, 150, 400, 50);
		 panel.add(waitingForPlayersLabel);
		
		 /*connected players label*/
		 JLabel connectedPlayersLabel = new JLabel("Connected players. . .");
		 connectedPlayersLabel.setForeground(Color.WHITE);
		 connectedPlayersLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		 connectedPlayersLabel.setBounds(320, 250, 200, 50);
		 panel.add(connectedPlayersLabel);
		 
		 /*connected players list,
		  * have a list of connected players, update the list every time someone connects
		  */
		 connectedPlayersPanel = new JPanel();
		 connectedPlayersPanel.setLayout(new BoxLayout(this.connectedPlayersPanel,BoxLayout.Y_AXIS));
		 connectedPlayersPanel.setOpaque(false);
		 connectedPlayersPanel.setBounds(300, 300, 400, 100);
		 /*FILLER CONNECTED PLAYERS LIST FOR TESTING*/
		 for(int i = 1; i <=4; i++){
			 JLabel label = new JLabel("Player " + i + " IP: 192.168.1.10" + i);
			 label.setForeground(Color.WHITE);
			 label.setFont(new Font("Times New Roman",Font.BOLD,20));
			 connectedPlayersPanel.add(label);
		 } 
		 panel.add(connectedPlayersPanel);
		 
		 /*Quit Button*/
		 JButton quitButton = new JButton("Quit");
		 quitButton.setFont(new Font("Times New Roman",Font.BOLD,48));
		 quitButton.setBounds(310, 450, 200, 75);
		 quitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				/*If quit is pressed, disconnect, exit program*/
				System.exit(0);
				
			}
		 });
		 panel.add(quitButton);
		 
		return panel;
	}
	
	private void updateConnectedPlayersList(){
		this.connectedPlayersPanel.removeAll();
		/*iterate through updated list of connected players and add back connectedPlayers via JLabels*/
		
	}
	
	public static void main(String [] args){
		new WaitingScreen();
	}
}
