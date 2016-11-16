



import Filters.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;


/**
 *
 * @author Jorismar
 * @author claudio
 */
public class Main  extends Application{


    /*
    public static void main(String[] args) {
        try {
            Average avg = new Average();
            //Median med = new Median();
            //SobelGradient sbl = new SobelGradient();
            //MaskSetter mask = new MaskSetter();
            
            image = ImageIO.read(new File("lena.png"));
            BufferedImage output = filterController(avg, 10);
            
            ImageIO.write(output, "png", new File("new_lena.png"));
            
            System.out.println("Done!");
        } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/


	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("assets/views/GUIPrototype.fxml"));

		Scene scene = new Scene(root, 1095, 529);

		primaryStage.setTitle("Image editor");
		primaryStage.setScene(scene);
		primaryStage.show();



	}

	public static void main(String[] args) {
		launch(args);
	}
}
