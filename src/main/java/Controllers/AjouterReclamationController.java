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
        if (!text.isEmpty()) {
            if (UserConnected != null) { // Check if UserConnected is not null
                int userId = UserConnected.getId();
                System.out.println(userId);
                User user = userService.findById(userId);
                System.out.println(user);
                if (user != null) { // Check if user is not null
                    Reclamation r = new Reclamation(user,text , type);
                    System.out.println(r);
                    try {

                        reclamationService.add(r);
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Reclamation added successfully.");
                        Stage stage = (Stage) textrec.getScene().getWindow();
                        stage.close();
                        parentController.refreshScrollPane(userId);
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add reclamation: " + e.getMessage());
                        System.out.println(e.getMessage());
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not logged in.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a reclamation.");
        }
        addrec=true;
        addrec=true;
        if (addrec) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+" "+"a ajouter une reclamation de type"+" "+this.typerec.getText())
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
}
