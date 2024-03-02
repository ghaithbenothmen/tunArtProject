package Controllers;

import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class GestionFormationController {

    private final FormationService formationService = new FormationService();

    @FXML
    private TableView<Formation> FormationTable;

    @FXML
    private TableColumn<Formation, Categorie> cat;

    @FXML
    private TableColumn<Formation, Date> datedeb;

    @FXML
    private TableColumn<Formation, Date> datefin;

    @FXML
    private TableColumn<Formation, String> desc;

    @FXML
    private TableColumn<Formation, Integer> idfor;

    @FXML
    private TableColumn<Categorie, Niveau> niveau;

    @FXML
    private TableColumn<Formation, String> nomfor;

    @FXML
    private TextField searchFor;

    private ObservableList<Formation> formationList = FXCollections.observableArrayList();
    @FXML
    public void initialize() throws SQLException {
        //formationList.addAll(formationService.findAll());
        idfor.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomfor.setCellValueFactory(new PropertyValueFactory<>("nom"));
        datedeb.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        datefin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        cat.setCellValueFactory(new PropertyValueFactory<>("cat_id")); //naamlo get l nom mtaa objet brk moch objet kemel
        cat.setCellFactory(column -> {
            return new TableCell<Formation, Categorie>() {
                @Override
                protected void updateItem(Categorie item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getNom()); // Assuming getNom() returns the name of the category
                    }
                }
            };
        });
       // FormationTable.setItems(formationList);

        refreshTable();
        //search
        searchFor.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });

        FormationTable.setRowFactory(tv -> {
            TableRow<Formation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Formation formation = row.getItem();
                    openUpdateFormationPage(formation);
                }
            });
            return row;
        });
    }
    private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            FormationTable.setItems(formationList);
        } else {
            ObservableList<Formation> filteredList = formationList.filtered(
                    formation -> formation.getNom().toLowerCase().contains(searchText.toLowerCase())
            );
            FormationTable.setItems(filteredList);
        }
    }

    private void openUpdateFormationPage(Formation formation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UpdateFormation.fxml"));
            Parent root = loader.load();
            UpdateFormationController controller = loader.getController();
            controller.initData(formation);

            controller.setGestionFormationController(this); // Pass a reference to GestionFormationController
            Stage stage = new Stage();
            stage.setTitle("Update Formation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void ajouterFor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterFormation.fxml"));
            Parent root = loader.load();
            AjouterFormationController controller = loader.getController();
            controller.setParentController(this); // Pass the current controller to the AddFormationController
            Stage stage = new Stage();
            stage.setTitle("Add Formation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteFor(ActionEvent event) {
        Formation selectedFormation = FormationTable.getSelectionModel().getSelectedItem();
        if (selectedFormation != null) {
            try {
                formationService.delete(selectedFormation);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void updateFor(ActionEvent event) {
        Formation selectedFormation = FormationTable.getSelectionModel().getSelectedItem();
        if (selectedFormation != null) {

            openUpdateFormationPage(selectedFormation);
        }
    }

    public void refreshTable() throws SQLException {
        FormationTable.getItems().clear();
        FormationTable.getItems().addAll(formationService.findAll());
    }

    public void initData(int userId) throws SQLException {
        FormationTable.getItems().clear();
        // Retrieve formations for the given user_id
        formationList.addAll(formationService.findByUserId(userId));
        // Populate the table with the formations
        FormationTable.setItems(formationList);
    }
}
