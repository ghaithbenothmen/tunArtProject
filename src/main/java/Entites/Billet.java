package Entites;

public class Billet {
    private int idBillet;
private float prixBillet;

    public int getIdBillet() {return idBillet;}

    public void setIdBillet(int idBillet) {this.idBillet = idBillet;}

    public float getPrixBillet() {return prixBillet;}

    public void setPrixBillet(float prixBillet) {this.prixBillet = prixBillet;}

    @Override
    public String toString() {
        return "Billet{" +
                "idBillet=" + idBillet +
                ", prixBillet=" + prixBillet +
                '}';  }
public Billet(){}
    public Billet(int idBillet, float prixBillet) {
        this.idBillet = idBillet;
        this.prixBillet = prixBillet;}
}

