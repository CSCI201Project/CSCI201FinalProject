package networking;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import project2.GameWindow;

public class ChatPanel extends JPanel {
	private static final long serialVersionUID = 5929911659518702196L;
	
	private GameWindow parent;
	private JPanel panel = this;
	
	private JPanel outerPanel = new JPanel(); 
	private JTextPane messageHistoryArea = new JTextPane();
	private JTextField sendNewMessageArea = new JTextField();
	
	private JPanel south = new JPanel();
	private JButton clear = new JButton("clear");
	private JButton send = new JButton("send");
	private Vector<String> playerNames = new Vector<String>();
	private JComboBox<String> playersCB = new JComboBox<String>(playerNames);
	
	private Timer reenableTimer;
	private Timer editableTimer;

	public ChatPanel(GameWindow gw) {
		this.parent = gw;
		
		//outer panel
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
			//message history area
			Border mhBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
					BorderFactory.createEmptyBorder(10, 10, 10, 10));
			messageHistoryArea.setBorder(mhBorder);
			messageHistoryArea.setDisabledTextColor(Color.BLACK);
			//messageHistoryArea.setLineWrap(true);
			messageHistoryArea.setEnabled(false);

			JScrollPane scrollPane = new JScrollPane(messageHistoryArea, 
					   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(500,500));
			
			//send new message area
			sendNewMessageArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
					BorderFactory.createEmptyBorder(0, 6, 0, 5)));
			sendNewMessageArea.setText("send a new message...");
			sendNewMessageArea.setEditable(false);
				//listeners
				sendNewMessageArea.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(!sendNewMessageArea.isEditable()) {
							sendNewMessageArea.setEditable(true);
							sendNewMessageArea.setText("");
							
							editableTimer.start();
							editableTimer.setRepeats(false);
						}
					}
				});
				
				ActionListener resetEditable = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if(sendNewMessageArea.isEditable()) {
							sendNewMessageArea.setEditable(false);
							sendNewMessageArea.setText("send a new message...");
							
							if(sendNewMessageArea.hasFocus()) parent.getFocus();
						}
					}
				};
				editableTimer = new Timer(3000, resetEditable);
				
				sendNewMessageArea.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke) {
						if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
							send.doClick();
						} else {
							editableTimer.restart();
							editableTimer.setRepeats(false);
						}
					}
				});
			
			//south panel
			south.setLayout(new BorderLayout());
				//send button action listeners
				ActionListener reenableButton = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						send.setEnabled(true);
					}
				};
				reenableTimer = new Timer(500, reenableButton);
				ActionListener sendMessage = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(sendNewMessageArea.isEditable()) {
							String message = sendNewMessageArea.getText();
							
							if(!message.equals("")) {
								sendNewMessageArea.setText("");
								
								String newChatMessage = "me to " + playersCB.getSelectedItem() + ": " + message + "\n";
								messageHistoryArea.setText(messageHistoryArea.getText() + newChatMessage);
								
								//add networking stuff here
								if(playersCB.getSelectedIndex() == 0) {
									gw.sendMessageAll(message);
								} else {
									gw.sendMessageTo((String) playersCB.getSelectedItem(), message);
								}
								
								send.setEnabled(false);
								reenableTimer.start();
								reenableTimer.setRepeats(false);
								
								editableTimer.restart();
								editableTimer.setRepeats(false);
							} else {
								//JOptionPane.showMessageDialog(panel, "Can not send empty messages! Please type some words");
								send.setEnabled(false);
								reenableTimer.start();
								reenableTimer.setRepeats(false);
							}
						}
					}
				};
				send.addActionListener(sendMessage);
	
				//clear button action listeners
				clear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendNewMessageArea.setEditable(false);
						sendNewMessageArea.setText("send a new message...");
					}
				});
				
				//players combo box
				playerNames.add("All");
				((JLabel) playersCB.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
				playersCB.setSelectedIndex(0);
			south.add(clear, BorderLayout.WEST);
			south.add(send, BorderLayout.EAST);
			south.add(playersCB, BorderLayout.CENTER);
		//adds
		outerPanel.add(scrollPane);
		outerPanel.add(sendNewMessageArea);
		outerPanel.add(south);

		this.setLayout(new BorderLayout());
		this.add(outerPanel, BorderLayout.CENTER);
	}
	
	public void addPlayerName(String name) {
		playerNames.add(name);
		playersCB.updateUI();
		((JLabel) playersCB.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void receiveMessage(String sender, String message) {
		String newChatMessage = sender + ": " + message + "\n";
		messageHistoryArea.setText(messageHistoryArea.getText() + newChatMessage);
	}
}
