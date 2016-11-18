/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Effects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.channels.Channel;

/**
 *
 * @author Jorismar
 */
public class Bands extends DigitalImageProcess.DigitalProcess {

	@Override
	protected int transform(BufferedImage img, int px, int py, Color channel) {

		Color color = new Color(img.getRGB(px, py));

		if (channel.equals(Color.red))

			color = new Color(color.getRed(), color.getRed(), color.getRed());

		else if (channel.equals(Color.green))

			color = new Color(color.getGreen(), color.getGreen(), color.getGreen());

		else
			
			color = new Color(color.getBlue(), color.getBlue(), color.getBlue());
		
		return color.getRGB();

	}

}
