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
public class HistogramEqualization extends DigitalImageProcess.DigitalProcess {
    private final int[][][] RGB;

    public HistogramEqualization() {
        super();
        this.RGB = new int[3][256][2];
    }

    @Override
    public BufferedImage apply(BufferedImage img, int width) throws CloneNotSupportedException {
        // Start color count
        for(int i = 0; i < 256; i++) {
            this.RGB[0][i][0] = 0;  // Red
            this.RGB[1][i][0] = 0;  // Green
            this.RGB[2][i][0] = 0;  // Blue
        }
        
        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
                Color color = new Color(img.getRGB(x, y));

                // Color count
                this.RGB[0][color.getRed()][0]++;   // Red
                this.RGB[1][color.getGreen()][0]++; // Green
                this.RGB[2][color.getBlue()][0]++;  // Blue
            }
        }

        float c = 255.0f / (img.getWidth() * img.getHeight());
        
        int r_count = 0;
        int g_count = 0;
        int b_count = 0;
        
        for(int i = 0; i < 256; i++) {
            // Color count
            r_count += this.RGB[0][i][0];  // Red
            g_count += this.RGB[1][i][0];  // Green
            b_count += this.RGB[2][i][0];  // Blue

            // F value
            this.RGB[0][i][1] = Math.round(c * r_count);  // Red
            this.RGB[1][i][1] = Math.round(c * g_count);  // Green
            this.RGB[2][i][1] = Math.round(c * b_count);  // Blue
        }

        return super.apply(img, width);
    }
    
    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        Color c = new Color(img.getRGB(px, py));
        return new Color(RGB[0][c.getRed()][1], RGB[1][c.getGreen()][1], RGB[2][c.getBlue()][1]).getRGB();
    }
}
