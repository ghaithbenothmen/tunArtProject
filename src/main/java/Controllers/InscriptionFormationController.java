package Controllers;

import Entites.Formation;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InscriptionFormationController  implements Initializable {

    @FXML
    private GridPane gridFor;

    private final FormationService formationService = new FormationService();
    private ObservableList<Formation> formationList = FXCollections.observableArrayList();


    public ObservableList<Formation> getAllFormationCard() throws SQLException {
        formationList.addAll(formationService.findAll());
        return formationList;
    }

    int row = 0;
    int column = 0;

    public void displayAllFormationCard() throws SQLException {
        formationList.clear();
        formationList.addAll(formationService.findAll());

        gridFor.getRowConstraints().clear();
        gridFor.getColumnConstraints().clear();
        for (int q = 0; q < formationList.size(); q++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FormationCard.fxml")); // Set the location of the FXML file
                AnchorPane pane = loader.load();
                FormationCardController forCard = loader.getController();
                forCard.setData(formationList.get(q));

                gridFor.addRow(row); // Add a new row if needed
                gridFor.add(pane, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row += 1;
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            displayAllFormationCard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}