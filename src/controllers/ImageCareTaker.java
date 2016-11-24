package controllers;

import java.util.LinkedList;
import java.util.Queue;
import DigitalImageProcess.Tools.Image;

public class ImageCareTaker {
	
	protected Queue<Image> states;
	public static ImageCareTaker instance;
	
	private ImageCareTaker(){
		states = new LinkedList<Image>();
	}
	
	public static ImageCareTaker getInstance(){
		
		if(instance ==  null){
			instance = new ImageCareTaker();
		}
		
		return instance;
	}
	
	public void addMemento(Image image){
		states.add(image);
	}
	
	public Image getLastImageSaved() throws Exception{
		
		if(states.isEmpty()){
			throw new Exception("There aren't saved image yet!");
		}
		
		//return queue head
		return states.remove();
	}
}
