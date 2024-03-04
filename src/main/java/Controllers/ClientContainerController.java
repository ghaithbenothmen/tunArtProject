package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;

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
        UserConnected = null;

        // Redirect to the login page
        loadPage("/Login.fxml");
    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void profil(ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit.fxml"));
            Parent root = loader.load();
            EditController controller = loader.getController();
            controller.senduser(UserConnected);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SidebarAdminController sidebar = new SidebarAdminController(this);
        this.loadPage("../InscriptionFormation.fxml");
    }
    @FXML
    void condidature(ActionEvent event) {
        this.loadPage("../ParticiperUtilisateur.fxml");
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
