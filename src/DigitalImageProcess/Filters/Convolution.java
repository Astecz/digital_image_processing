/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Filters;

import DigitalImageProcess.Tools.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author Jorismar
 */
public class Convolution extends DigitalImageProcess.DigitalProcess {
    @Override
    public BufferedImage apply(BufferedImage img, Object arg) {
        Mask mask = (Mask) arg;
        Kernel kernel = new Kernel(mask.getNumLines(), mask.getNumColumns(), mask.getSharpen());
        BufferedImage output = new ConvolveOp(kernel).filter(Image.clone(img), null);
        
        for(int x = 0; x < output.getWidth(); x++)
            for(int y = 0; y < output.getHeight(); y++)
                output.setRGB(x, y, this.transform(output, x, y, arg));
        
        return output;
    }

    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        Mask mask = (Mask) arg;
        Color color = new Color(img.getRGB(px, py), true);
        
        int r = color.getRed() + mask.getOffset();
        int g = color.getGreen() + mask.getOffset();
        int b = color.getBlue() + mask.getOffset();
        
        r = r < 0 ? 0 : r > 255 ? 255 : r;
        g = g < 0 ? 0 : g > 255 ? 255 : g;
        b = b < 0 ? 0 : b > 255 ? 255 : b;
        
        return new Color(r, g, b).getRGB();
    }
}
