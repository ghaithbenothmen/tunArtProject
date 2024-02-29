package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class InscriptionControllers implements Initializable {

    @FXML
    private ComboBox choices;
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtprenom;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txttel;
    @FXML
    private PasswordField txtmdp;
    @FXML
    private Button ajouter;
    @FXML
    private Button cnctbtn;

    UserService us= new UserService();
    @FXML
    private Button annuler;
    @FXML
    private Button uploadImgBtn;

    private String imageData;
    private Image image;
    @FXML
    private Text iscriL;
    @FXML
    private Button ret;
    @FXML
    private ImageView imageFor;
    @FXML
    private AnchorPane main_form;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> roleNames = FXCollections.observableArrayList();
        for (Role role : Role.values()) {
            roleNames.add(role.toString());
        }
        choices.setItems(FXCollections.observableArrayList(Role.ARTISTE, Role.CLIENT));
        choices.setValue(Role.ARTISTE); // Définir la valeur par défaut

        choices.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Role role = Role.valueOf((String) newValue);
                System.out.println("Selected role: " + role);

            }
        });


        //if (LoginController.UserConnected.getRole().equals("Admin")){
        //iscriL.setText("ajouter un utilisateur");
        //}

    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {


        String email=txtemail.getText();
        String tel= txttel.getText();


        Role role = Role.valueOf((String) choices.getSelectionModel().getSelectedItem());


        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("format email non valide!");
            alert.show();  }

        else if  (us.existemail(email)==true){



            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cet email est déjà enregistré!");
            alert.show();
        }





        else if (!tel.matches("\\d{8}")){

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("format tel non valide!");
            alert.show();

        }



        else  {




            try {


                User p = new User();
                p.setNom(txtnom.getText());
                p.setPrenom(txtprenom.getText());
                p.setTel(Integer.parseInt(txttel.getText()));
                p.setRole(role);
                p.setEmail(txtemail.getText());
                p.setMdp(txtmdp.getText());
                p.setImage(imageData);
                us.add(p);



                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("utilisateur ajouté!");
                alert.show();
            }

            catch (SQLException ex) {
                System.out.println("error" + ex.getMessage());
            }

        }
    }



    @FXML
    private void annuler(ActionEvent event) {


        txtnom.setText("");
        txtprenom.setText("");
        txtemail.setText("");
        txttel.setText("");
        txtmdp.setText("");
    }

    @FXML
    private void onUploadButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            imageData = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 147, 89, false, true);
            imageFor.setImage(image);
        }
    }

    @FXML
    void importImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            imageData = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 147, 89, false, true);
            imageFor.setImage(image);
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
//
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
//
//
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Se connecter");
        stage.setScene(scene);
        stage.show();
//
//       }


    }

    @FXML
    private void seConnecter(ActionEvent event) throws IOException {
//
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
//
//
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Se connecter");
        stage.setScene(scene);
        stage.show();
//
//       }


    }


}
