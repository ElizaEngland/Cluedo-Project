import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.border.Border;

import javax.swing.ImageIcon;

public class GraphicalInterface extends JFrame{
    
    private JTextField playerName;
	private JPasswordField password;
	private JButton newGame;
	private JRadioButton token;
	private int BOARDHEIGHT = 1000;
	private int BOARDWIDTH = 1000;
	private ImageIcon img = new ImageIcon("images/Hall.jpg");

	// JFram
	static JFrame f;

	// JButton
	static JButton b, b1, b2, b3;

	// label to diaplay text
	static JLabel l;
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

     	mainFrame();

	}


	public void mainFrame(){
        Border blackline = BorderFactory.createLineBorder(Color.black);
    	JFrame mainFrame = new JFrame();

    	//menu
    	JMenuBar menuBar = new JMenuBar();
		JMenu menu  = new JMenu("File");
		JMenuItem m1 = new JMenuItem("New Game");
		JMenuItem m2 = new JMenuItem("Exit");
		menu.add(m1);
		menu.add(m2);
		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);

		// column
		JPanel p1 = new JPanel();
		p1.setLayout( new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.setBorder(blackline);
		p1.setPreferredSize(new Dimension(BOARDWIDTH/5, BOARDHEIGHT));

		//buttons
		JButton roll = new JButton("ROLL");
		roll.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton suggestion = new JButton("SUGGESTION");
		suggestion.setAlignmentX(Component.CENTER_ALIGNMENT);
		suggestion.addActionListener( new ActionListener() {
		   public void actionPerformed(ActionEvent e)
		   {
			   makeSuggestion();
		   }
	   	}
		);
		JButton accusation = new JButton("ACCUSTATION");
		accusation.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1.add(roll);
		p1.add(suggestion);
		p1.add(accusation);


		// bottom
		JPanel p2= new JPanel();
		p2.setLayout( new BorderLayout());
		p2.setBorder(blackline);
		p2.setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT/5));

		JPanel p2a = new JPanel();
		p2a.setLayout( new FlowLayout());
		p2a.setBorder(blackline);
		p2a.setPreferredSize(new Dimension(700, BOARDHEIGHT/10));
		p2.add( p2a, "East");

		JPanel p2b = new JPanel();
		p2b.setLayout( new FlowLayout());
		p2b.setBorder(blackline);
		p2b.setPreferredSize(new Dimension(BOARDWIDTH/2, BOARDHEIGHT/10));
		for(String name : images){ // draws the hand
			ImageIcon i = drawHand(name);
			p2a.add(new JLabel(i));
		}
		p2.add( p2b, "West");

		// graph
		JPanel p3 = drawBoard();

		//add(p3);

		mainFrame.getContentPane().add( p1, "West");
		mainFrame.getContentPane().add( p2, "South");
		mainFrame.getContentPane().add(p3,"Center");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(BOARDWIDTH, BOARDHEIGHT);
		mainFrame.setVisible(true);
	

	}
	public void mainMenu(){
    	JFrame f = new JFrame();
		JPanel panel = new JPanel();
		//panel.setLayout(new GridBagLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel intro = new JLabel("Welcome to Cluedo!");
		intro.setFont(new Font("Serif", Font.PLAIN, 24));
		intro.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(intro);
		JButton newGame = new JButton("New Game");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton exit = new JButton("Exit");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(newGame);
		panel.add(exit);
		f.add(panel);

		newGame.addActionListener( new ActionListener() {
		   public void actionPerformed(ActionEvent e)
		   {
			   howMany();
		   }
	   }
		);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(350, 200);
		f.setVisible(true);
	}
    
    public void addPlayer() {
    	JFrame frame2 = new JFrame();
    	JPanel panel = new JPanel();

		JLabel player = new JLabel("Player name: ");
		JLabel tokenName = new JLabel("Choose a token: ");

		playerName = new JTextField(30);

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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel noOfPlayers = new JLabel("How many players: ");
		noOfPlayers.setFont(new Font("Serif", Font.PLAIN, 24));
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

	public void makeSuggestion(){

		f = new JFrame("panel");
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		JTextField b1 = new JTextField(30);
		JTextField b2 = new JTextField(30);
		JTextField b3 = new JTextField(30);

		JLabel l = new JLabel("Make A Suggestion");
		l.setFont(new Font("Serif", Font.PLAIN, 30));
		JLabel persontxt = new JLabel("Person: ");
		persontxt.setFont(new Font("Serif", Font.PLAIN, 24));
		JLabel weapontxt = new JLabel("Weapon: ");
		weapontxt.setFont(new Font("Serif", Font.PLAIN, 24));
		JLabel roomtxt = new JLabel("Room: ");
		roomtxt.setFont(new Font("Serif", Font.PLAIN, 24));

		p.add(l);
		p.add(persontxt);
		p.add(b1);
		p.add(weapontxt);
		p.add(b2);
		p.add(roomtxt);
		p.add(b3);
		f.add(p);
		f.setSize(400, 250);
		f.setVisible(true);
	}
	
		public ImageIcon drawHand(String imgName){
		BufferedImage lhimg = null;
		try {
			lhimg = ImageIO.read(new File("images/" +imgName +""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image limg = lhimg.getScaledInstance(BOARDWIDTH/10, BOARDHEIGHT/6, Image.SCALE_SMOOTH);
		ImageIcon imaglIcon = new ImageIcon(limg);


		return imaglIcon;
	}
	
	public ImageIcon resize(String imgName){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/" +imgName +""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image imgs = img.getScaledInstance(31,28, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(imgs);
		return imageIcon;
	}
	
	
	public JPanel drawBoard(){
		JPanel p3 = new JPanel();
		ImageIcon[][] grid;

		addTile();

		//p3.setBorder(blackline);
		int width = 25;
		int height = 26;
		p3.setLayout(new GridLayout(width,height));

		grid=new ImageIcon[width][height]; //allocate the size of grid
		for(Tile tile : tiles){
			grid[tile.getX()][tile.getY()] = tile.getImage();
			p3.add(new JLabel(grid[tile.getX()][tile.getY()])); //adds button to grid

		}
		return p3;
	}
	


	public void addTile(){
		try {
			FileReader fr = new FileReader("boardmapGUI.txt");
			BufferedReader dataReader = new BufferedReader(fr);

			String currentLine;
			while ((currentLine = dataReader.readLine()) != null) {
				String[] loadArray = currentLine.split(","); // splits the lines by the comma into an array for each line

				if (loadArray.length > 2) {
					String value = loadArray[0];
					if (value == null) {
						break;
					}
					int col = Integer.parseInt(loadArray[1]); // col position
					int row = Integer.parseInt(loadArray[2]); // row position


					if(value.equals("x")){ // if a room
						ImageIcon r = resize("outOfBoundsTile.jpg");
						Tile newTile = new Tile(r,false,false,false,true,row,col);
						tiles.add(newTile);
					}
					else if(value.equals("h")) {
                        ImageIcon r = resize("hallwayTile.jpg");
                        Tile newTile = new Tile(r, false, false, false, true, row, col);
                        tiles.add(newTile);
                    }
					else if(value.equals("D")){
						ImageIcon r = resize("doorTile.jpg");
						Tile newTile = new Tile(r,false,false,true,false,row,col);
						tiles.add(newTile);
					}
					else{
						ImageIcon r = resize("roomTile.jpg");
						Tile newTile = new Tile(r,true,false,false,false, row,col);
						tiles.add(newTile);
					}

				}
			}

		} catch (IOException e) {
			System.out.println(e);
		}

	}
	
}
