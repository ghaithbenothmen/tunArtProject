package Controllers;

import Entites.Actualite;
import Services.ActualiteService;
import Services.IService;
import Test.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ModifierActualiteController {
   private final ActualiteService cs = new ActualiteService();

    @FXML
    private TextField TitreTF;
    @FXML
    private TextArea ActualiteTA;
    @FXML
    private DatePicker DateDP;
    @FXML
    private String imagePath;
    @FXML
    private Label inputcontrol;
    @FXML

    int idModifier =0 ;
    public void refresh(List<Actualite>actualiteListList){
        int column=0;
        int row=1;
        for(Actualite a:actualiteListList){
            FXMLLoader card=new FXMLLoader(Main.class.getResource("competition-card-view.fxml"));
            try{
                AnchorPane anchorPane=card.load();
                ActualiteCardController item=card.getController();
                item.remplireData(a);
                item.setOnChangeListener((IService) this);
                if(column==2){
                    column=0;
                    row++;
                }
            }catch (IOException e){
                System.out.println("Erreur:"+e.getMessage());
            }

        }
    }

    @FXML
    void modifierCompetition(ActionEvent event) {
        if(idModifier!=0){
            String erreurMessage = validerChamps();
            if (!erreurMessage.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Validation", erreurMessage);
            }
            else{
                Actualite c=new Actualite();
                c.setId(idModifier);
                c.setTitre(TitreTF.getText());
                c.setText(ActualiteTA.getText());
                c.setDate(Date.valueOf(DateDP.getValue()));
                cs.modifier(c);
                refresh(cs.afficher());
            }

        }

    }
    private String validerChamps() {
        String erreurs = "";
        if (TitreTF.getText().trim().isEmpty()) {
            erreurs+=("Nom est requis.\n");
        }
        if (ActualiteTA.getText().trim().isEmpty()) {
            erreurs+=("Description est requise.\n");
        }
        return erreurs;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    @FXML
    void choisirImage(ActionEvent event) {
        try {
            // Créer un FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.jpeg"));

            // Ouvrir le FileChooser
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // Obtenir le chemin de l'image
                imagePath = file.getPath().replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffActualiteAdmin.fxml"));
            DateDP.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }




}
