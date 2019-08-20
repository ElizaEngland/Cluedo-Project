import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Map;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GraphicalInterface extends JFrame implements KeyListener, ActionListener {

    private JTextField enterPlayerName;
    private JRadioButton token;
    private int BOARDHEIGHT = 1000;
    private int BOARDWIDTH = 1000;
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<Tile> tiles;
    private Map<String, List<Integer>> positions = new HashMap<String,List<Integer>>();

    private ImageIcon diceOne;
    private ImageIcon diceTwo;
    private int moves;
    private int addPlayerCounter;
    private int playerAmount;
    private ArrayList<String> characters;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private ArrayList<JRadioButton> charButtons;
    public cluedoMain cluedoMainGame;

    boolean canMove = false;
    private JFrame mainFrame;
    private JPanel p1;
    private JPanel p2;
    private JPanel p2a;
    private JPanel p2b;
    private JPanel p3;
    private ImageIcon[][] grid;
    private JLabel playerName;
    private String playerTileName = "plumTile.jpg";
    JButton roll;
    JButton suggestion;
    JButton confirm;
    Player player;
    Tile currentTile;
    String typeOfTile;



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

        this.cluedoMainGame = cluedoMainGame;
        howMany();

    }



    public void updateMainFrame(Player player){

        //System.out.println("HERE");
        this.player = player;
        playerName = new JLabel(player.getName() + " turn");

        mainFrame.getContentPane().removeAll();

        p1 = new JPanel();

        p2 = new JPanel();
        p2.setLayout( new BorderLayout());
        p2.setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT/5));

        p3 = new JPanel();

        createPanelOne();
        updateDice();
        updateCards();
        updateBoard();

        p2.add(p2b, "West");
        p2.add(p2a,"East");

        mainFrame.getContentPane().add( p1, "West");
        mainFrame.getContentPane().add( p2, "South");
        mainFrame.getContentPane().add( p3, "Center");
        mainFrame.revalidate();
        mainFrame.repaint();

    }


    public void createMainFrame(Player player){
        this.player = player;
        mainFrame = new JFrame();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        addKeyListener(this);

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
       createPanelOne();


        // bottom
        p2= new JPanel();
        p2.setLayout( new BorderLayout());
        p2.setBorder(blackline);
        p2.setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT/5));

        updateCards();
        p2.add( p2a, "East");

        roll();
        moves=0;
        updateDice();


        // graph
        updateStartPositions();
        updateBoard();

        //add(p3);
        mainFrame.getContentPane().add( p1, "West");
        mainFrame.getContentPane().add( p2, "South");
        mainFrame.getContentPane().add( p3, "Center");

        mainFrame.addKeyListener(this);
        mainFrame.setFocusable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(BOARDWIDTH, BOARDHEIGHT);
        mainFrame.setVisible(true);

    }

    public void addPlayer() {
        System.out.println("Current size: " + cluedoMainGame.players.size());

        JFrame frame2 = new JFrame();
        JPanel panel = new JPanel();

        // loop for inputting
        JLabel player = new JLabel("Player name: ");
        JLabel tokenName = new JLabel("Choose a token: ");

        enterPlayerName = new JTextField(30);

        panel.add(player);
        panel.add(enterPlayerName);
        panel.add(tokenName);
        String selectedName = "";
//		final String fstring = "";

        // adds all of the buttons onto the panel
        Enumeration elements = buttonGroup.getElements();
        while(elements.hasMoreElements()){
            JRadioButton button = (JRadioButton)elements.nextElement();
            panel.add(button);
        }

        confirm = new JButton("Confirm player");
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
                       System.out.println(selectedName);
                       a = button;
                       System.out.println("selected: " + button.getText());

                   }
               }
               buttonGroup.remove(a);
               buttonGroup.clearSelection();
               addPlayerCounter+=1;
               cluedoMainGame.addPlayerGUI(enterPlayerName.getText(), selectedName, 0,0,null,null,null,null );

               if (cluedoMainGame.players.size() < playerAmount) {
                   frame2.setVisible(false);
                   addPlayer();
               }
               else{
                   frame2.setVisible(false);
                   cluedoMainGame.newGame();
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


    public void updateBoard() {

        JFrame test = new JFrame();
        System.out.println("Updating board..");
        p3 = new JPanel();
        JPanel testP = new JPanel();
        JPanel pnl = new JPanel();
        addTile();

        int width = 25;
        int height = 26;
        pnl.setLayout(new GridLayout(width, height));

        grid = new ImageIcon[width][height]; //allocate the size of grid
        for (Tile tile : tiles) {
            grid[tile.getX()][tile.getY()] = tile.getImage();
            pnl.add(new JLabel(grid[tile.getX()][tile.getY()])); //adds button to grid

        }

        p3.add(pnl);
    }

    public void addTile(){
        try {
            FileReader fr = new FileReader("boardmapGUI.txt");
            BufferedReader dataReader = new BufferedReader(fr);
            tiles = new ArrayList<Tile>();
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
                    for(String txt : positions.keySet()){
                        List<Integer> s = positions.get(txt);
                        Integer x =s.get(0);
                        Integer y = s.get(1);


                        if(row==y && col==x){
                            System.out.println(txt);
                            System.out.println(x);
                            System.out.println(y);

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
                            Tile newTile = new Tile(r, false, true, false, false, row, col);
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

    public void updateStartPositions() {
        positions.put("plumTile.jpg", Arrays.asList(23, 19));
        positions.put("greenTile.jpg", Arrays.asList(14, 0));
        positions.put("whiteTile.jpg", Arrays.asList(9, 0));
        positions.put("scarlettTile.jpg", Arrays.asList(7, 24));
        positions.put("mustardTile.jpg", Arrays.asList(0, 17));
        positions.put("peacockTile.jpg", Arrays.asList(23, 6));
    }

    public void updatePosition(String name, int direction){
        List<Integer> coords = positions.get(name);
        System.out.println("Updating positions..");
        int x = coords.get(0);
        int y = coords.get(1);
      //  System.out.println(x);
       // System.out.println(y);
        List<Integer> temp = new ArrayList<Integer>();

        for(Tile t : tiles){
            if(y==t.getX() && x==t.getY()){
                currentTile = t;
            }
        }

        if(canMove){
            if(direction==1){

                if(checkTile(currentTile,x,y-1)){
                    y--;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name,temp);
                    System.out.println("North");
                    moves--;
                }

            }
            if(direction==2){
                if(checkTile(currentTile,x+1,y)) {
                    x++;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    System.out.println("East");
                    moves--;
                }
            }
            if(direction==-1){
                if(checkTile(currentTile,x,y+1)) {
                    y++;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    System.out.println("South");
                    moves--;
                }
            }
            if(direction==-2){
                if(checkTile(currentTile,x-1,y)) {
                    x--;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    System.out.println("West");
                    moves--;
                }
            }
        }

        if(moves<1){
            canMove=false;
        }

        updateMainFrame(player);
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

        moves = roll1 + roll2;
        System.out.println("Rolled");

    }

    public void updateCards(){
        System.out.println("Updating cards..");
        p2a = new JPanel();
        p2a.setLayout( new FlowLayout());
        // p2a.setBorder(blackline);
        p2a.setPreferredSize(new Dimension(700, BOARDHEIGHT/10));
        for(Card card : player.handList){ // draws the hand
            String name = card.getName().toLowerCase() + ".jpg";
            ImageIcon i = drawHand(name);
            p2a.add(new JLabel(i));
        }

    }

    public void updateDice(){
        System.out.println("Updating dices..");
        p2b = new JPanel();
        p2b.removeAll();
      //  p2b.setLayout( new BoxLayout(p2b, BoxLayout.Y_AXIS));
       // p2b.setBorder(blackline);
        p2b.setPreferredSize(new Dimension(BOARDWIDTH/4, BOARDHEIGHT/10));

        JLabel turnsLeft = new JLabel(player.getName()+" has " + moves +" turns left.");
        p2b.add(new JLabel(diceOne), "West");
        p2b.add(new JLabel(diceTwo), "East");
        p2b.add(turnsLeft,"South");

        //p2b.revalidate();
       // p2b.repaint();
        p2.add( p2b, "West");
    }

    public void createPanelOne(){
        p1 = new JPanel();
        p1.setLayout( new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setPreferredSize(new Dimension(BOARDWIDTH/5, BOARDHEIGHT));

        //buttons
        playerName = new JLabel(player.getName() + " turn");
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerName.setPreferredSize(new Dimension(BOARDWIDTH/5, BOARDHEIGHT/10));

        roll = new JButton("ROLL");
        roll.setFocusable(false);
        roll.setAlignmentX(Component.CENTER_ALIGNMENT);
        roll.addActionListener(this);
        suggestion = new JButton("SUGGESTION");
        suggestion.setFocusable(false);
        suggestion.setAlignmentX(Component.CENTER_ALIGNMENT);
        suggestion.addActionListener(this);
        JButton accusation = new JButton("ACCUSTATION");
        accusation.setAlignmentX(Component.CENTER_ALIGNMENT);

        p1.add(playerName);
        p1.add(roll);
        p1.add(suggestion);
        p1.add(accusation);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            System.out.println("Left key pressed");
            updatePosition(playerTileName,-2);
        }

        if (key == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
            updatePosition(playerTileName,2);
        }

        if (key == KeyEvent.VK_UP) {
            System.out.println("Up key pressed");
            updatePosition(playerTileName,1);
        }

        if (key == KeyEvent.VK_DOWN) {
            System.out.println("Down key Pressed");
            updatePosition(playerTileName,-1);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key typed");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == roll){
            roll();
            updateMainFrame(player);
            canMove = true;

        }

        if(e.getSource() == suggestion){
            makeSuggestion();
        }
    }

    public boolean checkTile(Tile current, int x, int y) {
        for(Tile t : tiles){
            if(y == t.getX() && x == t.getY()){

                if(t.isRoom() && typeOfTile.equals("door")){
                    System.out.println("yeeeee");
                    return true;
                }

                if(t.isRoom()){
                    typeOfTile = "room";
                    System.out.println("is room");
                    return false;
                }
                else if(t.isDoor()){
                    typeOfTile = "door";
                    System.out.println("is door");
                    return true;
                }
                else if (t.isOutOfBounds()){
                    typeOfTile = "out";
                    System.out.println("out of bounds");
                    return false;
                }
                else if(t.isHallway()){
                    //onDoorTile=true;
                    System.out.println("hallway");
                    return true;
                }
                else{
                    System.out.println("idk");
                    return false;
                }

            }
        }
        System.out.println("not on board");
        return false;
    }


}




