package utils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
public class ImgUtil {
    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     * @param imgsrc   源图片地址
     * @param imgdist  目标图片地址
     * @param widthdist  压缩后图片的宽度
     * @param heightdist 压缩后图片的高度
     * @param rate       压缩的比例
     */
    public static void reduceImg(String imgsrc, String imgdist, Integer widthdist, Integer heightdist, Float rate) {
        try {
            File srcfile = new File(imgsrc);
            // 检查图片文件是否存在
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = ImageIO.read(srcfile);
            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
            //创建文件输出流
            FileOutputStream out = new FileOutputStream(imgdist);
            //将图片按JPEG压缩，保存到out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            imageToGray(tag);
            encoder.encode(tag);
            //关闭文件输出流
            out.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }
 
    /**
     * 原图大小等比压缩，不改变图片大小，压缩内存
     * @param imgsrc   源图片地址
     * @param imgdist  目标图片地址
     */
    public static void reduceImg(String imgsrc, String imgdist) {
        File srcfile = new File(imgsrc);
        int[] result = getImgWidthHeight(srcfile);
        int width = result[0];
        int height = result[1];
        reduceImg(imgsrc,imgdist,width,height,null);
    }
 
    /**
     * 原图大小等比压缩
     * @param imgsrc   源图片地址
     * @param imgdist  目标图片地址
     * @param rate  压缩比例
     */
    public static void reduceImg(String imgsrc, String imgdist,Float rate){
        reduceImg(imgsrc,imgdist,null,null,rate);
    }
 
    /**
     * 原图大小等比压缩
     * @param imgsrc   源图片地址
     * @param imgdist  目标图片地址
     * @param width  指定宽度
     * @param height  指定高度
     */
    public static void reduceImg(String imgsrc, String imgdist,int width,int height){
        reduceImg(imgsrc,imgdist,width,height,null);
    }
 
    /**
     * 获取图片宽度和高度
     *
     * @param
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            // 得到源图片宽
            result[0] =src.getWidth();
            // 得到源图片高
            result[1] =src.getHeight();
        } catch (Exception ef) {
            ef.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();  //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return result;
    }
    
    /**
     * 转成灰度图
     * @param image 
     * @return
     */
    public static BufferedImage imageToGray(BufferedImage image) {
    	int width = image.getWidth();
        int height = image.getHeight();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int p = image.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(i, j, p);
            }
        }
		return image;

    }
 
    public static void main(String[] args) throws IOException {
    	for(int i=1;i<=17;i++) {
    		String imgPath = "C:\\Users\\user.DESKTOP-2CMP9FO\\Desktop\\gui\\"+i+".png";
    		String savePath = "C:\\Users\\user.DESKTOP-2CMP9FO\\Desktop\\gui\\gray\\"+i+".png";
    		
    		File srcfile = new File(imgPath);
    		File distfile = new File(savePath);
    		System.out.println("原图大小：" + srcfile.length());
    		reduceImg(imgPath, savePath,38,24);
    		System.out.println("处理后文件大小：" + distfile.length());
    	}
 
    }
}