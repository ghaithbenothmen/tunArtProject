package Entites;

import java.util.Objects;

public class Collaborateur {
    private int id;
    private String nomComplet, email;

    public Collaborateur(int id, String nomComplet, String email) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.email = email;
    }

    public Collaborateur(String nomComplet, String email) {
        this.nomComplet = nomComplet;
        this.email = email;
    }

    public Collaborateur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Collaborateur{" +
                "id=" + id +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collaborateur that)) return false;
        return getId() == that.getId() && Objects.equals(getNomComplet(), that.getNomComplet()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomComplet(), getEmail());
    }
}
