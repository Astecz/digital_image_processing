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
public class ConvertToRGB extends DigitalImageProcess.DigitalProcess {
	@Override
	protected int transform(BufferedImage img, int px, int py, Object arg) {

		Color color = new Color(img.getRGB(px, py));

		/*
		 * Lê do arquivo os valores de YIQ, salvo na conversão RGB->YIQ Mas por
		 * enquanto vou deixar pegando os valores da imagem de entrada
		 */

		float Y = color.getRed();
		float I = color.getGreen();
		float Q = color.getBlue();

		double R = Y + (0.953 * I) + (0.621 * Q);
		double G = Y - (0.272 * I) - (0.647 * Q);
		double B = Y - (1.106 * I) + (1.703 * Q);

		R = R > 255 ? 255 : R;
		G = G > 255 ? 255 : G;
		B = B > 255 ? 255 : B;

		R = R < 0 ? 0 : R;
		G = G < 0 ? 0 : G;
		B = B < 0 ? 0 : B;

		R = Math.round(R);
		G = Math.round(G);
		B = Math.round(B);

		return new Color((int) (R), (int) G, (int) B).getRGB();

	}
}
