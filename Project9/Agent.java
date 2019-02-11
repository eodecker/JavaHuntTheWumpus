/**
* Agent.java
* Eli Decker
* 05/04/2018
*/

import java.awt.Graphics;
import java.awt.Color;

public class Agent {

	private int xPos;
	private int yPos;

	//a constructor that sets the position.
	public Agent(int x0, int y0) {
		xPos = x0;
		yPos = y0;
	}

	//returns the x position.
	public int getX() {
		return xPos;
	}
	
	//returns the y position.
	public int getY() {
		return yPos;
	}

	//sets the x position.
	public void setX( int newX ) {
		xPos = newX;
	}
	
	//sets the y position.
	public void setY( int newY ) {
		yPos = newY;
	}

	//returns a String containing the x and y positions, e.g. "(3.024, 4.245)".
	public String toString() {
		String s = "";
		s = "Postion: (" + xPos + ", " + yPos + ")";
		return s;
	}

	//does nothing, for now.
	public void updateState( Landscape scape ) {
		
	}

	//does nothing, for now.
	public void draw(Graphics g, int x0, int y0, int scale ) {
	}

	public static void main(String[] args) {
		Agent agent = new Agent( 1, 10);
		System.out.println(agent.toString());
		agent.setX(666);
		System.out.println(agent.toString());
		System.out.println(agent.getX());
	}




}