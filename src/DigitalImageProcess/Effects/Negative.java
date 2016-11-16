/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Effects;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class Negative extends DigitalImageProcess.DigitalProcess {

    @Override
    protected int transform(BufferedImage img, int px, int py, int width) {
        Color color = new Color(img.getRGB(px, py));
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()).getRGB();
    }
}
