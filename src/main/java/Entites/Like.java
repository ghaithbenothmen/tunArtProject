package Entites;

import java.util.Objects;

public class Like {

    private int id_user;
    private int id_oeuvre;
    private int id;
    private Boolean etat;
    private int LikeCount;

    public Like( int id , int id_user,int id_oeuvre , Boolean etat) {
        this.id = id;
        this.id_user = id_user;
        this.id_oeuvre = id_oeuvre;
        this.etat = etat;

    }

    public Like() {
    }

    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Like{" + "id_user=" + id_user + ", id_oeuvre=" + id_oeuvre + ", etat=" + etat + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like like)) return false;
        return getId_user() == like.getId_user() && getId_oeuvre() == like.getId_oeuvre() && getId() == like.getId() && getLikeCount() == like.getLikeCount() && Objects.equals(getEtat(), like.getEtat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_user(), getId_oeuvre(), getId(), getEtat(), getLikeCount());
    }
}
