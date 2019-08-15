import javax.swing.JPanel;

import java.util.ArrayList;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import javax.swing.JRadioButton;


public class GraphicalInterface extends JFrame{
    
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


		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(Color.lightGray);
		panel.setLayout(new GridBagLayout());
		JLabel sugg = new JLabel("Make A Suggestion");
		JLabel persontxt = new JLabel("Person: ");
		JTextField person = new JTextField(30);
		JLabel weapontxt = new JLabel("Weapon: ");
		JTextField weapon = new JTextField(30);
		JLabel roomtxt = new JLabel("Room: ");
		JTextField room = new JTextField(30);

        panel.add(sugg);
		panel.add(persontxt);
		panel.add(person);
		panel.add(weapontxt);
		panel.add(weapon);
		panel.add(roomtxt);
		panel.add(room);
		add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}

	public void mainMenu(){
		JPanel panel = new JPanel();

		JLabel intro = new JLabel("Welcome to Cluedo!");
		panel.add(intro);
		JButton newGame = new JButton("New Game");
		JButton exit = new JButton("Exit");
		panel.add(newGame);
		panel.add(exit);
		add(panel);

		newGame.addActionListener( new ActionListener() {
		   public void actionPerformed(ActionEvent e)
		   {
			   howMany();
		   }
	   }
		);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}
    
    public void addPlayer() {
    	JFrame frame2 = new JFrame();
    	JPanel panel = new JPanel();
		JLabel player = new JLabel("Player name: "); 
		playerName = new JTextField(30);
		JLabel tokenName = new JLabel("Choose a token: ");
		panel.add(player);
		panel.add(playerName);
		panel.add(tokenName);
		for(int i = 0; i < characters.size(); i++) {
			token = new JRadioButton();
			token.setText(characters.get(i)); 
			panel.add(token);
		}
		JButton confirm = new JButton("Confirm player");
		panel.add(confirm);
		frame2.add(panel);


		confirm.addActionListener( new ActionListener() {
		  public void actionPerformed(ActionEvent e)
		  {
			  addPlayer();
		  }
	  	}
		);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(500, 500);
		frame2.setVisible(true);

	}
	public void howMany() {
    	JFrame frame1 = new JFrame();
		JPanel panel = new JPanel();

		JLabel noOfPlayers = new JLabel("How many players: ");
		JTextField players = new JTextField(10);
		panel.add(noOfPlayers);
		panel.add(players);
		JButton confirmNoOfPlayers = new JButton("Confirm");
		panel.add(confirmNoOfPlayers);
		frame1.add(panel);

		confirmNoOfPlayers.addActionListener( new ActionListener() {
		   public void actionPerformed(ActionEvent e)
		   {
			   addPlayer();
		   }
	   	}
		);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(500, 200);
		frame1.setVisible(true);


	}



	
	
}
