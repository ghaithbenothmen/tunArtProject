package Controllers;

import Entites.Formation;
import Entites.Oeuvre;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchOeuvre;

    @FXML
    private Button update;





    @FXML
    void ajouterOeuvre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterOeuvre.fxml"));
            Parent root = loader.load();
            AjouterOeuvreControler controller = loader.getController();
            controller.setParentController(this); // Pass the current controller to the AddFormationController
            Stage stage = new Stage();
            stage.setTitle("Add Oeuvre");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    public void refreshScrollPane() throws SQLException {
        grid.getChildren().clear();
        getAllOeuvreCard();
//       oeuvreList.addAll(oeuvreService.findAll());

    }

    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();

    public ObservableList<Oeuvre> getAllOeuvreCard() throws SQLException {
        oeuvreList.addAll(oeuvreService.findAll());
        return oeuvreList;
    }
    int row = 0;
    int column=0;

    public void displayAllOeuvreCard(ObservableList<Oeuvre> oeuvreList) {
//        oeuvreList.clear();
//        oeuvreList.addAll(oeuvreService.findAll());

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
                System.out.println(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllOeuvreCard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        displayAllOeuvreCard(oeuvreList);
        searchOeuvre.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);

        });

    }
    private void filterData(String searchText)  {
        ObservableList<Oeuvre> filteredList = FXCollections.observableArrayList();
        for (Oeuvre oeuvre : oeuvreList) {
            if (oeuvre.getNom_Ouvre().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(oeuvre);
            }
        }
        grid.getChildren().clear();
        displayAllOeuvreCard(filteredList);
    }


}
