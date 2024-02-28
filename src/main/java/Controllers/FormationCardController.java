package Controllers;

import Entites.Formation;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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

    private ObservableList<Formation> formationList = FXCollections.observableArrayList();

    @FXML
    void inscrire(ActionEvent event) {

    }

    private Image image;
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

}
