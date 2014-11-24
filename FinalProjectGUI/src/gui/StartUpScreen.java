package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class StartUpScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7055888233799252581L;
	private JLabel characterLabel;

	public StartUpScreen(){
		super("Start Up Screen");
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
		ImageIcon temporaryImage = new ImageIcon("tempZombieSprite.png");
		JLabel characterImageLabel = new JLabel(new ImageIcon(this.resizeImage(temporaryImage,100,100)));
		characterImageLabel.setBounds(580, 100, 100, 100);
		panel.add(characterImageLabel);
		
		/*Surviors label*/
		JLabel surviorLabel = new JLabel("Survivors");
		surviorLabel.setForeground(Color.WHITE);
		surviorLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		surviorLabel.setBounds(200, 225, 275, 50);
		panel.add(surviorLabel);
		
		/*Zombies label*/
		JLabel zombieLabel = new JLabel("Zombies");
		zombieLabel.setForeground(Color.WHITE);
		zombieLabel.setFont(new Font("Times New Roman",Font.BOLD,32));
		zombieLabel.setBounds(500, 225, 275, 50);
		panel.add(zombieLabel);
		
		
		
		return panel;
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
		 new StartUpScreen();
	}
}
