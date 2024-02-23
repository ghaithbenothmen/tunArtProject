package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterActualiteController {
    private final ActualiteService cs = new ActualiteService();

    @FXML
    private TextArea ActualiteTA;
    @FXML
    private TextField DateTF;

    @FXML
    void ajouterAct(ActionEvent event) {
        cs.add(new Actualite (ActualiteTA.getText(), DateTF.getText()));

    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualite.fxml"));
            DateTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
