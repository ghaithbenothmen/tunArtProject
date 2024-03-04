package Controllers;

import Controllers.AjouterComController;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

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
/*    @FXML
    private void handleUpdateButton(ActionEvent event) {
        Utilisateur selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            openUpdatePage(selectedUser);
            System.out.println(selectedUser);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setContentText("Séléctionner l'utilisateur a modifier !");
            alert.showAndWait();
        }
    }

    void openUpdatePage(Utilisateur utilisateur) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierUtilisateur.fxml"));
                Parent root = loader.load();
                ModifierUtilisateurController modifierUtilisateurController = loader.getController();
                modifierUtilisateurController.setUtilisateur(utilisateur);
                Stage updateStage = new Stage();
                updateStage.setTitle("Modifier utilisateur");
                updateStage.setScene(new Scene(root));
                updateStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 */

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
                //actualiteService.supprimer.setActualite(this.actualite.getId());

                // Notify the user that the deletion was successful
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Product deleted successfully.");
                successAlert.showAndWait();
                // Refresh the page by reloading the same FXML file
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffActualiteAdmin.fxml"));
                    Parent root = loader.load();
                    ActualiteAdmin controller = loader.getController();
                    // Pass any necessary data to the controller if needed
                    // Set the new scene
                    Stage stage = (Stage) SuppBtn.getScene().getWindow(); // Get the current stage
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    void commentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCom.fxml"));
            Parent root = loader.load();
            AjouterComController ajouterComController = loader.getController();
            ajouterComController.setActualite(this.actualite.getId(),LoginController.UserConnected.getId());
            ComBtn.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    private ObservableList<Actualite> actualites= FXCollections.observableArrayList();

    private Image image;
    public void setData(Actualite actualite){
        this.actualite = actualite;
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



    public String translate (String s, String targetLanguage) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("q", s)
                .add("target", targetLanguage)
                .build();

        Request request = new Request.Builder()
                .url("https://google-translate1.p.rapidapi.com/language/translate/v2")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "836b7b9ce5msh58443bd6437c4a8p1013efjsn7302caca48d4")
                .addHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            String translatedText = json.getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText");

            return translatedText;
        } else {
            System.out.println("Request failed");
        }
        return null;
    }
    @FXML
    private void translateText(ActionEvent event) {
        try {
            String translatedText = translate(TextA.getText(), "ar");
            String translatedTitre = translate(TitreA.getText(), "ar");
            // Assuming you want to set the translated text back to TextA label
            TextA.setText(translatedText);
            TitreA.setText(translatedTitre);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}
