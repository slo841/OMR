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
	}

	public int[] processColumn(PImage image, int startX, int startY, int boxWidth, int boxHeight, int numRows) {
		int[] answers = new int[numRows];
		int counter = 0;
		for (int row = startY; row < startY + boxHeight * numRows; row = row + boxHeight) {
			answers[counter] = determineBubble(startY, startX, boxWidth, boxHeight, 5, image);
			counter++;
		}
		return answers;
	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;

		return (image.pixels[index]) & 255;
	}

	public int determineBubble(int r, int c, int width, int height, int numBubbles, PImage pixels) {
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

	public int getSumValue(int r, int c, int width, int height, PImage pixels) {
		int sum = 0;
		for (int row = r; row < height; row++) {
		for (int col = c; col < width; col++) {
			
				sum += getPixelAt(row, col, pixels);
			}
		}
		return sum;
	}

}
