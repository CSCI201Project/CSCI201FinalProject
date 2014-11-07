package chat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class client extends JFrame implements Runnable{

	private PrintWriter pw;
	private BufferedReader br;
	private Thread t  = new Thread(this);
	private JPanel outerPanel = new JPanel();
	private JLabel firstLabel = new JLabel("10/10 HEALTH");
	private JLabel secondLabel = new JLabel("Waiting for other player...");
	private JButton swordButton = new JButton("Sword");
	private JButton magicButton = new JButton("Magic");
	private String line = ""; 
	private int player1HP=10;
	private int player2HP=10;
	private int damage;
	private JTextArea messageHistoryArea = new JTextArea();
	private JTextField sendNewMessageArea = new JTextField();
	private JPanel south = new JPanel();
	private JButton clear = new JButton("clear");
	private JButton send = new JButton("send");
	Random rd = new Random();
	JFrame frame = this;

	public static void main(String [] args) {

		Scanner scan = new Scanner(System.in);
//				new client("", 1, scan);
		System.out.print("What is the name/IP of the server? ");
		String hostname = scan.nextLine();
		System.out.print("What is the port? ");
		int port = scan.nextInt();
		new client(hostname, port, scan);
	}


	public client(String hostname, int port, Scanner scan) {
		super("Chat Software");
		try {
			Socket s = new Socket(hostname, port);
			this.pw = new PrintWriter(s.getOutputStream());
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			t.start();

			setResizable(false);
			setSize(600,400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
			Border mhBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10));
			messageHistoryArea.setBorder(mhBorder);
			messageHistoryArea.setEnabled(false);
			messageHistoryArea.setDisabledTextColor(Color.BLACK);
			JScrollPane scrollPane = new JScrollPane (messageHistoryArea, 
					   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setPreferredSize(new Dimension(500,300));
			outerPanel.add(scrollPane);
			sendNewMessageArea.setPreferredSize(new Dimension(500,20));
			sendNewMessageArea.setText("Send new message by typing here...");
			sendNewMessageArea.setEditable(false);
			sendNewMessageArea.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					sendNewMessageArea.setEditable(true);
					sendNewMessageArea.setText("");
					
				}
			});
			
			outerPanel.add(scrollPane);
			outerPanel.add(sendNewMessageArea);
			south.setLayout(new BorderLayout());
			south.add(clear, BorderLayout.WEST);
			south.add(send, BorderLayout.EAST);
			outerPanel.add(south);

			ActionListener reenableButton = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					send.setEnabled(true);

				}
			};
			Timer timer = new Timer(500, reenableButton);
			ActionListener sendMessage = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					line = sendNewMessageArea.getText();
					sendNewMessageArea.setText("");
					messageHistoryArea.append("From me: " + line + "\n");
					
					send.setEnabled(false);
					timer.start();
					timer.setRepeats(false);

					if(line!=null){
						pw.println(line);
						pw.flush();
					}
					else{
						JOptionPane.showMessageDialog (frame, "Can not send empty messages! Please put in some words");
					}
				}
			};
			send.addActionListener(sendMessage);



			clear.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					sendNewMessageArea.setText("");
				}
			});



			this.setLayout(new BorderLayout());
			this.add(outerPanel, BorderLayout.CENTER);
			setVisible(true);

		} 
		catch (IOException ioe) {
			System.out.println("ioe in ChatClient: " + ioe.getMessage());
		}


	}

	public void run() {
		try {
			while(true) {

				String line = br.readLine();
				if(line.equals("CONNECTED")){
					System.out.println("client connected");
				}
				else if(!line.equals(null)){
					if(!line.equals("null")){
					messageHistoryArea.append("From Other Party: "+line+ "\n");
					}
					else{
						System.out.println("The other client disconnected, disconnecting...");
						break;
					}
				}
				else {

				}

			} 
		}

		catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
}
