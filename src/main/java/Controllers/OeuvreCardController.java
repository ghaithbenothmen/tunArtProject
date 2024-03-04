package Controllers;

import Entites.Like;
import Entites.Oeuvre;
import Entites.User;
import Services.LikeService;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import javax.xml.xpath.XPath;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;

public class OeuvreCardController  {

    boolean liked;
//    private Commentaire c2=new Commentaire();
//    CommentaireService cs = new CommentaireService();

    public boolean verif;
    private Like l2= new Like();
    private int likenbr;
    private Oeuvre o ;
    @FXML
    private Label nbr;

    @FXML
    private Label Type;

    @FXML
    private ImageView img;

    @FXML
    private Label nomArtiste;

    @FXML
    private Label nom_Oeuvre;

    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    @FXML
    private Button jaimebtn;
    @FXML
    private Button dislikebtn;
    private Oeuvre o2=new Oeuvre();
    OeuvreService os = new OeuvreService();
    LikeService ls = new LikeService();
    List<User> l = new ArrayList<>();

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




Oeuvre oeuvre;
    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();


    private Image image;
    public void setData(Oeuvre oeuvre) throws SQLException {
        this.oeuvre=oeuvre;
        System.out.println("objett"+this.oeuvre);
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


    public void setOeuvre(Oeuvre o) throws SQLException {
        this.o=o;
        List<Like> likes = ls.likefindALL();
        jaimebtn.setDisable(false);
        dislikebtn.setDisable(false);
        for (Like like : likes) {
            if (like.getEtat()) {
                // If a like is found, disable the like button
                jaimebtn.setDisable(true);
            } else {
                // If a dislike is found, disable the dislike button
                dislikebtn.setDisable(true);
            }
        }
        verif=ls.verif_like(LoginController.UserConnected.getId(), o.getRef());
        System.out.println(verif);

        jaimebtn.setDisable(true);
        dislikebtn.setDisable(true);
        if (verif==true){

            jaimebtn.setDisable(true);
            dislikebtn.setDisable(false);

        }


        if(verif==false){
            dislikebtn.setDisable(true);
            jaimebtn.setDisable(false);
        }
    }



    @FXML
    private void likeee(ActionEvent event) throws SQLException {

        Like like = new Like();
        like.setId_user(LoginController.UserConnected.getId());
        like.setId_oeuvre(this.o.getRef());
        like.setEtat(Boolean.TRUE);

        List<Like> likes = ls.affiche_like(like);
        System.out.println(likes);
            if (likes.size() == 0) {
                ls.ajouter(like);

                likenbr = ls.nbrlike(o);
                o.setNote(likenbr + 1);

                ls.ModifLike(like);
                jaimebtn.setDisable(true);
                dislikebtn.setDisable(false);
//                nbr.setText(String.valueOf(os.nbrlike(o)));

            } else {

                ls.ModifLike(like);
                likenbr = os.nbrlike(o);
                o.setNote(likenbr + 1);

                ls.ModifLike(like);
                jaimebtn.setDisable(true);
                dislikebtn.setDisable(false);
//                nbr.setText(String.valueOf(os.nbrlike(o)));
            }
        liked=true;
        if (liked) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+" "+"a aimée l'oeuvre"+" "+this.oeuvre.getNom_Ouvre()+" "+"de l'artiste"+" "+this.oeuvre.getArtiste_id().getNom())
                    .showInformation();
        }






    }

    @FXML
    private void dislike(ActionEvent event) throws SQLException {
        //c2.getId_user()
        Like like = new Like();
        like.setId_user(LoginController.UserConnected.getId());
        like.setId_oeuvre(this.o.getRef());
        like.setEtat(Boolean.FALSE);
        List<Like> likes = ls.affiche_like(like);

        if (likes.size()==0){

            ls.ajouter(like);




            likenbr=os.nbrlike(o);
            o.setNote(likenbr-1);
            os.update(o);
            dislikebtn.setDisable(true);
            jaimebtn.setDisable(false);
            nbr.setText(String.valueOf(os.nbrlike(o))); }


        else{

            ls.ModifLike(like);

            likenbr=os.nbrlike(o);
            o.setNote(likenbr-1);
            os.update(o);
            dislikebtn.setDisable(true);
            jaimebtn.setDisable(false);
//            nbr.setText(String.valueOf(os.nbrlike(o)));





        }
        liked=true;
        if (liked) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+" a détestée l'oeuvre"+" "+this.oeuvre.getNom_Ouvre()+" "+"de l'artiste"+" "+this.oeuvre.getArtiste_id().getNom())
                    .showInformation();
        }

    }


//    public void setcmnt(Oeuvre oeuvre) throws SQLException {
//
//
//        verif = ls.verif_like(UserConnected.getId(), o.getRef());
//        if (verif == true) {
//
//            jaimebtn.setDisable(true);
//            dislikebtn.setDisable(false);
//
//        }
//
//
//        if (verif == false) {
//            dislikebtn.setDisable(true);
//            jaimebtn.setDisable(false);
//        }
//
//
//    }
}
