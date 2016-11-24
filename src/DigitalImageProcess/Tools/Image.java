/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Tools;

import java.awt.image.BufferedImage;

import controllers.ImageCareTaker;

/**
 *
 * @author Jorismar
 */
public class Image {
	
	protected Image currentImage;//guarda o estado atual da imagem
	
    public static BufferedImage clone(BufferedImage img) {
        return new BufferedImage(
            img.getColorModel(), 
            img.copyData(null), 
            img.getColorModel().isAlphaPremultiplied(), 
            null
        );
    }
    
    public void pushImageState(){
    	ImageCareTaker.getInstance().addMemento(currentImage);
    }
    
    public void undoStateImage(){
    	
    	try {
			currentImage = ImageCareTaker.getInstance().getLastImageSaved();
		} catch (Exception e) {
			//VER O QUE FAZER COM A MENSAGEM DE ERRO, SE VAI TRATAR ESSA EXCEÇÃO AQUI
		}
    }
    
    public Image getCurrentImage() throws Exception{
    	
    	if(currentImage != null)
    		return currentImage;
    	
    	throw new Exception("There aren't saved image yet!"); 
    }
    
    public void setCurrentImage(Image img){
    	currentImage = img;
    }
}
