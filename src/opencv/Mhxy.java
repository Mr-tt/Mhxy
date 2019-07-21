package opencv;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

public class Mhxy {
	static {
		//如果不提前加载Core.NATIVE_LIBRARY_NAME会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) {
		//输出OpenCV版本信息
				System.out.println("OpenCV :"+Core.VERSION);
				//使用OpenCV自带的人脸识别XML
				CascadeClassifier facebook=new CascadeClassifier("/Users/yehaitao/Desktop/dl/new/xml/cascade.xml");
				//读取图片
				Mat image=Imgcodecs.imread("/Users/yehaitao/Desktop/dl/2.jpg");
//				Mat image=Imgcodecs.imread("/Users/yehaitao/Desktop/dl/fig/1.png");
				//启用识别类
				MatOfRect face = new MatOfRect();
				facebook.detectMultiScale(image, face);
				//Rect数组
				Rect[] rects=face.toArray();
				System.out.println("匹配到 "+rects.length+" 个人脸");
				
				//为每张识别到的人脸画一个圈
				for (int i = 0; i < rects.length; i++) {
					Imgproc.rectangle(image,new Point(rects[i].x, rects[i].y), new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 0, 255));
				}
				//展示图片
				HighGui show=new HighGui();
				show.imshow("显示", image);
				show.waitKey(0);
				show.destroyAllWindows();
	}
	
	public boolean jiankong(Mat mat) throws IOException {
		System.out.println("OpenCV :"+Core.VERSION);
		CascadeClassifier facebook=new CascadeClassifier("/Users/yehaitao/Desktop/dl/new/xml/cascade.xml");
		MatOfRect face = new MatOfRect();
		facebook.detectMultiScale(mat, face);
		Rect[] rects=face.toArray();
		System.out.println("匹配到 "+rects.length+" 大力");
		
		//为每张识别到的人脸画一个圈
		for (int i = 0; i < rects.length; i++) {
			Imgproc.rectangle(mat,new Point(rects[i].x, rects[i].y), new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 0, 255));
		}
		//展示图片
		//HighGui show=new HighGui();
		
		BufferedImage image = (BufferedImage) HighGui.toBufferedImage(mat);
		ImageIO.write(image,"jpg",new File("/Users/yehaitao/Desktop/buff/"+System.currentTimeMillis()+".jpg"));
		
		//		show.imshow("显示", mat);
//		show.waitKey(0);
//		show.destroyAllWindows();
		
		if(rects.length>0) {
			return true;
		}else {
			return false;
		}
	}
}
