package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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
    private Button ComBtn;
    @FXML
    private Button CommentaireBtn;

    @FXML
    void commentaireAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCommentaire.fxml"));
            CommentaireBtn.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void commentaire(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCom.fxml"));
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
