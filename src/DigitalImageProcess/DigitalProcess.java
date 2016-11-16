/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess;

import DigitalImageProcess.Tools.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public abstract class DigitalProcess {
    public BufferedImage apply(BufferedImage img, int width) throws CloneNotSupportedException {
        BufferedImage output = Image.clone(img);
        
        for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++)
                output.setRGB(x, y, this.transform(img, x, y, width));
        
        return output;
    }
    
    protected abstract int transform(BufferedImage img, int px, int py, int width);
}