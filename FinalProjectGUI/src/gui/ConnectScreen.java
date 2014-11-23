package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class ConnectScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8718869258020517546L;


	public ConnectScreen(){
		this.setTitle("Connect Screen");
		this.setSize(800, 600);
		///this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(setupGUI());
		this.setVisible(true);
	}
	
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
		JTextField playerNameTextField = new JTextField(50);
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
		JTextField ipAddressTextField = new JTextField(20);
		ipAddressTextField.setBounds(305, 400, 110, 25);
		panel.add(ipAddressTextField);
		/*THIS WILL BE NEEDED LATER ON*/
		
		/*Port Address Label*/
		JLabel portLabel = new JLabel("Port:");
		portLabel.setForeground(Color.WHITE);
		portLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
		portLabel.setBounds(420, 400, 50, 25);
		panel.add(portLabel);
		
		
		
		return panel;
	}
	
	
	public static void main(String [] args){
		new ConnectScreen();
	}
}
