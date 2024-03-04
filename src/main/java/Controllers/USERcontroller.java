package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class USERcontroller {

    @FXML
    private Button PARTICIPER;

    @FXML
    private Button VOTER;

    @FXML
    void PARTICIPERON(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ParticiperUtilisateur.fxml"));
            Parent root=loader.load();

            PARTICIPER.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VOTERON(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/VoterUtilisateur.fxml"));
            Parent root=loader.load();

            VOTER.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
