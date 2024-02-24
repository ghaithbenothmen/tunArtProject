package Entites;
import javafx.scene.control.TextField;

import java.util.Date;
import java.util.Objects;

public class Evenement {
    private int idEvent;
    private String lieu, nom_Event;
    private int nb_publique;
    private Date date;
    private Billet idBillet;

    public Evenement() {}

    public Evenement(int idEvent, String lieu, String nom_Event,
                     int nb_publique, Date date,Billet idBillet)  {
        this.idEvent = idEvent;
        this.lieu = lieu;
        this.nom_Event = nom_Event;
        this.nb_publique = nb_publique;
        this.date = date;
    this.idBillet=idBillet;
    }

    public int getIdEvent() {return idEvent;}

    public void setIdEvent(int idEvent) {this.idEvent = idEvent;}

    public String getLieu() {return lieu;}

    public void setLieu(String lieu) {this.lieu = lieu;}

    public String getNom_Event() {return nom_Event;}

    public void setNom_Event(String nom_Event) { this.nom_Event = nom_Event;}

    public int getNb_publique() {return nb_publique;}

    public void setNb_publique(int nb_publique) { this.nb_publique = nb_publique;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
    public Billet  getIdBillet() {return idBillet;}
    public void setIdBillet(Billet idBillet) {this.idBillet = idBillet;}


    @Override
    public String toString() {
        return "Evenement{" +
                " IdEvent = " + idEvent +
                ", Lieu = " + lieu + '\'' +
                ", Nom_Event = " + nom_Event + '\'' +
                ", Nb_public =" + nb_publique +
                ", Date =" + date +'\''+
                " , Id Billet =" +idBillet ; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evenement evenement)) return false;
        return idEvent == evenement.idEvent && Objects.equals(nom_Event, evenement.nom_Event); }

    @Override
    public int hashCode() { return Objects.hash(idEvent, nom_Event);}
}