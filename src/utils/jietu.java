package utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class jietu {
	public static void main(String[] args) {
		s();
	}

	/**
	 * 截高清图
	 */
	public static void s() {
		int height= 0;
        int width= 0;
        height= (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width= (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        System.out.println("height="+height+"__width="+width);
        int[] result= {width, height};
        
        Rectangle screenRect= new Rectangle(0, 0, result[0], result[1]);
        try {
            BufferedImage bi= new Robot().createScreenCapture(screenRect);
            ImageIO.write(bi, "png", new File("/Users/yehaitao/Desktop/hh.png"));
         
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
