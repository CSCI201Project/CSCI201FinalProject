package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectScreen extends JFrame {

	private static final long serialVersionUID = 8718869258020517546L;
	private JFrame frame = this;
	private PanelBackground panel;
	private JTextField playerNameTextField;
	private JTextField ipAddressTextField;
	private JTextField portTextField;
	private SoundPlayer swampSound;
	public ConnectScreen(){
		super("Connect Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
		/*play sound continuously until the player connects or quits*/
		swampSound = new SoundPlayer("sounds/swamp.wav");
		swampSound.playSoundContinuously();
	}
	
	/*Set's up the GUI and returns the panel holding the components*/
	public JPanel setupGUI(){
	    panel = new PanelBackground(0);
		panel.setLayout(null);
		
		/*Game Title*/
		JLabel titleLabel = new JLabel("Game Title");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Times New Roman",Font.BOLD,48));
		titleLabel.setBounds(280, 0, 300, 50);
		panel.add(titleLabel);
		
		/*Welcome Player Label*/
		JLabel welcomeLabel = new JLabel("Welcome to our game!");
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		welcomeLabel.setBounds(250, 100, 330, 50);
		panel.add(welcomeLabel);
		
		/*Label to instruct player to enter name */
		JLabel enterNameLabel = new JLabel("Enter your name:");
		enterNameLabel.setForeground(Color.WHITE);
		enterNameLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
		enterNameLabel.setBounds(250, 250, 150, 25);
		panel.add(enterNameLabel);
		
		/*Textfield for player to enter their name*/
	    playerNameTextField = new JTextField(50);
		playerNameTextField.setBounds(405, 250, 150, 25);
		panel.add(playerNameTextField);
		/*THIS WILL BE NEEDED LATER ON*/
		
		/*IP Address Label*/
		JLabel ipAddressLabel = new JLabel("IP:");
		ipAddressLabel.setForeground(Color.WHITE);
		ipAddressLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
		ipAddressLabel.setBounds(275, 400, 25, 25);
		panel.add(ipAddressLabel);
	
		/*IP Address TextField*/
	    ipAddressTextField = new JTextField(20);
		ipAddressTextField.setBounds(305, 400, 110, 25);
		panel.add(ipAddressTextField);
		/*THIS WILL BE NEEDED LATER ON*/
		
		/*Port Address Label*/
		JLabel portLabel = new JLabel("Port:");
		portLabel.setForeground(Color.WHITE);
		portLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
		portLabel.setBounds(420, 400, 50, 25);
		panel.add(portLabel);
		
		/*Port Text Field*/
	    portTextField = new JTextField(10);
		portTextField.setBounds(475, 400, 50, 25);
		panel.add(portTextField);
		/*THIS WILL BE NEEDED LATER ON*/
		
		/*Connect Button*/
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Times New Roman",Font.BOLD,20));
		connectButton.setBounds(275, 500, 125, 45);
		connectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				while(!validIP(ipAddressTextField.getText())){
					ipAddressTextField.setText(JOptionPane.showInputDialog(null, "Enter a valid ip address!"));
				}
				
				String ipAddress = ipAddressTextField.getText();
				//CODE TO CONNECT TO SERVER GOES HERE
				while(!isAllDigits(portTextField.getText())){
					 portTextField.setText(JOptionPane.showInputDialog(null, "Enter a valid port number!"));
				}
				
				/*if the client has gotten this far, they have entered a number for the port number*/
				int port = Integer.parseInt(portTextField.getText());
				
				
				/*try {
					
				     *COMMENTED OUT FOR TESTING PURPOSES
					 * Socket clientSocket = new Socket(ipAddressTextField.getText(),port);
					 *
				} catch (UnknownHostException e1) {
					
					System.out.println("Client could not connect: " + e1.getMessage());;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}*/
				
				
				/*After the client has connected to the server, stop the sound, and load up waiting screen*/
				swampSound.stopSound();
				//swampSound.closeAudioFile();
				frame.setVisible(false);
				
				
			}
			
		});
		panel.add(connectButton);
		/*THIS WILL BE NEEDED LATER ON*/
		
		/*Quit Button*/
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Times New Roman",Font.BOLD,20));
		quitButton.setBounds(405, 500, 125, 45);
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//QUIT GAME IF BUTTON IS PRESSED
				System.exit(0);
			}
			
		});
		panel.add(quitButton);

		return panel;
	}
	
	/*checks if the ip address is valid*/
	private static boolean validIP (String ip) {
	    try {
	        if (ip == null || ip.isEmpty()) {
	            return false;
	        }

	        String[] parts = ip.split( "\\." );
	        if ( parts.length != 4 ) {
	            return false;
	        }

	        for ( String s : parts ) {
	            int i = Integer.parseInt( s );
	            if ( (i < 0) || (i > 255) ) {
	                return false;
	            }
	        }
	        if(ip.endsWith(".")) {
	                return false;
	        }

	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}
	
	/*checks if the string parameter only contains digits*/
	private boolean isAllDigits(String portNumber){
		if(portNumber.matches("[0-9]+")){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public static void main(String [] args){
		new ConnectScreen();
		
		
			
		
	}
}
