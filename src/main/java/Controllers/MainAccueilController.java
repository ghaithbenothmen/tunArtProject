package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAccueilController implements Initializable {
    @FXML
    private StackPane contentArea;
    @FXML
    private Menu Accueil;

    @FXML
    private Menu Artistes;

    @FXML
    private Menu Concours;

    @FXML
    private Menu Oeuvres;

    @FXML
    private AnchorPane anchCol;

    @FXML
    private AnchorPane anchFor;

    @FXML
    private AnchorPane anchOe;

    @FXML
    private MenuItem sac;

    @FXML
    void Accueil(ActionEvent event) {

    }

    @FXML
    void Concours(ActionEvent event) {

    }

    @FXML
    void Formations(ActionEvent event) {
this.loadPage("../Login.fxml");
    }

    @FXML
    void artistes(ActionEvent event) {

    }

    @FXML
    void connect(ActionEvent event) {

    }

    @FXML
    void inscrire(ActionEvent event) {

    }

    @FXML
    void oeuvres(ActionEvent event) {

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //AccueilPageController sidebar = new AccueilPageController(this);
        this.loadPage("../Login.fxml");
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
