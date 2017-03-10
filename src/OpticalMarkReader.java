import java.text.Format;
import java.util.ArrayList;

import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {

	/***
	 * Method to do optical mark reading on page image. Return an AnswerSheet
	 * object representing the page answers.
	 * 
	 * @param image
	 * @return
	 */
	
	public AnswerSheet processPageImage(PImage image) {
		image.filter(PImage.GRAY);
		image.loadPixels();
		AnswerSheet answers = new AnswerSheet(48);
		
		processColumn(460, 125, 900, 290, 36, 38, 20, 5, image, 50, answers);
		processColumn(460, 410, 900, 570, 36, 38, 20, 5, image, 50, answers);
		processColumn(460, 693, 900, 852, 36, 38, 20, 5, image, 50, answers);
		processColumn(460, 975, 900, 1125, 36, 38, 20, 5, image, 50, answers);
		
		return answers;
	}
	
	public void processColumn(int startRow, int startCol, int endRow, int endCol,
			int boxWidth, int boxHeight, int boxSpacing, int numBoxes, PImage image, int threshold, 
			AnswerSheet answers) {
		
		for (int col = startCol; col < endCol; col = col + boxWidth) {
			for (int row = startRow; row < endRow; row = row + boxHeight) {
				int[] results = determineBubble(row, col, boxWidth, boxHeight, boxSpacing, numBoxes, image, threshold);
				int countVal = (int)(averageOf(results));
				
				
				answers.addAnswer(countAsAnswerBox(results, countVal));
			}
		}
	}
	
	private double averageOf(int[] results) {
		int count = 0;
		for (int i = 0; i < results.length; i++) {
			count += results[i];
		}
		return (double) (count / results.length);
	}
	
	public int countAsAnswerBox(int[] pixelCounts, int countVal) {
		for (int i = 0; i < pixelCounts.length; i++) {
			if (pixelCounts[i] > countVal * 1.15) {
				return i;
			}
		}
		return 0;
	}

	public static int[] determineBubble(int startRow, int startCol, int boxWidth, int boxHeight,
			int boxSpacing, int numOfBubbles, PImage image, int value) {
		int[] answers = new int[numOfBubbles];
		
		for (int i = 0; i < numOfBubbles; i++) {
			answers[i] = getSumBlackPixels(startRow, startCol + i * boxSpacing, boxWidth, boxHeight, image, value);
		}
		return answers;
	}
	
	public static int getSumBlackPixels(int startRow, int startCol, int width, int height, PImage image, int value) {
		int count = 0;
		for (int row = startRow; row < startRow + height; row++) {
			for (int col = startCol; col < startCol + width; col++) {
				if (getPixelAt(row, col, image) < value) 
					count++;
			}
		} 
		return count;
	}
	
	public static int getPixelAt(int row, int col, PImage image) {
		int index = row * image.width + col;

		return (image.pixels[index]) & 255;
	}
}
