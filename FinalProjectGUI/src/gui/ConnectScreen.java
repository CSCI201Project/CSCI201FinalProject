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
		welcomeLabel.setBounds(250, 0, 330, 250);
		panel.add(welcomeLabel);
		
		
		return panel;
	}
	
	
	public static void main(String [] args){
		new ConnectScreen();
	}
}
