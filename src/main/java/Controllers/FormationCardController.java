package Controllers;

import Entites.Formation;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class FormationCardController {
    private final FormationService formationService = new FormationService();

    @FXML
    private Label niveau;

    @FXML
    private Label nomArtiste;

    @FXML
    private Label nomCat;

    @FXML
    private Label nomFor;
    @FXML
    private ImageView imageFor;
    private Image image;
    private Formation formation;
    private ObservableList<Formation> formationList = FXCollections.observableArrayList();


    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    public void setData(Formation formation){
        nomFor.setText(formation.getNom());
        niveau.setText(formation.getNiveau().toString());
        nomCat.setText((formation.getCat_id().getNom()));

        String path = formation.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
            imageFor.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void inscrire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../InscriptionInfo.fxml"));
            AnchorPane pane = loader.load();
            InscriptionInfoController infoController = loader.getController();
            infoController.setData(formation);

            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
