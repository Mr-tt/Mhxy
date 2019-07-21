package compaerImageSimliar;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Compare {
	//判断色值的出现次数,并且储存
		private static HashMap GetRGBMap(BufferedImage bufferedImage)
		{
			HashMap map=new HashMap();
			for(int x=0;x<bufferedImage. getWidth();x++) {
				for(int y=0;y<bufferedImage. getHeight();y++)
				{
					double RGBValue=bufferedImage.getRGB(x, y);
					 //如果没有保存该色值，存入
					if(map. get(RGBValue)==null)
					{
						map.put(RGBValue, 1);
						//将该色值出现次数增加一次
					}
					else
					{
						int Times=(int) map.get(RGBValue);
						Times+=1;
						map. put (RGBValue,Times);
					}
				}
			}
			return map;
		}
		
		//计算相似度
		private static HashMap VectorNormalizing(HashMap RGBMap) {
			//求图片特征向量1的模长
			double ModulaLength=0;
			for(Object i:RGBMap.keySet())
			{
				//先将所有平方相加
				ModulaLength+=Math.pow((double)(int) RGBMap.get(i), 2);
	 
			}
			//求平方根
			ModulaLength=Math.sqrt(ModulaLength);
			//将图片特征向量1标准化
			for(Object i: RGBMap. keySet())
			{
				double a=(double)(int) RGBMap.get(i);
				a=a/ModulaLength;
				RGBMap.put(i, a);
			}
			return RGBMap;
		}
		
		//求出所需的cos<a,b>即是相似程度
		public static float CompareImage(BufferedImage bufferedImage1, BufferedImage bufferedImage2) 
		{
			HashMap RGBMap1=GetRGBMap(bufferedImage1);
			HashMap RGBMap2=GetRGBMap(bufferedImage2);
			//相似度
			float Similarity=0;    //第一张与第二张相似程度
			
			//将两个hashMap视作特征向量进行归一化
			RGBMap1=VectorNormalizing(RGBMap1);
			RGBMap2=VectorNormalizing(RGBMap2);
			
			//向量点积得到相似度
			for(Object i:RGBMap1.keySet())
			{
				double Value2;
				if(RGBMap2.get(i)==null)
				{
				  Value2=0;
				  
				}else
				{
				  Value2 = (double) RGBMap2.get(i);
				  double Value1=(double) RGBMap1.get(i);
				  Similarity+=Value1*Value2;
	 
				}
			}
			return Similarity*100;
		}
	 
		/*计算相似程度并且输出相似度*/
		public static String ImageSerach(String Imagepath_one,String Imagepath_two)
		{
			float sum = 0;
			try {
				BufferedImage image1 = ImageIO.read(new FileInputStream(Imagepath_one));
				BufferedImage image2 = ImageIO.read(new FileInputStream(Imagepath_two));
				HashMap hm1 = GetRGBMap(image1);
				HashMap one = VectorNormalizing(hm1);
				sum = CompareImage(image1,image2);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "\n--------------RGB比较:\n["+Imagepath_two+"] \n["+Imagepath_one+"] \n 比较结果:"+sum;
			
		}
		
		
		public static void main(String[] args) {
			String s = ImageSerach("/users/yehaitao/desktop/p11.jpg", "/users/yehaitao/desktop/p1.jpg");
			System.err.println(s);
		}
}
