package Controllers;

import Entites.*;
import Services.ReclamationService;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import static Controllers.LoginController.UserConnected;

public class ModifierReclamationController {
    boolean updated;

    @FXML
    private Button add;

    @FXML
    private TextField textrec;

    @FXML
    private TextField typerec;
    private GestionReclamation gestionReclamation;

    public void setGestionReclamationController(GestionReclamation gestionReclamation) {
        this.gestionReclamation = gestionReclamation;
    }
    Reclamation reclamation;
    public void initData(Reclamation reclamation) throws SQLException {
        this.reclamation = reclamation;
        textrec.setText(reclamation.getText());
        typerec.setText(reclamation.getType());

    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    ReclamationService reclamationService = new ReclamationService();
    UserService userService= new UserService();

    @FXML
    void ModifierReclamation(ActionEvent event) throws SQLException {

        String type = typerec.getText();
        String text = textrec.getText();

        Reclamation updatedReclamation = new Reclamation();
        updatedReclamation.setId(reclamation.getId());
        updatedReclamation.setText(type);
        updatedReclamation.setType(text);

        int userid = UserConnected.getId();

        User user = userService.findById(userid);

        updatedReclamation.setId_user(user);


        try {
            reclamationService.update(updatedReclamation);
            // Show confirmation alert with OK button handler
            showAlertWithRedirect(Alert.AlertType.CONFIRMATION, "Succès", "Formation mise à jour",
                    "La formation a été mise à jour avec succès.", "GestionReclamation.fxml");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour",
                    "Une erreur s'est produite lors de la mise à jour de la formation : " + e.getMessage());
        }

        updated=true;
        if (updated) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+"a modifier une oeuvre")
                    .showInformation();
        }

    }

    // Show alert with OK button handler for redirection
    private void showAlertWithRedirect(Alert.AlertType type, String title, String header, String content, String fxmlFile) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {
                // Redirect to GestionReclamation.fxml page
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../GestionReclamation.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) typerec.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}


