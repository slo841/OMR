import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.sun.prism.paint.Color;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class VisualTester extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int currentImageIndex = 0;
	int w = 1200;
	int h = 900;
	public static OpticalMarkReader markReader = new OpticalMarkReader();

	public void setup() {
		size(w, h);
		images = PDFHelper.getPImagesFromPdf("/omrtest.pdf");
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(currentImageIndex);
			image(current_image, 0, 0); // display image i

			/*text(mouseX + ", " + mouseY, mouseX, mouseY);
			fill(0);
			
			int w = 20; h = 20;
			int color = OpticalMarkReader.countBlackPixels(mouseY, mouseX, w, h, current_image, 25);
			text(color, mouseX, mouseY + 40);
			*/
			
//			for (int col = 467; col < w; col = col + 150) {
//				for (int row = 33; row < h; row = row + 24) {
//					rect(row, col, 20, 24);
//				}
//			}
			
//			rect(33, 467, 20, 24);
//			rect(33, 620, 20, 24);
//			rect(33, 845, 20, 24);
			//create rectangles around all the bubbles

			/*for (int col = 125; col< 290; col = col + 36) {
				for (int row = 460; row < 900; row = row + 38) {
					rect(col, row, 36, 38);
					noFill();
				}
			}*/
			
			/*for (int col = 410; col< 570; col = col + 36) {
				for (int row = 460; row < 900; row = row + 38) {
					rect(col, row, 36, 38);
					noFill();
				}
			}*/
			
			/*for (int col = 693; col< 852; col = col + 36) {
				for (int row = 460; row < 900; row = row + 38) {
					rect(col, row, 36, 38);
					noFill();
				}
			}*/
			
			for (int col = 975; col< 1125; col = col + 36) {
				for (int row = 460; row < 900; row = row + 38) {
					rect(col, row, 36, 38);
					noFill();
				}
			}
			
		}
	}

	public void mouseReleased() {
		currentImageIndex = (currentImageIndex + 1) % images.size(); // increment
																		// current
																		// image
	}
}
