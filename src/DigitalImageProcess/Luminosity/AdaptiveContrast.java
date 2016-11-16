/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Luminosity;

import DigitalImageProcess.Tools.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class AdaptiveContrast extends DigitalImageProcess.DigitalProcess {
    private int dev_r;
    private int dev_g;
    private int dev_b;
    private final float c_eq;

    public AdaptiveContrast() {
        super();
        this.dev_r = 0;
        this.dev_g = 0;
        this.dev_b = 0;
        this.c_eq = 1.0f;
    }
    
    public AdaptiveContrast(float c) {
        super();
        this.dev_r = 0;
        this.dev_g = 0;
        this.dev_b = 0;
        this.c_eq = c;
    }
    
    @Override
    protected int transform(BufferedImage img, int px, int py, int num_masks) {
        int r = 0, g = 0, b = 0;
        long len = num_masks * num_masks;
        
        int x_start = px - (Math.round((float) (num_masks / 2)));
        int x_end = px + (num_masks / 2);
        int y_start = py - (Math.round((float) (num_masks / 2)));
        int y_end = py + (num_masks / 2);
        
        Color[] colors = new Color[(x_end - x_start) * (y_end - y_start)];
        int i = 0;
        
        for (int x = x_start; x < x_end; x++)
            for (int y = y_start; y < y_end; y++) {
                if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight()) {
                    colors[i] = new Color(img.getRGB(x, y), true);
                    
                    r += colors[i].getRed();
                    g += colors[i].getGreen();
                    b += colors[i].getBlue();
                    
                    i++;
                } else len--;
            }
        
        int avg_r = Math.round(r/len);
        int avg_g = Math.round(g/len);
        int avg_b = Math.round(b/len);
        
        int size = i;
        
        for(i = 0; i < size; i++) {
            this.dev_r += (colors[i].getRed() - avg_r) * 2;
            this.dev_g += (colors[i].getGreen() - avg_g) * 2;
            this.dev_b += (colors[i].getBlue() - avg_b) * 2;
        }
        
        this.dev_r = (int) Math.sqrt(this.dev_r);
        this.dev_g = (int) Math.sqrt(this.dev_g);
        this.dev_b = (int) Math.sqrt(this.dev_b);

        Color proc = new Color(avg_r, avg_g, avg_b);
        Color pxl = new Color(img.getRGB(px, py), true);
        
        r = this.dev_r == 0 ? pxl.getRed()   : (proc.getRed()   + Math.round((this.c_eq / this.dev_r) * (pxl.getRed()   - proc.getRed())));
        g = this.dev_g == 0 ? pxl.getGreen() : (proc.getGreen() + Math.round((this.c_eq / this.dev_g) * (pxl.getGreen() - proc.getGreen())));
        b = this.dev_b == 0 ? pxl.getBlue()  : (proc.getBlue()  + Math.round((this.c_eq / this.dev_b) * (pxl.getBlue()  - proc.getBlue())));
        
        return new Color(r, g, b).getRGB();
    }
}
