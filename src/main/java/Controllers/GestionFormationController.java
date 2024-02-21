package Controllers;

import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        formationList.addAll(formationService.findAll());
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
        FormationTable.setItems(formationList);

        refreshTable();


        searchFor.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
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
    @FXML
    void ajouterFor(ActionEvent event) {

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

    }

    private void refreshTable() throws SQLException {
        FormationTable.getItems().clear();
        FormationTable.getItems().addAll(formationService.findAll());
    }
}
