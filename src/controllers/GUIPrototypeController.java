package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GUIPrototypeController {
	@FXML
	private Pane mainPanel;

	@FXML
	private Slider mascaraSlider;
	@FXML
	private Button mediaButton;
	@FXML
	private Button medianaButton;
	@FXML 
	private Button convolucaoButton;
	@FXML
	private Button expansaoButton;
	@FXML
	private Button equalizacaoButton;
	@FXML
	private Slider limiarizacaoSlider;
	@FXML
	private Button contrasteButton;
	@FXML
	private Button sobelButton;
	@FXML
	private Slider bandasSlider;
	@FXML
	private Button rbgyiqButton;
	@FXML
	private Button yiqrgbButton;
	@FXML
	private Button negativoButton;
	@FXML
	private Slider cBrilhoSlider;
	@FXML
	private Button multiplicativoButton;
	@FXML
	private Button aditivoButton;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem saveFile;

	@FXML
	private ImageView imageView;

	private File imageUrl;
	private URL url;
	private FileChooser fileChooser;

	public GUIPrototypeController(){
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione a imagem");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
		);
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		);
	}

	/**
	 * Initializing buttons handle.
	 */
	@FXML
	private void initialize(){
		mascaraSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			System.out.println("using slider");
		});



	}

	/**
	 * Need finish it
	 * @param event
	 */
	@FXML
	public void openFileHandle(ActionEvent event){
		imageUrl = fileChooser.showOpenDialog(mainPanel.getScene().getWindow());
		if(imageUrl != null){
			try {
				url = imageUrl.toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			imageView.setImage(new Image(url.toExternalForm()));
		}

	}




}
