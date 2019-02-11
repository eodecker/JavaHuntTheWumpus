/** 
* 04/30/2018
* Eli Decker
* Vertex.java
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

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

enum Direction {NORTH, SOUTH, EAST, WEST};

public class Vertex extends Agent implements Comparable<Vertex>{
	private int id;
	private int cost;
	private boolean teleporter;
	private boolean marked;
	private boolean visible;
	private Hashmap<Direction, Vertex> edges;
	private boolean visited;
	
	// return the opposite direction of the given one 
	public static Direction opposite (Direction d) {
		if (d == Direction.NORTH) return Direction.SOUTH;
		else if (d == Direction.SOUTH) return Direction.NORTH;
		else if (d == Direction.EAST)  return Direction.WEST;
		else return Direction.EAST;
	}
	
	public Vertex (int id, int x0, int y0) {
		super( x0, y0 );
		this.id = id;
		// each vertex can have up to 4 neighbors at 4 directions 
		this.edges = new Hashmap<Direction, Vertex>( 5, new compareDir() );
		visited = false;
		visible = false;
		cost = 0;
		teleporter = false;
		marked = false;
	}
	
	//modify the object's adjacency map so that it connects with the other Vertex. 
	//This is a uni-directional link.
	public void connect (Vertex v, Direction dir) {
		if (!edges.containsKey(dir)) edges.put(dir, v);
	}

	//returns the Vertex in the specified direction or null.
	public Vertex getNeighbor(Direction dir) {
		if (edges.containsKey(dir)) return this.edges.get(dir);
		else return null;
	}

	//returns a Collection, which could be an ArrayList, of all of the object's neighbors.
	public ArrayList<Vertex> getNeighbors() {
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		for (Direction d : Direction.values() ) {
			if (getNeighbor(d) != null) {
				neighbors.add( getNeighbor(d) );
			}
		}
		return neighbors;
		
	}

	// returns cost
	public int getCost() {
		return cost;
	}

	// sets if it is a teleporter or not
	public void setTeleporter(boolean isTeleporter) {
		teleporter = isTeleporter;
	}

	// sets the cost field
	public void setCost(int c) {
		cost = c;
	}

	// set the marked field
	public void setMarked(boolean isM) {
		marked = isM;
	}

	// returns if vertex is marked or not
	public boolean isMarked() {
		return marked;
	}

	// sets whether or not the vertex is visible
	public void setVisible( boolean isVisible ) {
		visible = isVisible;
	}

	// compares cost of two vertices
	public int compareTo(Vertex a) {
		return this.getCost() - a.getCost();
     
	}

	// draw method for vertex - draws the "room"
	public void draw(Graphics g, int x0, int y0, int scale) {
		if (!this.visible) return;
		
		int xpos = x0 + this.getX()*scale;
		int ypos = y0 + this.getY()*scale;
		int border = 2;
		int half = scale / 2;
		int eighth = scale / 8;
		int sixteenth = scale / 16;

		RoundGradientPaint rgp;
		
		// draw rectangle for the walls of the cave
		if (this.cost <= 2) {
			// wumpus is nearby
			g.setColor( new Color(130, 31, 1));
			// rgp = new RoundGradientPaint(xpos+border*20, ypos + border*20, Color.magenta,
   //      	new Point2D.Double(0, 85), new Color(89, 62, 0));
			// try {
			// 	BufferedImage image = ImageIO.read(new File("Close.png"));
			// 	g.drawImage( image, xpos + border, ypos + border, scale - 2*border, scale - 2 * border, null );
			// }
			// catch(FileNotFoundException ex) {
	  //         System.out.println("WordCounter.read():: unable to open file "  );
	  //       }
	  //       catch(IOException ex) {
	  //         System.out.println("WordCounter.read():: error reading file " );
	  //       }
	    }
	    else if (teleporter) {
	    	g.setColor( new Color(185, 66, 244));
	    }
		else {
			// wumpus is not nearby
			g.setColor( new Color(89, 62, 0));
			// rgp = new RoundGradientPaint(xpos+border*20, ypos + border*20, Color.magenta,
   //      	new Point2D.Double(0, 85), new Color(89, 62, 0));
			// try {
			// 	BufferedImage image = ImageIO.read(new File("NotClose.png"));
			// 	g.drawImage( image, xpos + border, ypos + border, scale - 2*border, scale - 2 * border, null );
			// }
			// catch(FileNotFoundException ex) {
	  //         System.out.println("WordCounter.read():: unable to open file "  );
	  //       }
	  //       catch(IOException ex) {
	  //         System.out.println("WordCounter.read():: error reading file " );
	  //       }
		}
	    // Graphics2D g2 = (Graphics2D) g;
    	// RoundRectangle2D r = new RoundRectangle2D.Float(xpos + border, ypos + border, scale - 2*border, scale - 2 * border, 25,
     //    25);
    	
    	// g2.setPaint(rgp);
    	// g2.fill(r);
		g.fillRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
		
		
		// draw doorways as boxes
		g.setColor( new Color(209, 160, 0) );
		if (this.edges.containsKey(Direction.NORTH))
			g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
		if (this.edges.containsKey(Direction.SOUTH))
			g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
				  eighth, eighth + sixteenth);
		if (this.edges.containsKey(Direction.WEST))
			g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
		if (this.edges.containsKey(Direction.EAST))
			g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
				  eighth + sixteenth, eighth);
	}


	public String toString() {
		String string = "";
		string += "Number of neighbors: " + getNeighbors().size() + ".    Cost: " + getCost() + ".   Marked: " + isMarked();
		return string;
	}

	class RoundGradientPaint implements Paint {
    protected Point2D point;

    protected Point2D mRadius;

    protected Color mPointColor, mBackgroundColor;

    public RoundGradientPaint(double x, double y, Color pointColor,
        Point2D radius, Color backgroundColor) {
      if (radius.distance(0, 0) <= 0)
        throw new IllegalArgumentException("Radius must be greater than 0.");
      point = new Point2D.Double(x, y);
      mPointColor = pointColor;
      mRadius = radius;
      mBackgroundColor = backgroundColor;
    }

    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
        Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
      Point2D transformedPoint = xform.transform(point, null);
      Point2D transformedRadius = xform.deltaTransform(mRadius, null);
      return new RoundGradientContext(transformedPoint, mPointColor,
          transformedRadius, mBackgroundColor);
    }

    public int getTransparency() {
      int a1 = mPointColor.getAlpha();
      int a2 = mBackgroundColor.getAlpha();
      return (((a1 & a2) == 0xff) ? OPAQUE : TRANSLUCENT);
    }
  }
  public class RoundGradientContext implements PaintContext {
    protected Point2D mPoint;

    protected Point2D mRadius;

    protected Color color1, color2;

    public RoundGradientContext(Point2D p, Color c1, Point2D r, Color c2) {
      mPoint = p;
      color1 = c1;
      mRadius = r;
      color2 = c2;
    }

    public void dispose() {
    }

    public ColorModel getColorModel() {
      return ColorModel.getRGBdefault();
    }

    public Raster getRaster(int x, int y, int w, int h) {
      WritableRaster raster = getColorModel().createCompatibleWritableRaster(
          w, h);

      int[] data = new int[w * h * 4];
      for (int j = 0; j < h; j++) {
        for (int i = 0; i < w; i++) {
          double distance = mPoint.distance(x + i, y + j);
          double radius = mRadius.distance(0, 0);
          double ratio = distance / radius;
          if (ratio > 1.0)
            ratio = 1.0;

          int base = (j * w + i) * 4;
          data[base + 0] = (int) (color1.getRed() + ratio
              * (color2.getRed() - color1.getRed()));
          data[base + 1] = (int) (color1.getGreen() + ratio
              * (color2.getGreen() - color1.getGreen()));
          data[base + 2] = (int) (color1.getBlue() + ratio
              * (color2.getBlue() - color1.getBlue()));
          data[base + 3] = (int) (color1.getAlpha() + ratio
              * (color2.getAlpha() - color1.getAlpha()));
        }
      }
      raster.setPixels(0, 0, w, h, data);

      return raster;
    }
  }

	// test function
	public static void main( String[] argv ) {
		System.out.println("Driection: " + Direction.NORTH + ". Opposite direction: " + opposite(Direction.NORTH) );
		System.out.println("Driection: " + Direction.SOUTH + ". Opposite direction: " + opposite(Direction.SOUTH) );
		System.out.println("Driection: " + Direction.EAST + ". Opposite direction: " + opposite(Direction.EAST) );
		System.out.println("Driection: " + Direction.WEST + ". Opposite direction: " + opposite(Direction.WEST) );
		Vertex v1 = new Vertex(1, 10, 20);
		Vertex v2 = new Vertex(2, 10, 20);
		Vertex v3 = new Vertex(1, 10, 20);
		Vertex v4 = new Vertex(2, 10, 20);
		Vertex v5 = new Vertex(2, 10, 20);
		Vertex v6 = new Vertex(2, 10, 20);
		v1.connect(v2, Direction.EAST);
		v2.connect(v3, Direction.SOUTH);
		v3.connect(v4, Direction.WEST);
		v4.connect(v1, Direction.NORTH);

		v1.connect(v5, Direction.NORTH);
		v1.connect(v6, Direction.WEST);

		System.out.println("Neighbors: " + v1.getNeighbors() );
		System.out.println(v1.toString());


	}	
}

// comparator class separate
class compareDir implements Comparator<Direction> {
		public int compare( Direction a, Direction b ) {
				return a.compareTo(b);
		}
}