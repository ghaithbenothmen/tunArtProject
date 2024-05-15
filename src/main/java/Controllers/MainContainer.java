package Controllers;

import Entites.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;


public class MainContainer implements Initializable {
    //SidebarAdminController sidebar = new SidebarAdminController(this);
    @FXML
    private StackPane contentArea;
    @FXML
    void logout(ActionEvent event) {
        UserConnected = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void stats(MouseEvent event) {
        loadPage("../StatUser.fxml");
    }

    @FXML
    void listut(ActionEvent event) {

        loadPage("../AfficherUser.fxml");
    }
    @FXML
    void Listeart(ActionEvent event) {

        loadPage("../AfficherArtistes.fxml");
    }

    @FXML
    void concours(ActionEvent event) {
        loadPage("/GestionConcours.fxml");
    }
    @FXML
    void collab(ActionEvent event) {
        loadPage("/AfficherCollaborateur.fxml");
    }
    @FXML
    void actua(ActionEvent event) {
        loadPage("/AffActualiteAdmin.fxml");
    }

    @FXML
    void rec(ActionEvent event) {
        loadPage("/GestionReclamation.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SidebarAdminController sidebar = new SidebarAdminController(this);
        this.loadPage("/StatUser.fxml");
    }

    public void loadContent(Node node) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(node);
       // System.out.println("Content loaded in MainContainer."+this);
    }
    void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            Parent root = loader.load();
            System.out.println("Page loaded: " + fxmlPath);
            loadContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}