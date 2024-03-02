package Controller;

import Entites.Actualite;
import Entites.Commentaire;
import Services.ActualiteService;
import com.google.protobuf.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class ActualiteCardController {
    private final ActualiteService actualiteService = new ActualiteService();

    @FXML
    private Label TitreA;

    @FXML
    private Label TextA;

    @FXML
    private ImageView imageFor;

    @FXML
    private Label DateA;
    @FXML
    private AnchorPane feed;

    @FXML
    private Button ComBtn;
    @FXML
    private Button CommentaireBtn;
    @FXML
    private Button  ModifierBtn;
    @FXML
    private Button SuppBtn;
    private Actualite actualite;

    @FXML
    void commentaireAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCommentaire.fxml"));
            CommentaireBtn.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    /*    @FXML
        void supprimerActulaite(ActionEvent event) {
            Actualite ActSupprimer = feed.
            int indexASupprimer = feed.getSelectionModel().getSelectedIndex();
            feed.getItems().remove(indexASupprimer);
            cs.supprimer(coursASupprimer);
        }*/
    @FXML
    void ModifierAct(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierActualite.fxml"));
            ModifierBtn.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void handleDeletion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the service method to delete the product from the database
                Actualite actualiteToDelete = new Actualite();
                actualiteService.supprimer(actualite);//(actualite.getId())
                // Notify the user that the deletion was successful
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Product deleted successfully.");
                successAlert.showAndWait();

                // Close the current stage or window
                //Stage stage = (Stage) Modifier.getScene().getWindow();
                //stage.close();
            }
        });
    }
    @FXML
    void commentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCom.fxml"));
            Parent root = loader.load();
            AjouterComController ajouterComController = loader.getController();
            ajouterComController.setActualite(7896,1003);
            ComBtn.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    private ObservableList<Actualite> actualites= FXCollections.observableArrayList();

    private Image image;
    public void setData(Actualite actualite){
        TitreA.setText(actualite.getTitre());
        TextA.setText(actualite.getText());
        DateA.setText(actualite.getDate().toString());
        String path = actualite.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 207, 190, false, true);
            imageFor.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
