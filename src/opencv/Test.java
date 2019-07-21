package opencv;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.nio.BufferUnderflowException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Test {
	public static void main(String[] args) throws AWTException, IOException {

		// String path = "/Users/yehaitao/Desktop/dl/pos";
		// File txtfile = new File(path+"pos.txt");
		// FileOutputStream fos = new FileOutputStream(txtfile);
		// PrintWriter pw = new PrintWriter(fos,true);
		// String s = "";
		// File[] files = new File(path).listFiles();
		// for (File file : files) {
		// pw.println("pos/"+file.getName()+" 1 0 0 40 40");
		// }
		// Robot r = new Robot();
		// r.mouseMove(513, 303);
		// r.mousePress(InputEvent.BUTTON1_MASK);
		// r.mouseRelease(InputEvent.BUTTON1_MASK);
		//
		// r.delay(1000);
		// r.mouseMove(234, 309);
		// r.mousePress(InputEvent.BUTTON1_MASK);
		// r.mouseRelease(InputEvent.BUTTON1_MASK);

//		 int[] rgb = getImgColor();
//		 getPositionByRBG(rgb);
		 
		// BufferedImage b = ImageIO.read(new File("/Users/yehaitao/Desktop/2.jpg"));
		// //Graphics2D g = b.createGraphics();
		// Graphics g = b.getGraphics();
		// g.setColor(Color.RED);//画笔颜色
		// g.drawRect(138, 188, 53, 50);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		// g.drawRect(190, 156, 53, 50);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		// g.drawRect(238, 122, 59, 52);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		// ImageIO.write(b, "jpg", new
		// File("/Users/yehaitao/Desktop/draw/"+System.currentTimeMillis()+".jpg"));

		//		for (int i = 0; i < 5; i++) {
//
			Robot r = new Robot();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rectangle = new Rectangle(screenSize);
			BufferedImage bu = r.createScreenCapture(rectangle);
			ImageIO.write(bu, "png", new File("/Users/yehaitao/Desktop/test/" + System.currentTimeMillis() + ".png"));
			r.delay(1000);
//		}
	}

	/*
	 * 得到某个像素点的颜色
	 */
	public static int[] getImgColor() throws IOException {
		int[] rgb = new int[3];
		int i = 267;
		int j = 250;
		BufferedImage bi = ImageIO.read(new File("/Users/yehaitao/Desktop/1.jpg"));
		System.err.println("high=" + bi.getHeight() + "**weight=" + bi.getWidth());
		int pixel = bi.getRGB(i, j);
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
		System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
		return rgb;
	}

	/*
	 * 找到图片中某个像素点的 坐标
	 */
	public static void getPositionByRBG(int[] r) throws IOException {

		long a = System.currentTimeMillis();
		BufferedImage bi = ImageIO.read(new File("/Users/yehaitao/Desktop/rr2.png"));
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				int[] rgb = new int[3];
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				if (rgb[0] == r[0] && rgb[1] == r[1] && rgb[2] == r[2]) {
					System.err.println("找到了鼠标啦坐标是:(" + i + "," + j + ")");
				}
			}
		}
		long b = System.currentTimeMillis();
		System.err.println(b - a);
	}

}
