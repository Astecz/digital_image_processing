package controllers;



import DigitalImageProcess.DigitalProcess;
import DigitalImageProcess.Effects.Bands;
import DigitalImageProcess.Effects.Negative;
import DigitalImageProcess.Effects.Thresholding;
import DigitalImageProcess.Luminosity.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import DigitalImageProcess.Filters.*;


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

	@FXML
	private Label mascaraLabel;

	@FXML
	private Label limiarizacaoLabel;

	@FXML
	private Label contrasteLabel;

	@FXML
	private Slider rSlider;

    @FXML
    private Slider gSlider;

    @FXML
    private Slider bSlider;

    @FXML
	private Label brilhoLabel;

    @FXML
    private Label rLabel;

	@FXML
	private Label gLabel;

	@FXML
	private Label bLabel;


	private File imageUrl;
	private File imageEdited;

	private URL url;
	private FileChooser fileChooser;
	private Average average;
	private Median median;
	private SobelGradient sobel;
	private MaskSetter mask;
    private Alert alert;
	private BufferedImage output;
	private static BufferedImage image; //ESTA VARI�VEL DEVERIA SER DO TIPO DIGITALIMAGEPROCESS.TOOLS.IMAGE
	private String imageName = "/new_lena.png";
	private int mascaraValue = 0;
    private AdaptiveContrast contrast;
    private Negative negative;
    private AdditiveBrightnes additive;
    MultiplicativeBrightnes multiplicative;
    private HistogramExpansion hist_exp;
    private HistogramEqualization hist_eq;
    private Thresholding thresholding;
    private Bands bands;
	private Color imageRGB;
    private int brightValue;

    public static BufferedImage processController(DigitalProcess process, Object arg) throws CloneNotSupportedException {
        return process.apply(image, arg);
    }

	public GUIPrototypeController(){
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione a imagem");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		);


        this.alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");
        this.mask = new MaskSetter();
        contrast = new AdaptiveContrast(1.0f);
        negative = new Negative();
        additive = new AdditiveBrightnes();
        sobel = new SobelGradient();
        hist_exp = new HistogramExpansion();
        hist_eq = new HistogramEqualization();
        average = new Average();
        thresholding = new Thresholding();
        bands = new Bands();
        multiplicative = new MultiplicativeBrightnes();

	}

	/**
	 * Initializing buttons handle.
	 */
	@FXML
	private void initialize(){
        buttonStatus(true);

        this.mascaraSlider.valueProperty().addListener((observable, oldValue, newValue)->{
                this.mascaraLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
        });

        this.limiarizacaoSlider.valueProperty().addListener((observable, oldValue, newValue)->{
            this.limiarizacaoLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
        });

        this.contrasteSlider.valueProperty().addListener((observable, oldValue, newValue)->{
            this.contrasteLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
        });

        this.cBrilhoSlider.valueProperty().addListener((observable, oldValue, newValue)->{
            this.brilhoLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
        });

		this.rSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			this.rLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
		});

		this.gSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			this.gLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
		});

		this.bSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			this.bLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
		});


        this.mascaraSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) -> {
            if(!isNowChanging) {
                this.mascaraValue = (int) this.mascaraSlider.getValue();
                try {
                    this.output = processController(mask, this.mascaraValue);
                    this.mascaraLabel.setText(String.valueOf(this.mascaraValue));
                    editing(output, this.imageName);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.limiarizacaoSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
                if(!isNowChanging) {
                    try {
                        this.output = processController(thresholding, (int) this.limiarizacaoSlider.getValue());
                        editing(output, this.imageName);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
        });

        this.contrasteSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
            if(!isNowChanging) {
                try {
                    this.output = processController(contrast, (int) this.contrasteSlider.getValue());
                    editing(output, this.imageName);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });

        this.cBrilhoSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
            if(!isNowChanging) {
                try {
                    this.brightValue = (int) this.cBrilhoSlider.getValue();
                    this.output = processController(additive, brightValue);
                    editing(output, this.imageName);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });

		this.rSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
			if(!isNowChanging) {
				try {
					this.output = processController(bands, new Color((int) rSlider.getValue(), 0 ,0));
					editing(output, this.imageName);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		});

		this.gSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
			if(!isNowChanging) {
				try {
					this.output = processController(bands, new Color(0, (int) gSlider.getValue(), 0));
					editing(output, this.imageName);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		});

		this.bSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
			if(!isNowChanging) {
				try {
					this.output = processController(bands, new Color(0, 0, (int) gSlider.getValue()));
					editing(output, this.imageName);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
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
                buttonStatus(false);
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

	@FXML
	public void saveFileHandle(ActionEvent event){
		fileChooser = new FileChooser();
		fileChooser.setTitle("Salvar imagem");
		File finalImageUrl = fileChooser.showSaveDialog(mainPanel.getScene().getWindow());
		System.out.println("IMAGE: " + imageUrl);
		String temp = finalImageUrl.getParent() + "/" + finalImageUrl.getName() + ".png";

		try {
			if(output == null){
				ImageIO.write(image, "png", new File(temp));
			}else{
				ImageIO.write(output, "png", new File(temp));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		//imageEdited = new File(temp);
		//url = imageEdited.toURI().toURL();
	}

	/**
	 *	Function will apply the value from mask.
	 * @param event of clicked on button média.
     *
	 */
	@FXML
	public void mediaClicked(ActionEvent event){
        if(this.mascaraValue <= 0){
            alert.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alert.setContentText("Valor para a máscara!");
            alert.showAndWait();
        }else{
			try {
				this.output = processController(average, this.mascaraValue);
				editing(output, this.imageName);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

    }

	/**
	 * The function will apply the value got in mask.
	 * @param event of click in Mediana button
	 */
	@FXML
	public void medianaClicked(ActionEvent event){
        if(this.mascaraValue <= 0){
            alert.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alert.setContentText("Um valor para a máscara!");
            alert.showAndWait();
        }else{
			try {
				this.output = processController(median, this.mascaraValue);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
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
        try {
            this.output = processController(hist_exp, null);
            editing(output, this.imageName);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

	/**
	 *
	 * @param event of click in equalização button
	 */
	@FXML
	public void equalizacaoClicked(ActionEvent event){
        try {
            this.output = processController(hist_eq, null);
            editing(output, this.imageName);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

	/**
	 *
	 * @param event of click in sobel button
	 */
	@FXML
	public void sobelClicked(ActionEvent event){
        try {
            this.output = processController(sobel, null);
            editing(output, this.imageName);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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
        try {
            this.output = processController(negative, 3);
            editing(output, this.imageName);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

	/**
	 *
	 * @param event of click in multiplicativo button
	 */
	@FXML
	public void multiplicativoClicked(ActionEvent event){
        if(this.brightValue <= 0){
            alert.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alert.setContentText("Valor para o brilho tem que ser diferente de 0!");
            alert.showAndWait();
        }else {
            try {
                this.output = processController(multiplicative, this.brightValue);
                editing(output, this.imageName);
            } catch (CloneNotSupportedException e) {
            }
        }

	}

	/**
	 *
	 * @param event of click in aditivo button
	 */
	@FXML
	public void aditivoClicked(ActionEvent event){
        if(this.brightValue <= 0){
            alert.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alert.setContentText("Valor para o brilho tem que ser diferente de 0!");
            alert.showAndWait();
        }else {
            try {
                this.output = processController(additive, this.brightValue);
                editing(output, this.imageName);
            } catch (CloneNotSupportedException e) {
            }
        }
	}

	/**
	 *
	 * @param output the filter applied will generate a new Image called output
	 *               than will create a new file (image)
	 */
	public void editing(BufferedImage output, String imageName){
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

    /**
     * Function called to change the buttons states between enable and disable
     * @param status it's a boolean wich is true to disable and false to enable.
     */
	public void buttonStatus(boolean status){
		this.mascaraSlider.setDisable(status);
        this.mediaButton.setDisable(status);
        this.medianaButton.setDisable(status);
        this.convolucaoButton.setDisable(status);
        this.expansaoButton.setDisable(status);
        this.equalizacaoButton.setDisable(status);
        this.limiarizacaoSlider.setDisable(status);
        this.contrasteSlider.setDisable(status);
        this.sobelButton.setDisable(status);
        this.rSlider.setDisable(status);
        this.gSlider.setDisable(status);
        this.bSlider.setDisable(status);
        this.yiqrgbButton.setDisable(status);
        this.rbgyiqButton.setDisable(status);
        this.negativoButton.setDisable(status);
        this.cBrilhoSlider.setDisable(status);
        this.multiplicativoButton.setDisable(status);
        this.aditivoButton.setDisable(status);
        this.saveFile.setDisable(status);
	}


}
