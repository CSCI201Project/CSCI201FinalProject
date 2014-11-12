package gui;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField ipAddressTextField;
	private JTextField portNumberTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setSize(new Dimension(800,600));
		setTitle("GUI\r\n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(800, 600));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel loginScreenPanel = new JPanel();
		tabbedPane.add("Login Screen GUI", loginScreenPanel);
		loginScreenPanel.setLayout(null);
		
		JLabel gameTitleLabel = new JLabel("GAME TITLE");
		gameTitleLabel.setBounds(288, 11, 202, 38);
		gameTitleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
		loginScreenPanel.add(gameTitleLabel);
		
		JLabel welcomePlayerLabel = new JLabel("Welcome to our game!");
		welcomePlayerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		welcomePlayerLabel.setBounds(293, 165, 217, 27);
		loginScreenPanel.add(welcomePlayerLabel);
		
		JLabel enterNameLabel = new JLabel("Enter your name:");
		enterNameLabel.setBounds(273, 289, 110, 27);
		loginScreenPanel.add(enterNameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(397, 292, 127, 20);
		loginScreenPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel ipAddressLabel = new JLabel("IP:");
		ipAddressLabel.setBounds(243, 429, 46, 14);
		loginScreenPanel.add(ipAddressLabel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(243, 495, 299, 38);
		loginScreenPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton connectButton = new JButton("Connect");
		buttonPanel.add(connectButton);
		
		JButton quitButton = new JButton("Quit");
		buttonPanel.add(quitButton);
		
		ipAddressTextField = new JTextField();
		ipAddressTextField.setText("Enter IP Address");
		ipAddressTextField.setBounds(278, 426, 105, 20);
		loginScreenPanel.add(ipAddressTextField);
		ipAddressTextField.setColumns(10);
		
		JLabel portLabel = new JLabel("Port:");
		portLabel.setBounds(393, 429, 46, 14);
		loginScreenPanel.add(portLabel);
		
		portNumberTextField = new JTextField();
		portNumberTextField.setText("Enter Port#");
		portNumberTextField.setBounds(438, 426, 86, 20);
		loginScreenPanel.add(portNumberTextField);
		portNumberTextField.setColumns(10);
		
		//WAITING SCREEN PANEL
		JPanel waitingScreenPanel = new JPanel();
		tabbedPane.addTab("Waiting Screen GUI", null, waitingScreenPanel, null);
		waitingScreenPanel.setLayout(null);
		
		JButton playButton = new JButton("Play!");
		playButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		playButton.setBounds(351, 11, 119, 51);
		waitingScreenPanel.add(playButton);
		
		JComboBox<String> timeLimitCB = new JComboBox<String>();
		timeLimitCB.setBounds(384, 101, 119, 20);
		for(int i = 1; i <=5 ;i++){
			timeLimitCB.addItem(i + " minutes");
		}
		waitingScreenPanel.add(timeLimitCB);
		
		JLabel timeLimitLabel = new JLabel("Time Limit:");
		timeLimitLabel.setBounds(300, 104, 74, 14);
		waitingScreenPanel.add(timeLimitLabel);
		
		JLabel waitingForOtherPlayersLabel = new JLabel("Waiting for other players to connect . . . ");
		waitingForOtherPlayersLabel.setBounds(300, 143, 254, 20);
		waitingScreenPanel.add(waitingForOtherPlayersLabel);
		
		JLabel connectedPlayersLabel = new JLabel("Connected Players. . .");
		connectedPlayersLabel.setBounds(351, 248, 119, 14);
		waitingScreenPanel.add(connectedPlayersLabel);
		
		JPanel connectedPlayersPanel = new JPanel();
		connectedPlayersPanel.setBounds(334, 279, 147, 129);
		waitingScreenPanel.add(connectedPlayersPanel);
		
		JButton quitWaitingScreenButton = new JButton("Quit");
		quitWaitingScreenButton.setBounds(370, 430, 89, 51);
		waitingScreenPanel.add(quitWaitingScreenButton);
		
		for(int i = 0; i < 4;i++){
			JLabel playerLabel = new JLabel("Player #" + i + " has connected");
			connectedPlayersPanel.add(playerLabel);
		}
		
		
	}
}
