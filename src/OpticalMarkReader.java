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
				int[] results = checkRowOfBoxes(row, col, boxWidth, boxHeight, boxSpacing, numBoxes, image, threshold);
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

	public static int[] checkRowOfBoxes(int startRow, int startCol, int boxWidth, int boxHeight,
			int boxSpacing, int numOfBubbles, PImage image, int value) {
		int[] answers = new int[numOfBubbles];
		
		for (int i = 0; i < numOfBubbles; i++) {
			answers[i] = countBlackPixels(startRow, startCol + i * boxSpacing, boxWidth, boxHeight, image, value);
		}
		return answers;
	}
	
	public static int countBlackPixels(int startRow, int startCol, int width, int height, PImage image, int value) {
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
	
//	public AnswerSheet processPageImage(PImage image, Location loc) {
//	image.filter(PImage.GRAY);
//	image.loadPixels();
//	
//	for (int qRow = 0; qRow < 25; qRow++) {
//		for (int qCol = 0; qCol < 4; qCol++) {
//			int row = loc.getQStartRow() + qRow * loc.getQRowSpacing();
//			int col = loc.getQStartCol() + qCol * loc.getQColSpacing();
//			
//			int[] results = checkRowOfBoxes(row, col, loc.getBoxWidth(), loc.getBoxHeight(), loc.getBubbleCols(), );
//			int countVal = (int)(averageOf(results) * 1.15);
//			ArrayList<Integer> answers = boxCountsToAnswer(results, countVal);
//		}
//	}
//	return null;
//}
	
	/*public static AnswerSheet processPageImage(PImage image) {
	image.filter(PImage.GRAY);
	AnswerSheet answers;
	answers = new AnswerSheet(48);
	// for (int i = 115; i < 970; i = i + 283) {
	// int[] columnAnswers = processColumn(image, i, 460, 90, 37, 12);
	// for (int j = 0; j < columnAnswers.length; j++) {
	// answers.addAnswer(columnAnswers[j]);
	// }
	// }

	for (int col = 125; col < 290; col = col + 36) {
		for (int row = 460; row < 900; row = row + 38) {
			answers.addAnswer(determineBubble(row, col, 38, 36, 5, image));
		}
	}

	return answers;
}*/

/*public int[] processColumn(PImage image, int startX, int startY, int boxWidth, int boxHeight, int numRows) {
	int[] answers = new int[numRows];
	int counter = 0;
	for (int row = startY; row < startY + boxHeight * numRows; row = row + boxHeight) {
		answers[counter] = determineBubble(startY, startX, boxWidth, boxHeight, 5, image);
		counter++;
	}
	return answers;
}*/

	/*public static int determineBubble(int r, int c, int width, int height, int numBubbles, PImage pixels) {
		int boxWidth = width / numBubbles, min = 255, maxBubble = 0;

		for (int i = 0; i < numBubbles; i++) {
			int value = getSumValue(r, c + i * boxWidth, width, height, pixels);
			if (value < min) {
				min = value;
				maxBubble = i;
			}
		}
		return maxBubble;
	}

	public static int getSumValue(int r, int c, int width, int height, PImage pixels) {
		int sum = 0;
		for (int row = r; row < height; row++) {
			for (int col = c; col < width; col++) {

				sum += getPixelAt(row, col, pixels);
			}
		}
		return sum;
	}*/

}
