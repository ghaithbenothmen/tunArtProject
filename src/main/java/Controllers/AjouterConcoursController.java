package Controllers;

import Entites.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Entites.Concours;
import Services.ServiceConcours;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private DatePicker dateinput;


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

        //date initialize
        dateinput.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Date is picked, do something
                System.out.println("DateF value: " + newValue);
            } else {
                // Date is not picked
                System.out.println("DateF not picked");
            }
        });

        /*
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf((txtdate.getText()));
        } catch (IllegalArgumentException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Date incorrecte");
            alert1.showAndWait();
            return;
        }*/

        LocalDate today = LocalDate.now();
        LocalDate dateFin = dateinput.getValue();
        java.sql.Date sqlDateFin= java.sql.Date.valueOf(dateFin);
        if(dateFin.isBefore(today))
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Date incorrecte");
            alert1.showAndWait();
            return;
        }

        if (prix <= 0 || type.describeConstable().isEmpty() || lien.isEmpty() || nom.isEmpty() ) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Erreur de saisie");
            alert1.showAndWait();
            return;
        } else {
            Concours p1 = new Concours(prix, sqlDateFin, type, lien, nom);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Concour ajouté avec succés");
            alert1.showAndWait();
            try {
                ser.add(p1);
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
