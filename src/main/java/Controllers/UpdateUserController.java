package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateUserController {

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


    private User user;

    UserService userService = new UserService();

    AfficherUserController afficherUserController = new AfficherUserController();

    @FXML
    void updateUser(ActionEvent event) {

        String nom = txtnom.getText();
        String prenom = txtprenom.getText();
        String email = txtemail.getText();
        int tel = Integer.parseInt(txttel.getText());
        Role role = ((Role) txtrole.getSelectionModel().getSelectedItem());
        //String image = upload.getText();





        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setNom(nom);
        updatedUser.setPrenom(prenom);
        updatedUser.setEmail(email);
        updatedUser.setTel(tel);
        updatedUser.setRole(role);
        //updatedUser.setImage(image);


        try {
            userService.update(updatedUser);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "User mise à jour", "L'utilisateur' a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

            //refreshi tab
            afficherUserController.refreshTable();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", "Une erreur s'est produite lors de la mise à jour de la formation : " + e.getMessage());
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


        ObservableList<String> roleNames = FXCollections.observableArrayList();
        for (Role role : Role.values()) {
            roleNames.add(role.toString());
        }
        txtrole.setItems(roleNames);

        ObservableList<Role> roleList = FXCollections.observableArrayList(Role.values());
        txtrole.setItems(roleList);
        txtrole.setValue(user.getRole());


        upload.setText(user.getImage());
    }
}



