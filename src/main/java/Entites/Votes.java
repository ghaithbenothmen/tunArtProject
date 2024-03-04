package Entites;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Votes {

    private int id;
    private Date date;
    private int ID_concours;
    private int ID_user;

    public Votes(int id,Date date,int ID_concours,int ID_user)
    {
        this.id=id;
        this.date=date;
        this.ID_concours=ID_concours;
        this.ID_user=ID_user;
    }

    public Votes(Date date,int ID_concours,int ID_user)
    {
        this.date=date;
        this.ID_concours=ID_concours;
        this.ID_user=ID_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getID_concours() {
        return ID_concours;
    }

    public void setID_concours(int ID_concours) {
        this.ID_concours = ID_concours;
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "id=" + id +
                ", date=" + date +
                ", ID_concours=" + ID_concours +
                ", ID_user=" + ID_user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Votes votes = (Votes) o;
        return id == votes.id && ID_concours == votes.ID_concours && ID_user == votes.ID_user && Objects.equals(date, votes.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, ID_concours, ID_user);
    }
}
