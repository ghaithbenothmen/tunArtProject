/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Entites.AESCrypt;
import Entites.Formation;
import Entites.Role;
import Entites.User;
import Services.FormationService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button cnbt;
    @FXML
    private Button cnbt1;
    @FXML

    private TextField email;
    @FXML
    private PasswordField mdp;


    public static User UserConnected;


    UserService us= new UserService();
    @FXML
    private Button icibt;
    @FXML
    private ImageView logo;
    @FXML
    private Text slogan;
    @FXML
    private Text bien;

    public AESCrypt CryptVar;
    public  String key = "ThisIsASecretKey";

    private int selectedFormationId;
    FormationService formationService =new FormationService();

    public  Formation formation=null;

    private boolean loginSuccessful = false;

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    @FXML
    private void connect(ActionEvent event) throws Exception {

        if (email.getText().isEmpty() || mdp.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
        } else if (!email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email non valide");
            alert.setHeaderText(null);
            alert.setContentText("Format d'email non valide!");
            alert.show();
        } else {
            Boolean verif = false;
            List<User> users = us.findAll();

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(email.getText()) && users.get(i).getMdp().equals(mdp.getText())) {
                    UserConnected = users.get(i);
                    verif = true;
                    break;
                }
            }

            if (verif) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bienvenue " + UserConnected.getNom() + " " + UserConnected.getPrenom());
                alert.showAndWait();

                if (UserConnected.getRole().equals(Role.ARTISTE)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../ArtisteContainer.fxml"));
                    Parent root = loader.load();

                   // GestionFormationController gestionFormationController = loader.getController();
                    //gestionFormationController.initData(UserConnected.getId());

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("");
                    stage.setScene(scene);
                    stage.show();

                }if (UserConnected.getRole().equals(Role.CLIENT)) {

                    if (selectedFormationId != 0) {
                        System.out.println(selectedFormationId);

                        this.formation= formationService.findById(selectedFormationId);
                        System.out.println("this is for"+this.formation+"thissss id"+selectedFormationId);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ClientContainer.fxml"));
                        Parent root = loader.load();
                        InscriptionInfoController inscriptionInfoController = loader.getController();
                        inscriptionInfoController.setData(this.formation);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Afficher For");
                        stage.setScene(scene);
                        stage.show();

                    }else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ClientContainer.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Afficher");
                        stage.setScene(scene);
                        stage.show();

                    }
                }  if (UserConnected.getRole().equals(Role.ADMIN)){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainContainer.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Afficher User");
                    stage.setScene(scene);
                    stage.show();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur inexistant!");
                alert.show();
            }
        }
    }


    @FXML
    private void inscrire(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterPersonnes.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void mdp_ob(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmailCheck.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void passwrd(ActionEvent event) throws IOException {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("changement de mot de passe");
        stage.setScene(scene);
        stage.show();


    }

    public void setRequestedFormationId(int formationId) {
        this.selectedFormationId = formationId;
    }


}