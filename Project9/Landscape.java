/**
* Landscape.java
* Eli Decker
* 05/04/2018
*/

import java.util.Collections;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;

public class Landscape {
	private int width;
	private int height;
	private int counter = 0;
	private LinkedList<Vertex> background;
	private LinkedList <Agent> foreground;
	private Color c;

	// constructor with no parameters
	public Landscape() {
		width = 400;
		height = 400;
		background = new LinkedList <Vertex> ();
		foreground = new LinkedList <Agent> ();
	}

	// a constructor that sets the width and height fields, and initializes the agent list.
	public Landscape(int w, int h) {
		width = w;
		height = h;
		background = new LinkedList <Vertex> ();
		foreground = new LinkedList <Agent> ();
	}

	//returns the height.
	public int getHeight() {
		return height;
	}

	//returns the width.
	public int getWidth() {
		return width;
	}

	//inserts an agent at the beginning of its list of agents.
	public void addBackground( Vertex a ) {
		background.add( a );
	}

	// removes customer from landscape arraylist
	public void removeBackground( Vertex a ) {
		background.remove( a );
	}

	// adds a  agent to list of agents
	public void addForeground( Agent c ) {
		foreground.add( c );
	}

	// returns list of all the foreground objects
	public LinkedList<Agent> getList() {
		return foreground;
	}

	// returns list of all the agents
	public LinkedList<Vertex> getAgents() {
		return background;
	}

	// returns the number of foreground agents
	public int numberOfForeground() {
		return foreground.size();
	}

	public void reset() {
		background = new LinkedList <Vertex> ();
		foreground = new LinkedList <Agent> ();
	}


	// // updates the state of each agent, in a random order
	public void updateAgents() {
		Agent toRemove = null;
		LinkedList <Agent> inQueue = new LinkedList<Agent>();

		// for (Agent z : background ) {
		// 	z.updateState(this);
			
		// }
		// background = inQueue;
		// for ( Agent y : foreground ) {
		// 	y.updateState();
		// }
	}


	// returns a String representing the Landscape. It can be as simple as 
	//indicating the number of Agents on the Landscape.
	public String toString() {
		String s = "Number of Agents: " + background.size();
		return s;
	}

	// sets the color of the cells in the landscape
	public Color setColor( float h, float s, float b) {
		
		c =  Color.getHSBColor( h, s, b);
		return c;
	}

	// Calls the draw method of all the agents on the Landscape.
	public void draw(Graphics g)  {
		for (Vertex a : background ) {
			a.draw(g, 0, 0, 80);
		}
		for (Agent d : foreground ) {
			
			d.draw(g, 0, 0, 80);
		}
	}

	public static void main(String[] args) {

		Landscape scape = new Landscape(100, 100);
		for (int i = 0; i < 3; i++) {
			Vertex agent = new Vertex(1, 100+10*i, 100+10*i);
			scape.addBackground( agent );
		}

		System.out.println("Test: " + scape.toString());

	}
}