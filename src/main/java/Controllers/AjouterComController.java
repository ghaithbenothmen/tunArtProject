package Controllers;

import Entites.Actualite;
import Entites.Commentaire;
import Entites.User;
import Services.ActualiteService;
import Services.CommentaireService;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterComController {
    @FXML
    private TextArea ContenuTA;
    @FXML
    private TableColumn PrenomCol ;
    @FXML
    private TableColumn TitreCol ;
    @FXML
    private Button RetourBtn;
    @FXML
    private TableColumn<Commentaire, String> contenuCol;
    @FXML
    private javafx.scene.control.TableView<Commentaire> TableView;

    @FXML
    private DatePicker DateDP;
    private final CommentaireService ser = new CommentaireService();
    private int idAct;
    private int idUser;

    @FXML
    void AjouterCom(ActionEvent event) {
        try {
            System.out.println("zezzrezrz"+this.idAct);
            System.out.println("sdfsd"+this.idUser);
            String contenuC = ContenuTA.getText();
            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(this.idAct);
            UserService userService = new UserService();
            User user = userService.findById(this.idUser);

            Commentaire c =new Commentaire (actualite,user,contenuC);
            //Commentaire c = new Commentaire(this.idAct,this.idUser,contenuC);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Commentaire ajouté avec succès");
            alert1.showAndWait();
            ser.ajouter(c);
            initialize();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setActualite(int idAct,int idUser) {
        this.idAct = idAct;
        this.idUser = idUser;
    }

    @FXML
    void NavigezVersListActU(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffActualiteUser.fxml"));
            RetourBtn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() throws SQLException {
        List<Commentaire> commentaire = ser.afficher();
        ObservableList<Commentaire> observableList = FXCollections.observableList(commentaire);
        TableView.setItems(observableList);

        // Set cell value factories for each column
        PrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        TitreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenuC"));
    }


}
