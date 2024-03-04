package Controllers;

import Entites.Oeuvre;
import Entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import static Controllers.LoginController.UserConnected;

public class ArtisteCardController {

    private User u;

    @FXML
    private Label cantactart;

    @FXML
    private ImageView imgart;

    @FXML
    private Label nomart;

    @FXML
    private Label roleart;

    private Image image;
    public void setData(User user){
        nomart.setText(user.getNom()+" "+user.getPrenom());
        roleart.setText(user.getRole().toString());
        cantactart.setText(user.getEmail());


        String path = user.getImage();

        try {
            image = new Image(new File(path).toURI().toURL().toString(),207,138,false,true);
            imgart.setImage(image);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    public void setUser(User u){
        this.u=u;
    }

    @FXML
    void openprofilepage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
            AnchorPane pane = loader.load();
            AfficherController oeuvreController = loader.getController();
            oeuvreController.serData(u);
            Stage stage = new Stage();
            stage.setTitle("Liste Artiste");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }







}
