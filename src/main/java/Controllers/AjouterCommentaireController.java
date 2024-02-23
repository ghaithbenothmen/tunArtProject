package Controllers;

import Entites.Commentaire;
import Services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AjouterCommentaireController {
    @FXML
    private TextArea ContenuTA;

    @FXML
    private DatePicker DateDP;
    private final CommentaireService ser = new CommentaireService();
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    @FXML
    void AjouterCommentaire(ActionEvent event) {
        try {

            String contenuC = ContenuTA.getText();
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date dateC = dateFormat.parse(String.valueOf(DateDP.getTooltip()));*/
            LocalDate datedb=DateDP.getValue();
            Date dateC = convertToDate(datedb);
            /*// Récupérer la date sélectionnée du DatePicker
            LocalDate localDate = DateDP.getValue();
            // Convertir LocalDate en Date
            Date dateC = java.sql.Date.valueOf(localDate);*/
            Commentaire c1 = new Commentaire( contenuC, dateC);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Commentaire ajouté avec succès");
            alert1.showAndWait();

            ser.add(c1);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
