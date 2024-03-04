package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class GestionConcoursController {


    @FXML
    private Button best;

    @FXML
    private Button Concours;

    @FXML
    private Button Participate;

    @FXML
    private Button QR;

    @FXML
    private Button Vote;

    @FXML
    void ConcoursACTION(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
            Parent root=loader.load();

            Concours.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ParticipateACTION(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ParticiperUtilisateur.fxml"));
            Parent root=loader.load();

            Participate.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void QRACTION(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/QRcode.fxml"));
            Parent root=loader.load();

            QR.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VoteACTION(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/VoterUtilisateur.fxml"));
            Parent root=loader.load();

            Vote.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BestACTION(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/BestConcours.fxml"));
            Parent root=loader.load();

            Vote.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
