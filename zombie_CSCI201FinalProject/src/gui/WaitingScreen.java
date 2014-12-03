package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import networking.ZombieGameClient;
import networking.ZombieGameServer;
import networking.ZombieThread;

public class WaitingScreen extends JFrame implements Nextable {
	private static final long serialVersionUID = 1164599519412930761L;

	PanelBackground panel;
	
	private JButton playButton;
	private JComboBox<String> timeLimitCB;
	private JPanel connectedPlayersPanel;
	private SoundPlayer ambienceSound;
	
	private boolean isHost = false;
	private ZombieGameServer server;
	private ZombieGameClient client;
	
	public WaitingScreen(boolean isHost) {
		super("Waiting Screen");
		
		this.isHost = isHost;
		
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
		
		ambienceSound = new SoundPlayer("sounds/waitingScreenAmbience.wav");
	    ambienceSound.playSoundContinuously();
	}
	
	public JPanel setupGUI(){
		panel = new PanelBackground(1);
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
		playButton.setEnabled(this.isHost);
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {				
				ambienceSound.stopSound();
				ambienceSound.closeAudioFile();
				setVisible(false);
				
				server.nextScreen();
				
				StartUpScreen sus = new StartUpScreen(true);
				sus.setServer(server);
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
		 for(int i = 1; i <= 5; i++){
			 timeLimitCB.addItem(i + " Minutes");
		 }
		 timeLimitCB.setSelectedIndex(timeLimitCB.getItemCount()-1);
		 
		 timeLimitCB.setFont(new Font("Times New Roman",Font.BOLD,15));
		 timeLimitCB.setBounds(400, 100, 150, 50);
		 timeLimitCB.setEnabled(this.isHost);
		 panel.add(timeLimitCB);
		
		 /*Label to indicate that server is waiting for players to connect*/
		 BlinkLabel waitingForPlayersLabel = new BlinkLabel("Waiting for other players to connect. . .");
		 waitingForPlayersLabel.setForeground(Color.WHITE);
		 waitingForPlayersLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		 waitingForPlayersLabel.setBounds(250, 150, 400, 50);
		 waitingForPlayersLabel.setBlinking(true);
		 panel.add(waitingForPlayersLabel);
		
		 /*connected players label*/
		 JLabel connectedPlayersLabel = new JLabel("Connected players. . .");
		 connectedPlayersLabel.setForeground(Color.WHITE);
		 connectedPlayersLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		 connectedPlayersLabel.setBounds(315, 250, 200, 50);
		 panel.add(connectedPlayersLabel);
		 
		 /*connected players list,
		  * have a list of connected players, update the list every time someone connects
		  */
		 connectedPlayersPanel = new JPanel();
		 connectedPlayersPanel.setLayout(new BoxLayout(this.connectedPlayersPanel,BoxLayout.Y_AXIS));
		 connectedPlayersPanel.setOpaque(false);
		 connectedPlayersPanel.setBounds(285, 300, 400, 100);
		 
		 /*Quit Button*/
		 JButton quitButton = new JButton("Quit");
		 quitButton.setFont(new Font("Times New Roman",Font.BOLD,48));
		 quitButton.setBounds(300, 482, 200, 75);
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
	
	public void setServer(ZombieGameServer zgs) {
		this.server = zgs;
		this.server.setFrame(this);
	}
	public void setClient(ZombieGameClient zgc) {
		this.client = zgc;
		this.client.setFrame(this);
	}
	
	public void nextScreen() {
		ambienceSound.stopSound();
		ambienceSound.closeAudioFile();
		setVisible(false);
	
		StartUpScreen sus = new StartUpScreen(false);
		sus.setClient(client);
	}
	
	public void updateConnectedPlayersList() {
		this.connectedPlayersPanel.removeAll();
		/*iterate through updated list of connected players and add back connectedPlayers via JLabels*/
		if(this.server != null) {
			for(ZombieThread zt : this.server.getConnections()) {
				JLabel label = new JLabel(zt.getPlayerName() + " IP: " + zt.getIP());
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Times New Roman",Font.BOLD,20));
				connectedPlayersPanel.add(label);
			}
			panel.add(connectedPlayersPanel);
		}
	}
	
	public static void main(String [] args){
		new WaitingScreen(true);
	}
}
