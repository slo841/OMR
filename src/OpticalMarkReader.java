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

		for (int row = 120; row < 315; row++) {
			for (int col = 465; col < 900; col++) {
				determineBubble(row, col, 45, 45, 5, image);
			}
		}
		return null;
	}
	
	public int determineFirstBlackPixelInVerticalRange(int rowStart,int colStart, int vertical){
		return 0;
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

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				sum += getPixelAt(row, col, pixels);
			}
		}
		return sum;
	}

}
