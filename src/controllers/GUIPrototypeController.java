package controllers;



import DigitalImageProcess.Colors.ConvertToRGB;
import DigitalImageProcess.Colors.ConvertToYIQ;
import DigitalImageProcess.DigitalProcess;
import DigitalImageProcess.Effects.Bands;
import DigitalImageProcess.Effects.Negative;
import DigitalImageProcess.Effects.Thresholding;
import DigitalImageProcess.Luminosity.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DigitalImageProcess.Filters.*;
import utils.ViewsManipulate;


/**
 * @author Claudio Djohnnatha
 */
public class GUIPrototypeController implements Initializable {
	@FXML
	private Pane mainPanel;

	@FXML
	private Slider maskSlider;

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
	private Label maskLabel;

	@FXML
	private Label limiarizacaoLabel;

	@FXML
	private Label contrasteLabel;

    @FXML
	private Label brilhoLabel;

	@FXML
	private Label imageNameLabel;

	@FXML
	private Label imageSizeLabel;

	@FXML
	private ToggleGroup rgbGroup;

    @FXML
    private RadioButton rRadio;

	@FXML
	private RadioButton gRadio;

	@FXML
	private RadioButton bRadio;

	@FXML
	private RadioButton noneRadio;


	private File imageUrl;
	private File imageEdited;

	private URL url;
	private FileChooser fileChooser;
	private Average average;
	private Median median;
	private SobelGradient sobel;
	private Mask mask;
    private Alert alertWarning;
	private Alert alertOk;
	private BufferedImage output;
	private static BufferedImage image; //ESTA VARI�VEL DEVERIA SER DO TIPO DIGITALIMAGEPROCESS.TOOLS.IMAGE
	private String imageName = "/new_lena.png";
	private int mascaraValue = 0;
    private AdaptiveContrast contrast;
    private Negative negative;
    private AdditiveBrightnes additive;
    private MultiplicativeBrightnes multiplicative;
    private HistogramExpansion hist_exp;
    private HistogramEqualization hist_eq;
    private Thresholding thresholding;
    private Bands bands;
	private Color imageRGB;
    private int brightValue;
	private ViewsManipulate otherView;
	private ArrayList<Integer> matrix;
	private ConvertToYIQ yiq;
	private ConvertToRGB rgb;
	private Convolution convolution;



	public static BufferedImage processController(DigitalProcess process, Object arg) throws CloneNotSupportedException {
        return process.apply(image, arg);
    }


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione a imagem");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		);


		this.alertWarning = new Alert(Alert.AlertType.WARNING);
		this.alertOk = new Alert(Alert.AlertType.CONFIRMATION);
		alertWarning.setTitle("Alerta");
		this.mask = new Mask();
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
		otherView = new ViewsManipulate();
		matrix = new ArrayList<Integer>();
		yiq = new ConvertToYIQ();
		rgb = new ConvertToRGB();
		convolution = new Convolution();
		median = new Median();

        buttonStatus(true);

		this.maskSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			this.maskLabel.setText(String.valueOf(Math.round(newValue.floatValue())));
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

		this.maskSlider.valueChangingProperty().addListener((observableValue, wasChanging, isNowChanging) ->{
			if(!isNowChanging) {
				this.mascaraValue = (int) this.maskSlider.getValue();
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

		rgbGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {

            if (rgbGroup.getSelectedToggle() != null) {
				try {
					if(rRadio.isArmed()){
						this.output = processController(bands, new Color(255, 0, 0));
						editing(output, this.imageName);
					}

					if(gRadio.isArmed()){
						this.output = processController(bands, new Color(0, 255, 0));
						editing(output, this.imageName);
					}

					if(bRadio.isArmed()){
						this.output = processController(bands, new Color(0, 0, 255));
						editing(output, this.imageName);
					}
					if(noneRadio.isArmed()){
						System.out.println("TEST");

						editing(this.image, this.imageName);
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
                // Do something here with the userData of newly selected radioButton

            }

        });



    }






	/**
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
				this.imageNameLabel.setText(imageUrl.getName());
				this.imageSizeLabel.setText(image.getWidth() + " x " + image.getHeight());
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
				imageEdited.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	Function will apply the value from mask.
	 * @param event of clicked on button média.
     *
	 */
	@FXML
	public void mediaClicked(ActionEvent event){
        if(this.mascaraValue <= 0){
            alertWarning.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alertWarning.setContentText("Valor para a máscara!");
            alertWarning.showAndWait();
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
            alertWarning.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alertWarning.setContentText("Um valor para a máscara!");
            alertWarning.showAndWait();
        }else{
			try {
				this.output = processController(median, 10);
				editing(output, this.imageName);
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
		Mask3x3Controller mask3x3 = (Mask3x3Controller) otherView.loadScreen(getClass(), "/assets/views/Mask3x3.fxml", "Mascara 3x3",
				233, 221);

		if(mask3x3 != null){
			mask3x3.setValueReturn(matrix1 -> {
				this.matrix = matrix1;
				Convolution conv = new Convolution();

				/********* Configure Mask *********/

				 // Create matrix 3x3
				 mask.createMatrix(3, 3);

				 // Fill matrix
				 for(int x = 0; x < 3; x++)
				 	for(int y = 0; y < 3; y++)
					 	mask.setMatrixValue(x, y, matrix.get(y));

				 // Set offset
				 mask.setOffset(3);

				 // Set sharpen
				 mask.setSharpen(1, 2);
				/** ------------------ **/
				try {
					this.output = processController(convolution, mask);
					editing(output, imageName);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			});

		}
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
	public void yiqClicked(ActionEvent event){
		FileChooser yiqChooser = new FileChooser();
		yiqChooser.setTitle("Selecione o arquivo .yiq");
		yiqChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		yiqChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("TEXT files (*.yiq)", "*.yiq")
		);
		String temp = fileChooser.showOpenDialog(mainPanel.getScene().getWindow()).getPath();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(temp));
			output = processController(rgb, reader);
			reader.close();
			editing(output, "imageToRGB");
			fileChooser = new FileChooser();
			fileChooser.setTitle("Salvar arquivo RGB");
			File finalImageUrl = fileChooser.showSaveDialog(mainPanel.getScene().getWindow());
			temp = finalImageUrl.getParent() + "/" + finalImageUrl.getName() + ".png";
			ImageIO.write(output, "png", new File(temp));
			alertOk.setHeaderText("Sucesso!");
			alertOk.setContentText("Salvo em RGB com sucesso!");
			alertOk.showAndWait();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			alertWarning.setHeaderText("Algo ocorreu ao converter para YIQ!");
			alertOk.setContentText(e.getMessage());
			alertOk.showAndWait();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param event of click in yiq -> rgb button
	 */
	@FXML
	public void rgbClicked(ActionEvent event){
		fileChooser = new FileChooser();
		fileChooser.setTitle("Salvar arquivo YIQ");
		File finalImageUrl = fileChooser.showSaveDialog(mainPanel.getScene().getWindow());
		String temp = finalImageUrl.getParent() + "/" + finalImageUrl.getName() + ".yiq";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
			processController(yiq, writer);
			writer.close();
			alertOk.setHeaderText("Sucesso!");
			alertOk.setContentText("Convertido para YIQ com sucesso!");
			alertOk.showAndWait();
		} catch (IOException e) {
			alertWarning.setHeaderText("Algo ocorreu ao converter para YIQ!");
			alertOk.setContentText(e.getMessage());
			alertOk.showAndWait();
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

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
            alertWarning.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alertWarning.setContentText("Valor para o brilho tem que ser diferente de 0!");
            alertWarning.showAndWait();
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
            alertWarning.setHeaderText("Avisos! Ops, você esqueceu algo!");
            alertWarning.setContentText("Valor para o brilho tem que ser diferente de 0!");
            alertWarning.showAndWait();
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
			URL url;
			ImageIO.write(output, "png", new File(this.imageUrl.getParent() + imageName));
			String temp = imageUrl.getParent() + imageName;
			imageEdited = new File(temp);
			url = imageEdited.toURI().toURL();
			//image = ImageIO.read(imageEdited);
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
		//this.mask3x3Button.setDisable(status);
		this.maskSlider.setDisable(status);
        this.mediaButton.setDisable(status);
        this.medianaButton.setDisable(status);
        this.convolucaoButton.setDisable(status);
        this.expansaoButton.setDisable(status);
        this.equalizacaoButton.setDisable(status);
        this.limiarizacaoSlider.setDisable(status);
        this.contrasteSlider.setDisable(status);
        this.sobelButton.setDisable(status);
        //this.rRadio.setDisable(status);
        //this.gRadio.setDisable(status);
        //this.bRadio.setDisable(status);
        this.yiqrgbButton.setDisable(status);
        this.rbgyiqButton.setDisable(status);
        this.negativoButton.setDisable(status);
        this.cBrilhoSlider.setDisable(status);
        this.multiplicativoButton.setDisable(status);
        this.aditivoButton.setDisable(status);
        this.saveFile.setDisable(status);
	}



}
