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
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        
    	Color color = new Color(img.getRGB(px, py));
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float y = ((0.299f * r) + (0.587f * g) + (0.114f * b));
        float i = ((0.596f * r) - (0.274f * g) - (0.322f * b));
        float q = ((0.212f * r) - (0.523f * g) + (0.311f * b));       
        
        
        /*Dá pra setar os valores na imagem e guardar em arquivo, como está no documento de requisitos
         *Tem que ter um módulo pra salvar em HD essas altorerações, pode salvar em txt, usando um padrão 
         *que dê pra recuerar os valores originais
         */
        
        
        i = i < 0? 0 : i;
        q = q < 0? 0 : q; 
        
        y = Math.round(y);
        i = Math.round(i);
        q = Math.round(q);
        
        return new Color(y,i,q).getRGB(); // Pra que serve isso???
    }
    
}
