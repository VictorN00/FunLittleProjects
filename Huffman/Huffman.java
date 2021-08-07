import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {
	
	PriorityQueue<HuffmanNode> tree;
	//int num_bits_per;
	
	public Huffman(String filename) throws Exception {
		Scanner file = new Scanner(new File(filename));
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		while (file.hasNext()) {
			String line = file.nextLine().replaceAll("[^A-Za-z ]", "");
			for (int i = 0; i < line.length(); i++) {
				char process = line.charAt(i);
				if (!count.containsKey(process))
					count.put(process, 0);
				count.put(process, count.get(process) + 1);
			}
		}
		//num_bits_per = count.size();
		//System.out.println(num_bits_per);
		tree = new PriorityQueue<HuffmanNode>();
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			tree.add(new HuffmanNode(entry.getKey(), entry.getValue()));
		}
		
		while (tree.size() > 1) {
			HuffmanNode one = tree.remove(), two = tree.remove();
			System.out.println(one.frequency + " " + two.frequency);
			HuffmanNode sum = new HuffmanNode(null, one.frequency + two.frequency, one, two);
			tree.add(sum);
		}
	}
	
	public String encode(String raw) {
		StringBuffer encrypted = new StringBuffer();
		for (int i = 0; i < raw.length(); i++) {
			encrypted.append(encode(tree.peek(), raw.charAt(i), ""));
		}
		return encrypted.toString();
	}
	
	private String encode(HuffmanNode curr, char v, String path) {
		if (curr != null) {
			if (curr.value != null && curr.value == v) {
				return path.toString();
			}
			String path_left = encode(curr.left, v, path + "0");
			if (path_left.length() > 0)
				return path_left;
			String path_right = encode(curr.right, v, path + "1");
			if (path_right.length() > 0)
				return path_right;
		}
		return "";
	}
	
	public String decode(String bits) {
		HuffmanNode curr = tree.peek();
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < bits.length(); i++) {
			char p = bits.charAt(i);
			System.out.println(curr.frequency);
			if (curr.left == null && curr.right == null) {
				message.append(curr.value);
				curr = tree.peek();
			}
			
			if (p == '0')
				curr = curr.left;
			else if (p == '1')
				curr = curr.right;
		}
		if (curr.value != null)
			message.append(curr.value);
		return message.toString();
	}
	
}
