
public class HuffmanRunner {

	public void run() throws Exception {
		Huffman one = new Huffman("huffman_test.dat");
		System.out.println(one.encode("abc"));
		System.out.println(one.decode("110100"));
	}

	public static void main(String[] args) throws Exception {
		new HuffmanRunner().run();
	}

}