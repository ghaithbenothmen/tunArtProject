package Entites;

/*import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;*/

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Actualite {
    private int id;
    private String titre;
    private String text;
    private Date date;
    private String image;

    public Actualite( String titre, String text, Date date, String image) {
        this.titre = titre;
        this.text = text;
        this.date = date;
        this.image = image;
    }
    public Actualite( int id ,String titre, String text, Date date, String image) {
        this.id=id;
        this.titre = titre;
        this.text = text;
        this.date = date;
        this.image = image;
    }
    public Actualite( int id ,String titre) {
        this.id=id;
        this.titre = titre;
    }



    public Actualite() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Actualite{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", image='" + image + '\'' +
                '}';
    }
}

