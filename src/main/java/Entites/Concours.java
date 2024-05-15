package Entites;

import java.util.Date;
import java.util.Objects;

public class Concours {
    private int reference;
    private int prix;
    private Date date;
    private Type type;
    private String Lien;
    private String SType;
    private String nom;
    
    private int Nparticipant;
    private int Nvote;
    private int Maxparticipant;

    public Concours(int reference,String nom) {
        this.reference = reference;
        this.nom = nom;
    }
    public Concours(int reference, int prix, Date date, Type type,
                    String lien,String nom,int Nparticipant,int Nvote,int Maxparticipant) {
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.type = type;
        this.Lien = lien;
        this.nom = nom;
        this.Nparticipant = Nparticipant;
        this.Nvote = Nvote;
        this.Maxparticipant = Maxparticipant;
    }
    public Concours(int reference, int prix, Date date, String SType,
                    String lien,String nom,int Nparticipant,int Nvote,int Maxparticipant) {
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.SType = SType;
        this.Lien = lien;
        this.nom = nom;
        this.Nparticipant = Nparticipant;
        this.Nvote = Nvote;
        this.Maxparticipant = Maxparticipant;

    }

    public Concours(int reference, int prix, Date date, String lien,String nom) {
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.Lien = lien;
        this.nom = nom;
    }


    public Concours(int prix, Date date, Type type,
                    String lien,String nom,int Nparticipant,int Nvote,int Maxparticipant) {
        this.prix = prix;
        this.date = date;
        this.type = type;
        this.Lien = lien;
        this.nom = nom;
        this.Nparticipant = Nparticipant;
        this.Nvote = Nvote;
        this.Maxparticipant = Maxparticipant;
    }


    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public String getLien() {
        return Lien;
    }
    public void setLien(String lien) {
        Lien = lien;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSType(){return String.valueOf(type);}

    @Override
    public String toString() {
        return "\nConcours{" +
                "id=" + reference +
                ", date='" + date + '\'' +
                ", type=" + SType +
                ", prix='" + prix + '\'' +
                ", Lien=" + Lien +
                ", Nom=" + nom +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Concours concours = (Concours) o;
        return reference == concours.reference && prix == concours.prix && Objects.equals(date, concours.date) && type == concours.type && Objects.equals(Lien, concours.Lien) && Objects.equals(SType, concours.SType) && Objects.equals(nom, concours.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, prix, date, type, Lien, SType, nom);
    }

    public int getNparticipant() {
        return Nparticipant;
    }

    public void setNparticipant(int nparticipant) {
        Nparticipant = nparticipant;
    }

    public int getNvote() {
        return Nvote;
    }

    public void setNvote(int nvote) {
        Nvote = nvote;
    }

    public int getMaxparticipant() {
        return Maxparticipant;
    }

    public void setMaxparticipant(int maxparticipant) {
        Maxparticipant = maxparticipant;
    }
}
