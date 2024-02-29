package Controllers;

import Entites.Collaborateur;
import Services.CollaborateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CollaborateurController implements Initializable {

    @FXML
    private TextField txtnomcomplet;
    @FXML
    private TextField txtmail;
    @FXML
    private Button ajouter;
    CollaborateurService cs= new CollaborateurService();
    @FXML
    private Button annuler;
    @FXML
    private Button ret;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {


        String tel= txtnomcomplet.getText();
        String email=txtmail.getText();



        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("format email non valide!");
            alert.show();  }

        else if  (cs.existEmail(email)==true){



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cet email est déjà enregistré!");
            alert.show();
        }
        else  {
            try {
                Collaborateur c = new Collaborateur();
                c.setNomComplet(txtnomcomplet.getText());
                c.setEmail(txtmail.getText());
                cs.add(c);



                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Collaborateur ajouté!");
                alert.show();
            }

            catch (SQLException ex) {
                System.out.println("Error" + ex.getMessage());
            }

        }
    }

    @FXML
    private void annuler(ActionEvent event) {


        txtnomcomplet.setText("");
        txtmail.setText("");
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
//       if (LoginController.UserConnected.getRole().equals("Admin")){
//
//       FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherUser.fxml"));
//            Parent root = loader.load();
//
//
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setTitle("Afficher Users");
//        stage.setScene(scene);
//        stage.show();
//
//       }
//
//       else if(LoginController.UserConnected==null){
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
//
//
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
//
//       }


    }
}
