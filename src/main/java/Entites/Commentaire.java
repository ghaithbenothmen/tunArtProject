package Entites;

import javafx.scene.control.DatePicker;

import java.util.Date;

public class Commentaire {
    private int id_c;

    private Actualite id_act;
    private User id_user;
    private String contenuC;
    private String prenom;
    private String titre;



    public Commentaire(String titre,String contenuC, String prenom) {
        this.prenom = prenom;
        this.titre= titre;
        this.contenuC=contenuC ;
    }

    public Commentaire(int id_c, Actualite id_act, User id_user, String contenuC) {
        this.id_c = id_c;
        this.id_act= id_act;
        this.id_user = id_user;
        this.contenuC=contenuC ;
    }

    public Commentaire(Actualite id_act, User id_user, String contenuC) {
        this.id_act = id_act;
        this.id_user = id_user;
        this.contenuC = contenuC;
    }

    public Commentaire(String contenuC) {
        this.contenuC=contenuC ;
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


    public Actualite getId_act() {
        return id_act;
    }

    public void setId_act(Actualite id_act) {
        this.id_act = id_act;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getContenuC() {
        return contenuC;
    }

    public void setContenuC(String contenuC) {
        this.contenuC = contenuC;
    }




    @Override
    public String toString() {
        return "Commentaire{" +
                "id_c=" + id_c +
                ", id_act=" + id_act +
                ", id_user=" + id_user +
                ", contenuC='" + contenuC + '\'' +
                '}';
    }
}
