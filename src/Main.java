/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DigitalImageProcess.Colors.ConvertToRGB;
import DigitalImageProcess.Colors.ConvertToYIQ;
import DigitalImageProcess.DigitalProcess;
import DigitalImageProcess.Effects.*;
import DigitalImageProcess.Filters.*;
import DigitalImageProcess.Luminosity.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jorismar
 */
public class Main {//extends Application {
    private static BufferedImage image;
    
    public static BufferedImage processController(DigitalProcess process, Object arg) throws CloneNotSupportedException {
        return process.apply(image, arg);
    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("assets/views/GUIPrototype.fxml"));

        Scene scene = new Scene(root, 1095, 529);

        primaryStage.setTitle("Image editor");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
*/
    public static void main(String[] args) {
        //launch(args);
        Convolution conv = new Convolution();
        
    /********* Configure Mask *********/
        Mask mask = new Mask();

        // Create matrix 3x3
        mask.createMatrix(3, 3);
        
        // Fill matrix
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                mask.setMatrixValue(i, j, i + j);
        
        // Set offset
        mask.setOffset(3);
        
        // Set sharpen
        mask.setSharpen(1, 2);
    /**********************************/

        try {
            image = ImageIO.read(new File("lena.png"));
            BufferedImage output1 = processController(conv, mask);
            ImageIO.write(output1, "png", new File("processed/convolution.png"));
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}   
        
        
/*
            ConvertToYIQ yiq = new ConvertToYIQ();
            ConvertToRGB rgb = new ConvertToRGB();
            
            try {
            image = ImageIO.read(new File("lena.png"));

            BufferedWriter writer = new BufferedWriter(new FileWriter("yiq.txt"));
            processController(yiq, writer);
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("yiq.txt"));
            BufferedImage output1 = processController(rgb, reader);
            reader.close();
            ImageIO.write(output1, "png", new File("processed/yiq2rgb.png"));
            
            } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException");
            } catch (CloneNotSupportedException ex) {
            System.err.println("CloneNotSupportedException");
            } catch (IOException ex) {
            System.err.println("IOException");
            }
            }
            
            */
            /*
            public static void main(String[] args) {
            try {
            Average avg = new Average();
            Median med = new Median();
            SobelGradient sbl = new SobelGradient();
            AdaptiveContrast contrast = new AdaptiveContrast(1.0f);
            Negative negative = new Negative();
            HistogramExpansion hist_exp = new HistogramExpansion();
            HistogramEqualization hist_eq = new HistogramEqualization();
            Bands bands = new Bands();
            Thresholding thresholding = new Thresholding();
            AdditiveBrightness additive = new AdditiveBrightness();
            MultiplicativeBrightness multiplicative = new MultiplicativeBrightness();
            
            image = ImageIO.read(new File("lena.png"));
            
            //BufferedImage output1 = processController(avg, 20);
            //BufferedImage output2 = processController(med, 10);
            //BufferedImage output3 = processController(sbl, null);
            //BufferedImage output4 = processController(contrast, 3);
            //BufferedImage output5 = processController(negative, 3);
            //BufferedImage output6 = processController(hist_exp, null);
            //BufferedImage output7 = processController(hist_eq, null);
            //BufferedImage output8 = processController(bands, new Color(255, 0, 0));
            //BufferedImage output9 = processController(bands, new Color(0, 255, 0));
            //BufferedImage output10 = processController(bands, new Color(0, 0, 255));
            //BufferedImage output11 = processController(thresholding, 125);
            BufferedImage output12 = processController(additive, 10);
            BufferedImage output13 = processController(multiplicative, 10);
            
            //ImageIO.write(output1, "png", new File("processed/average.png"));
            //ImageIO.write(output2, "png", new File("processed/median.png"));
            //ImageIO.write(output3, "png", new File("processed/sobel.png"));
            //ImageIO.write(output4, "png", new File("processed/contrast_adaptativo.png"));
            //ImageIO.write(output5, "png", new File("processed/negative.png"));
            //ImageIO.write(output6, "png", new File("processed/histogram_expansion.png"));
            //ImageIO.write(output7, "png", new File("processed/histogram_equalization.png"));
            //ImageIO.write(output8, "png", new File("processed/Band_Red.png"));
            //ImageIO.write(output9, "png", new File("processed/Band_Green.png"));
            //ImageIO.write(output10, "png", new File("processed/Band_Blue.png"));
            //ImageIO.write(output11, "png", new File("processed/thresholding.png"));
            ImageIO.write(output12, "png", new File("processed/additive.png"));
            ImageIO.write(output13, "png", new File("processed/multiplicative.png"));
            
            System.out.println("Done!");
            } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
