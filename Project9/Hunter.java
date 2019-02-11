/** 
* 05/4/2018
* Eli Decker
* Hunter.java
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

public class Hunter extends Agent{

	private Vertex location;
	private boolean isAlive;
	private boolean armed;

	public Hunter (int x0, int y0, Vertex initPos) {
		super( x0, y0 );
		location = initPos;
		isAlive = true;
		armed = false;
	}

	// changes the hunter's position to a new vertex
	public Vertex changePos( Vertex newPos ) {
		if (newPos == null) {
			return null;
		}
		else {
			location = newPos;
			return newPos;
		}
		
	}

	public Vertex getPos(){
		return location;
	}

	public void armBow() {
		armed = true;
	}

	public void disarmBow() {
		armed = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void alive( boolean state) {
		isAlive = state;
	}

	public void draw(Graphics g, int x0, int y0, int scale) {
		
		int xpos = 0 + x0 + location.getX()*scale;
		int ypos = 0 + y0 + location.getY()*scale;

		if (isAlive && !armed) {
			try {
				BufferedImage image = ImageIO.read(new File("unArmed.png"));
				g.drawImage( image, xpos, ypos, scale -4, scale -4, null );
			}
			catch(FileNotFoundException ex) {
	          System.out.println("WordCounter.read():: unable to open file "  );
	        }
	        catch(IOException ex) {
	          System.out.println("WordCounter.read():: error reading file " );
	        }
		}
		else if (isAlive && armed ) {
			try {
				BufferedImage image = ImageIO.read(new File("Armed.png"));
				g.drawImage( image, xpos, ypos, scale -4, scale -4, null );
			}
			catch(FileNotFoundException ex) {
	          System.out.println("WordCounter.read():: unable to open file "  );
	        }
	        catch(IOException ex) {
	          System.out.println("WordCounter.read():: error reading file " );
	        }
		}
		else {
			try {
				BufferedImage image = ImageIO.read(new File("deadHunter.png"));
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