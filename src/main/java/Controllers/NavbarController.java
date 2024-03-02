package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;


public class NavbarController implements Initializable {

    @FXML
    private Text welcomeText;
    @FXML
    private ImageView profileIcon;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String name = UserConnected.getNom();
        welcomeText.setText("Bienvenue, "+name+"!");
        if(UserConnected.getRole().equals("Artiste")){
            profileIcon.setVisible(false);

        }
    }

    @FXML
    private void openProfile(MouseEvent event) {
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




}