package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import Entites.Formation;
import Entites.Oeuvre;
import Services.FormationService;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;

public class AccueilPageController implements Initializable {

    @FXML
    private Menu Accueil;

    @FXML
    private Menu Artistes;

    @FXML
    private GridPane gridOe;

    @FXML
    private Menu Concours;

    @FXML
    private Menu Oeuvres;

    @FXML
    private AnchorPane anchCol;

    @FXML
    private AnchorPane anchFor;

    @FXML
    private GridPane gridFor;

    @FXML
    private AnchorPane anchOe;

    @FXML
    private MenuItem sac;


    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvres = FXCollections.observableArrayList();

    private final FormationService formationService = new FormationService();
    private ObservableList<Formation> formationList = FXCollections.observableArrayList();

    @FXML
    void Accueil(ActionEvent event) {

    }

    @FXML
    void Concours(ActionEvent event) {

    }

    @FXML
    void Formations(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../InscriptionFormation.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Liste Artiste");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    void artistes(ActionEvent event) {

    }

    @FXML
    void connect(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void inscrire(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ajouterPersonnes.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @FXML
    void oeuvres(ActionEvent event) {

    }
    @FXML
    void plusOev(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherAllOeuvre.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Liste Artiste");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
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
    public void displayOeuvresCards(ObservableList<Oeuvre> oeuvres) {
        gridOe.getRowConstraints().clear();
        gridOe.getColumnConstraints().clear();
        int row = 0;
        int column = 0;
        for (Oeuvre oeuvre : oeuvres) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../OeuvreCard.fxml"));
                AnchorPane pane = loader.load();
                OeuvreCardController oeCard = loader.getController();
                oeCard.setData(oeuvre);
                oeCard.setOeuvre(oeuvre);
                gridOe.addRow(row);
                gridOe.add(pane, column, row);

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
    public ObservableList<Formation> getAllFormationCard() throws SQLException {
        formationList.addAll(formationService.findAll());
        return formationList;
    }
    public ObservableList<Oeuvre> getAllOeuvresCard() throws SQLException {
        this.oeuvres.addAll(oeuvreService.findAll());
        System.out.println("addded"+this.oeuvres);
        return this.oeuvres;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("thissss oee"+this.oeuvres);
            getAllFormationCard();
            getAllOeuvresCard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayFormationCards(formationList);
        displayOeuvresCards(this.oeuvres);
    }
}
