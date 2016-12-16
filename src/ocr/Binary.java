
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;

public class Binary{

    BufferedImage image;
    int width;
    int height;
    int vt=20;
    int ht=50; 
    int threshold=100;
    public Binary() {
        try {
            File input = new File("/home/prashant/Desktop/images.jpeg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    int sum = red + green + blue;
                    Color newColor;
                    if (sum > threshold) {
                        newColor = new Color(255, 255, 255);
                        
                    } else {
                        newColor = new Color(0, 0, 0);

                    }

                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            
            //Horizontal  Smearing
            BufferedImage horizontally_smeared=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; i++) {
                int distance=0;
                boolean started=false;
                for (int j = 0; j < width; j++) {
                    Color c=new Color(image.getRGB(j, i));
                    if(c.getRed()== 0){     
                       //bi.setRGB(j, i,0 );
                       //Already black so not need to do anything.
                       started=true;
                       distance=0;
                    }else{
                       if(distance>=ht||started==false){
                        horizontally_smeared.setRGB(j, i,0xFFFFFF );
                       }
                       distance+=1;
                    }
                }
            } 
            File h_output = new File("/home/prashant/Desktop/horizontally_smeared.jpeg");
            ImageIO.write(horizontally_smeared, "jpeg", h_output);
            //Vertical Smearing
            BufferedImage vertically_smeared=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                boolean started=false;                
                int distance=0;
                for (int j = 0; j < height; j++) {
                    Color c=new Color(image.getRGB(i, j));
                    if(c.getRed()== 0){     
                       //bi.setRGB(j, i,0 );
                       //Already black so not need to do anything.
                       distance=0;
                       started=true;
                    }else{
                       if(distance>=vt||started==false){
                        vertically_smeared.setRGB(i, j,0xFFFFFF );
                       }
                       distance+=1;
                    }
                }
            } 
            File v_output = new File("/home/prashant/Desktop/vertically_smeared.jpeg");
            ImageIO.write(vertically_smeared, "jpeg", v_output);
             //Anding the Two images
            BufferedImage  combined =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Color c1,c2;
                    c1=new Color(horizontally_smeared.getRGB(i,j));
                    c2=new Color(vertically_smeared.getRGB(i,j));
                    if(c1.getRed()==0||c2.getRed()==0){
                           combined.setRGB(i, j,0 );
                    }
                    else{
                        combined.setRGB(i, j,0xFFFFFF );
                    }

                }
            } 
            // Smearing Horizontally Again

            for (int i = 0; i < height; i++) {
                int distance=0;
                boolean started=false;
                for (int j = 0; j < width; j++) {
                    Color c=new Color(combined.getRGB(j, i));
                    if(c.getRed()== 0){     
                       //bi.setRGB(j, i,0 );
                       //Already black so not need to do anything.
                       started=true;
                       distance=0;
                    }else{
                       if(distance>=ht||started==false){
                        combined.setRGB(j, i,0xFFFFFF );
                       }else{
                       //Extra Smearing
                       combined.setRGB(j, i, 0);
                       }
                       distance+=1;
                    }
                }
            } 
            File combined_output = new File("/home/prashant/Desktop/final.jpeg");
            ImageIO.write(combined, "jpeg", combined_output);
            

        } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Error.EXiting");
               System.exit(1);
        }
    }

   public static  void main(String args[]) throws Exception {
        Binary obj = new Binary();
    }
}