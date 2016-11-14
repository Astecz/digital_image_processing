/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Filters.*;
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
    
    public static BufferedImage filterController(Filter filter, int matrix_width) throws CloneNotSupportedException {
        return filter.apply(image, matrix_width);
    }
    
    public static void main(String[] args) {
        try {
            Average avg = new Average();
            //Median med = new Median();
            //SobelGradient sbl = new SobelGradient();
            //MaskSetter mask = new MaskSetter();
            
            image = ImageIO.read(new File("lena.png"));
            BufferedImage output = filterController(avg, 10);
            
            ImageIO.write(output, "png", new File("new_lena.png"));
            
            System.out.println("Done!");
        } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
