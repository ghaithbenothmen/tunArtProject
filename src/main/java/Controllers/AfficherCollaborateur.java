package Controllers;

import Entites.Collaborateur;
import Entites.User;
import Services.CollaborateurService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherCollaborateur {

    @FXML
    private TextField cher;

    @FXML
    private TableColumn<?, ?> emailcol;

    @FXML
    private TableColumn<?, ?> nomcol;

    @FXML
    private TableView<Collaborateur> collabTable;


    private  final CollaborateurService collaborateurService = new CollaborateurService();
    private ObservableList<Collaborateur> collaborateurList = FXCollections.observableArrayList();
    @FXML
    public void initialize() throws SQLException {
        collaborateurList.addAll(collaborateurService.findAll());
        System.out.println(collaborateurList);
        nomcol.setCellValueFactory(new PropertyValueFactory<>("NomComplet"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));



        collabTable.setItems(collaborateurList);
        System.out.println(collaborateurList);

        refreshTable();


        //search
        cher.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });



    }

    public void refreshTable() throws SQLException {
        collabTable.getItems().clear();
        collabTable.getItems().addAll(collaborateurService.findAll());
    }
    private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            collabTable.setItems(collaborateurList);
        } else {
            ObservableList<Collaborateur> filteredList = collaborateurList.filtered(
                    collaborateur -> collaborateur.getNomComplet().toLowerCase().contains(searchText.toLowerCase())
            );
            collabTable.setItems(filteredList);
        }
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Collaborateur.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ajouter collaborateur");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteCollaborateur(ActionEvent event) {
        Collaborateur selectedCollaborateur = collabTable.getSelectionModel().getSelectedItem();
        if (selectedCollaborateur != null) {
            try {
                collaborateurService.delete(selectedCollaborateur);
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }


    }

    @FXML
    void updateCollaborateur(ActionEvent event) {
        Collaborateur selectedCollaborateur = collabTable.getSelectionModel().getSelectedItem();
        if (selectedCollaborateur != null){
            openUpdateUserPage(selectedCollaborateur);
        }

    }

    private void openUpdateUserPage(Collaborateur collaborateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../updateCollaborateur.fxml"));
            Parent root = loader.load();
            updateCollaborateurController controller = loader.getController();
            controller.initData(collaborateur);
            controller.setAfficherCollaborateur(this);
            Stage stage = new Stage();
            stage.setTitle("Modifier collaborateur");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}