package controllers;

import Entites.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class AjouterEvenementController {
    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLieu;

    @FXML
    private TextField txtNbPublic;

    @FXML
    private TextField txtNomEv;
    private final EvenementService serEv = new EvenementService();

    @FXML
    void ajouterEvenement(ActionEvent event) throws ParseException {
        int idEv = Integer.parseInt(txtId.getText());
        String lieu = txtLieu.getText();
        String nomEvent = txtNomEv.getText();
        int nbPublic = Integer.parseInt(txtNbPublic.getText());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(String.valueOf(txtDate.getTooltip()));

        Evenement Ev1 = new Evenement(idEv, lieu, nomEvent, nbPublic, date);
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation ");
        alert1.setContentText("Evenement ajouté avec succés ");
        alert1.showAndWait();

        try {
            serEv.add(Ev1);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void afficherEvenement(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root =loader.load();

            controllers.AfficherEvenementController dc = loader.getController();
            dc.setLbNom(txtId.getText());
            txtId.getScene().setRoot(root);
        }


        catch (IOException e) {throw new RuntimeException(e);}

    }
}