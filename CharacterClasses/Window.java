package project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame{
	private PlayingField pf;
	
	Window(PlayingField pf){
		this.setSize(800,600);
		this.setMinimumSize(new Dimension(800,600));
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


