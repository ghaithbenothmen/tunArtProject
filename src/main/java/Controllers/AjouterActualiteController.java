package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDate;
import javafx.scene.image.ImageView;
public class AjouterActualiteController {
    private final ActualiteService cs = new ActualiteService();

    @FXML
    private ImageView main_form;

    @FXML
    private TextField TitreTF;
    @FXML
    private TextArea ActualiteTA;
    @FXML
    private DatePicker DateDP;
    @FXML
    private Label inputcontrol;
    @FXML
    private String imagePath;

    @FXML
    void ajouterAct(ActionEvent event) {
        try {
            if  (imagePath == null ) {
                inputcontrol.setText("You didn't enter an image!");

            }else
            if ((TitreTF.getText() == null || TitreTF.getText().isEmpty())) {
                inputcontrol.setText("You didn't choose a Titre!");
            }
            else
                if((ActualiteTA.getText() == null || ActualiteTA.getText().isEmpty())) {
                inputcontrol.setText("You didn't enter a description!");
            }else{
                String titre = TitreTF.getText();
                String text = ActualiteTA.getText();
                String image = imagePath;
                // Récupérer la date sélectionnée du DatePicker
                LocalDate datedb = DateDP.getValue();
                java.sql.Date date = java.sql.Date.valueOf(datedb);
                Actualite c1 = new Actualite(titre ,text, date , image);
                cs.ajouter(c1);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Confirmation");
                alert1.setContentText("Commentaire ajouté avec succès");
                alert1.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void choisirImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            try {
                // Convert file to byte array
                byte[] fileContent = Files.readAllBytes(file.toPath());

                // Send the image data to Symfony backend
                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:8000/upload-image"))
                        .header("Content-Type", "application/octet-stream")
                        .POST(HttpRequest.BodyPublishers.ofByteArray(fileContent))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

                // If the response is successful, set the image to the ImageView
                if (response.statusCode() == 200) {
                    Image image = new Image(new ByteArrayInputStream(fileContent));
                    System.out.println(image);
                    main_form.setImage(image);
                    imagePath=response.body();

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffActualiteAdmin.fxml"));
            DateDP.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
