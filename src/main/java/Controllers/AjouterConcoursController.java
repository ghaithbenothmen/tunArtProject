package controllers;

import Entites.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import Entites.Concours;
import Service.ServiceConcours;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterConcoursController implements Initializable {

    @FXML
    private Button afficher;

    @FXML
    private Button confirm;


    @FXML
    private TextField txtdate;

    @FXML
    private TextField txtlien;

    @FXML
    private TextField txtprix;

    @FXML
    private TextField txtnom;


    @FXML
    private ChoiceBox<Type> ChoiceType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceType.getItems().addAll(Type.values());
    }

    private final ServiceConcours ser=new ServiceConcours();

    @FXML
    void ajouterconcours(ActionEvent event) {


        int prix = Integer.parseInt(txtprix.getText());
        Type type = ChoiceType.getValue();
        String lien = txtlien.getText();
        String nom = txtnom.getText();
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf((txtdate.getText()));
        } catch (IllegalArgumentException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Date incorrecte");
            alert1.showAndWait();
            return;
        }

        if (prix <= 0 || type.describeConstable().isEmpty() || lien.isEmpty() || nom.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Erreur de saisie");
            alert1.showAndWait();
            return;
        } else {
            Concours p1 = new Concours(prix, date, type, lien, nom);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Concour ajouté avec succés");
            alert1.showAndWait();
            try {
                ser.ajouter(p1);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
            Parent root=loader.load();

            confirm.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void afficherConcours(ActionEvent event) {

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
            Parent root=loader.load();

            afficher.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
