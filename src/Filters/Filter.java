/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import java.awt.image.BufferedImage;
import Tools.Utils;

/**
 *
 * @author Jorismar
 */
public abstract class Filter {
    public BufferedImage apply(BufferedImage img, int msize) throws CloneNotSupportedException {
        BufferedImage output = Utils.clone(img);
        
        for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++)
                output.setRGB(x, y, this.transform(img, x, y, msize));
        
        return output;
    }
    
    protected abstract int transform(BufferedImage img, int px, int py, int width);
}