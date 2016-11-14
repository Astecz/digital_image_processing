/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jorismar
 */
public class Utils {
    public static BufferedImage clone(BufferedImage img) {
        return new BufferedImage(
            img.getColorModel(), 
            img.copyData(null), 
            img.getColorModel().isAlphaPremultiplied(), 
            null
        );
    }
}
