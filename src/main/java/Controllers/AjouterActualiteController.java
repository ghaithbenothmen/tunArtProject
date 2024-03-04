package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class AjouterActualiteController {
    private final ActualiteService cs = new ActualiteService();

    @FXML
    private TextField TitreTF;
    @FXML
    private TextArea ActualiteTA;
    @FXML
    private DatePicker DateDP;
    @FXML
    private Label inputcontrol;
    @FXML
    private String imagePath;

    @FXML
    void ajouterAct(ActionEvent event) {
        try {
            if  (imagePath == null ) {
                inputcontrol.setText("You didn't enter an image!");

            }else
            if ((TitreTF.getText() == null || TitreTF.getText().isEmpty())) {
                inputcontrol.setText("You didn't choose a Titre!");
            }
            else
                if((ActualiteTA.getText() == null || ActualiteTA.getText().isEmpty())) {
                inputcontrol.setText("You didn't enter a description!");
            }else{
                String titre = TitreTF.getText();
                String text = ActualiteTA.getText();
                String image = imagePath;
                // Récupérer la date sélectionnée du DatePicker
                LocalDate datedb = DateDP.getValue();
                java.sql.Date date = java.sql.Date.valueOf(datedb);
                Actualite c1 = new Actualite(titre ,text, date , image);
                cs.ajouter(c1);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Confirmation");
                alert1.setContentText("Commentaire ajouté avec succès");
                alert1.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void choisirImage(ActionEvent event) {
        try {
            // Créer un FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.jpeg"));

            // Ouvrir le FileChooser
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // Obtenir le chemin de l'image
                imagePath = file.getPath().replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffActualiteAdmin.fxml"));
            DateDP.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
