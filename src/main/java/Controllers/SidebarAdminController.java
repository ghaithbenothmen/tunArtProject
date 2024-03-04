package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarAdminController {

    @FXML
    private StackPane contentArea;

    private MainContainer mainContainer;

    public SidebarAdminController(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
        System.out.println("new"+this.mainContainer);
    }
    public SidebarAdminController() {
        // Constructeur sans arguments
    }
    @FXML
    void connect(ActionEvent event) {
        loadPage("/Login.fxml");
    }

    @FXML
    void stats(MouseEvent event) {
        loadPage("../AfficherUser.fxml");
    }

    @FXML
    void listut(ActionEvent event) {
        loadPage("../AfficherUser.fxml");
    }

    @FXML
    void collab(ActionEvent event) {
        loadPage("/Collaborateurs.fxml");
    }

    void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            Parent root = loader.load();
            System.out.println("Page loaded: " + fxmlPath);
            System.out.println("ffffffff"+this.mainContainer);
            mainContainer.loadContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}