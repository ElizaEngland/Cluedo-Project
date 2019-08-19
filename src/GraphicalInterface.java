
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.border.Border;

import javax.swing.ImageIcon;

public class GraphicalInterface extends JFrame{
    
    private JTextField playerName;
	private JRadioButton token;
	private int BOARDHEIGHT = 1000;
	private int BOARDWIDTH = 1000;
	private ArrayList<String> images = new ArrayList<String>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Map<String, List<Integer>> startingPos = new HashMap<String,List<Integer>>();

    private ImageIcon diceOne;
    private ImageIcon diceTwo;
    private int addPlayerCounter;
    private int playerAmount;
    private ArrayList<String> characters;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private ArrayList<JRadioButton> charButtons;

    public cluedoMain mainCluedo;

    
    public GraphicalInterface(cluedoMain cluedoMainGame){
        super("Cluedo");
		setLayout(new FlowLayout());
		characters = new ArrayList<String>();
		characters.add("SCARLETT");
        characters.add("PLUM");
        characters.add("GREEN");
        characters.add("WHITE");
        characters.add("PEACOCK");
        characters.add("MUSTARD");

        images.add("dining.jpg");
		images.add("dagger.jpg");
		images.add("pipe.jpg");
		images.add("white.jpg");
		images.add("rope.jpg");
		images.add("conservatory.jpg");

        // create new JButton
        JRadioButton scarlett = new JRadioButton("Scarlett");
        JRadioButton green = new JRadioButton("Green");
        JRadioButton white = new JRadioButton("White");
        JRadioButton peacock = new JRadioButton("Peacock");
        JRadioButton mustard = new JRadioButton("Mustard");
        JRadioButton plum = new JRadioButton("Plum");
        // add buttons
        buttonGroup.add(scarlett);
        buttonGroup.add(green);
        buttonGroup.add(white);
        buttonGroup.add(peacock);
        buttonGroup.add(mustard);
        buttonGroup.add(plum);

//        mainCluedo = new cluedoMain(); // edited
        mainFrame();
        howMany();
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
        JLabel playerName = new JLabel("Elizas turn");
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton roll = new JButton("ROLL");
		roll.setAlignmentX(Component.CENTER_ALIGNMENT);
        roll.addActionListener( new ActionListener() {
              public void actionPerformed(ActionEvent e)
              {
                  roll();
              }
          }
        );

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
		p1.add(playerName);
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
        for(String name : images){ // draws the hand
            ImageIcon i = drawHand(name);
            p2a.add(new JLabel(i));
        }
		p2.add( p2a, "East");


		JPanel p2b = new JPanel();
		p2b.setLayout( new BoxLayout(p2b, BoxLayout.Y_AXIS));
		p2b.setBorder(blackline);
		p2b.setPreferredSize(new Dimension(BOARDWIDTH/2, BOARDHEIGHT/10));
        p2b.add(new JLabel(diceOne));

        p2b.add(new JLabel(diceTwo));
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
        System.out.println("Current size: " + mainCluedo.players.size());

        JFrame frame2 = new JFrame();
        JPanel panel = new JPanel();

        // loop for inputting
        JLabel player = new JLabel("Player name: ");
        JLabel tokenName = new JLabel("Choose a token: ");

        playerName = new JTextField(30);

        panel.add(player);
        panel.add(playerName);
        panel.add(tokenName);
        String selectedName = "";
//		final String fstring = "";

        // adds all of the buttons onto the panel
        Enumeration elements = buttonGroup.getElements();
        while(elements.hasMoreElements()){
            JRadioButton button = (JRadioButton)elements.nextElement();
            panel.add(button);
        }

        JButton confirm = new JButton("Confirm player");
        panel.add(confirm);
        frame2.add(panel);

        confirm.addActionListener( new ActionListener() {
               public void actionPerformed(ActionEvent e)
               {
                   String selectedName="";
                   Enumeration elements = buttonGroup.getElements();
                   AbstractButton a=null;
                   while(elements.hasMoreElements()){
                       JRadioButton button = (JRadioButton)elements.nextElement();
                       if (button.isSelected()){
                           selectedName = button.getText();
                           a = button;
                           System.out.println("selected: " + button.getText());

                       }
                   }
                   buttonGroup.remove(a);
                   buttonGroup.clearSelection();
                   addPlayerCounter+=1;
                   mainCluedo.addPlayerGUI(playerName.getText(), selectedName, 0,0,null,null,null,null );

                   if (mainCluedo.players.size() < playerAmount) {
                       frame2.setVisible(false);
                       addPlayer();
                   }
                   else{
                       frame2.setVisible(false);
                   }
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
                  frame1.setVisible(false);

                  String stringNumOfPlayers = players.getText();
                  playerAmount = Integer.parseInt(stringNumOfPlayers);
    //			   System.out.println("Num of players: " + intNumOfPlayers);
                  addPlayer();

              }
          }
        );

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(500, 200);
        frame1.setVisible(true);

    }


    public void makeSuggestion(){

		JFrame f = new JFrame("panel");
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		JTextField b1 = new JTextField(30); // person
		JTextField b2 = new JTextField(30); // weapon
		JTextField b3 = new JTextField(30); // room

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

            updatePositions();
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

                    boolean done =false;
                    for(String txt : startingPos.keySet()){
                        List<Integer> s = startingPos.get(txt);
                        System.out.println(s);
                        Integer x =s.get(0);
                        Integer y = s.get(1);

                        if(row==y && col==x){
                            ImageIcon r = resize(txt);
                            Tile newTile = new Tile(r,false,false,false,false,row,col);
                            tiles.add(newTile);
                            done=true;
                        }
                    }

                    if(!done) {
                        if (value.equals("x")) { // if a room
                            ImageIcon r = resize("outOfBoundsTile.jpg");
                            Tile newTile = new Tile(r, false, false, false, true, row, col);
                            tiles.add(newTile);
                        } else if (value.equals("h")) {
                            ImageIcon r = resize("hallwayTile.jpg");
                            Tile newTile = new Tile(r, false, false, false, true, row, col);
                            tiles.add(newTile);
                        } else if (value.equals("D")) {
                            ImageIcon r = resize("doorTile.jpg");
                            Tile newTile = new Tile(r, false, false, true, false, row, col);
                            tiles.add(newTile);
                        } else {
                            ImageIcon r = resize("roomTile.jpg");
                            Tile newTile = new Tile(r, true, false, false, false, row, col);
                            tiles.add(newTile);
                        }
                    }


				}
			}

		} catch (IOException e) {
			System.out.println(e);
		}

	}

    public void updatePositions() {
        startingPos.put("plumTile.jpg", Arrays.asList(23, 19));
        startingPos.put("greenTile.jpg", Arrays.asList(14, 0));
        startingPos.put("whiteTile.jpg", Arrays.asList(9, 0));
        startingPos.put("scarlettTile.jpg", Arrays.asList(7, 24));
        startingPos.put("mustardTile.jpg", Arrays.asList(0, 17));
        startingPos.put("peacockTile.jpg", Arrays.asList(23, 6));
        System.out.println(startingPos.size());
    }

    public void roll(){
        int roll1 = (int) Math.ceil(Math.random() * 6);
        int roll2 = (int) Math.ceil(Math.random() * 6);

        BufferedImage dice = null;
        try {
            dice = ImageIO.read(new File("images/dice" + roll1 +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dice1 = dice.getScaledInstance(BOARDWIDTH/12, BOARDHEIGHT/12, Image.SCALE_SMOOTH);
        diceOne = new ImageIcon(dice1);

        try {
            dice = ImageIO.read(new File("images/dice" + roll2 +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dice2 = dice.getScaledInstance(BOARDWIDTH/12, BOARDHEIGHT/12, Image.SCALE_SMOOTH);
        diceTwo = new ImageIcon(dice2);

    }
}


     