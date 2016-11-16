/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DigitalImageProcess.DigitalProcess;
import DigitalImageProcess.Effects.*;
import DigitalImageProcess.Filters.*;
import DigitalImageProcess.Luminosity.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jorismar
 */
public class Main {
    private static BufferedImage image;
    
    public static BufferedImage processController(DigitalProcess process, int value) throws CloneNotSupportedException {
        return process.apply(image, value);
    }
    
    public static void main(String[] args) {
        try {
            //Average avg = new Average();
            //Median med = new Median();
            //SobelGradient sbl = new SobelGradient();
            //AdaptiveContrast contrast = new AdaptiveContrast(1.0f);
            //Negative negative = new Negative();
            //HistogramExpansion hist_exp = new HistogramExpansion();
            HistogramEqualization hist_eq = new HistogramEqualization();
            
            image = ImageIO.read(new File("lena.png"));
            
            //BufferedImage output1 = processController(avg, 20);
            //BufferedImage output2 = processController(med, 10);
            //BufferedImage output3 = processController(sbl, 0);
            //BufferedImage output4 = processController(contrast, 3);
            //BufferedImage output5 = processController(negative, 3);
            //BufferedImage output6 = processController(hist_exp, 0);
            BufferedImage output7 = processController(hist_eq, 0);
            
            //ImageIO.write(output1, "png", new File("average.png"));
            //ImageIO.write(output2, "png", new File("median.png"));
            //ImageIO.write(output3, "png", new File("sobel.png"));
            //ImageIO.write(output4, "png", new File("contrast_adaptativo.png"));
            //ImageIO.write(output5, "png", new File("negative.png"));
            //ImageIO.write(output6, "png", new File("histogram_expansion.png"));
            ImageIO.write(output7, "png", new File("histogram_equalization.png"));
            
            System.out.println("Done!");
        } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
