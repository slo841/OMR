import java.util.ArrayList;

import processing.core.PImage;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private ArrayList<Integer> answers;

	public AnswerSheet(int numAnswers) {
		answers = new ArrayList<Integer>();
	}
	
	public int getLength() {
		return answers.size();
	}

	public void addAnswer(int a) {
		answers.add(a);
	}

	public int getAnswer(int a) {
		return answers.get(a);
	}
	
	public void getAnswers() {
		for (int i = 0; i < answers.size(); i++) {
			System.out.println(answers.get(i));
		}
	}

}
