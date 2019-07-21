package test;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import utils.BufImgToMat;

public class JieDuMiao {
	public static void main(String[] args) throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screenSize);
		Robot r = new Robot();
		int[] rgb = new int[3];
		// while(true) {
		// run(r,rectangle);
		// }
		 for (int k = 1; k < 31; k++) {
		BufferedImage b = ImageIO.read(new File("/Users/yehaitao/Desktop/newnew/dumiao/" + k + ".jpg"));
		BufferedImage bi = b.getSubimage(286, 72, 90, 60);
		
		int c = 0;int d=0;
		for (int i = 0; i < 90; i++) {
			for (int j = 0; j < 60; j++) {
				rgb[0] = (bi.getRGB(i, j) & 0xff0000) >> 16;
				rgb[1] = (bi.getRGB(i, j) & 0xff00) >> 8;
				rgb[2] = (bi.getRGB(i, j) & 0xff);
//				System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
				if(rgb[0]>230 && rgb[1]>230 && rgb[2]>230) {
					c++;
				}else {
					d++;
				}
			}
		}
		System.err.println(k+".jpg一共有白色点"+c+"个");
		System.err.println(k+".jpg一共有非白色点"+d+"个");
		double cc =c;
		double rate =(double) (cc/5400);
		System.err.println(rate);
		System.err.println("-----------------");
		// b.getSubimage(286, 72, 90, 60).getRGB(i, j);

//		ImageIO.write(b.getSubimage(286, 72, 90, 60), "jpg", new File("/Users/yehaitao/Desktop/newnew/dumiao/ttt.jpg"));

		 }
	}

	public static void run(Robot r, Rectangle rectangle) throws AWTException, IOException {

		BufferedImage buffImg = r.createScreenCapture(rectangle);
		ImageIO.write(buffImg.getSubimage(300, 70, 56, 62), "jpg",
				new File("/Users/yehaitao/Desktop/newnew/dumiao/" + System.currentTimeMillis() + ".jpg"));
	}
}
