/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Jorismar
 */
public class Median extends DigitalImageProcess.DigitalProcess {
    @Override
    protected int transform(BufferedImage img, int px, int py, Object matx_width) {
        Integer matrix_width = (Integer) matx_width;
        int matsize = matrix_width * matrix_width;
        
        int[] r = new int[matsize];
        int[] g = new int[matsize];
        int[] b = new int[matsize];
        
        Arrays.fill(r, 0);
        Arrays.fill(g, 0);
        Arrays.fill(b, 0);
        
        for (int x = px - Math.round((float) matrix_width/2), count = 0; x < px + (matrix_width/2); x++)
            for (int y = py - Math.round((float) matrix_width/2); y < py + (matrix_width/2); y++) {
                if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight()) {
                    Color color = new Color(img.getRGB(x, y), true);
                    
                    r[count] = color.getRed();
                    g[count] = color.getGreen();
                    b[count] = color.getBlue();
                    
                    count++;
                }
                // Moved to inside of if, increment of count while pixel position is invalid result in black pixels.
                // count++; 
            }
        
        Arrays.sort(r);
        Arrays.sort(g);
        Arrays.sort(b);
        
        return new Color(r[r.length/2], g[g.length/2], b[b.length/2]).getRGB();
    }
}
