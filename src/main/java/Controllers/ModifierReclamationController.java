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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
    UserService userService = new UserService();

    @FXML
    void ModifierReclamation(ActionEvent event) throws SQLException {
        String type = textrec.getText();
        String text = typerec.getText();
        System.out.println(textrec.getText());

        // Check for bad words in the text
        String sanitizedText = bad_words(text);

        // Proceed with updating the reclamation
        Reclamation updatedReclamation = new Reclamation();

        updatedReclamation.setId(reclamation.getId());
        updatedReclamation.setType(text);

        updatedReclamation.setText(type);


        // Replace bad words with **
        if (!sanitizedText.equals(text)) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a reclamation without bad words.", "Please enter a reclamation without bad words");
            text = sanitizedText; // Update text with sanitized version
        }

        // Set the updated text in the reclamation object
        updatedReclamation.setText(text);

        int userId = UserConnected.getId();
        User user = userService.findById(userId);
        updatedReclamation.setId_user(user);

        try {
            reclamationService.update(updatedReclamation);

            // Show confirmation alert with OK button handler
            showAlertWithRedirect(Alert.AlertType.CONFIRMATION, "Succès", "Reclamation mise à jour",
                    "La réclamation a été mise à jour avec succès.", "GestionReclamation.fxml");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour",
                    "Une erreur s'est produite lors de la mise à jour de la réclamation : " + e.getMessage());
        }

        // Show alert about bad words
        updated = true;
        if (updated) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom() + " a modifié une réclamation")
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