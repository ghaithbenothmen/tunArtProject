package Entites;

import java.util.Objects;

public class Reclamation {
    private int id;
    private User id_user;
    private String text;
    private String type;

    public Reclamation(int id, User id_user, String type, String text) {
        this.id=id;
        this.id_user=id_user;
        this.type=type;
        this.text=text;
    }

    public Reclamation(User id_user, String type, String text) {
        this.id_user=id_user;
        this.type=type;
        this.text=text;
    }

    public Reclamation() {

    }

    public Reclamation(int id, String type, String text) {
        this.id=id;
        this.type=type;
        this.text=text;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reclamation that)) return false;
        return getId() == that.getId() && getId_user() == that.getId_user() && Objects.equals(getText(), that.getText()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getId_user(), getText(), getType());
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
