/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class Average extends Filter {
    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        int r = 0, g = 0, b = 0;
       
        long len = width * width;
        
        for(int x = px - (Math.round((float)(width / 2))); x < (px + (width / 2)); x++)
            for (int y = py - (Math.round((float)(width / 2))); y < (py + (width / 2)); y++) {
                if(x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight()) {
                    Color color = new Color(img.getRGB(x, y), true);
                    r += color.getRed();
                    g += color.getGreen();
                    b += color.getBlue();
                } else len--;
            }

        return new Color(Math.round(r/len), Math.round(g/len), Math.round(b/len)).getRGB();
    }
}
