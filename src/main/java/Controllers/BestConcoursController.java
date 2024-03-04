package Controllers;

import Entites.Concours;
import Entites.Type;
import Services.ServiceVotes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Date;

import static Entites.Type.MUSIC;
import static Entites.Type.valueOf;

public class BestConcoursController {



    @FXML
    private Button best;

    @FXML
    private Label label;

    private final ServiceVotes serV=new ServiceVotes();

    public void setLbname(String lbname) {
        this.label.setText(lbname);
    }

    @FXML
    void TapOnBest(ActionEvent event) {
        Concours c = new Concours(43,50,new Date(2024,03,30), Type.PHOTOGRAPHY,"hednhehd","PHOTOGRAPHIA");
        int t=serV.BestConcours(c);
        System.out.println(t);
    }
}
