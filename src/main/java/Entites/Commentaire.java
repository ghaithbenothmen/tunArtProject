package Entites;

import javafx.scene.control.DatePicker;

import java.util.Date;

public class Commentaire {
    private int id_c;

    private int id_act;
    private int id_user;
    private String contenuC;


    public Commentaire(int id_c, int id_act, int id_user, String contenuC) {
        this.id_c = id_c;
        this.id_act= id_act;
        this.id_user = id_user;
        this.contenuC=contenuC ;
    }

    public Commentaire(int id_act, int id_user, String contenuC) {
        this.id_act = id_act;
        this.id_user = id_user;
        this.contenuC = contenuC;
    }

    public Commentaire(String contenuC) {
        this.contenuC=contenuC ;
    }


    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }


    public int getId_act() {
        return id_act;
    }

    public void setId_act(int id_act) {
        this.id_act = id_act;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int is_user) {
        this.id_user = is_user;
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
