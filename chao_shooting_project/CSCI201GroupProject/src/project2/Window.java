package project2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Networking.ChatClient;

public class Window extends JFrame{
	private PlayingField pf;
	private JLabel label = new JLabel("dsadasd");
//	public JFrame frame = this;
	Window(PlayingField pf){
		this.setLayout(new BorderLayout());
		this.setSize(1000,600);
		this.setLocation(100, 50);
		this.setMinimumSize(new Dimension(800,600));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ChatClient chatPanel = new ChatClient("localhost", 5555);
//		chatPanel.add(label);
//		chatPanel.setPreferredSize(new Dimension(300,300));
//		this.add(chatPanel);
		
		System.out.println("width and height: " + this.getWidth() + " " + this.getPreferredSize().height);
		chatPanel.setPreferredSize(new Dimension(300,this.getHeight()));
//		chatPanel.setPreferredSize(new Dimension(300,600));
		this.add(chatPanel, BorderLayout.EAST);
		
		this.pf = pf;
		this.add(this.pf);
		Thread t = new Thread(pf);

		this.setVisible(true);
		t.start();
	}

	public static void main(String [] args){
		new Window(new PlayingField());
		
	}
}


