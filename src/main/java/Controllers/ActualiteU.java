package Controllers;


import Entites.Actualite;
import Services.ActualiteService;
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

public class ActualiteU implements Initializable {

    @FXML
    private GridPane gridFor;

    private final ActualiteService actualiteService = new ActualiteService();
    private ObservableList<Actualite> actualites = FXCollections.observableArrayList();


    public ObservableList<Actualite> getAllActualiteCard() throws SQLException {
        actualites.addAll(actualiteService.findAll());
        return actualites;
    }
    @FXML
    private AnchorPane feed1;

    int row = 0;
    int column = 0;

    public void displayAllActualiteCard() throws SQLException {
        actualites.clear();
        actualites.addAll(actualiteService.findAll());

        gridFor.getRowConstraints().clear();
        gridFor.getColumnConstraints().clear();
        for (int q = 0; q < actualites.size(); q++) {
            AnchorPane anchorpane = new AnchorPane();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ActualiteCard.fxml")); // Set the location of the FXML file
                AnchorPane pane = loader.load();
                ActualiteCardController forCard = loader.getController();
                forCard.setData(actualites.get(q));


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
            displayAllActualiteCard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}