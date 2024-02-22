package Entites;

import java.util.Objects;

public class Actualite {
    private int id;
    private String text;
    private String date;



    public Actualite() {}
    public Actualite(int id ,String text, String date) {
        this.id=id;
        this.text = text;
        this.date = date;
    }
    public Actualite(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Actualite(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Actualite{" + "id=" + id + ", text=" + text + ", date=" + date + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actualite actualite = (Actualite) o;
        return id == actualite.id && Objects.equals(text, actualite.text) && Objects.equals(date, actualite.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,text,date);
    }

}

