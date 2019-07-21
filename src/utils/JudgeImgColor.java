package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class JudgeImgColor {

	public static void main(String[] args) throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat mat = Imgcodecs.imread("/Users/yehaitao/Desktop/9.png");
		if (mat.type() == CvType.CV_8UC1) {
			System.err.println("灰色图像");
		} else if (mat.type() == CvType.CV_8UC3) {
			System.err.println("彩色图像");
		}
		//judge();
	}
	
	/**
	 * 得到某个像素点的rgb颜色如（0，0，0）
	 * @throws IOException
	 */
	@Test
	public static boolean judge(BufferedImage bi) throws IOException {
		
		int[] rgb = new int[3];
        int i=605;
        int j=508;
//		BufferedImage bi = ImageIO.read(new File("/Users/yehaitao/Desktop/f1.png"));
		System.err.println("high="+bi.getHeight()+"**weight="+bi.getWidth());
		 int pixel = bi.getRGB(i, j);
		 rgb[0] = (pixel & 0xff0000) >> 16;
		 rgb[1] = (pixel & 0xff00) >> 8;
         rgb[2] = (pixel & 0xff);
//		 System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
		 if(rgb[0]-rgb[2]>20) {
			 System.out.println("非战斗状态");
			 return false;
		 }else {
			 System.err.println("战斗状态!");
			 return true;
		 }
	}

}
