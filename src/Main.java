import java.io.File;
import java.util.ArrayList;

import processing.core.PImage;

public class Main {
	public static final String PDF_PATH = "/omrtest.pdf";
	public static OpticalMarkReader markReader = new OpticalMarkReader();

	public static void main(String[] args) {
		System.out.println("Welcome!  I will now auto-score your pdf!");
		System.out.println("Loading file..." + PDF_PATH);
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);

		System.out.println("Scoring all pages...");
		ArrayList<AnswerSheet> answers = getPageAnswers(images);
		double[][] data1 = scoreAllPages(answers);
		String strData1 = dataToString(data1);

		CSVData.writeDataToFile("/Desktop/NewWorkspace/OMR/scores.txt", strData1);

		System.out.println("Processing analysis...");
		double[][] data2 = questionScore(answers, 48);
		String strData2 = dataToString(data2);

		CSVData.writeDataToFile("/Desktop/NewWorkspace/OMR/question_scores.txt", strData2);

		for (int i = 0; i < answers.size(); i++) {
			answers.get(i).getAnswers();
		}

		System.out.println("Complete!");

		// Optional: add a saveResults() method to save answers to a csv file
	}

	/***
	 * Score all pages in list, using index 0 as the key.
	 * 
	 * return CSVData file of all pages scores
	 * 
	 * @param images
	 *            List of images corresponding to each page of original pdf
	 */
	private static double[][] scoreAllPages(ArrayList<AnswerSheet> scoredSheets) {
		// Score the first page as the key
		AnswerSheet key = scoredSheets.get(0);

		// create a 2d array of data for each page using # of incorrect
		double[][] data = new double[scoredSheets.size()][4];

		for (int i = 1; i < scoredSheets.size(); i++) {
			AnswerSheet answers = scoredSheets.get(i);

			int incorrect = getNumOfWrong(answers, key);
			int correct = answers.getLength() - incorrect;
			double percentIncorrect = Math.round((incorrect / answers.getLength()) * 1000.0) / 1000.0;
			double percentCorrect = Math.round((correct / answers.getLength()) * 1000.0) / 1000.0;

			data[i][0] = correct;
			data[i][1] = incorrect;
			data[i][2] = percentCorrect;
			data[i][3] = percentIncorrect;
		}

		return data;
	}

	public static double[][] questionScore(ArrayList<AnswerSheet> answers, int numProblems) {
		AnswerSheet key = answers.get(0);
		double[][] data = new double[numProblems][2];
		int[] questionsWrong = new int[numProblems];

		for (int i = 1; i < answers.size(); i++) {
			for (int j = 0; j < questionsWrong.length; j++) {
				AnswerSheet sheet = answers.get(i);
				if (!isAnswerRight(sheet, key, j)) {
					questionsWrong[j]++;
				}
			}
		}

		for (int r = 0; r < data.length; r++) {
			data[r][0] = questionsWrong[r];
			data[r][1] = Math.round(questionsWrong[r] / answers.size() * 1000.0) / 1000.0;
		}
		return data;
	}

	private static ArrayList<AnswerSheet> getPageAnswers(ArrayList<PImage> images) {
		ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();

		// Score the first page as the key
		AnswerSheet key = markReader.processPageImage(images.get(0));

		for (int i = 1; i < images.size(); i++) {
			PImage image = images.get(i);

			AnswerSheet answers = markReader.processPageImage(image);
			scoredSheets.add(answers);
		}

		return scoredSheets;
	}

	public static String dataToString(double[][] data) {
		String ans = "";

		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data[0].length; c++) {
				ans += data[r][c] + ", ";
			}
		}
		return ans;
	}

	private static int getNumOfWrong(AnswerSheet sheet, AnswerSheet key) {
		int count = 0;
		for (int i = 0; i < sheet.getLength(); i++) {
			if (!isAnswerRight(sheet, key, i)) {
				count++;
			}
		}
		return count;
	}

	private static boolean isAnswerRight(AnswerSheet sheet, AnswerSheet key, int index) {
		if (sheet.getAnswer(index) != key.getAnswer(index))
			return false;
		return true;
	}
}