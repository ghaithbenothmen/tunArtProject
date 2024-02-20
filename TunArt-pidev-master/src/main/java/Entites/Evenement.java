package Entites;
import javafx.scene.control.TextField;

import java.util.Date;

public class Evenement {
    private int idEvent;
    private String lieu, nom_Event;
    private int nb_publique;
    private Date date;

    public Evenement(TextField txtId_Evenement) {}

    public Evenement(int idEvent, String lieu, String nom_Event,
                     int nb_publique, Date date)  {
        this.idEvent = idEvent;
        this.lieu = lieu;
        this.nom_Event = nom_Event;
        this.nb_publique = nb_publique;
        this.date = date; }

    public int getIdEvent() {return idEvent;}

    public void setIdEvent(int idEvent) {this.idEvent = idEvent;}

    public String getLieu() {return lieu;}

    public void setLieu(String lieu) {this.lieu = lieu;}

    public String getNom_Event() {return nom_Event;}

    public void setNom_Event(String nom_Event) {
        this.nom_Event = nom_Event;}

    public int getNb_publique() {return nb_publique;}

    public void setNb_publique(int nb_publique) {
        this.nb_publique = nb_publique;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    @Override
    public String toString() {
        return "Evenement{" +
                "idEvent=" + idEvent +
                ", lieu='" + lieu + '\'' +
                ", nom_Event='" + nom_Event + '\'' +
                ", nb_publique=" + nb_publique +
                ", date=" + date +
                '}'; }
}