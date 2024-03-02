package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterPersonnesControllers {

    @FXML
    private Button ajouter;

    @FXML
    private Button annuler;

    @FXML
    private ChoiceBox<Role> choices ;

    @FXML
    private TextField txtemail;

    @FXML
    private Text iscriL;

    @FXML
    private PasswordField txtmdp;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private Button ret;

    @FXML
    private TextField txttel;

    @FXML
    private Button uploadImgBtn;

    private final UserService ser = new UserService();

    public void initialize(URL url, ResourceBundle rb) {
        choices.getItems().addAll(Role.ADMIN, Role.CLIENT, Role.ARTISTE);
        choices.getSelectionModel().select(Role.ADMIN);



//        if (LoginController.UserConnected.getRole().equals("Admin")){
//        iscriL.setText("ajouter un utilisateur");
//        }

    }
    @FXML
    void ajouter(ActionEvent event) {
        String nom=txtnom.getText();
        String prenom=txtprenom.getText();
        String email=txtemail.getText();
        String mdp=txtmdp.getText();
        int tel= Integer.parseInt(txttel.getText());
        Role role = choices.getValue();

        User u=new User(tel,nom,prenom,email,mdp,role);

        Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succés");
        alert1.showAndWait();

        try {
            ser.add(u);
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    void annuler(ActionEvent event) {

    }

    @FXML
    void onUploadButtonClick(ActionEvent event) {

    }

    @FXML
    void retour(ActionEvent event) {

    }

}
