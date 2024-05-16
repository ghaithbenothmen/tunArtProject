package Controllers;

import Entites.Commentaire;
import Services.CommentaireService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherCommentaireController {
    private final CommentaireService cs = new CommentaireService();

    @FXML
    private TableColumn<Commentaire, Integer> id_userCol ;

    @FXML
    private TableColumn<Commentaire, String> id_actCol ;
    @FXML
    private TableColumn<Commentaire, String> contenuCol;

    @FXML
    private TableView<Commentaire> TableView;
    @FXML
    private TableColumn PrenomCol ;
    @FXML
    private TableColumn TitreCol ;

    @FXML
    private TableColumn<Commentaire, Date> dateCol;

    @FXML
    private Button retour;
    @FXML
    void ondelete() throws SQLException {
        Commentaire selectedActualite = TableView.getSelectionModel().getSelectedItem();
        if (selectedActualite != null) {
            cs.supprimer(selectedActualite);
            TableView.getItems().remove(selectedActualite);
        } else {
            // Handle case where no actualite is selected
            // You might want to show an error message or provide feedback to the user
        }
    }

    @FXML
    void initialize() throws SQLException {
        List<Commentaire> commentaire = cs.afficher();
        ObservableList<Commentaire> observableList = FXCollections.observableList(commentaire);
        TableView.setItems(observableList);

        // Set cell value factories for each column
        PrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        TitreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenuc"));
    }
    @FXML
    void naviguezVersPagePrecedent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffActualiteAdmin.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}