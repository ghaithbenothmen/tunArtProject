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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

    @FXML
    private Button modifier;

    @FXML
    private Button reset;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private ComboBox txtrole;

    @FXML
    private TextField txttel;

    @FXML
    private Button upload;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane main_form;




    private User user;

    private  String imagePath;
    private Image imageGet;
    private Image imageU;
    private String img;


    UserService userService = new UserService();

    AfficherUserController afficherUserController = new AfficherUserController();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> roleNames = FXCollections.observableArrayList("ARTISTE", "CLIENT");
        txtrole.setItems(roleNames);
        txtrole.setValue("ARTISTE"); // Définir la valeur par défaut

        txtrole.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Role role = Role.valueOf(String.valueOf(newValue));
                System.out.println("Selected role: " + role);
            }
        });
    }


    @FXML
    void updateUser(ActionEvent event) {

        String nom = txtnom.getText();
        String prenom = txtprenom.getText();
        String email = txtemail.getText();
        int tel = Integer.parseInt(txttel.getText());
        Role role = ((Role) txtrole.getSelectionModel().getSelectedItem());
        String image = upload.getText();





        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setNom(nom);
        updatedUser.setPrenom(prenom);
        updatedUser.setEmail(email);
        updatedUser.setTel(tel);
        updatedUser.setRole(role);
        updatedUser.setImage(img);


        try {
            userService.update(updatedUser);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "User mise à jour", "L'utilisateur' a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

            //refreshi tab
            afficherUserController.refreshTable();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", "Une erreur s'est produite lors de la mise à jour : " + e.getMessage());
        }
    }


    public void setAfficherUserController(AfficherUserController afficherUserController) {
        this.afficherUserController = afficherUserController;
    }


    @FXML
    void resetForm(ActionEvent event) {
        txtnom.setText("");
        txtprenom.setText("");
        txtemail.setText("");
        txttel.setText("");
        upload.setText("");
    }


    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initData(User user) throws SQLException {
        this.user = user;
        txtnom.setText(user.getNom());
        txtprenom.setText(user.getPrenom());
        txtemail.setText(user.getEmail());
        txttel.setText(String.valueOf(user.getTel()));
        this.img = user.getImage();

        // Configuration des rôles ARTISTE et CLIENT uniquement
        ObservableList<String> roleNames = FXCollections.observableArrayList("ARTISTE", "CLIENT");
        txtrole.setItems(roleNames);
        txtrole.setValue(user.getRole());

        String path = user.getImage();
        if (path != null && !path.isEmpty()) { // Check if the image path is not null and not empty
            try {
                imageGet = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
                image.setImage(imageGet);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        // Affichage du chemin de l'image de l'utilisateur
        upload.setText(user.getImage());
    }


    @FXML
    void importImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(upload.getScene().getWindow());

        if (file != null) {

            img = file.getAbsolutePath();

        }
    }

    @FXML
    private void retour_login(MouseEvent event) throws IOException {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();






    }

}



