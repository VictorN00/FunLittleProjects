import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsMain {

	public void run() throws Exception {
		System.out.println("Welcome to the 20 question program.\n");
		boolean read_previous_tree = yesTo("Do you want to read in the previous tree? (y/n)? ");
		QuestionsGame game;
		if (read_previous_tree) {
			Scanner file = new Scanner(new File("previous_tree_file_name.txt"));
			game = new QuestionsGame(file.nextLine());
		}
		else {
			Scanner in = new Scanner(System.in);
			while (true) {
				System.out.print("Please provide a file name :: ");
				try {
					String filename = in.nextLine();
					game = new QuestionsGame(filename);
					PrintStream write = new PrintStream(new File("previous_tree_file_name.txt"));
					write.print(filename);
					break;
				} catch (Exception e) {
					System.out.println("The file is either bad or cannot be found. Try again.");
				}
			}
		}
		game.askQuestions();
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

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new QuestionsMain().run();
	}

}
