package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientContainerController implements Initializable{
    @FXML
    private StackPane contentArea;

    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void artistes(ActionEvent event) {

    }

    @FXML
    void evenements(ActionEvent event) {

    }

    @FXML
    void formations(ActionEvent event) {
        loadPage("../InscriptionFormation.fxml");
    }
    @FXML
    void categorie(ActionEvent event) {
        //loadPage("../GestionCategorie.fxml");
    }
    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void profil(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SidebarAdminController sidebar = new SidebarAdminController(this);
        this.loadPage("../InscriptionFormation.fxml");
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
