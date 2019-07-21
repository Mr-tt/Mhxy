package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class te {

	public static void main(String[] args) throws Exception {

		
//		int count = 270;
//		BufferedImage b = ImageIO.read(new File("/users/yehaitao/desktop/1.png"));
		// ImageIO.write(b.getSubimage(0, 21, 643, 502), "jpg", new
//		System.err.println(b.getWidth());
//		ImageIO.write(grayImage(b), "jpg", new File("/users/yehaitao/desktop/gray1.jpg"));
//		for (int i = 0; i < b.getWidth(); i = i + 55) {
//			for (int j = 0; j < b.getHeight(); j = j + 55) {
//				try {
//
//					ImageIO.write(b.getSubimage(i, j, 55, 55), "jpg",
//							new File("/users/yehaitao/desktop/neg/" + count + ".jpg"));
//					count++;
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.err.println(e);
//				}
//			}
//		}
		
//		for(int i =32;i<70;i++) {
//			Scanner in = new Scanner(System.in);
//			int s = Integer.parseInt(in.nextLine());
//			int s1 = Integer.parseInt(in.nextLine());
//			BufferedImage bu = ImageIO.read(new File("/users/yehaitao/desktop/鼠标未识别素材/"+(i-30)+".jpg"));
//			
//			ImageIO.write(bu.getSubimage(s, s1, 24, 24), "jpg", new File("/users/yehaitao/desktop/mouse/fig-24X24/"+i+".jpg"));
//			ImageIO.write(grayImage(bu.getSubimage(s, s1, 24, 24)), "jpg", new File("/users/yehaitao/desktop/mouse/pos/"+i+".jpg"));
//			
//		}
		
	}

	/**
	 * 将bufferedimage图像灰度化
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage grayImage(BufferedImage image) {

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;
	}

	public static void move(int x, int y, int x1, int y1) throws AWTException {
		Robot r = new Robot();

		// boolean isx = (x>x1);
		// boolean isy = (y>y1);
		while (!(x == x1 && y == y1)) {
			if (x > x1) {
				x = x - 1;

			} else if (x < x1) {
				x = x + 1;
			}
			if (y > y1) {
				y = y - 1;
			} else if (y < y1) {
				y = y + 1;
			}
			r.mouseMove(x, y);
			System.err.println("移动到+（" + x + "," + y + ")");
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		r.delay(4000);

		// System.err.println("终于移到啦");

	}
}
