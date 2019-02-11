/** 
* 04/02/2018
* Eli Decker
* KeyValuePair.java
*/


public class KeyValuePair<Key,Value> {
	Key key;
	Value value;

	// the constructor initializing the key and value fields.
	public KeyValuePair( Key k, Value v ) {
		key = k;
		value = v;
	}

	// returns the key
	public Key getKey() {
		return key;
	}

	// returns the value
	public Value getValue() {
		return value;
	}

	// sets the value
	public void setValue( Value newV) {
		value = newV;
	}

	// returns a String containing both the key and value
	public String toString() {
		String string = new String();
		string = key + ", " + value;
		return string;
	}

	public static void main (String args[]) {
		KeyValuePair<String,Integer> pair = new KeyValuePair<String,Integer>( "a",  1);
		System.out.println(pair.toString());

		System.out.println(pair.getKey());
		System.out.println(pair.getValue());

		pair.setValue(3);
		System.out.println(pair.toString());

	} 
}