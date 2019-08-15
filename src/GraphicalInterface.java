import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GraphicalInterface extends JFrame implements WindowListener, MouseListener {
    
    private JTextField playerName;
	private JPasswordField password;
	private JButton newGame;
	private JRadioButton token;
	// roll button
	// add player button
	//name
	// character selection
	
	private ArrayList<String> characters;
    
    public GraphicalInterface(){
        super("Cluedo");
		setLayout(new FlowLayout());
		characters = new ArrayList<String>();
		characters.add("SCARLETT");
        characters.add("PLUM");
        characters.add("GREEN");
        characters.add("WHITE");
        characters.add("PEACOCK");
        characters.add("MUSTARD");
    }
    
    public void addPlayer() {
		JLabel player = new JLabel("Player name: "); 
		playerName = new JTextField(30);
		JLabel tokenName = new JLabel("Choose a token: ");
		add(player);
		add(playerName);
		add(tokenName);
		for(int i = 0; i < characters.size(); i++) {
			token = new JRadioButton();
			token.setText(characters.get(i)); 
			add(token);
		}
		JButton confirm = new JButton("Confirm player");
		add(confirm);	
	}
	public void howMany() {
		JPanel panel = new JPanel();
		this.setPreferredSize(new Dimension(100, 50));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		JLabel noOfPlayers = new JLabel("How many players: ");
		JTextField players = new JTextField(10);
		panel.add(noOfPlayers);	
		panel.add(players);
		JButton confirmNoOfPlayers = new JButton("Confirm");
		panel.add(confirmNoOfPlayers);
		add(panel);			
	}
	
	
	
}


import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class tester {
	
	public static void main(String[] arg) {
		
		GraphicalInterface yeee = new GraphicalInterface();
		
		yeee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		yeee.setSize(500, 500);
		yeee.setVisible(true);
	
		
	}
}