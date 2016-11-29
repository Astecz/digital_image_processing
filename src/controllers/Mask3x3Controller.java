package controllers;

import com.sun.istack.internal.NotNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by claudio on 27/11/16.
 */
public class Mask3x3Controller implements Initializable {

    private ArrayList<Integer> matrix3x3;
    private ValueReturnListener value;
    @FXML
    private TextField input1x1;
    @FXML
    private TextField input1x2;
    @FXML
    private TextField input1x3;
    @FXML
    private TextField input2x1;
    @FXML
    private TextField input2x2;
    @FXML
    private TextField input2x3;
    @FXML
    private TextField input3x1;
    @FXML
    private TextField input3x2;
    @FXML
    private TextField input3x3;
    private Alert alertWarning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matrix3x3 = new ArrayList<Integer>();
        alertWarning = new Alert(Alert.AlertType.WARNING);
    }



    @FXML
    public void confirmButtonAction(ActionEvent event){
        boolean verify = true;
        if(isNumeric(input1x1.getText()) && isNumeric(input1x2.getText()) &&
                isNumeric(input1x3.getText()) && isNumeric(input2x1.getText())
                && isNumeric(input2x2.getText()) && isNumeric(input2x3.getText()) &&
                isNumeric(input3x1.getText()) && isNumeric(input3x2.getText()) &&
                isNumeric(input3x3.getText())){

            matrix3x3.add(Integer.parseInt(input1x1.getText()));
            matrix3x3.add(Integer.parseInt(input1x2.getText()));
            matrix3x3.add(Integer.parseInt(input1x3.getText()));
            matrix3x3.add(Integer.parseInt(input2x1.getText()));
            matrix3x3.add(Integer.parseInt(input2x2.getText()));
            matrix3x3.add(Integer.parseInt(input2x3.getText()));
            matrix3x3.add(Integer.parseInt(input3x1.getText()));
            matrix3x3.add(Integer.parseInt(input3x2.getText()));
            matrix3x3.add(Integer.parseInt(input3x3.getText()));

        }else{
            verify = false;
            alertWarning.setHeaderText("Avisos!  Tem algo errado");
            alertWarning.setContentText("Matriz só aceita numeros ou faltou preencher todos.");
            alertWarning.showAndWait();
        }

        for(int values: matrix3x3){
            if(values > 255 || values < 0){
                verify = false;
                alertWarning.setHeaderText("Avisos!  Foi digitado algo diferente");
                alertWarning.setContentText("Matriz só aceita números até 255.");
                alertWarning.showAndWait();

            }
        }

        if(verify){
            value.OnReturnListener(matrix3x3);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }

    }

    @FXML
    public void cancelButtonAction(ActionEvent event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void setValueReturn(ValueReturnListener valueReturn) {
        this.value = valueReturn;
    }




    public interface ValueReturnListener {
        void OnReturnListener(@NotNull ArrayList<Integer> matrix);
    }


    public boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }
}
