package controllers;


import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class AfficherEvenementController {

    @FXML
    private Label lbNom;

    public void setLbNom(String lbNom) {
        this.lbNom.setText(lbNom); }

    public Label getLbNom() {
        return lbNom;
    }
}
