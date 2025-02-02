package Controllers;

import Entites.Oeuvre;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAllOeuvreController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private GridPane grid;

    public void displayAllOeuvreCard(List<Oeuvre> oeuvreList) {
//        oeuvreList.clear();
//        oeuvreList.addAll(oeuvreService.findAll());
        System.out.println("hello new0"+oeuvreList);
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
        int row = 0;
        int column = 0;

        for(Oeuvre oeuvre : oeuvreList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../OeuvreCard.fxml"));
                AnchorPane pane = loader.load();
                OeuvreCardController OCard = loader.getController();
                OCard.setData(oeuvre);
                OCard.setOeuvre(oeuvre);
                grid.addRow(row);
                grid.add(pane, column, row);

                column++;
                if(column == 3){
                    column = 0;
                    row +=1;
                }
            } catch (Exception e){
                System.out.println(""+e);
            }
        }
    }
    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();
    public ObservableList<Oeuvre> getAllOeuvreCard() throws SQLException {

        oeuvreList.addAll(oeuvreService.findAll());
        return oeuvreList;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            getAllOeuvreCard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayAllOeuvreCard(oeuvreList);


    }

}