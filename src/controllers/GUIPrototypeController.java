package controllers;

import DigitalImageProcess.Filters.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Claudio Djohnnatha
 */
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
	private Slider contrasteSlider;
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
	private File imageEdited;

	private URL url;
	private FileChooser fileChooser;
	private Average average;
	private Median median;
	private SobelGradient sobel;
	private MaskSetter mask;

	private BufferedImage output;
	private static BufferedImage image;
	private String imageName = "/new_lena.png";
	private int mascaraValue = 0;


	public static BufferedImage filterController(Filter filter, int matrix_width) throws CloneNotSupportedException {
		return filter.apply(image, matrix_width);
	}

	public GUIPrototypeController(){
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione a imagem");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
		);
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		);

		average = new Average();
		median = new Median();
		sobel = new SobelGradient();
		mask = new MaskSetter();


	}

	/**
	 * Initializing buttons handle.
	 */
	@FXML
	private void initialize(){
		mascaraSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			try {
				this.mascaraValue = Math.round(newValue.floatValue());
				this.output = filterController(mask, this.mascaraValue);
				System.out.println(Math.round(newValue.floatValue()));
				editing(output);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
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
				image = ImageIO.read(imageUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageView.setImage(new Image(url.toExternalForm()));
			System.out.println(imageUrl);
		}
	}

	/**
	 *	Function will apply the value from mask.
	 * @param event of clicked on button média.
     *
	 */
	@FXML
	public void mediaClicked(ActionEvent event){
		//this.output = filterController(median, this.mediaValue);
	}

	/**
	 * The function will apply the value got in mask.
	 * @param event of click in Mediana button
	 */
	@FXML
	public void medianaClicked(ActionEvent event){
		try {
			this.output = filterController(median, this.mascaraValue);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param event of click in convolução button
	 */
	@FXML
	public void convolucaoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in expansão button
	 */
	@FXML
	public void expansaoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in equalização button
	 */
	@FXML
	public void equalizacaoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in sobel button
	 */
	@FXML
	public void sobelClicked(ActionEvent event){

	}


	/**
	 *
	 * @param event of click in rgb -> yiq button
	 */
	@FXML
	public void rgbClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in yiq -> rgb button
	 */
	@FXML
	public void yiqClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in negativo
	 */
	@FXML
	public void negativoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in multiplicativo button
	 */
	@FXML
	public void multiplicativoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param event of click in aditivo button
	 */
	@FXML
	public void aditivoClicked(ActionEvent event){

	}

	/**
	 *
	 * @param output the filter applied will generate a new Image called output
	 *               than will create a new file (image)
	 */
	public void editing(BufferedImage output){
		try {
			ImageIO.write(output, "png", new File(this.imageUrl.getParent() + imageName));
			String temp = imageUrl.getParent() + imageName;
			imageEdited = new File(temp);
			url = imageEdited.toURI().toURL();
			image = ImageIO.read(imageEdited);
			imageView.setImage(new Image(url.toExternalForm()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
