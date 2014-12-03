package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import networking.ZombieGameClient;
import networking.ZombieGameServer;
import project2.GameWindow;
import project2.PlayingField;

public class StartUpScreen extends JFrame implements Nextable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7055888233799252581L;
	private JLabel characterLabel;
	
	private boolean isHost;
	private ZombieGameServer server;
	private ZombieGameClient client;

	public StartUpScreen(boolean isHost) {
		super("Start Up Screen");
		
		this.isHost = isHost;
		
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(setupGUI());
		this.setVisible(true);
	}
	
	public JPanel setupGUI(){
		PanelBackground panel = new PanelBackground(2);
		panel.setLayout(null);
		
		/*Label to inform client whether they are a zombie or a survivor
		 * NOTE:THIS WILL NEED TO BE MODIFIED ONCE THE NETWORKING HAS BEEN IMPLEMENTED*/
		this.characterLabel = new JLabel("You are a " + "zombie!");
		characterLabel.setForeground(Color.WHITE);
		characterLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		characterLabel.setBounds(280, 100, 275, 50);
		panel.add(characterLabel);
		
		/*Image to display to client indicating displaying that they are a zombie or survior*/
		ImageIcon temporaryImage = new ImageIcon("images/tempZombieSprite.png");
		JLabel characterImageLabel = new JLabel(new ImageIcon(this.resizeImage(temporaryImage,100,100)));
		characterImageLabel.setBounds(580, 100, 100, 100);
		panel.add(characterImageLabel);
		
		/*Survivors label*/
		JLabel surviorsLabel = new JLabel("Survivors");
		surviorsLabel.setForeground(Color.WHITE);
		surviorsLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		surviorsLabel.setBounds(200, 225, 275, 50);
		panel.add(surviorsLabel);
		
		/*Zombies label*/
		JLabel zombiesLabel = new JLabel("Zombies");
		zombiesLabel.setForeground(Color.WHITE);
		zombiesLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		zombiesLabel.setBounds(500, 225, 275, 50);
		panel.add(zombiesLabel);
		
		/*Survivor Panel
		 * Put in temporary info for list of survivors since we do not have actual data yet*/
		JPanel survivorPanel = new JPanel();
		survivorPanel.setLayout(new BoxLayout(survivorPanel,BoxLayout.Y_AXIS));
		survivorPanel.setOpaque(false);
		for(int i = 0; i < 2; i++){
			JLabel surviorLabel = new JLabel("Player#" + i + " IP: 192.168.1.10" + i);
			surviorLabel.setForeground(Color.WHITE);
			surviorLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
			survivorPanel.add(surviorLabel);
		}
		survivorPanel.setBounds(200,275, 200, 100);
		panel.add(survivorPanel);
		
		
		/*Zombie Panel
		 * Put in temporary info for list of survivors since we do not have actual data yet*/
		JPanel zombiePanel = new JPanel();
		zombiePanel.setLayout(new BoxLayout(zombiePanel,BoxLayout.Y_AXIS));
		zombiePanel.setOpaque(false);
		for(int i = 3; i <= 4; i++){
			JLabel zombieLabel = new JLabel("Player#" + i + " IP: 192.168.1.10" + i);
			zombieLabel.setForeground(Color.WHITE);
			zombieLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
			zombiePanel.add(zombieLabel);
		}
		zombiePanel.setBounds(500,275, 200, 100);
		panel.add(zombiePanel);
		
		/*Waiting for host label*/
		BlinkLabel waitingLabel = new BlinkLabel("Waiting for host to start the game .  .  . ");
		waitingLabel.setForeground(Color.WHITE);
		waitingLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
		waitingLabel.setBounds(200, 375, 500, 50);
		waitingLabel.setBlinking(true);
		panel.add(waitingLabel);
		
		/*Start Button, only host should be able to click on this, use a flag to see if client
		 * is a host, if client is a host, enable access to button, otherwise they cannot cick
		 * on the button
		 */
		JButton startButton = new JButton("Start");
		/*if(isHost){
			startButton.setEnabled(true);
		}
		else{
			startButton.setEnabled(false);
		}*/
		startButton.setFont(new Font("Times New Roman",Font.BOLD,32));
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//START GAME HERE
				
				setVisible(false);
				
				if(isHost) {
					server.nextScreen();
				}
				
				GameWindow gw = new GameWindow(new PlayingField());
				gw.setServer(server);
			}
			
		});
		startButton.setBounds(250, 475, 200, 75);
		startButton.setEnabled(isHost);
		panel.add(startButton);
		
		/*Quit Button, exits the program*/
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Times New Roman",Font.BOLD,32));
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		quitButton.setBounds(500, 475, 150, 75);
		panel.add(quitButton);
		return panel;
	}
	
	public void setServer(ZombieGameServer zgs) {
		this.server = zgs;
	}
	public void setClient(ZombieGameClient zgc) {
		this.client = zgc;
		client.setFrame(this);
	}
	public void nextScreen() {
		setVisible(false);
	
		GameWindow gw = new GameWindow(new PlayingField());
		gw.setClient(client, server);
	}
	
	/*takes in a ImageIcon, and the desired scaled dimensions, and returns the scaled image of the
	 * image passed in*/
	public Image resizeImage(ImageIcon backGround, int resizedImageWidth, int resizedImageHeight){
		Image image = backGround.getImage();
		Image newImage = image.getScaledInstance(resizedImageWidth, resizedImageHeight, Image.SCALE_SMOOTH);
		backGround = new ImageIcon(newImage);
		return backGround.getImage();
	}
	
	public static void main(String [] args){
		 new StartUpScreen(true);
	}
}
