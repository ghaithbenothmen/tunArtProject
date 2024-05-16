package Entites;

import Entites.Actualite;
import Entites.User;
import javafx.scene.control.DatePicker;

import java.util.Date;

public class Commentaire {
    private int id_c;

    private Actualite actualite_id;
    private User user_id;
    private String contenuc;
    private String prenom;
    private String titre;



    public Commentaire(String titre,String contenuc, String prenom) {
        this.prenom = prenom;
        this.titre= titre;
        this.contenuc=contenuc ;
    }

    public Commentaire(int id_c, Actualite actualite_id, User user_id, String contenuc) {
        this.id_c = id_c;
        this.actualite_id= actualite_id;
        this.user_id = user_id;
        this.contenuc=contenuc ;
    }

    public Commentaire(Actualite actualite_id, User user_id, String contenuc) {
        this.actualite_id = actualite_id;
        this.user_id = user_id;
        this.contenuc = contenuc;
    }

    public Commentaire(String contenuC) {
        this.contenuc=contenuc ;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }


    public Actualite getActualite_id() {
        return actualite_id;
    }

    public void setActualite_id(Actualite actualite_id) {
        this.actualite_id = actualite_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getContenuc() {
        return contenuc;
    }

    public void setContenuc(String contenuc) {
        this.contenuc = contenuc;
    }




    @Override
    public String toString() {
        return "Commentaire{" +
                "id_c=" + id_c +
                ", actualite_id=" + actualite_id+
                ", user_id=" + user_id +
                ", contenuc='" + contenuc + '\'' +
                '}';
    }
}