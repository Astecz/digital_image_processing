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
public class HistogramExpansion extends DigitalImageProcess.DigitalProcess {
    private int r_min, g_min, b_min;
    private int r_max, g_max, b_max;
    private final int[][] RGB;

    public HistogramExpansion() {
        super();
        this.RGB = new int[3][256];
    }

    @Override
    public BufferedImage apply(BufferedImage img, Object arg) {
        this.r_min = 255;
        this.g_min = 255;
        this.b_min = 255;
        this.r_max = 0;
        this.g_max = 0;
        this.b_max = 0;

        for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++) {
                Color color = new Color(img.getRGB(x, y));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                if(r < this.r_min) this.r_min = r;
                    else if(r > this.r_max) this.r_max = r;

                if(g < this.g_min) this.g_min = g;
                    else if(g > this.g_max) this.g_max = g;

                if(b < this.b_min) this.b_min = b;
                    else if(b > this.b_max) this.b_max = b;
            }

        for(int i = 0; i < 256; i++) {
            this.RGB[0][i] = Math.round((((float)(i - this.r_min)) / (this.r_max - this.r_min)) * 255);
            this.RGB[1][i] = Math.round((((float)(i - this.g_min)) / (this.g_max - this.g_min)) * 255);
            this.RGB[2][i] = Math.round((((float)(i - this.b_min)) / (this.b_max - this.b_min)) * 255);
        }
        
        return super.apply(img, arg);
    }
    
    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        Color c = new Color(img.getRGB(px, py));
        return new Color(RGB[0][c.getRed()], RGB[1][c.getGreen()], RGB[2][c.getBlue()]).getRGB();
    }
}
