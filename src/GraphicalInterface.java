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
    private ButtonGroup buttonGroup = new ButtonGroup();
    private ButtonGroup characterGroup = new ButtonGroup();
    private ButtonGroup weaponGroup = new ButtonGroup();
    private ButtonGroup roomGroup = new ButtonGroup();
    private ArrayList<JRadioButton> charButtons;
    public cluedoMain cluedoMainGame;

    boolean canMove = false;
    private JFrame mainFrame;
    private JFrame suggestionFrame;
    private JFrame accusationFrame;
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
    JButton accusation;
    JButton confirm;
    JButton confirmSuggestion;
    JButton confirmAccusation;
    Player player;
    Tile currentTile;
    String typeOfTile;
    boolean inRoom =false;
    String currentRoom;



    public GraphicalInterface(cluedoMain cluedoMainGame){
        super("Cluedo");
        setLayout(new FlowLayout());

        buttonGroup.add(new JRadioButton("Scarlett"));
        buttonGroup.add(new JRadioButton("Green"));
        buttonGroup.add(new JRadioButton("White"));
        buttonGroup.add(new JRadioButton("Peacock"));
        buttonGroup.add(new JRadioButton("Mustard"));
        buttonGroup.add(new JRadioButton("Plum"));

        characterGroup.add(new JRadioButton("Scarlett"));
        characterGroup.add(new JRadioButton("Green"));
        characterGroup.add(new JRadioButton("White"));
        characterGroup.add(new JRadioButton("Peacock"));
        characterGroup.add(new JRadioButton("Mustard"));
        characterGroup.add(new JRadioButton("Plum"));

        weaponGroup.add(new JRadioButton("Dagger"));
        weaponGroup.add(new JRadioButton("Revolver"));
        weaponGroup.add(new JRadioButton("Rope"));
        weaponGroup.add(new JRadioButton("Spanner"));
        weaponGroup.add(new JRadioButton("Pipe"));
        weaponGroup.add(new JRadioButton("Candlestick"));

        roomGroup.add(new JRadioButton("Hall"));
        roomGroup.add(new JRadioButton("Lounge"));
        roomGroup.add(new JRadioButton("Dining"));
        roomGroup.add(new JRadioButton("Kitchen"));
        roomGroup.add(new JRadioButton("Ballroom"));
        roomGroup.add(new JRadioButton("Conservatory"));
        roomGroup.add(new JRadioButton("Billiard"));
        roomGroup.add(new JRadioButton("Library"));
        roomGroup.add(new JRadioButton("Study"));

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
//        noOfPlayers.setFont(new Font("Serif", Font.PLAIN, 24));
        JRadioButton amount3 = new JRadioButton("3");
        JRadioButton amount4 = new JRadioButton("4");
        JRadioButton amount5 = new JRadioButton("5");
        JRadioButton amount6 = new JRadioButton("6");

        panel.add(noOfPlayers);
        panel.add(amount3);
        panel.add(amount4);
        panel.add(amount5);
        panel.add(amount6);
        JButton confirmNoOfPlayers = new JButton("Confirm");
        panel.add(confirmNoOfPlayers);
        frame1.add(panel);


//        confirmNoOfPlayers.addActionListener(this);

        confirmNoOfPlayers.addActionListener( new ActionListener() {
                                                  public void actionPerformed(ActionEvent e)
                                                  {

                                                      frame1.setVisible(false);
                                                      if (amount3.isSelected()){
                                                          playerAmount = Integer.parseInt(amount3.getText());
                                                          System.out.println("3 pressed");
                                                      }
                                                      if (amount4.isSelected()){
                                                          playerAmount = Integer.parseInt(amount4.getText());
                                                          System.out.println("4 pressed");

                                                      }
                                                      if (amount5.isSelected()){
                                                          playerAmount = Integer.parseInt(amount5.getText());
                                                          System.out.println("5 pressed");

                                                      }
                                                      if (amount6.isSelected()){
                                                          playerAmount = Integer.parseInt(amount6.getText());
                                                          System.out.println("6 pressed");

                                                      }
//                      String stringNumOfPlayers = players.getText();
                                                      addPlayer();
                                                  }
                                              }
        );

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(500, 200);
        frame1.setVisible(true);

    }


    public void makeSuggestion(){

        suggestionFrame = new JFrame("panel");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));


        JLabel l = new JLabel("Make A Suggestion");
        p.add(l);
        JLabel per = new JLabel("Choose a person:");
        p.add(per);
        Enumeration elementsP = characterGroup.getElements();
        while(elementsP.hasMoreElements()){
            JRadioButton button = (JRadioButton)elementsP.nextElement();
            p.add(button);
        }

        JLabel weap = new JLabel("Choose a weapon:");
        p.add(weap);
        Enumeration elementsW = weaponGroup.getElements();
        while(elementsW.hasMoreElements()){
            JRadioButton button = (JRadioButton)elementsW.nextElement();
            p.add(button);
        }

        JLabel room = new JLabel("In the "+ currentRoom);
        p.add(room);


        confirmSuggestion = new JButton("Confirm");
        confirmSuggestion.addActionListener(this);
        p.add(confirmSuggestion);

        suggestionFrame.add(p);
        suggestionFrame.setSize(250, 650);
        suggestionFrame.setVisible(true);
        suggestionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void makeAccusation(){
        accusationFrame = new JFrame("panel");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));


        JLabel l = new JLabel("Make an Accusation");
        p.add(l);
        JLabel per = new JLabel("Choose a person:");
        p.add(per);
        Enumeration elementsP = characterGroup.getElements();
        while(elementsP.hasMoreElements()){
            JRadioButton button = (JRadioButton)elementsP.nextElement();
            p.add(button);
        }

        JLabel weap = new JLabel("Choose a weapon:");
        p.add(weap);
        Enumeration elementsW = weaponGroup.getElements();
        while(elementsW.hasMoreElements()){
            JRadioButton button = (JRadioButton)elementsW.nextElement();
            p.add(button);
        }

        JLabel room = new JLabel("Choose a room:");
        p.add(room);
        Enumeration elementsR = roomGroup.getElements();
        while(elementsR.hasMoreElements()){
            JRadioButton button = (JRadioButton)elementsR.nextElement();
            p.add(button);
        }

        confirmAccusation= new JButton("Confirm");
        confirmAccusation.addActionListener(this);
        p.add(confirmAccusation);

        accusationFrame.add(p);
        accusationFrame.setSize(250, 650);
        accusationFrame.setVisible(true);
        accusationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        System.out.println("Updating board..");
        p3 = new JPanel();
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

                    String roomName =null;
                    boolean done =false;
                    for(String txt : positions.keySet()){
                        List<Integer> s = positions.get(txt);
                        Integer x =s.get(0);
                        Integer y = s.get(1);

                        if(row==y && col==x){
                            ImageIcon r = resize(txt);
                            Tile newTile = new Tile(r,false,false,false,false,row,col,roomName);
                            tiles.add(newTile);
                            done=true;
                        }
                    }


                    if(!done) {
                        if (value.equals("x")) { // if a room
                            ImageIcon r = resize("outOfBoundsTile.jpg");
                            Tile newTile = new Tile(r, false, false, false, true, row, col,roomName);
                            tiles.add(newTile);
                        } else if (value.equals("h")) {
                            ImageIcon r = resize("hallwayTile.jpg");
                            Tile newTile = new Tile(r, false, true, false, false, row, col,roomName);
                            tiles.add(newTile);
                        } else if (value.equals("D")) {
                            ImageIcon r = resize("doorTile.jpg");
                            Tile newTile = new Tile(r, false, false, true, false, row, col,roomName);
                            tiles.add(newTile);
                        } else {
                            roomName = whatRoomTile(value);
                            ImageIcon r = resize("roomTile.jpg");
                            Tile newTile = new Tile(r, true, false, false, false, row, col,roomName);
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
        List<Integer> temp = new ArrayList<Integer>();

        for(Tile t : tiles){
            if(y==t.getX() && x==t.getY()){
                currentTile = t;
            }
        }

        if(canMove){
            if(direction==1){ // north
                if(checkTile(currentTile,x,y-1)){
                    y--;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name,temp);
                    moves--;
                }
            }
            if(direction==2){ // east
                if(checkTile(currentTile,x+1,y)) {
                    x++;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    moves--;
                }
            }
            if(direction==-1){ // south
                if(checkTile(currentTile,x,y+1)) {
                    y++;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    moves--;
                }
            }
            if(direction==-2){ //west
                if(checkTile(currentTile,x-1,y)) {
                    x--;
                    temp.add(x);
                    temp.add(y);
                    positions.replace(name, temp);
                    moves--;
                }
            }
        }

        if(moves<1){
            canMove=false;
        }
        System.out.println(currentRoom);
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
        if(inRoom){
            suggestion.setVisible(true);
        }else{
            suggestion.setVisible(false);
        }

        accusation = new JButton("ACCUSATION");
        accusation.setAlignmentX(Component.CENTER_ALIGNMENT);
        accusation.setFocusable(false);
        accusation.addActionListener(this);

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

        if(e.getSource() == accusation){
            makeAccusation();
        }

        if(e.getSource() == confirmSuggestion){
            int count=0;

            Enumeration characterEle = characterGroup.getElements();
            Enumeration weaponEle = weaponGroup.getElements();
            while(characterEle.hasMoreElements()){
                JRadioButton button = (JRadioButton)characterEle.nextElement();
                if (button.isSelected()){
                    count++;
                    System.out.println("selected: " + button.getText());

                }
            }

            while(weaponEle.hasMoreElements()){
                JRadioButton button = (JRadioButton)weaponEle.nextElement();
                if (button.isSelected()){
                    count++;
                    System.out.println("selected: " + button.getText());

                }
            }

            if(count==2) {
                suggestionFrame.setVisible(false);
            }

        }

        if(e.getSource() == confirmAccusation){
            int count=0;
            System.out.println("Here we compare hands");
            Enumeration characterEle = characterGroup.getElements();
            Enumeration weaponEle = weaponGroup.getElements();
            Enumeration roomEle = roomGroup.getElements();
            while(characterEle.hasMoreElements()){
                JRadioButton button = (JRadioButton)characterEle.nextElement();
                if (button.isSelected()){
                    count++;
                    System.out.println("selected: " + button.getText());

                }
            }

            while(weaponEle.hasMoreElements()){
                JRadioButton button = (JRadioButton)weaponEle.nextElement();
                if (button.isSelected()){
                    count++;
                    System.out.println("selected: " + button.getText());

                }
            }

            while(roomEle.hasMoreElements()){
                JRadioButton button = (JRadioButton)roomEle.nextElement();
                if (button.isSelected()){
                    count++;
                    System.out.println("selected: " + button.getText());

                }
            }

            if(count==3) {
                accusationFrame.setVisible(false);
            }

        }


    }

    public boolean checkTile(Tile current, int x, int y) {
        for(Tile t : tiles){
            if(y == t.getX() && x == t.getY()){

                if(inRoom){
                    if(t.isDoor()){
                        currentRoom = t.getRoomName();
                        inRoom = false;
                        typeOfTile = "exit door";
                        return true;
                    }
                    else if(t.isRoom()){
                        currentRoom = t.getRoomName();
                        typeOfTile = "room";
                        return true;
                    }
                    else if (t.isOutOfBounds()){
                        typeOfTile = "out";
                        System.out.println("out of bounds");
                        return false;
                    }
                    else if(t.isHallway()){
                        System.out.println("hallway");
                        return false;
                    }
                    else{
                        System.out.println("idk");
                        return false;
                    }

                }
                else{
                    if(t.isRoom() && typeOfTile.equals("door")){ // entering room through door
                        currentRoom = t.getRoomName();
                        inRoom=true;
                        return true;
                    }

                    if(t.isRoom()){
                        typeOfTile = "room";
                        System.out.println("is room");
                        return false;
                    }
                    else if(t.isDoor()){
                        currentRoom = t.getRoomName();
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
                        currentRoom = t.getRoomName();
                        System.out.println("hallway");
                        return true;
                    }
                    else{
                        System.out.println("idk");
                        return false;
                    }
                }


            }
        }
        System.out.println("not on board");
        return false;
    }

    public String whatRoomTile(String value){
        String text;
        if(value.equals("K")){  text = "kitchen"; } // kitchen
        else if(value.equals("B")){text = "ballroom";} // ballroom
        else if (value.equals("C")){text = "conservatory";}//conservatory
        else if(value.equals("N")){ text = "dining";}//dining room
        else if(value.equals("O")){text = "lounge";}//lounge
        else if(value.equals("H")){ text = "hall";} //hall
        else if(value.equals("S")){ text = "study";}// study
        else if(value.equals("L")){ text = "library";}// library
        else if(value.equals("I")){text = "billiard";}// billiard
        else{ text = "cellar"; } //cellar

        return text;
    }

}




