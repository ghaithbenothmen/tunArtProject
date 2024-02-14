package Entites;

import java.util.*;

public class Formation {
    private int id;
    private String nom;
    private int artiste_id;
    private Date dateDebut;
    private Date dateFin;
    private Niveau niveau;
    private String description;
    private int cat_id;

    public Formation() {
    }

    public Formation(int id, String nom, int artiste_id, Date dateDebut, Date dateFin, Niveau niveau, String description, int cat_id) {
        this.id=id;
        this.nom=nom;
        this.artiste_id = artiste_id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.niveau = niveau;
        this.description = description;
        this.cat_id = cat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArtiste_id() {
        return artiste_id;
    }

    public void setArtiste_id(int artiste_id) {
        this.artiste_id = artiste_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public Formation(String nom, int artiste_id, Date dateDebut, Date dateFin, Niveau niveau, String description,int cat_id) {
        this.nom=nom;
        this.artiste_id = artiste_id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.niveau = niveau;
        this.description = description;
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "\nFormation{\n" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", artiste=" + artiste_id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", niveau=" + niveau +
                ", description='" + description + '\'' +
                ", categorie=" + cat_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formation formation = (Formation) o;
        return id == formation.id && Objects.equals(nom, formation.nom) && Objects.equals(artiste_id, formation.artiste_id) && Objects.equals(dateDebut, formation.dateDebut) && Objects.equals(dateFin, formation.dateFin) && niveau == formation.niveau && Objects.equals(description, formation.description) && Objects.equals(cat_id, formation.cat_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, artiste_id, dateDebut, dateFin, niveau, description, cat_id);
    }

}
