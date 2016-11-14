/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Tools.Utils;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author Jorismar
 */
public class SobelGradient extends Filter {
    private BufferedImage copy1;
    private BufferedImage copy2;
    private final float [] sobelh = new float [] {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    private final float [] sobelv = new float [] {1, 2, 1, 0, 0, 0, -1, -2, -1};
    
    @Override
    public BufferedImage apply(BufferedImage img, int width) throws CloneNotSupportedException {
        this.copy1 = new ConvolveOp(new Kernel(3, 3, sobelh)).filter(img, null);
        this.copy2 = new ConvolveOp(new Kernel(3, 3, sobelv)).filter(img, null);
        
        return super.apply(img, 0);
    }

    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        Color color1 = new Color(this.copy1.getRGB(px, py), true);
        Color color2 = new Color(this.copy2.getRGB(px, py), true);

        int sum_r = (int) Math.sqrt((color1.getRed()   * color1.getRed())   + (color2.getRed()   * color2.getRed()));
        int sum_g = (int) Math.sqrt((color1.getGreen() * color1.getGreen()) + (color2.getGreen() * color2.getGreen()));
        int sum_b = (int) Math.sqrt((color1.getBlue()  * color1.getBlue())  + (color2.getBlue()  * color2.getBlue()));

        sum_r = sum_r > 255 ? 255 : sum_r < 0 ? 0 : sum_r;
        sum_g = sum_g > 255 ? 255 : sum_g < 0 ? 0 : sum_g;
        sum_b = sum_b > 255 ? 255 : sum_b < 0 ? 0 : sum_b;

        return new Color(sum_r, sum_g, sum_b).getRGB();
    }
}
