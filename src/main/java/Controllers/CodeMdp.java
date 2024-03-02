
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class CodeMdp implements Initializable {

    @FXML
    private TextField code;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void setCode(int c){

        code.setText(String.valueOf(c));

    }








}
