package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DrawInBufferedImg {
	
	/**
	 * 在BufferedImage对象中画矩形，并写到磁盘上
	 * @param bufferedImage 
	 * @param savePath  文件输出路径
	 * @throws IOException
	 */
	public static void draw(BufferedImage bufferedImage,String savePath,ArrayList<int[]> array,String[] ress) throws IOException {
		//Graphics2D g = b.createGraphics();
		 Graphics g = bufferedImage.getGraphics();
		 g.setColor(Color.RED);//画笔颜色
		 int i=0;
		 for (int[] a : array) {
			 
			 g.drawRect(a[0],a[1],a[2],a[3]);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			 g.setColor(Color.YELLOW);
			 g.drawString(ress[i], a[0], a[1]);
			 i=i+1;
		 }
	     ImageIO.write(bufferedImage, "jpg", new File(savePath));
	     System.err.println("画成功");
	}
	
	
	public static void draw(BufferedImage bufferedImage,String savePath,ArrayList<int[]> array) throws IOException {
		//Graphics2D g = b.createGraphics();
		 Graphics g = bufferedImage.getGraphics();
		 g.setColor(Color.RED);//画笔颜色
//		 int i=0;
		 for (int[] a : array) {
			 
			 g.drawRect(a[0],a[1],a[2],a[3]);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
//			 g.setColor(Color.YELLOW);
//			 g.drawString(ress[i], a[0], a[1]);
//			 i=i+1;
		 }
	     ImageIO.write(bufferedImage, "jpg", new File(savePath));
//	     System.err.println("画成功");
	}
}
