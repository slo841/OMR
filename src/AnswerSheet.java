import java.util.ArrayList;

import processing.core.PImage;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private PImage image;
	private ArrayList<Integer> pixels, answers;
	private int rows, bubbleCols, sectionCols;
	
	public AnswerSheet(PImage image, int rows, int bubbleCols, int numOfSections) {
		this.image = image;
		this.pixels = new ArrayList<Integer>();
		this.sectionCols = numOfSections;
		
		this.bubbleCols = bubbleCols;
		this.rows = rows;
		answers = new ArrayList<>(rows * numOfSections);
	}

//	public PImage getImage() {
//		return image;
//	}
//
//	public void setImage(PImage image) {
//		this.image = image;
//	}

	public ArrayList<Integer> getPixels() {
		return pixels;
	}

	public void setPixels(ArrayList<Integer> pixels) {
		this.pixels = pixels;
	}

	public ArrayList<Integer> getAnswers() {
		return answers;
	}
	
	public void addAnswers(int pixel) {
		answers.add(pixel);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getBubbleCols() {
		return bubbleCols;
	}

	public void setBubbleCols(int bubbleCols) {
		this.bubbleCols = bubbleCols;
	}

	public int getSectionCols() {
		return sectionCols;
	}

	public void setSectionCols(int sectionCols) {
		this.sectionCols = sectionCols;
	}
	
}
