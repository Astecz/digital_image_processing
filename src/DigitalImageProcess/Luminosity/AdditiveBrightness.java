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
public class AdditiveBrightness extends DigitalImageProcess.DigitalProcess {

    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        Integer C = (Integer) arg;

        Color color = new Color(img.getRGB(px, py));

        int R = color.getRed() + C;
        int G = color.getGreen() + C;
        int B = color.getBlue() + C;

        R = R <= 255 ? R : 255; // Pra que isso??
        G = G <= 255 ? G : 255; // Pra que isso??
        B = B <= 255 ? B : 255; // Pra que isso??

        return color.getRGB();
    }
}
