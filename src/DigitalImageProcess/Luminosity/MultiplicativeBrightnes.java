package DigitalImageProcess.Luminosity;

import java.awt.Color;
import java.awt.image.BufferedImage;

import DigitalImageProcess.DigitalProcess;

public class MultiplicativeBrightnes extends DigitalProcess {

	@Override
	protected int transform(BufferedImage img, int px, int py, Object arg) {
		
		Integer C = (Integer) arg;

		Color color = new Color(img.getRGB(px, py));

		int R = color.getRed() * C;
		int G = color.getGreen() * C;
		int B = color.getBlue() * C;

		R = R <= 255 ? R : 255;
		G = G <= 255 ? G : 255;
		B = B <= 255 ? B : 255;

		Color newColor = new Color(R, G, B);
		
		return newColor.getRGB();		
		
	}

}
