package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class SidebarController implements Initializable {

    @FXML
    private Button eventListBtn;
    private MainContainer mcc;
    @FXML
    private Button homeBtn;
    @FXML
    private Button produitsBtn;
    @FXML
    private Button reclamationsBtn;
    @FXML
    private Button locauxBtn;
    /**
     * Initializes the controller class.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }




    @FXML
    private void eventListPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            AfficherUserController controller = loader.getController();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void homePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LandingPage.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            LandingPageController controller = loader.getController();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }








}