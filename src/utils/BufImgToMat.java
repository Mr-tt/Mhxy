package utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class BufImgToMat {
    
    BufferedImage original;
    int itype;
    int mtype;

    
    
    /**
     * buffer转Mat
     * @param image
     * @param imgType bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType 转换成mat的type 如 CvType.CV_8UC3
     */
    public BufImgToMat(BufferedImage image, int imgType, int matType) {
            original = image;
            itype = imgType;
            mtype = matType;
    }

    public Mat getMat() {
            if (original == null) {
                    throw new IllegalArgumentException("original == null");
            }

            // Don't convert if it already has correct type
//            if (original.getType() != itype) {
                    // Create a buffered image
                    BufferedImage image1 = new BufferedImage(original.getWidth(),
                                    original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

                    // Draw the image onto the new buffer
                    Graphics2D g = image1.createGraphics();
                    try {
                            g.setComposite(AlphaComposite.Src);
                            g.drawImage(original, 0, 0, null);
                    } finally {
                            g.dispose();
                    }
//            }
           
            byte[] pixels = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
            Mat mat = Mat.eye(image1.getHeight(), image1.getWidth(), mtype);
            mat.put(0, 0, pixels);
            return mat;
    }
    
    
    
    
    public static Mat img2Mat(BufferedImage in)
    {
          Mat out;
          byte[] data;
          int r, g, b;

          if(in.getType() == BufferedImage.TYPE_INT_RGB)
          {
              out = new Mat(240, 320, CvType.CV_8UC3);
              data = new byte[320 * 240 * (int)out.elemSize()];
              int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
              for(int i = 0; i < dataBuff.length; i++)
              {
                  data[i*3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                  data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                  data[i*3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
              }
          }
          else
          {
              out = new Mat(240, 320, CvType.CV_8UC1);
              data = new byte[320 * 240 * (int)out.elemSize()];
              int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
              for(int i = 0; i < dataBuff.length; i++)
              {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
              }
           }
           out.put(0, 0, data);
           return out;
     }
}
