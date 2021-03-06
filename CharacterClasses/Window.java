package project2;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	private PlayingField pf;
	
	public Window(PlayingField pf){
		this.setSize(800,600);
		this.setMinimumSize(new Dimension(800,600));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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


