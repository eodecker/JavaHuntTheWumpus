/** 
* 04/09/2018
* Eli Decker
* Hashmap.java
*/
import java.util.ArrayList;
import java.util.Comparator;

public class Hashmap<K,V> implements MapSet<K,V> {
	private Object[] map;
	private int size;
	private int collision; 
	private Comparator<K> comparator;

	public Hashmap (int initCap, Comparator<K> comp) {
		map = new Object[initCap];
		size = 0;
		collision = 0;
		comparator = comp;
	}

	// adds or updates a key-value pair
    // If there is already a piar with new_key in the map, then update
    // the pair's value to new_value.
    // If there is not already a pair with new_key, then
    // add pair with new_key and new_value.
    // returns the old value or null if no old value existed
    public V put( K new_key, V new_value ) {
    	int hash = Math.abs(new_key.hashCode() );
		int index = hash % map.length;
		
		//handle collisions
		if (map[index] != null && !((BSTMap<K, V>)map[index]).containsKey(new_key) ) {
			((BSTMap<K, V>)map[index]).put(new_key, new_value);
			collision++;
			size++;
			return null;
		}

		//when index of map is null
		else if (map[index] == null) {
			map[index] = new BSTMap<K, V>( comparator );
			((BSTMap<K, V>)map[index]).put( new_key, new_value );
			size++;

			return null;
		}

		// when index of map already has something
		else {
			V v = ((BSTMap<K, V>)map[index]).get(new_key);
			((BSTMap<K, V>)map[index]).put(new_key, new_value);
			return v;

			
		}
	}
    
    // Returns true if the map contains a key-value pair with the given key
    public boolean containsKey( K key ) {
    	int hash = Math.abs(key.hashCode() );
		int index = hash % map.length;
		if (map[index] == null) {return false;}
		else {
			if ( ((BSTMap<K, V>)map[index]).containsKey(key) ) {return true;}
			else {return false;}
		}
		
    }
    
    // Returns the value associated with the given key.
    // If that key is not in the map, then it returns null.
    public V get( K key ) {
    	int hash = Math.abs(key.hashCode() );
		int index = hash % map.length;
		if (((BSTMap<K, V>)map[index]).containsKey(key)) {
			return ((BSTMap<K, V>)map[index]).get(key);
		}
		else {return null;}
	}
    
    // Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet() {
    	ArrayList<K> keyList = new ArrayList<K>();
    	for ( Object bst : map ) {
    		keyList.addAll(((BSTMap<K, V>)bst).keySet());
    	}
    	return keyList;
    }
    	


    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values() {
    	ArrayList<V> values = new ArrayList<V>();
    	for ( Object bst : map ) {
    		values.addAll(((BSTMap<K, V>)bst).values());
    	}
    	return values;
    }


    // return an ArrayList of pairs. There is no defined order for the
    // keys, and they do not need to match keySet or values (but they can).
    public ArrayList<KeyValuePair<K,V>> entrySet() {
    	ArrayList<KeyValuePair<K, V>> pairsList = new ArrayList<KeyValuePair<K, V>>();
    	for ( Object bst : map ) {
    		pairsList.addAll(((BSTMap<K, V>)bst).entrySet());
    	}
    	return pairsList;
    }

    // Returns the number of key-value pairs in the map.
    public int size() {
    	return size;
    }

    // Returns the number of key-value pairs in the map.
    public int collisions() {
    	return collision;
    }
    // stub method
    public int depth() {
    	return 0;
    }
        
    // removes all mappings from this MapSet
    public void clear() {
    	map = new Object[0];
		size = 0;
		collision = 0;
    }

    public String toString () {
		String s = "";
		for ( Object bst : map ) {
			if (bst == null) {s+= "";}
			else {
				s+= ((BSTMap<K, V>)bst).toString();
			}
    		
    	}
		return s;
	}

	public static void main( String[] argv ) {
		Hashmap<String, Integer> map = new Hashmap<String, Integer>( 10, new StringAscending() );

		System.out.println( "Contains Key twenty: " + map.containsKey( "twenty") );
		map.put( "twenty", 20 );
		map.put( "ten", 10 );
		map.put( "eleven", 11 );
		map.put( "five", 5 );
		map.put( "six", 6 );

		map.put( "one", 1 );
		map.put( "two", 2 );
		map.put( "three", 3 );
		map.put( "four", 4 );
		map.put( "seven", 7 );

		map.put( "eleven", 100 );
		map.put( "six", 600 );

		System.out.println("***********");
		System.out.println( map.toString() );
		System.out.println( "Size: " + map.size() );
		System.out.println( "Collisions: " + map.collisions() );
		System.out.println("***********");

		System.out.println( "Contains Key twenty: " + map.containsKey( "twenty") );

		map.clear();
		System.out.println("***********");
		System.out.println( map.toString() );
		System.out.println("***********");

	}

}

