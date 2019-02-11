/** 
* 05/4/2018
* Eli Decker
* Wumpus.java
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;

public class Wumpus extends Agent{

	private Vertex location;
	private boolean visible;
	private boolean isAlive;

	public Wumpus (int x0, int y0, Vertex initPos) {
		super( x0, y0 );
		location = initPos;
		visible = false;
		isAlive = true;

	}

	// changes the hunter's position to a new vertex
	public Vertex changePos( Vertex newPos ) {
		location = newPos;
		return newPos;
	}

	public Vertex getPos() {
		return location;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void alive( boolean state) {
		isAlive = state;
	}

	// sets whether or not the vertex is visible
	public void setVisible( boolean isVisible ) {
		visible = isVisible;
	}

	public void draw(Graphics g, int x0, int y0, int scale) {
		
		if (visible) {
			int xpos = x0 + location.getX()*scale;
			int ypos = y0 + location.getY()*scale;
			if (!isAlive) {
				try {
					BufferedImage image = ImageIO.read(new File("deadWumpus.png"));
					g.drawImage( image, xpos, ypos, scale -4, scale -4, null );
				}
				catch(FileNotFoundException ex) {
		          System.out.println("WordCounter.read():: unable to open file "  );
		        }
		        catch(IOException ex) {
		          System.out.println("WordCounter.read():: error reading file " );
		        }
			}
			
		}
		
	}

}