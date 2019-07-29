package utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

public class Buzhuo {
	
	
	public static void buzhuo(double x,double y) throws Exception {
		Robot r = new Robot();
		
		r.mouseMove((int)x, (int)y);
		r.delay(908);
		modifyMouse(166, 230);
		
		r.keyPress(157);//按下mac的commanded键
		r.keyPress(KeyEvent.VK_G);
		r.keyRelease(KeyEvent.VK_G);
		r.keyRelease(157);
		
		r.delay(543);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(1000);
		r.mouseMove(330, 350);//鼠标基准位
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * buzhuo-old
	 * @param i
	 * @throws AWTException
	 * @throws IOException
	 */
	public static void buzhuo(int i) throws AWTException, IOException {
		Robot r = new Robot();
		if(i!=0) {
			//移动到捕捉附近
//			r.mousePress(InputEvent.BUTTON1_MASK);
//			r.mouseRelease(InputEvent.BUTTON1_MASK);
//			r.mouseMove(520, 290);
//			r.delay(100);
//			
//			r.mouseMove(529, 296);
//			r.delay(893);
//			modifyMouse(529, 296);
//
//			r.delay(900);
//			r.mousePress(InputEvent.BUTTON1_MASK);
//			r.delay(300);
//			r.mouseRelease(InputEvent.BUTTON1_MASK);
			
			
			
			if(i==1) {
//				r.mousePress(InputEvent.BUTTON1_MASK);
//				r.mouseRelease(InputEvent.BUTTON1_MASK);
//				r.mouseMove(160, 240);
//				r.delay(100);
				
				r.mouseMove(166, 230);
				r.delay(908);
				//修正鼠标
				modifyMouse(166, 230);
				
				r.keyPress(157);//按下mac的commanded键
				r.keyPress(KeyEvent.VK_G);
				r.keyRelease(KeyEvent.VK_G);
				r.keyRelease(157);
				
				r.delay(543);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(1000);
				r.mouseMove(330, 350);//鼠标基准位
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}else if(i==2) {
//				r.mousePress(InputEvent.BUTTON1_MASK);
//				r.mouseRelease(InputEvent.BUTTON1_MASK);
//				r.mouseMove(210, 202);
//				r.delay(100);
				
				r.mouseMove(214, 196);
				r.delay(908);

				modifyMouse(214, 196);
				//按下command+G捕捉
				r.keyPress(157);//按下mac的commanded键
				r.keyPress(KeyEvent.VK_G);
				r.keyRelease(KeyEvent.VK_G);
				r.keyRelease(157);
				

				r.delay(678);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(463);
				
				r.mouseMove(330, 350);//鼠标基准位
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}else if(i==3) {
//				r.mousePress(InputEvent.BUTTON1_MASK);
//				r.mouseRelease(InputEvent.BUTTON1_MASK);
//				r.mouseMove(265, 163);
//				r.delay(100);
				
				r.mouseMove(260, 166);
				r.delay(908);
				modifyMouse(260, 166);  //修正

				//按下command+G捕捉
				r.keyPress(157);//按下mac的commanded键
				r.keyPress(KeyEvent.VK_G);
				r.keyRelease(KeyEvent.VK_G);
				r.keyRelease(157);
				
				
				r.delay(875);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(932);

				r.mouseMove(330, 350);//鼠标基准位
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);

			}
		}else  if(i==0) { //逃跑
			//移到附近
//			r.mouseMove(529, 336);
//			r.mousePress(InputEvent.BUTTON1_MASK);
//			r.mouseRelease(InputEvent.BUTTON1_MASK);
//			r.delay(100);

			r.mouseMove(533, 322);
			r.delay(908);
			//修正鼠标坐标
			modifyMouse(533, 322);
			r.delay(1000);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			
		}
		
	}
	
	
	/**
	 * 解决鼠标漂移
	 * @param buffImg
	 * @param x  鼠标的目的坐标
	 * @param y
	 * @return 
	 * @throws IOException
	 * @throws AWTException 
	 */
	public static void modifyMouse(int x, int y ) throws IOException, AWTException {
		System.err.println("解决鼠标漂移---先截一张图");
		System.err.println("目标坐标为：("+x+","+y+")");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screenSize);
		Robot r = new Robot();
		BufferedImage buffImg = r.createScreenCapture(rectangle);
		
		Mat image = new BufImgToMat(buffImg, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
		CascadeClassifier facebook = new CascadeClassifier("/Users/yehaitao/Desktop/mouse/xml/cascade.xml");
		MatOfRect face = new MatOfRect();
		facebook.detectMultiScale(image, face);
		Rect[] rects = face.toArray();
		System.err.println("识别到"+rects.length+"个鼠标");
		ArrayList<int[]> mouseSite = new ArrayList<>();
		for (int i = 0; i < rects.length; i++) {
			System.err.println("识别到鼠标--准备修正");
			//------------为了保存图片
			int[] a = new int[4];
			a[0]=rects[i].x;
			a[1]=rects[i].y;
			a[2]=rects[i].width;
			a[3]=rects[i].height;
			mouseSite.add(a);
			//------------
			if(rects[i].width>40 || rects[i].height>40) {
				System.err.println("错误识别"+rects[i].x+","+rects[i].y);
			}else {
				//正确识别坐标，计算偏移量
				System.err.println("鼠标发生偏移，准备调整！");
				System.err.println("鼠标现在的位置("+rects[i].x+","+rects[i].y);
				int flagX = 3;
				int flagY;
				int pianyiX =0;
				int pianyiY =0;
				if(rects[i].x >x) {  //flagX=0
					 pianyiX = rects[i].x-x;
					 x= (x-pianyiX) ;
					 System.err.println("X坐标右偏"+pianyiX);
				}else{         		//flagX = 1
					 pianyiX = x-rects[i].x;
					 x= (x+pianyiX) ;
					 System.err.println("X坐标左偏"+pianyiX);
					 
				}
				if(rects[i].y >y) { 	//flagY = 0
					 pianyiY = rects[i].y-y;
					 y=(y-pianyiY);
					 System.err.println("Y坐标下偏"+pianyiY);
				}else{				//flagY = 1
					 pianyiY = y-rects[i].y;
					 y=(y+pianyiY);
					 System.err.println("Y坐标上偏"+pianyiY);
				}
			

				System.err.println("要准备修正坐标到-("+x+","+y+")");
				r.delay(1000);
				r.mouseMove(x, y);
				r.delay(347);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				System.err.println("鼠标偏移修正完毕");
				
				//判断是否移动成功
//				BufferedImage buffImg1 = r.createScreenCapture(rectangle);
//				Mat image1 = new BufImgToMat(buffImg1, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
//				MatOfRect face1 = new MatOfRect();
//				facebook.detectMultiScale(image1, face1);
//				Rect[] rects1 = face.toArray();
//				for (int k = 0; k < rects1.length; k++) {
//					if(rects[i].width>40 || rects[i].height>40) {
//						System.err.println("错误识别"+rects[i].x+","+rects[i].y);
//					}else {
//						if(rects1[i].x==x && rects1[i].y==y) {
//							break;
//						}else {
//							modifyMouse(x,y);
//						}
//					}
//				}

			}
			
		}
		DrawInBufferedImg.draw(buffImg, "/Users/yehaitao/Desktop/newnew/4/"+System.currentTimeMillis()+".jpg", mouseSite);
	
	}
	
}
