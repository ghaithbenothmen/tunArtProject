package Controllers;

import Entites.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainContainer implements Initializable {

    @FXML
    private AnchorPane navbarPane;
    @FXML
    private AnchorPane sidebarPane;
    @FXML
    private AnchorPane contentPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Load navbar
            FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("/Navbar.fxml"));
            AnchorPane nbar = navbarLoader.load();
            navbarPane.getChildren().setAll(nbar);
            //Load sidebar & Landing Page
            if (LoginController.UserConnected.getRole().equals(Role.ADMIN)) {
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/SidebarAdmin.fxml"));
                AnchorPane sbar = sidebarLoader.load();
                sidebarPane.getChildren().setAll(sbar);
                SidebarAdminController controller = sidebarLoader.getController();
                //Load content
                FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/LandingAdmin.fxml"));
                AnchorPane content = contentLoader.load();
                contentPane.getChildren().setAll(content);
            }
            else {
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/Sidebar.fxml"));
                AnchorPane sbar = sidebarLoader.load();
                sidebarPane.getChildren().setAll(sbar);
                SidebarController controller = sidebarLoader.getController();
                //Load content
                FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/LandingPage.fxml"));
                AnchorPane content = contentLoader.load();
                contentPane.getChildren().setAll(content);
            }

            //Save an instance of the Main Container Controller to edit the Content Pane
            MCCSaver.setMCC(this);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setContent(Node node) {
        contentPane.getChildren().setAll(node);
    }

    public void refresh() {
        contentPane.layout();
    }

}