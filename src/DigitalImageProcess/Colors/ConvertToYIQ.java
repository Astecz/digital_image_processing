/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Colors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Jorismar
 */
public class ConvertToYIQ extends DigitalImageProcess.DigitalProcess {
    @Override
    public BufferedImage apply(BufferedImage img, Object arg) {
        BufferedWriter buffer = (BufferedWriter) arg;
        
        try {
            buffer.write(img.getWidth() + " " + img.getHeight() + "\n");

            for(int x = 0; x < img.getWidth(); x++)
                for(int y = 0; y < img.getHeight(); y++)
                    this.transform(img, x, y, buffer);
        } catch (IOException ex) {
            System.err.println("Error on write YIQ file.");
        }
        
        return null;
    }
    
    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        BufferedWriter buffer = (BufferedWriter) arg;
        Color color = new Color(img.getRGB(px, py), true);
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float y = ((0.299f * r) + (0.587f * g) + (0.114f * b)) / 256;
        float i = ((0.596f * r) - (0.274f * g) - (0.322f * b)) / 256;
        float q = ((0.212f * r) - (0.523f * g) + (0.311f * b)) / 256;       
        
        //i = i < 0 ? 0 : i;
        //q = q < 0 ? 0 : q; 
        
        //y = Math.round(y);
        //i = Math.round(i);
        //q = Math.round(q);
        
        try {
            buffer.write(y + " " + i + " " + q + "\n");
        } catch (IOException ex) {
            System.err.println("Error on write YIQ file.");
        }
        
        return 0;
    }
    
}
