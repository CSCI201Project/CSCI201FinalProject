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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8718869258020517546L;
	
	private JTextField playerNameTextField;
	private JTextField ipAddressTextField;
	private JTextField portTextField;

	public ConnectScreen(){
		super("Connect Screen");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
		
		try{
			 AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("swamp.wav"));
		        Clip clip = AudioSystem.getClip();
		        clip.open(inputStream);
		        clip.loop(Clip.LOOP_CONTINUOUSLY);
		        Thread.sleep(10000); // looping as long as this thread is alive
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/*Set's up the GUI and returns the panel holding the components*/
	public JPanel setupGUI(){
		PanelBackground panel = new PanelBackground(0);
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
				//CODE TO CONNECT TO SERVER GOES HERE
				while(!isAllDigits(portTextField.getText())){
					 portTextField.setText(JOptionPane.showInputDialog(null, "Enter a valid port number!"));
				}
				int port = Integer.parseInt(portTextField.getText());
				/*try {
					
				     *COMMENTED OUT FOR TESTING PURPOSES
					 * Socket clientSocket = new Socket(ipAddressTextField.getText(),port);
					 *
				} catch (UnknownHostException e1) {
					
					System.out.println("Client could not connect: " + e1.getMessage());;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
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
