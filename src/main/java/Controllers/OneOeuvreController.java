package Controllers;

import Entites.Oeuvre;
import Entites.TypeOeuvre;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.security.auth.callback.ConfirmationCallback;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class OneOeuvreController {

    @FXML
    private Label Type;

    @FXML
    private Label Type1;

    @FXML
    private Button delete;

    @FXML
    private Label desc;

    @FXML
    private Label nom_Oeuvre;

    @FXML
    private Button update;

    @FXML
    private ImageView img;

    private final OeuvreService oeuvreService = new OeuvreService();
    private Oeuvre oeuvre;
    private OneOeuvreController oneOeuvreController;
    private AfficherController afficherController ;


//    public void setAfficherController(AfficherController afficherController) {
//        this.afficherController = afficherController;
//    }

    @FXML
    void ModifierOeuvre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierOeuvre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Update Oeuvre");
            stage.setScene(scene);
            stage.show();
            ModifierOeuvreControler controler = loader.getController();
            controler.initData(oeuvre);
            //stage.close();


//            parentController.refreshScrollPane();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void getCard(Oeuvre oeuvre){
//        oeuvre=oeuvre;
//        nom_Oeuvre.setText(oeuvre.getNom_Ouvre());
//        Type.setText(oeuvre.getTypeOeuvre().toString());
//        desc.setText(oeuvre.getDescription());
//        File imageFile = new File(oeuvre.getImg());
//        Image image1= new Image(imageFile.toURI().toString());
//
//        img.setImage(image1);
//    }

    @FXML
    void delete(ActionEvent event) throws SQLException {

        try{
            oeuvreService.delete(this.oeuvre);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Oeuvre supprimer", "L'oeuvre a été supprimer avec succès.");
            Stage Currentstage = (Stage) nom_Oeuvre.getScene().getWindow();
            Currentstage.close();
//            afficherController.refreshScrollPane();*

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
            Parent root = loader.load();
            afficherController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Afficher Oeuvre");
            stage.setScene(new Scene(root));
            stage.show();
            
        }catch (SQLException e){
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Image image;
    public void setData(Oeuvre oeuvre){
        nom_Oeuvre.setText(oeuvre.getNom_Ouvre());
        Type.setText(oeuvre.getTypeOeuvre().toString());
        desc.setText(oeuvre.getDescription());

        String path = oeuvre.getImg();
        try {
            image = new Image(new File(path).toURI().toURL().toString(),207,138,false,true);
            img.setImage(image);
            this.oeuvre=oeuvre;
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOeuvre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
