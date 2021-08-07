import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

public class QuestionsGame {
	// Your code here
	private QuestionNode root;

	String file_name;

	public QuestionsGame() {
		root = new QuestionNode('A', "computer");
	}

	public QuestionsGame(String filename) throws Exception {
		root = null;
		file_name = filename;
		read(filename);
	}

	public void read(String filename) throws Exception {
		file_name = filename;
		root = read(root, new Scanner(new File(filename)));
	}

	private QuestionNode read(QuestionNode curr, Scanner file_in) {
		if (file_in.hasNext()) {
			char type = file_in.nextLine().charAt(0);
			String info = file_in.nextLine();
			curr = new QuestionNode(type, info, null, null);
			if (type == 'Q') {
				curr.left = read(curr.left, file_in);
				curr.right = read(curr.right, file_in);
			}
			return curr;
		}
		return null;
	}

	public void write(String filename) throws Exception {
		write(root, new PrintStream(new File(filename)));
	}

	private void write(QuestionNode curr, PrintStream file_out) {
		if (curr != null) {
			file_out.printf("%c:\n%s\n", curr.type, curr.info);
			write(curr.left, file_out);
			write(curr.right, file_out);
		}
	}

	public void askQuestions() throws Exception {
		if (root == null) {
			System.out.println("The question tree is empty.");
			return;
		}
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("Please think of an object for me to guess.");
			QuestionNode curr = root, prev = null;
			char prev_move = 'L';
			while (curr.type != 'A') {
				prev = curr;
				if (yesTo(curr.info + " (y/n)? ")) {
					curr = curr.left;
					prev_move = 'L';
				}
				else {
					curr = curr.right;
					prev_move = 'R';
				}
			}
			if (yesTo("Would your object happen to be a " + curr.info + "? (y/n)? "))
				System.out.println("Great, I got it right!");
			else {
				System.out.print("What is the name of your object? ");
				String object = in.nextLine().trim();
				System.out.print("Please give me a yes/no question that distinguishes between your object and mine --> ");
				String question = in.nextLine().trim();
				boolean qans = yesTo("And what is the answer for your object? (y/n)? ");
				QuestionNode replace = new QuestionNode('Q', question, qans ? new QuestionNode('A', object) : curr, qans ? curr : new QuestionNode('A', object));
				if (prev_move == 'L')
					prev.left = replace;
				else
					prev.right = replace;
			}
			write(file_name);
			boolean again = yesTo("\nDo you want to go again? (y/n)? ");
			if (!again) {
				System.out.println("Thanks for playing!");
				return;
			}
			else
				System.out.println();
		}
	}

	public boolean yesTo(String prompt) {
		Scanner in = new Scanner(System.in);
		String ans = "";
		while (!ans.equals("y") && !ans.equals("n")) {
			System.out.print(prompt);
			ans = in.nextLine().trim();
		}
		return ans.equals("y");
	}

	private static class QuestionNode {
		// Your code here
		public QuestionNode left, right;

		public char type;
		public String info;

		public QuestionNode(char type, String info) {
			this.type = type;
			this.info = info;
		}

		public QuestionNode(char type, String info, QuestionNode left, QuestionNode right) {
			this.type = type;
			this.info = info;
			this.left = left;
			this.right = right;
		}
	}
}
