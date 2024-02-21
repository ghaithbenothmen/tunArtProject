package controllers;

import Entites.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class SupprimerEvenementController {

    @FXML
    private TextField txtId_Evenement;

    @FXML
    private final EvenementService serEv = new EvenementService();

    void SupprimerEvenement(ActionEvent event) {

        int idEv = Integer.parseInt(txtId_Evenement.getText());

        Evenement Ev2 = new Evenement(txtId_Evenement);
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Suppression ");
        alert1.setContentText("Evenement supprimé avec succés ");
        alert1.showAndWait();

        try { serEv.add(Ev2);}
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();}
    }
    @FXML
    void afficherEvenement(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerEvenement.fxml"));
            Parent root =loader.load();
            controllers.AfficherEvenementController dc = loader.getController();
            dc.setLbNom(txtId_Evenement.getText());
            txtId_Evenement.getScene().setRoot(root);}

        catch (IOException e) { throw new RuntimeException(e);}
    }
}