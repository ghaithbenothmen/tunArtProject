package Controllers;

import Entites.Billet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.text.ParseException;

import static Services.BilletService.serBillet;

public class ReserverBilletController {

    @FXML
    private TextField txtNomEvent;
    private float prixBillet;

    public TextField getTxtNomEvent() {return txtNomEvent;}

    public void setTxtNomEvent(TextField txtNomEvent) {this.txtNomEvent = txtNomEvent;}

    @FXML
    void ReserverBillet(ActionEvent event)throws ParseException {



            int idBillet = Integer.parseInt(txtNomEvent.getText());
           Billet b1 = new Billet(idBillet,prixBillet) ;


            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation ");
            alert1.setContentText("Billet réservé avec succés ");
            alert1.showAndWait();

            try { serBillet.add(b1);}
            catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait(); }
        }
    }
