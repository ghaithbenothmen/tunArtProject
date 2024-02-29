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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class SidebarAdminController implements Initializable {

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
    private Button commandeBtn;
    @FXML
    private Button LocationBtn;
    @FXML
    private Button utilisateursBtn;
    @FXML
    private Button ticketsBtn;
    /**
     * Initializes the controller class.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }




    @FXML
    private void eventListPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherListeEvent.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LandingPageAdmin.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            LandingAdminController controller = loader.getController();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void produitsPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gere.fxml"));
        Parent root = loader.load();

        // Set the root of the current scene to the new FXML file
        produitsBtn.getScene().setRoot(root);
    }

    @FXML
    private void reclamationPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminRecPanel.fxml"));
        Parent root = loader.load();

        // Set the root of the current scene to the new FXML file
        reclamationsBtn.getScene().setRoot(root);
    }

    @FXML
    private void commandePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminCommande.fxml"));
        Parent root = loader.load();

        // Set the root of the current scene to the new FXML file
        commandeBtn.getScene().setRoot(root);
    }

    @FXML
    private void locationPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESTIONLOCAUX.fxml"));
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
    private void utilisateursPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SidebarAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ticketsPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketList.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            AfficherUserController controller = loader.getController();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}