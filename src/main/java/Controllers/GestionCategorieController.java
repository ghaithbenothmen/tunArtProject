package Controllers;

import Entites.Categorie;
import Services.CategorieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;


public class GestionCategorieController {

    @FXML
    private TableView<Categorie> categorieTable;

    @FXML
    private TableColumn<Categorie, Integer> idcat;

    @FXML
    private TableColumn<Categorie, String> nomcat;

    @FXML
    private TextField searchCat;

    @FXML
    private TextField txtcat;

    private final CategorieService categorieService = new CategorieService();

    private ObservableList<Categorie> categorieList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        categorieList.addAll(categorieService.findAll());
        idcat.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcat.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorieTable.setItems(categorieList);

        refreshTable();
        categorieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtcat.setText(newSelection.getNom());
            }
        });

        searchCat.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });
    }
    private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            categorieTable.setItems(categorieList);
        } else {
            ObservableList<Categorie> filteredList = categorieList.filtered(
                    categorie -> categorie.getNom().toLowerCase().contains(searchText.toLowerCase())
            );
            categorieTable.setItems(filteredList);
        }
    }
    @FXML
    void ajouterCat(ActionEvent event) {
        String nom = txtcat.getText();
        if (nom.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nom de catégorie.");
            return;
        }

        if (nom.length() > 255) {
            showAlert("Erreur", "Le nom de la catégorie ne peut pas dépasser 255 caractères.");
            return;
        }

        if (!nom.isEmpty()) {
            Categorie categorie = new Categorie(nom);
            try {
                categorieService.add(categorie);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }


    @FXML
    void deleteCat(ActionEvent event) {
        Categorie selectedCategorie = categorieTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                categorieService.delete(selectedCategorie);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        txtcat.setText("");
    }

    @FXML
    void updateCat(ActionEvent event) {
        Categorie selectedCategorie = categorieTable.getSelectionModel().getSelectedItem();
        String newNom = txtcat.getText();
        if (selectedCategorie != null && !newNom.isEmpty()) {

            selectedCategorie.setNom(newNom);
            System.out.println(selectedCategorie);
            try {

                categorieService.update(selectedCategorie);

                refreshTable();

                txtcat.clear();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }

    private void refreshTable() throws SQLException {
        categorieTable.getItems().clear();
        categorieTable.getItems().addAll(categorieService.findAll());
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
