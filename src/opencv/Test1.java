
package opencv;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Test1 {
	
	static {
		//如果不提前加载Core.NATIVE_LIBRARY_NAME会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) throws AWTException {
		//输出OpenCV版本信息
		System.out.println("OpenCV :"+Core.VERSION);
		//使用OpenCV自带的人脸识别XML
		//CascadeClassifier facebook=new CascadeClassifier("/Users/yehaitao/Desktop/opencv-4.0.0/data/haarcascades/haarcascade_frontalface_alt.xml");
		CascadeClassifier facebook=new CascadeClassifier("/Users/yehaitao/Desktop/mouse/xml/cascade.xml");
//		CascadeClassifier facebook=new CascadeClassifier("/Users/yehaitao/Desktop/mouse/shubiao1-xml/cascade.xml");
		//读取图片
//		Mat image=Imgcodecs.imread("/Users/yehaitao/Desktop/鼠标未识别素材/2.jpg");
		Mat image=Imgcodecs.imread("/Users/yehaitao/Desktop/3.jpg");
		//启用识别类
		MatOfRect face = new MatOfRect();
		facebook.detectMultiScale(image, face);
		//Rect数组
		Rect[] rects=face.toArray();
//		if(rects.length!=0) {
//			System.err.println("***");
//			click();
//		}
		System.out.println("匹配到 "+rects.length+" 个人脸");
		//为每张识别到的人脸画一个圈
		for (int i = 0; i < rects.length; i++) {
			Imgproc.rectangle(image,new Point(rects[i].x, rects[i].y), new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 0, 255));
		}
		//展示图片
		HighGui show=new HighGui();
		show.imshow("大脸盘子", image);
		show.waitKey(0);
		show.destroyAllWindows();
	}
	
	@Test
	public void shibiedali() throws FileNotFoundException {
		

		String path = "/Users/yehaitao/Desktop/dl/";
		File txtfile = new File(path+"pos.txt");  
        FileOutputStream fos = new FileOutputStream(txtfile);  
        PrintWriter pw = new PrintWriter(fos,true);  
        String s = "";  
        File[] files = new File(path).listFiles();  
        for (File file : files) {  
            pw.println("pos/"+file.getName()+" 1 0 0 40 40");  
        }  
	}
	
	@Test
	public static void click() throws AWTException {
		Robot r = new Robot();
		r.mouseMove(506, 303);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		
		r.delay(100);
		
//		r.mouseMove(69, 66);
//		r.mousePress(InputEvent.BUTTON1_MASK);
//		r.mouseRelease(InputEvent.BUTTON1_MASK);
		
//		r.mouseMove(30, 10);
//		
//		r.mousePress(InputEvent.BUTTON1_MASK);
//		r.mouseRelease(InputEvent.BUTTON1_MASK);
//		r.delay(1000);
//		
//		r.mouseMove(60, 60);
//		r.mousePress(InputEvent.BUTTON1_MASK);
//		
//		r.mouseRelease(InputEvent.BUTTON1_MASK);
		
	}
	
}

