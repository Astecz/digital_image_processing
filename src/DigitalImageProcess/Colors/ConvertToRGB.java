/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Colors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Jorismar
 */

public class ConvertToRGB extends DigitalImageProcess.DigitalProcess {
    @Override
    public BufferedImage apply(BufferedImage img, Object arg) {
        BufferedReader buffer = (BufferedReader) arg;

        try {
            String[] dims = buffer.readLine().split(" ");
            
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);
            
            BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            String line;
            
            for(int px = 0; px < width; px++)
                for(int py = 0; py < height; py++) {
                    line = buffer.readLine();
                    
                    if(line == null) {
                        px = width;
                        break;
                    }

                    output.setRGB(px, py, this.transform(img, px, py, line.split(" ")));
                }
            
            return output;
        } catch (IOException ex) {
            // Exceptions Handler
            return null;
        }
    }
    
    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        String[] bands = (String[]) arg;

        if(bands.length != 3)
            return 0;

        float y = Float.parseFloat(bands[0]);
        float i = Float.parseFloat(bands[1]);
        float q = Float.parseFloat(bands[2]);

        float rf = (y + (0.953f * i) + (0.621f * q));
        float gf = (y - (0.272f * i) - (0.647f * q));
        float bf = (y - (1.106f * i) + (1.703f * q));
        
        int r = (int)(rf * 256);
        int g = (int)(gf * 256);
        int b = (int)(bf * 256);

        r = r > 255 ? 255 : r < 0 ? 0 : r;
        g = g > 255 ? 255 : g < 0 ? 0 : g;
        b = b > 255 ? 255 : b < 0 ? 0 : b;

        return new Color(r, g, b).getRGB();
    }
}
