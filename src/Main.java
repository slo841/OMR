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
		CSVData file = scoreAllPages(images);

		System.out.println("Complete!");
		
		// Optional:  add a saveResults() method to save answers to a csv file
	}

	/***
	 * Score all pages in list, using index 0 as the key.
	 * 
	 * NOTE:  YOU MAY CHANGE THE RETURN TYPE SO YOU RETURN SOMETHING IF YOU'D LIKE
	 * 
	 * @param images List of images corresponding to each page of original pdf
	 */
	private static CSVData scoreAllPages(ArrayList<PImage> images) {
		ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();

		// Score the first page as the key
		AnswerSheet key = markReader.processPageImage(images.get(0));
		
		//create a 2d array of data for each page using # of incorrect
		double[][] data = new double[images.size()][4];
		
		for (int i = 1; i < images.size(); i++) {
			PImage image = images.get(i);

			AnswerSheet answers = markReader.processPageImage(image);
			scoredSheets.add(answers);
			
			int incorrect = getNumOfWrong(answers, key);
			int correct = answers.getLength() - incorrect;
			double percentIncorrect = Math.round((incorrect / answers.getLength()) * 1000.0) / 1000.0;
			double percentCorrect = Math.round((correct / answers.getLength()) * 1000.0) / 1000.0;
			
			data[i][0] = correct;
			data[i][1] = incorrect;
			data[i][2] = percentCorrect;
			data[i][3] = percentIncorrect;
		}
		
		return new CSVData(data);
	}
	
	public static CSVData studentsScore(ArrayList<PImage> images, int numProblems) {
		AnswerSheet key = markReader.processPageImage(images.get(0));
		int[] questionsWrong = new int[numProblems];
		
		for (int i = 1; i < images.size(); i++) {
			for (int j = 0; j < questionsWrong.length; j++) {
				
			}
		}
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