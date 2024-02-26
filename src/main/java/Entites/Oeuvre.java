package Entites;
import java.util.*;

public class Oeuvre {
    private int Ref;
    private int Artiste_id;
    private String Nom_Ouvre;
    private String Img;
    private Date Date_Publication;
    private String Description;
    private Boolean note;
    private TypeOeuvre TypeOeuvre;

    public Oeuvre() {
    }

    public Oeuvre(int Ref, String nom_Ouvre, String img, Date date_Publication, String description, Boolean note, TypeOeuvre TypeOeuvre) {
//        this.Artiste_id = artiste_id;
        this.Ref = Ref;
        this.Nom_Ouvre = nom_Ouvre;
        this.Img = img;
        this.Date_Publication = date_Publication;
        this.Description = description;
        this.note = note;
        this.TypeOeuvre=TypeOeuvre;
    }

    public Oeuvre(String nom, String image, String description, TypeOeuvre TypeOeuvre, Date date) {
        this.Nom_Ouvre = nom;
        this.Img = image;
        this.Date_Publication = date;
        this.Description = description;
        this.TypeOeuvre = TypeOeuvre;
    }

    public int getRef() {
        return Ref;
    }

    public void setRef(int Ref) {
        this.Ref = Ref;
    }

    public int getArtiste_id() {
        return Artiste_id;
    }

    public void setArtiste_id(int artiste_id) {
        Artiste_id = artiste_id;
    }

    public String getNom_Ouvre() {
        return Nom_Ouvre;
    }

    public void setNom_Ouvre(String nom_Ouvre) {
        this.Nom_Ouvre = nom_Ouvre;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        this.Img = img;
    }

    public Date getDate_Publication() {
        return Date_Publication;
    }

    public void setDate_Publication(Date date_Publication) {
        this.Date_Publication = date_Publication;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public Boolean getNote() {
        return note;
    }

    public void setNote(Boolean note) {
        this.note = note;
    }

    public TypeOeuvre getTypeOeuvre() {
        return TypeOeuvre;
    }

    public void setTypeOeuvre(TypeOeuvre TypeOeuvre) {
        this.TypeOeuvre = TypeOeuvre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oeuvre oeuvre)) return false;
        return getRef() == oeuvre.getRef() && getArtiste_id() == oeuvre.getArtiste_id() && Objects.equals(getNom_Ouvre(), oeuvre.getNom_Ouvre()) && Objects.equals(getImg(), oeuvre.getImg()) && Objects.equals(getDate_Publication(), oeuvre.getDate_Publication()) && Objects.equals(getDescription(), oeuvre.getDescription()) && Objects.equals(getNote(), oeuvre.getNote()) && Objects.equals(getTypeOeuvre(), oeuvre.getTypeOeuvre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRef(), getArtiste_id(), getNom_Ouvre(), getImg(), getDate_Publication(), getDescription(), getNote(), getTypeOeuvre());
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "Ref=" + Ref +
                ", Artiste_id=" + Artiste_id +
                ", Nom_Ouvre='" + Nom_Ouvre + '\'' +
                ", Img='" + Img + '\'' +
                ", Date_Publication=" + Date_Publication +
                ", Description='" + Description + '\'' +
                ", note=" + note +
                ", TypeOeuvre='" + TypeOeuvre + '\'' +
                '}';
    }
}
