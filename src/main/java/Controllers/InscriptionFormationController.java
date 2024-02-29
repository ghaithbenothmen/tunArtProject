package Controllers;

import Entites.Formation;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InscriptionFormationController  implements Initializable {

    @FXML
    private GridPane gridFor;
    @FXML
    private TextField searchFor;
    private final FormationService formationService = new FormationService();
    private ObservableList<Formation> formationList = FXCollections.observableArrayList();



    public ObservableList<Formation> getAllFormationCard() throws SQLException {
        formationList.addAll(formationService.findAll());
        return formationList;
    }

    int row = 0;
    int column = 0;

    public void displayFormationCards(ObservableList<Formation> formationList) {
        gridFor.getRowConstraints().clear();
        gridFor.getColumnConstraints().clear();
        int row = 0;
        int column = 0;
        for (Formation formation : formationList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FormationCard.fxml"));
                AnchorPane pane = loader.load();
                FormationCardController forCard = loader.getController();
                forCard.setData(formation);
                forCard.setFormation(formation);
                gridFor.addRow(row);
                gridFor.add(pane, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllFormationCard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayFormationCards(formationList);
        searchFor.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });

    }
    private void filterData(String searchText) {
        ObservableList<Formation> filteredList = FXCollections.observableArrayList();
        for (Formation formation : formationList) {
            if (formation.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(formation);
            }
        }
        gridFor.getChildren().clear();
        displayFormationCards(filteredList);
    }
}