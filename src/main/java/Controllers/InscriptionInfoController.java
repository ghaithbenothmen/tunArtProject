package Controllers;


import Entites.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;


public class InscriptionInfoController {

    @FXML
    private Label dateD;

    @FXML
    private Label dateF;

    @FXML
    private Label desc;

    @FXML
    private ImageView imageFor;

    @FXML
    private Label niveau;

    @FXML
    private Label nomCat;

    @FXML
    private Label nomFor;

    private Image image;
    @FXML
    void confirmerInsc(ActionEvent event) {

    }


    public void setData(Formation formation){
        nomFor.setText(formation.getNom());
        niveau.setText(formation.getNiveau().toString());
        nomCat.setText((formation.getCat_id().getNom()));
        desc.setText(formation.getDescription());
        dateD.setText(formation.getDateDebut().toString());
        dateF.setText(formation.getDateFin().toString());

        String path = formation.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 366, 366, false, true);
            imageFor.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
