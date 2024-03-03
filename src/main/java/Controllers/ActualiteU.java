package Controllers;


import Entites.Actualite;
import Services.ActualiteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ActualiteU implements Initializable {

    @FXML
    private GridPane gridFor;
    @FXML
    private Button BtnChat;

    @FXML
    private ComboBox<String> languageComboBox;

/*    public static final String ACCOUNT_SID = "ACbe7739e913f726ee5ea8caca45b7843a";
    public static final String AUTH_TOKEN = "4d3f09669e1c0de3cc5f98a5cceead10";*/

    private final ActualiteService actualiteService = new ActualiteService();
    private ObservableList<Actualite> actualites = FXCollections.observableArrayList();


    public ObservableList<Actualite> getAllActualiteCard() throws SQLException {
        actualites.addAll(actualiteService.afficher());
        return actualites;
    }
    @FXML
    private AnchorPane feed1;

    int row = 0;
    int column = 0;

    public void displayAllActualiteCard() throws SQLException {
        //translate:
        languageComboBox.getItems().addAll("fr", "en", "ar");
        languageComboBox.getSelectionModel().selectFirst();
        //end
        actualites.clear();
        actualites.addAll(actualiteService.afficher());

        gridFor.getRowConstraints().clear();
        gridFor.getColumnConstraints().clear();
        for (int q = 0; q < actualites.size(); q++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ActualiteCardUser.fxml")); // Set the location of the FXML file
                AnchorPane pane = loader.load();
                Controllers.ActualiteCardController forCard = loader.getController();
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
    @FXML
    void naviguezVersChat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ChatBot.fxml"));
            BtnChat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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