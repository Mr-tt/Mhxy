package opencv;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import utils.BufImgToMat;
import utils.Buzhuo;
import utils.FaceCompareMain;
import utils.JudgeImgColor;

public class Pinmu {
	public static void main(String[] args) throws IOException {
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		for(int i=0;i<20;i++) {
			boolean f = captureScreen();
			if(!f) {
				break;
			}
		}
		
//		test1();
	}
	
	public static boolean captureScreen() throws IOException{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获取当前屏幕大小
		Rectangle rectangle = new Rectangle(screenSize);// 指定捕获屏幕区域大小，这里使用全屏捕获
//		Rectangle rectangle = new Rectangle(0,0,400,300);// 指定捕获屏幕区域大小，这里使用全屏捕获
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//本地环境
		GraphicsDevice[] gs = ge.getScreenDevices();//获取本地屏幕设备列表
		System.err.println("eguid温馨提示，找到"+gs.length+"个屏幕设备");
		Robot robot=null;
		int ret=-1;
		for(int index=0;index<10;index++){
			GraphicsDevice g=gs[index];
			try {
				robot= new Robot(g);
				BufferedImage img=robot.createScreenCapture(rectangle);
				
				//将BufferedImage 文件写入磁盘
//				ImageIO.write(img,"jpg",new File("/Users/yehaitao/Desktop/newbuff/"+System.currentTimeMillis()+".png"));
				//将BufferedImage 转成Mat，提供给opencv调用
				//BufImgToMat b2m = new BufImgToMat(img, BufferedImage.TYPE_INT_RGB, CvType.CV_8UC3);
				BufImgToMat b2m = new BufImgToMat(img, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
				Mat mat = b2m.getMat();
				boolean iszhandou = JudgeImgColor.judge(img);
				//战斗状态下截一张图
				if(iszhandou) {
					ImageIO.write(img,"jpg",new File("/Users/yehaitao/Desktop/newbuff/"+System.currentTimeMillis()+".jpg"));
					BufferedImage subImg1 = img.getSubimage(138, 188, 56, 52);
//					ImageIO.write(subImg1,"jpg",new File("/Users/yehaitao/Desktop/newbuff/"+subImg1+".jpg"));
					Mat subMat1 = new BufImgToMat(subImg1, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
					double res1 = FaceCompareMain.compare(subMat1, "/Users/yehaitao/Desktop/gui1.png");
					int flag = 0;
					if(res1>0.4) {
						System.err.println("1号位抓海龟啦！！");
						flag=1;
						Buzhuo.buzhuo(1);
					}
					BufferedImage subImg2 = img.getSubimage(190, 156, 53, 50);
//					ImageIO.write(subImg2,"jpg",new File("/Users/yehaitao/Desktop/newbuff/"+subImg2+".jpg"));
					Mat subMat2 = new BufImgToMat(subImg2, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
					double res2 = FaceCompareMain.compare(subMat2, "/Users/yehaitao/Desktop/gui1.png");
					if(res2>0.4) {
						System.err.println("2抓海龟啦！！");
						flag=1;
						Buzhuo.buzhuo(2);
					}
					BufferedImage subImg3 = img.getSubimage(238, 122, 59, 52);
//					ImageIO.write(subImg3,"jpg",new File("/Users/yehaitao/Desktop/newbuff/"+subImg3+".jpg"));
					Mat subMat3 = new BufImgToMat(subImg3, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3).getMat();
					double res3 = FaceCompareMain.compare(subMat3, "/Users/yehaitao/Desktop/gui1.png");
					if(res3>0.4) {
						System.err.println("3抓海龟啦！！");
						flag=1;
						Buzhuo.buzhuo(3);
					}
					if(flag==0) {
						Buzhuo.buzhuo(0);
					}
					
					return true;
				}
//				Mhxy mhxy = new Mhxy();
//				boolean flag = mhxy.jiankong(mat);
//				if(flag) {
////					robot.mouseMove(26, 13);
////					robot.mousePress(InputEvent.BUTTON1_MASK);
////					robot.mouseRelease(InputEvent.BUTTON1_MASK);
//				}
				robot.delay(1000);
				if(img!=null&&img.getWidth()>1){
					ret=index;
					break;
				}
			} catch (AWTException e) {
				System.err.println("打开第"+index+"个屏幕设备失败，尝试打开第"+(index+1)+"个屏幕设备");
			}
		}
//		System.err.println("打开的屏幕序号："+ret);
//		CanvasFrame frame = new CanvasFrame("eguid屏幕录制");// javacv提供的图像展现窗口
//		int width = 800;
//		int height = 600;
//		frame.setBounds((int) (screenSize.getWidth() - width) / 2, (int) (screenSize.getHeight() - height) / 2, width,
//				height);// 窗口居中
//		frame.setCanvasSize(width, height);// 设置CanvasFrame窗口大小
//		while (frame.isShowing()) {
//			BufferedImage image = robot.createScreenCapture(rectangle);// 从当前屏幕中读取的像素图像，该图像不包括鼠标光标
//			frame.showImage(image);
//			
//			try {
//				Thread.sleep(45);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		frame.dispose();
		return true;
	}

	@Test
	public static void test1() {
		
		BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
		
		byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

	}
	
}
