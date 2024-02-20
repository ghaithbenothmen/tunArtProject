package Entites;

import java.util.Objects;

public class Categorie {

    private int id;
    private String nom;

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Categorie( String nom) {

        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id && Objects.equals(nom, categorie.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }
}
