package Controllers;

import Entites.Reclamation;
import Entites.User;
import Services.ReclamationService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import static Controllers.LoginController.UserConnected;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AjouterReclamationController {
    boolean addrec;
    private UserService userService = new UserService();

    @FXML
    private Button add;

    @FXML
    private TextField textrec;

    @FXML
    private TextField typerec;

    private GestionReclamation parentController;

    private final ReclamationService reclamationService = ReclamationService.getInstance();
    private GestionReclamation gestionReclamationController;

    // Setter method to set the reference to the GestionReclamation controller
    public void setGestionReclamationController(GestionReclamation gestionReclamationController) {
        this.gestionReclamationController = gestionReclamationController;
    }


    @FXML
    private void AjouterReclamation(ActionEvent event) throws SQLException {
        String type = typerec.getText();
        String text = textrec.getText();

        // Check for bad words in the text
        String sanitizedText = bad_words(text);

        // Proceed with adding the reclamation
        if (UserConnected != null) {
            int userId = UserConnected.getId();
            User user = userService.findById(userId);
            if (user != null) {
                Reclamation r = new Reclamation(user, sanitizedText, type);
                try {
                    reclamationService.add(r);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Reclamation added successfully.");
                    Stage stage = (Stage) textrec.getScene().getWindow();
                    stage.close();
                    parentController.refreshScrollPane(userId);
                    addrec = true;

                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add reclamation: " + e.getMessage());
                }

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "User not logged in.");
        }


        // Show alert about bad words
        if (!sanitizedText.equals(text)) {
            showAlert(Alert.AlertType.WARNING, "Warning", "ATTENTION !! Vous avez écrit un gros mot.");
        }
        if (addrec) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom() + " " + "a ajouter une reclamation de type" + " " + this.typerec.getText())
                    .showInformation();

        }


    }






    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setParentController(GestionReclamation parentController) {
        this.parentController = parentController;
    }
    public String bad_words(String inputText) {
        List<String> badWordsList = Arrays.asList("fuck", "fucked", "nigga", "putin", "merde", "pute", "schlampe", "fuck you", "arse", "arsehole", "ass", "asses", "assface", "assfaces", "asshole", "assholes", "bastard", "bastards", "beaner", "bellend", "bint", "bitch", "bitches", "bitchy", "blowjob", "blump", "blumpkin", "bollocks");

        String sanitizedText = inputText;

        for (String badWord : badWordsList) {
            if (sanitizedText.toLowerCase().contains(badWord.toLowerCase())) {
                // Replace bad word with **
                sanitizedText = sanitizedText.replaceAll("\\b" + badWord + "\\b", "**");
            }
        }

        return sanitizedText;
    }


}

