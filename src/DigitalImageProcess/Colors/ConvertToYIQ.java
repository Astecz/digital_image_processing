/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Colors;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class ConvertToYIQ extends DigitalImageProcess.DigitalProcess {

    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        Color color = new Color(img.getRGB(px, py));
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float y = ((0.299f * r) + (0.587f * g) + (0.114f * b)) / 256;
        float i = ((0.596f * r) - (0.274f * g) - (0.322f * b)) / 256;
        float q = ((0.212f * r) - (0.523f * g) + (0.311f * b)) / 256;
        
        return 0; // Pra que serve isso???
    }
    
}
