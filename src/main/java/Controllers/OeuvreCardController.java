package Controllers;

import Entites.Oeuvre;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class OeuvreCardController {

    private Oeuvre o ;

    @FXML
    private Label Type;

    @FXML
    private ImageView img;

    @FXML
    private Label nomArtiste;

    @FXML
    private Label nom_Oeuvre;

    @FXML
    void openpage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../OneOeuvre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            OneOeuvreController oneOeuvreController=loader.getController();
            oneOeuvreController.setData(o);
            stage.setTitle("One Oeuvre");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();


    private Image image;
    public void setData(Oeuvre oeuvre){
        nom_Oeuvre.setText(oeuvre.getNom_Ouvre());
        Type.setText(oeuvre.getTypeOeuvre().toString());


        String path = oeuvre.getImg();

        try {
            image = new Image(new File(path).toURI().toURL().toString(),207,138,false,true);
            img.setImage(image);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }


    public void setOeuvre(Oeuvre o){
        this.o=o;
    }







}
