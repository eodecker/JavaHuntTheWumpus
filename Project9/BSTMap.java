/** 
* 04/02/2018
* Eli Decker
* BSTMap.java
*/

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {

	private TNode<K, V> root;
	private Comparator<K> comparator;
	private int size;


	// constructor: takes in a Comparator object
	public BSTMap( Comparator<K> comp ) {
			// initialize fields here
		comparator = comp;
		root = null;
		size = 0;
	}

	// Put the key-value pair into the BSTMap
	// Returns the old value or null if a new key was added
	public V put( K key, V value ) {
			// check for and handle the special case
			// call the root node's put method
		if (root == null) {
			root = new TNode<K, V>( key, value, null, null);
			size++;
			return null;
		}
		else {
			V val = root.put(key, value, comparator);
			if (val != null) size++;
			return val;
		}

	}

	// gets the value at the specified key or null
	public V get( K key ) {
			// check for and handle the special case
			// call the root node's get method
		if (root == null) {
			return null;
		}
		else {
			V val = root.get( key, comparator );
			return val;
		}
	}

	public boolean containsKey( K key ) {
			// check for and handle the special case
			// call the root node's get method
		if (root == null) {
			return false;
		}
		else {
			return root.containsKey(key, comparator);
		}
	}

	// returns depth of tree
	public int depth () {
		if (root == null) return 0;
		return root.depth();
	}

	//stub method
	public int collisions() {
		return 0;
	}

	// traverses
	public void traverse() {
		if (root == null) System.out.println("Empty Tree");
		else root.traverse();
	}

	// Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet() {
    	ArrayList<K> keyList = new ArrayList<K>();
    	root.keySetHelper( keyList);
    	return keyList;
    }



    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values() {
    	ArrayList<V> valuesList = new ArrayList<V>();
    	root.valuesHelper( valuesList);
    	return valuesList;
    }
    
    // return an ArrayList of pairs. There is no defined order for the
    // keys, and they do not need to match keySet or values (but they can).
    public ArrayList<KeyValuePair<K,V>> entrySet() {
    	ArrayList<KeyValuePair<K, V>> pairsList = new ArrayList<KeyValuePair<K, V>>();
    	root.entrySetHelper( pairsList );
    	return pairsList;
    }

    // Returns the number of key-value pairs in the map.
    public int size() {
    	return entrySet().size();
    }
        
    // removes all mappings from this MapSet
    public void clear() {
    	root = null;
		size = 0;
    }

    public String toString() {
    	String string = "";
    	for ( KeyValuePair<K, V> pair : entrySet() ) {
    		string += pair + "\n";
    	}
    	return string;
    }




	// Write stubs (functions with no code) for the remaining
	// functions in the MapSet interface


	// You can make this a nested class, doesn't have to be
	private class TNode<K, V> {
			// need fields for the left and right children
			// need a KeyValuePair to hold the data at this node
		private KeyValuePair< K, V> pair;
		private TNode<K, V> left;
		private TNode<K, V> right;

			// constructor, given a key and a value
			public TNode( K k, V v, TNode<K, V> l, TNode<K, V> r ) {
					// initialize all of the TNode fields
				pair = new KeyValuePair<K, V>(k, v);
				left = l;
				right = r;
			}

			// Takes in a key, a value, and a comparator and inserts the TNode
			// Returns the old value of the node, if replaced, or null if inserted
			public V put( K key, V value, Comparator<K> comp ) {
					// implement the binary search tree put
					// insert only if the key doesn't already exist
				if (comp.compare( key, pair.getKey()) == 0 ) {
					V oldVal = pair.getValue();
					pair.setValue( value );
					return oldVal;
				}

				else if ( comp.compare(key, pair.getKey()) > 0 ) {
					if (right == null ) {
						right = new TNode<K, V>(key, value, null, null);
						return null;
					}
					else {
						right.put(key, value, comp);
						return value;
					}
				}

				else {
					if (left == null ) {
						left = new TNode<K, V>(key, value, null, null);
						return null;
					}
					else {
						left.put(key, value, comp);
						return value;
					}
				}
			}

			// Takes in a key and a comparator
			// Returns the value associated with the key or null
			public V get( K key, Comparator<K> comp ) {
				int comparison = comp.compare(this.pair.getKey(), key);

				if (comparison == 0) {
					return this.getPair().getValue();
				}

				else if (comparison > 0) {
					if (left == null) {
						return null;
					}
					else {
						return left.get(key, comp);
					}
				}

				else {
					if (right == null ) {
						return null;
					}
					else {
						return right.get( key, comp );
					}
				}
			}

			// checks if the TNode contains the key
			public boolean containsKey( K key, Comparator<K> comp ) {
				int comparison = comp.compare(this.pair.getKey(), key );
				if (comparison == 0) {
					return true;
				}

				if (left == null && right == null ) {
					return false;
				}

				if ( comparison > 0 ) {
					if (left == null ) {
						return false;
					}
					else {
						return left.containsKey( key, comp );
					}
				}

				else {
					if (right == null ) {
						return false;
					}
					else {
						return right.containsKey( key, comp);
					}
				}
			}

			// gets the left node
			public TNode<K, V> getLeft() {
				return left;
			}
			// gets the right node
			public TNode<K, V> getRight() {
				return right;
			}
			// returns the key value pair of the node
			public KeyValuePair<K, V> getPair() {
				return pair;
			}
			// returns the value
			public V getValue() {
				return pair.getValue();
			}
			// returns the key
			public K getKey() {
				return pair.getKey();
			}

			// returns the depth of the tree
			public int depth () {
				if (left == null && right == null) return 0;

				int depL = 0;
				if (left != null) depL = left.depth() + 1;
				int depR = 0;
				if (right != null) depR = right.depth() + 1;

				if (depL > depR) return depL;
				return depR;
			}

			// Remaining methods below, mostly for building ArrayLists
			
			// traverses over TNodes
			public void traverse() {
						if( left != null) {left.traverse();}
						System.out.println( " " + pair.getValue() + " " );
						if( right != null) {right.traverse();}
			}

			// helps pair list by traversing nodes
			public void entrySetHelper( ArrayList<KeyValuePair<K,V>> list) {
					if (this != null ) {
						list.add(this.getPair() );
						if( left != null) {left.entrySetHelper( list );}
						if( right != null) {right.entrySetHelper( list );}
					}
			}
			// helps keys list by traversing nodes
			public void keySetHelper( ArrayList<K> list) {
					if (this != null ) {
						if( left != null) {left.keySetHelper( list );}
						list.add(this.getKey() );
						if( right != null) {right.keySetHelper( list );}
					}
			}
			// helps values list by traversing nodes
			public void valuesHelper( ArrayList<V> list) {
					if (this != null ) {
						if( left != null) {left.valuesHelper( list );}
						list.add(this.getValue() );
						if( right != null) {right.valuesHelper( list );}
					}
			}

			public String toString( TNode<K, V> node ) {
				String string = "";
				toString( node );
				string += "Key: " + node.getKey() + " Value: " + node.getValue();
				return string;
			}


			
			
	}// end of TNode class

	// test function
	public static void main( String[] argv ) {
			// create a BSTMap
			BSTMap<String, Integer> bst = new BSTMap<String, Integer>( new StringAscending() );

			System.out.println( "Contains Key twenty: " + bst.containsKey( "twenty") );
			bst.put( "twenty", 20 );
			bst.put( "ten", 10 );
			bst.put( "eleven", 11 );
			bst.put( "five", 5 );
			bst.put( "six", 6 );

			System.out.println("***********");
			System.out.println( bst.toString() );
			System.out.println("***********");


			System.out.println( bst.get( "eleven" ) );
			bst.traverse();
			bst.clear();
			bst.traverse();

			bst.put( "twenty", 20 );
			bst.put( "ten", 10 );
			bst.put( "eleven", 11 );
			bst.put( "five", 5 );
			bst.put( "six", 6 );
			bst.put( "six", 7 );
			bst.traverse();

			System.out.println( "Contains Key twenty: " + bst.containsKey( "twenty") );
			System.out.println( "Size: " + bst.size() );
			System.out.println( "list size: " + bst.entrySet().size() );
			System.out.println( "Depth: " + bst.depth() );
			System.out.println("List: " + bst.entrySet() );

			System.out.println("List of Keys: " + bst.keySet() );
			System.out.println("List of Values: " + bst.values() );

	}

}

// comparator class separate
class StringAscending implements Comparator<String> {
		public int compare( String a, String b ) {
				return a.compareTo(b);
		}
}

class IntegerAscending implements Comparator<Integer> {
		public int compare( Integer a, Integer b ) {
				return a.compareTo(b);
		}
}