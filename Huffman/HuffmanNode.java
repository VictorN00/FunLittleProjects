
public class HuffmanNode implements Comparable<HuffmanNode> {
	
	HuffmanNode left;
	HuffmanNode right;
	
	Character value;
	int frequency;
	
	public HuffmanNode(Character v, int f) {
		value = v;
		frequency = f;
		left = null;
		right = null;
	}
	
	public HuffmanNode(Character v, int f, HuffmanNode l, HuffmanNode r) {
		value = v;
		frequency = f;
		left = l;
		right = r;
	}

	@Override
	public int compareTo(HuffmanNode o) {
		// TODO Auto-generated method stub
		return frequency - o.frequency;
	}
	
	/*
	 * public String toString() { return "(" + value + ", " + frequency + ")"; }
	 */
	
}
