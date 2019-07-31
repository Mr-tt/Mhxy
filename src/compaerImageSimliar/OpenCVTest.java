package compaerImageSimliar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import utils.BufImgToMat;

public class OpenCVTest {
	static String path = "/Users/yehaitao/Desktop/";

	@Test
	public static void run() throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat source, template;
		// 将文件读入为OpenCV的Mat格式
		source = Imgcodecs.imread(path + "2.jpg");
//		BufferedImage bi = ImageIO.read(new File(path + "r.jpg"));
//		source = BufImgToMat.img2Mat(bi);
		template = Imgcodecs.imread(path + "g1.png");
		// 创建于原图相同的大小，储存匹配度
		Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1,
				CvType.CV_32FC1);
		for (int i = 0; i < 10; i++) {
			// 调用模板匹配方法
			Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
			// 规格化
			 Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
			// 获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
			Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
			Point matchLoc = mlr.minLoc;
			
			Rect rect = new Rect((int)matchLoc.x, (int)matchLoc.y, template.width(), template.height());
			Mat m = source.submat(rect);
			Imgcodecs.imwrite(path + "mmm.png", m);
			
			int r = ThreeHistCompare.compareHistogram(template,m);
			if(r==1) {
				
			// 在原图上的对应模板可能位置画一个绿色矩形
			Imgproc.rectangle(source, matchLoc,
					new Point(matchLoc.x + template.width(), matchLoc.y + template.height()), new Scalar(0, 255, 255));
			
			
			BufferedImage bfImage = BufImgToMat.MatToBufferedImage(source);
//			setColor(bfImage, matchLoc.x, matchLoc.y, template.width(), template.height());
			
			}
		}
		// 将结果输出到对应位置
		Imgcodecs.imwrite(path + "hr1.png", source);
		System.err.println(true);
	}
	
	public static void setColor(BufferedImage bfImage,double x,double y,double weight,double height) {
		for(int m=0;m<weight;m++) {
			for(int n = 0;n<height;n++) {
				bfImage.setRGB(m, n, 0xffffff);
			}
		}

	}

	public static void main(String[] args) throws IOException {
		run();
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		Mat source, template;
//		// 将文件读入为OpenCV的Mat格式
//		source = Imgcodecs.imread(path + "m.jpeg");
//		template = Imgcodecs.imread(path + "g1.png");
//		// 创建于原图相同的大小，储存匹配度
//		Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1,
//				CvType.CV_32FC1);
//		// 调用模板匹配方法
//		Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
//		// 规格化
//		// Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
//		// 获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
//		Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
//
//		Point matchLoc = mlr.minLoc;
//		// 在原图上的对应模板可能位置画一个绿色矩形
//		Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()),
//				new Scalar(0, 255, 255));
//
//		System.err.println(template.width() + "-y-" + template.height());
//		// 将结果输出到对应位置
//		Imgcodecs.imwrite(path + "rm22.png", source);
//		System.err.println(true);

	}
}