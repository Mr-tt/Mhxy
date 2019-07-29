package myproject;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JTextField;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.BFMatcher;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import test.te;
import utils.BufImgToMat;
import utils.Buzhuo;
import utils.DrawInBufferedImg;
import utils.FaceCompareMain;
import utils.JudgeImgColor;

public class Main {
	static JTextField text = new JTextField();

	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
		while (true) {
			run();
			Thread.sleep(6000);
		}

	}

	public static void run() throws AWTException, IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// 1.监控游戏场景 战斗（战斗方法）or普通(掉用行走方法)
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screenSize);
		Robot r = new Robot();
		BufferedImage buffImg = r.createScreenCapture(rectangle);
		boolean isZhandou = JudgeImgColor.judge(buffImg);
		if (!isZhandou) {
			walk(); // 控制人物行走
		} else {
			// 截图一张战斗截图写入文件夹
			ImageIO.write(buffImg, "jpg",
					new File("/Users/yehaitao/Desktop/newbuff/" + System.currentTimeMillis() + ".jpg"));
			BufferedImage warImage = r.createScreenCapture(rectangle); // 截图判断，是否有数字读秒
			
			boolean isDuMiao = judgeIsDuMiao(warImage);
			if (isDuMiao) {
				detectObject(warImage); // 战斗场景
			} else {
				// 暂时不做事
			}
		}

	}

	/**
	 * 判断战斗场景是否在读秒
	 * 
	 * @param warImage
	 */
	private static boolean judgeIsDuMiao(BufferedImage warImage) {
		// TODO Auto-generated method stub
		BufferedImage bi = warImage.getSubimage(286, 72, 90, 60);
		int[] rgb = new int[3];

		int c = 0;
		int d = 0;
		for (int i = 0; i < 90; i++) {
			for (int j = 0; j < 60; j++) {
				rgb[0] = (bi.getRGB(i, j) & 0xff0000) >> 16;
				rgb[1] = (bi.getRGB(i, j) & 0xff00) >> 8;
				rgb[2] = (bi.getRGB(i, j) & 0xff);
				// System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + "," + rgb[1] + ","
				// + rgb[2] + ")");
				if (rgb[0] > 230 && rgb[1] > 230 && rgb[2] > 230) {
					c++;
				} else {
					d++;
				}
			}
		}
		double cc = c;
		double rate = (double) (cc / 5400);
		if (rate > 0.05) {
			System.err.println("--正在读秒阶段-请快选择操作");
			return true;
		} else {
			System.err.println("--正在非读秒阶段--");
			return false;
		}

	}

	/**
	 * 检测是否有目标怪物
	 * 
	 * @param bufferedImage
	 */
	public static void detectObject(BufferedImage bufferedImage) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat source, template;
		// 将文件读入为OpenCV的Mat格式
		template = Imgcodecs.imread("/users/yehaitao/desktop/g1.png");
		source = new BufImgToMat(bufferedImage,BufferedImage.TYPE_3BYTE_BGR,CvType.CV_8UC3).getMat();
		

		// 创建于原图相同的大小，储存匹配度
		Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1,
				CvType.CV_32FC1);

		// 调用模板匹配方法
		Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
		// 规格化
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
		// 获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
		Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
		System.err.println(mlr.minVal + "--" + mlr.maxVal);
		Point matchLoc = mlr.minLoc;
		// 在原图上的对应模板可能位置画一个绿色矩形
		Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()),
				new Scalar(0, 255, 255));
		Imgcodecs.imwrite("/users/yehaitao/desktop/find/"+System.currentTimeMillis()+".png", source);
		try {
			Buzhuo.buzhuo(matchLoc.x+template.width()/2, matchLoc.y+template.height()/2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("捕捉出错");
			e.printStackTrace();
		}
	}

	/*
	 * old-三个位置的怪物分析-新版本已不适用 战斗场景1.开始分析对面有几只大力
	 */
	private static void Oldzhandou(BufferedImage buffImg) throws AWTException, IOException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(System.currentTimeMillis());

		ArrayList<int[]> array = new ArrayList<int[]>();
		int[] zuobiao1 = { 138, 213, 53, 25 };
		int[] zuobiao2 = { 190, 181, 53, 25 };
		int[] zuobiao3 = { 238, 147, 53, 25 };
		array.add(zuobiao1);
		array.add(zuobiao2);
		array.add(zuobiao3);
		// 1-3号位怪物截图
		BufferedImage subImg1 = buffImg.getSubimage(138, 213, 53, 25);
		BufferedImage subImg2 = buffImg.getSubimage(190, 181, 53, 25);
		BufferedImage subImg3 = buffImg.getSubimage(238, 147, 53, 25);
		// BufferedImage---->Mat对象 （给opencv使用）
		Mat subMat1 = new BufImgToMat(subImg1, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
		Mat subMat2 = new BufImgToMat(subImg2, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
		Mat subMat3 = new BufImgToMat(subImg3, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
		ImageIO.write(subImg1, "jpg",
				new File("/Users/yehaitao/Desktop/three/" + dateformat.format(System.currentTimeMillis()) + "-1.jpg"));
		ImageIO.write(subImg2, "jpg",
				new File("/Users/yehaitao/Desktop/three/" + dateformat.format(System.currentTimeMillis()) + "-2.jpg"));
		ImageIO.write(subImg3, "jpg",
				new File("/Users/yehaitao/Desktop/three/" + dateformat.format(System.currentTimeMillis()) + "-3.jpg"));
		double res1 = FaceCompareMain.compare(subMat1, "/Users/yehaitao/Desktop/2.jpg");
		double res2 = FaceCompareMain.compare(subMat2, "/Users/yehaitao/Desktop/2.jpg");
		double res3 = FaceCompareMain.compare(subMat3, "/Users/yehaitao/Desktop/2.jpg");
		String[] ress = { String.valueOf(res1), String.valueOf(res2), String.valueOf(res3) };
		DrawInBufferedImg.draw(buffImg,
				"/Users/yehaitao/Desktop/draw/" + dateformat.format(System.currentTimeMillis()) + ".jpg", array, ress);

		if (res1 > 0.6) {
			buzhuo(1);
		} else if (res2 > 0.6) {
			buzhuo(2);
		} else if (res3 > 0.6) {
			buzhuo(3);
		} else {
			buzhuo(0);// 逃跑
		}

	}

	/*
	 * 控制人物捕捉怪物---战斗场景调用
	 */
	private static void buzhuo(int i) throws AWTException, IOException {
		// TODO Auto-generated method stub
		Buzhuo.buzhuo(i);
	}

	/*
	 * 控制人物行走---非战斗场景调用
	 */
	private static void walk() throws AWTException {
		// TODO Auto-generated method stub
		Robot r = new Robot();
		r.mouseMove(550, 120);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(3000);
		r.mouseRelease(InputEvent.BUTTON1_MASK);

		r.mouseMove(550, 450);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(2000);
		r.mouseRelease(InputEvent.BUTTON1_MASK);

		r.mouseMove(25, 162);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(3000);
		r.mouseRelease(InputEvent.BUTTON1_MASK);

		r.mouseMove(100, 450);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(2000);
		r.mouseRelease(InputEvent.BUTTON1_MASK);

		// r.mousePress(InputEvent.BUTTON1_MASK);
		// r.mouseRelease(InputEvent.BUTTON1_MASK);

	}

	public void getScreeSize() {
		// 获取当前屏幕大小,指定捕获屏幕区域大小，这里使用全屏捕获
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Rectangle rectangle = new Rectangle(screenSize);
	}

}
