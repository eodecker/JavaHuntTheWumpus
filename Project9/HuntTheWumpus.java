/** 
* 05/4/2018
* Eli Decker
* HuntTheWumpus.java
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

public class HuntTheWumpus extends JFrame{

	private Landscape scape;
	private Graph graph;
	private Hunter hunter;
	private Wumpus wump;


    private int iteration;
    // text message displayed when the user performs a certain action
    private JLabel textMessage;
    // the room that the player fires at
    private Vertex target;
    /** holds the drawing canvas **/
    private BasicPanel canvas;

    private Vertex teleport;
    
    /** height of the main drawing window **/
    int height;
    /** width of the main drawing window **/
    int width;

	private enum PlayState { PLAY, STOP, QUIT }
    private enum ArrowState { ARMED, DISARMED }
	private PlayState state= PlayState.PLAY;
    private ArrowState arrow = ArrowState.DISARMED;

	// constructor method
	public HuntTheWumpus (int width, int height) {
		super("Hunt The Wumpus");
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        // fields
        scape = new Landscape(width, height);

        //canvas
        this.canvas = new BasicPanel( height, width );
        this.add( this.canvas, BorderLayout.CENTER );
        this.pack();
        this.setVisible( true );
        
        this.width = width;
        this.height = height;

        this.textMessage = new JLabel("Hello, welcome to Eli's Hunt The Wumpus Game");

		//sets everything up
        this.setUp();

        JButton quit = new JButton("Quit");
        JButton newgame = new JButton("New Game");
        // listen for keystrokes
        Control control = new Control();
        quit.addActionListener(control);
        newgame.addActionListener(control);
        this.addKeyListener(control);
        
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(this.textMessage);
        panel.add(quit);
        panel.add(newgame);
                
        this.add(panel, BorderLayout.SOUTH);
        this.pack();


        this.setFocusable(true);

	}

    private void setupUI() {
        // add elements to the UI
        this.textMessage.setText("Hello, welcome to Eli's Hunt The Wumpus Game");
        
    }

    public void setUp() {

        // if (firstSetUp) {

        // }
        // else {

        // }
        //build the map of rooms
        scape.reset();
        graph = new Graph();
        int size = width/80;
        for(int i=0; i<Math.pow(size,2); i++){
            Vertex v = new Vertex( 0, i%size, i/size );
            scape.addBackground(v);
            graph.addVertex(v);
        }

        for( Vertex v1 : graph.getList() ){
            for( Vertex v2 : graph.getList() ){
                // test to see if two vertices are adjacent in the same row
                if( v1.getY() == v2.getY() && v1.getX() + 1 == v2.getX() ){
                    graph.addEdge( v1, Direction.EAST, v2 );
                }
                
                // test to see if two vertices are adjacent in the same column
                else if( v1.getX() == v2.getX() && v1.getY() + 1 == v2.getY() ) {
                    graph.addEdge( v1, Direction.SOUTH, v2 );
                }

            }
        }

        // create the hunter and wumpus
        Random rand = new Random();
        int num1 = rand.nextInt( graph.vertexCount() );
        int num2 = rand.nextInt( graph.vertexCount() );
        int num3 = rand.nextInt( graph.vertexCount() );
        //don't let the  hunter start in a room unconnected 
        //run dijsktra's algorithm to determine the cost between the 2 rooms
        graph.shortestPath( graph.getVertex(num2));
        while( graph.getVertex(num1).getCost() == Integer.MAX_VALUE || num1 == num2 ) {
            num1 = rand.nextInt( graph.vertexCount() );
            num2 = rand.nextInt( graph.vertexCount() );
            // recalculate the length of the path between the rooms
            graph.shortestPath( graph.getVertex(num2));
        }

        // starting rooms of the hunter and wumpus
        this.hunter = new Hunter( 0, 0, this.graph.getVertex( num1 ) );
        this.scape.addForeground(this.hunter);
        this.hunter.getPos().setVisible(true);
        this.wump = new Wumpus( 0, 0, this.graph.getVertex( num2 ) );
        this.scape.addForeground(this.wump);


        while ((num3 == num1) || (num3 == num2)) {
        	num3 = rand.nextInt( graph.vertexCount() );
        }
        this.teleport = this.graph.getVertex( num3 );
  
        this.target = null;
        this.requestFocus();
        this.arrow=ArrowState.DISARMED;

        setupUI();
    }

    private class BasicPanel extends JPanel {
        
        /**
         * Creates the drawing canvas
         * @param height the height of the panel in pixels
         * @param width the width of the panel in pixels
         **/
        public BasicPanel(int height, int width) {
            super();
            this.setPreferredSize( new Dimension( width, height ) );
            this.setBackground(new Color(48, 37, 0));
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
                Random gen = new Random();
            super.paintComponent(g);
            scape.draw(g);
            
        }
    } // end class BasicPanel

    public void iterate() {   
        this.iteration++;
        // if the hunter has walked into the room of the wumpus
        if (this.hunter.getPos()==this.wump.getPos())
            //he dies
            this.hunter.alive(false);
        // if the hunter is dead
        if (!this.hunter.isAlive()){
            //the game is over
            this.wump.setVisible(true);
            //print the results;
            this.textMessage.setText("Game Over. The wumpus ate you.");
            state = PlayState.STOP;
        }

        //teleport
        if (this.hunter.getPos() == teleport) {
        	teleportHunter();
        }

        //if the wumpus is dead
        if (!this.wump.isAlive()){
            //the game is over
            this.wump.setVisible(true);
            //print the results;
            this.textMessage.setText("You Win! You killed the wumpus.");
            state = PlayState.STOP;
        }
        if(target!=null && this.arrow==ArrowState.ARMED){
            if(target==this.wump.getPos()) {
                this.wump.alive(false);
            }
            //otherwise the wumpus hears the arrow and eats the hunter
            else {
                this.wump.changePos( this.hunter.getPos() );
            }
        }

        if (this.state != PlayState.QUIT) {       
            // update the landscape, display    
            this.repaint();   
        }
    } // end of iterate method

    public void teleportHunter() {
    	Random rand = new Random();
        int location = rand.nextInt( graph.vertexCount() );
        
        while ((this.graph.getVertex( location ) == this.hunter.getPos() ) || (this.graph.getVertex( location ) == this.wump.getPos() )) {
        	location = rand.nextInt( graph.vertexCount() );
        }
        this.teleport.setTeleporter(true);
        this.hunter.changePos(this.graph.getVertex( location ) ).setVisible(true);
        System.out.println("You were teleported!");
    }
    
    
    // move the hunter in some direction
    public boolean moveHunter(Direction dir){
        //if the move is within the limits of the graph, and there is a connection, then move
        if ( hunter.getPos().getNeighbor( dir ) == null ) {
            return false;
        }
        else {
            hunter.changePos( hunter.getPos().getNeighbor( dir )).setVisible(true);
            return true;
        } 
    }


    public void armArrow(){
        this.arrow=ArrowState.ARMED;
        this.hunter.armBow();
    }
    public void disarmArrow(){
        this.arrow=ArrowState.DISARMED;
        this.hunter.disarmBow();
    }
    public void fire(Direction dir){
        //determine where the arrow will hit depending on the direction
        this.target = hunter.getPos().getNeighbor( dir );
        if (target == wump.getPos() ) {
            wump.alive(false);
        }
    }

	private class Control extends KeyAdapter implements ActionListener {

        public void keyTyped(KeyEvent e) {   
            if (("" + e.getKeyChar()).equalsIgnoreCase(" ") && state == PlayState.PLAY) {
                if (arrow == ArrowState.DISARMED ) {
                    armArrow();
                    System.out.println("*** Hunter is preparing to fire ***");
                }
                else {
                    disarmArrow();
                    System.out.println("*** Hunter is disarmed ***");
                } 
            }

            if (arrow==ArrowState.ARMED && state == PlayState.PLAY ) {
                if (("" + e.getKeyChar()).equalsIgnoreCase("w")) {
                    fire(Direction.NORTH);
                    System.out.println("*** shot up  ***");
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("s")) {
                    fire(Direction.SOUTH);
                    System.out.println("*** shot down ***");
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("a")) {
                    fire(Direction.WEST);
                    System.out.println("*** shot left ***");
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("d")) {
                    fire(Direction.EAST);
                    System.out.println("*** shot right   ***");
                }
            }
            else if (arrow==ArrowState.DISARMED && state == PlayState.PLAY) {
                if (("" + e.getKeyChar()).equalsIgnoreCase("w")) {
                    if (moveHunter(Direction.NORTH) ) {
                        System.out.println("*** moved   up  ***");
                    }
                    else {System.out.println("Can't move there."); }
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("s")) {
                    if (moveHunter(Direction.SOUTH) ) {
                        System.out.println("*** moved   down  ***");
                    }
                    else {System.out.println("Can't move there."); }
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("a")) {
                    if (moveHunter(Direction.WEST) ) {
                        System.out.println("*** moved   left  ***");
                    }
                    else {System.out.println("Can't move there."); }
                }
                else if (("" +  e.getKeyChar()).equalsIgnoreCase("d")) {
                    if (moveHunter(Direction.EAST) ) {
                        System.out.println("*** moved   right  ***");
                    }
                    else {System.out.println("Can't move there."); }
                }
            }
                
            if (("" + e.getKeyChar()).equalsIgnoreCase("q")) {
                state = PlayState.QUIT;
                System.out.println("*** Simulation ended ***");
            }
            if (("" + e.getKeyChar()).equalsIgnoreCase("r")) {
                setUp();
                System.out.println("*** New game ***");
            }
        }

        public void actionPerformed(ActionEvent event) {
            if( event.getActionCommand().equalsIgnoreCase("New Game") ) {
                state = PlayState.PLAY;
                setUp();
            }
            else if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
                state = PlayState.QUIT;
            }
        }
    } // end class Control


	public static void main(String[] argv) throws InterruptedException {
		HuntTheWumpus w = new HuntTheWumpus( 640, 640 );
		while(w.state != PlayState.QUIT) {
			w.iterate();
			Thread.sleep(33);
		}
		w.dispose();
	}
} // end class HuntTheWumpus