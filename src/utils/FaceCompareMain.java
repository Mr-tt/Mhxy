package utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class FaceCompareMain {

    //初始化人脸探测器
    static CascadeClassifier faceDetector;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        faceDetector = new CascadeClassifier(
//            "D:\\ib\\face-detact\\src\\com\\company\\haarcascade_frontalface_alt.xml");
    }

    // 1.  灰度化（减小图片大小）
    // 2. 人脸识别
    // 3. 人脸切割
    // 4. 规一化(人脸直方图)
    // 5. 直方图相似度匹配

    public static void main(String[] args) throws IOException {
        String basePicPath = "/Users/yehaitao/Desktop/";
        //BufferedImage bi = ImageIO.read(new File(basePicPath+"te.jpg"));
        		
        double compareHist = compare_image(basePicPath+"h11.jpg",basePicPath+"p11.jpg");
     
    }
    
    
    public static double compare(Mat mat, String imgurl) {
//      Mat mat_1 = conv_Mat(img_1);
//      Mat mat_2 = conv_Mat(img_2);
  		Mat mat_1 =  mat;
  		Mat mat_2 =  Imgcodecs.imread(imgurl);
      
      Mat hist_1 = new Mat();
      Mat hist_2 = new Mat();
      
      //颜色范围
      MatOfFloat ranges = new MatOfFloat(0f, 256f);
      //直方图大小， 越大匹配越精确 (越慢)
      MatOfInt histSize = new MatOfInt(1000);

      Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
      Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

      // CORREL 相关系数
      double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
      System.out.println(res);
     
      
      return res;
  }
    

    public static double compare_image(String img_1, String img_2) {
//        Mat mat_1 = conv_Mat(img_1);
//        Mat mat_2 = conv_Mat(img_2);
    		Mat mat_1 =  Imgcodecs.imread(img_1);
    		Mat mat_2 =  Imgcodecs.imread(img_2);
        
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();
        
        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(1000);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

        // CORREL 相关系数
        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        System.out.println(res);
        if (res > 0.5) {
            System.out.println("匹配成功！");
        } else {
            System.out.println("不匹配");
        }
        
        return res;
    }

    // "D:\\ib\\face-detact\\src\\com\\company\\a1.jpg"
    //找到图片中的人脸，截取出来，返回Mat
    private static Mat conv_Mat(String img_1) {
        Mat image0 = Imgcodecs.imread(img_1);

        Mat image = new Mat();
        //灰度转换
        Imgproc.cvtColor(image0, image, Imgproc.COLOR_BGR2GRAY);

        MatOfRect faceDetections = new MatOfRect();
        //探测人脸
        faceDetector.detectMultiScale(image, faceDetections);

        // rect中是人脸图片的范围
        for (Rect rect : faceDetections.toArray()) {
            //切割rect人脸
            Mat mat = new Mat(image, rect);
            return mat;
        }
        return null;
    }

}
