package Controllers;

import Entites.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Entites.Concours;
import Services.ServiceConcours;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateConcoursController implements Initializable {

    @FXML
    private ChoiceBox<Type> ChoiceType;

    @FXML
    private Button afficher;

    @FXML
    private Label concoursID;

    @FXML
    private DatePicker dateinput;

    @FXML
    private Button confirm;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtlien;

    @FXML
    private TextField txtprix;

    private final ServiceConcours ser=new ServiceConcours();

    public Label getLbname() {
        return concoursID;
    }
    public void setLbname(String lbname) {
        this.concoursID.setText(lbname);
    }

    public void setTxtnom(String lbname) {
        this.txtnom.setText(lbname);
    }
    public void setTxtprix(Integer lbname) {
        this.txtprix.setText(String.valueOf(lbname));
    }
    public void setTxtlien(String lbname) {
        this.txtlien.setText(lbname);
    }
    public void setChoiceType(Type lbname) {
        this.ChoiceType.setValue(lbname);
    }
    public void setDateinput(String lbname) {
        this.dateinput.setValue(LocalDate.parse(lbname));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceType.getItems().addAll(Type.values());
    }

    @FXML
    void UpdateConcours(ActionEvent event) {

        //DATE

        //date initialize
        dateinput.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Date is picked, do something
                System.out.println("DateF value: " + newValue);
            } else {
                // Date is not picked
                System.out.println("DateF not picked");
            }
        });


        //date input control
        LocalDate today = LocalDate.now();
        LocalDate dateFin = dateinput.getValue();
        java.sql.Date sqlDateFin= java.sql.Date.valueOf(dateFin);
        if(dateFin.isBefore(today))
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Date incorrecte");
            alert1.showAndWait();
            return;
        }



        /* old date form with string
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf((txtdate.getText()));
        } catch (IllegalArgumentException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Date incorrecte");
            alert1.showAndWait();
            return;
        }*/
        
        
        int prix=Integer.parseInt(txtprix.getText());
        Type type= (Type) ChoiceType.getValue();
        String lien=txtlien.getText();
        String nom=txtnom.getText();
        int ref= Integer.parseInt(concoursID.getText());

        Concours p1=new Concours(ref,prix,sqlDateFin,type,lien,nom);
        if (prix<=0||type.describeConstable().isEmpty()||lien.isEmpty()||nom.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setContentText("Erreur de saisie");
            alert1.showAndWait();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Concour modifié avec succés");
            alert1.showAndWait();

            ser.updatea(p1);

            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
                Parent root=loader.load();

                confirm.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void afficherConcours(ActionEvent event) {

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
            Parent root=loader.load();

            afficher.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
