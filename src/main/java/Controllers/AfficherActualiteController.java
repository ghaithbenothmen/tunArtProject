package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AfficherActualiteController {
    private final ActualiteService cs = new ActualiteService();

    @FXML
    private TableColumn<Actualite, Integer> UserCol ;

    @FXML
    private TableColumn<Actualite, String> ActualiteCol ;
    @FXML
    private TableColumn<Actualite, String> DateCol;
    @FXML
    private TableView<Actualite> TableView;
    @FXML
    void ondelete() {
        Actualite selectedActualite = TableView.getSelectionModel().getSelectedItem();
        if (selectedActualite != null) {
            cs.delete(selectedActualite);
            TableView.getItems().remove(selectedActualite);
        } else {
            // Handle case where no actualite is selected
            // You might want to show an error message or provide feedback to the user
        }
    }

    @FXML
    void initialize() {

        List<Actualite> cours = cs.findAll();
        ObservableList<Actualite> observableList = FXCollections.observableList(cours);
        TableView.setItems(observableList);
        ActualiteCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    }
}
