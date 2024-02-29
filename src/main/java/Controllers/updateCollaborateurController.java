package Controllers;

import Entites.Collaborateur;
import Entites.Role;
import Entites.User;
import Services.CollaborateurService;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class updateCollaborateurController {

    @FXML
    private Button annulercol;

    @FXML
    private TextField mailcol;

    @FXML
    private Button modcol;

    @FXML
    private TextField nomcomp;

    private Collaborateur collaborateur;

    CollaborateurService collaborateurService = new CollaborateurService();

    AfficherCollaborateur afficherCollaborateur = new AfficherCollaborateur();

    @FXML
    void annulerform(ActionEvent event) {
        nomcomp.setText("");
        mailcol.setText("");

    }

    @FXML
    void updatecol(ActionEvent event) {
        String nom = nomcomp.getText();
        String email = mailcol.getText();






        Collaborateur updatecol = new Collaborateur();
        updatecol.setId(collaborateur.getId());
        updatecol.setNomComplet(nom);
        updatecol.setEmail(email);



        try {
            collaborateurService.update(updatecol);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "User mise à jour", "Collaborateur a été mise à jour avec succès.");


            Stage stage = (Stage) nomcomp.getScene().getWindow();
            stage.close();

            //refreshi tab
            afficherCollaborateur.refreshTable();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", "Une erreur s'est produite lors de la mise à jour : " + e.getMessage());
        }
    }

    public void setAfficherCollaborateur(AfficherCollaborateur afficherCollaborateur) {
        this.afficherCollaborateur = afficherCollaborateur;
    }


    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initData(Collaborateur collaborateur) throws SQLException {
        this.collaborateur = collaborateur;
        nomcomp.setText(collaborateur.getNomComplet());
        mailcol.setText(collaborateur.getEmail());

    }





}
