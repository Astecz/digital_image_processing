/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Luminosity;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class Brightness extends DigitalImageProcess.DigitalProcess {

    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected int additive(BufferedImage img, int px, int py, int C){
    	
    	Color color = new Color(img.getRGB(px, py));
    	
    	int R = color.getRed() + C;
    	int G = color.getGreen() + C;
    	int B = color.getBlue() + C;
    	
    	R = R <= 255? R : 255;
    	G = G <= 255? G : 255;
    	B = B <= 255? B : 255;
    	
    	return color.getRGB();
    }
    
    protected int multiplicative(BufferedImage img, int px, int py, int C){
    	
    	Color color = new Color(img.getRGB(px, py));
    	
    	int R = color.getRed() * C;
    	int G = color.getGreen() * C;
    	int B = color.getBlue() * C;
    	
    	R = R <= 255? R : 255;
    	G = G <= 255? G : 255;
    	B = B <= 255? B : 255;
    	
    	return color.getRGB();
    }
    
}
