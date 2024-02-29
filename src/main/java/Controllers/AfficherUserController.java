package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class AfficherUserController {


    @FXML
    private TableColumn<User, Integer> prenomuser;
    @FXML
    private GridPane grid;
    UserService us = new UserService();
    @FXML
    private TextField cher;

    @FXML
    private TableColumn<User, String> emailuser;


    @FXML
    private TableColumn<User,String> nomuser;

    @FXML
    private TableColumn<User, Role> roleuser;

    @FXML
    private TableColumn<User, Integer> teluser;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User,String> mdp;

    /**
     * Initializes the controller class.
     */


private  final UserService userService = new UserService();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    @FXML
    public void initialize() throws SQLException {
        userList.addAll(userService.findAll());
        System.out.println(userList);
        nomuser.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenomuser.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        emailuser.setCellValueFactory(new PropertyValueFactory<>("Email"));
        teluser.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        roleuser.setCellValueFactory(new PropertyValueFactory<>("Role"));
        mdp.setCellValueFactory(new PropertyValueFactory<>("Mdp"));


        userTable.setItems(userList);
        System.out.println(userList);

            refreshTable();


        //search
        cher.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });



    }









    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterPersonnes.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ajouter utilisateur");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private void retour(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }




    @FXML
    void deleteUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userService.delete(selectedUser);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }

    }

    @FXML
    void updateUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            openUpdateUserPage(selectedUser);
        }

    }

    private void openUpdateUserPage(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UpdateUser.fxml"));
            Parent root = loader.load();
            UpdateUserController controller = loader.getController();
            controller.initData(user);
            controller.setAfficherUserController(this);
            Stage stage = new Stage();
            stage.setTitle("Modifier utilisateurs");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void refreshTable() throws SQLException {
        userTable.getItems().clear();
        userTable.getItems().addAll(userService.findAll());
    }
    private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            userTable.setItems(userList);
        } else {
            ObservableList<User> filteredList = userList.filtered(
                    user -> user.getNom().toLowerCase().contains(searchText.toLowerCase())
            );
            userTable.setItems(filteredList);
        }
    }

    @FXML
    void resetForm(ActionEvent event) {

    }



}
