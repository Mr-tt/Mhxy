package test;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import utils.BufImgToMat;
import utils.DrawInBufferedImg;

public class TestMouse {
	static {
		// 如果不提前加载Core.NATIVE_LIBRARY_NAME会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		try {
			for(int i =0;i<1000;i++) {
				run();
			}
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 识别鼠标位置，并画框框
	 * @throws AWTException
	 * @throws IOException
	 */
	public static void run() throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screenSize);
		Robot r = new Robot();
		BufferedImage buffImg = r.createScreenCapture(rectangle);
		Mat image = new BufImgToMat(buffImg, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();

		CascadeClassifier facebook = new CascadeClassifier("/Users/yehaitao/Desktop/mouse/xml/cascade.xml");
		// Mat image=Imgcodecs.imread("/Users/yehaitao/Desktop/t3.jpg");
		MatOfRect face = new MatOfRect();
		facebook.detectMultiScale(image, face);
		Rect[] rects = face.toArray();
//		for (int i = 0; i < rects.length; i++) {
//			Imgproc.rectangle(image, new Point(rects[i].x, rects[i].y),
//					new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 0, 255));
//		}
		ArrayList<int[]> mouseSite = new ArrayList<>();
		for (int i = 0; i < rects.length; i++) {
			int[] a = new int[4];
			a[0]=rects[i].x;
			a[1]=rects[i].y;
			a[2]=rects[i].width;
			a[3]=rects[i].height;
			mouseSite.add(a);
			
		}		
		DrawInBufferedImg.draw(buffImg, "/Users/yehaitao/Desktop/newnew/t/"+System.currentTimeMillis()+".jpg", mouseSite);
	}
}
