package Services;

import Entites.Like;
import Entites.Oeuvre;
import Utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeService {

    Connection cnx;

    public LikeService() {
        cnx = ConnexionDB.getInstance().getCon();
    }


    public void ajouter(Like l) throws SQLException {
        String req = "INSERT INTO `like` (id, id_user, id_oeuvre, etat, LikeCount) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setInt(1, l.getId());
        System.out.println(l.getId());
        stmt.setInt(2, l.getId_user());
        System.out.println(l.getId_user());
        stmt.setInt(3, l.getId_oeuvre());
        System.out.println(l.getId_oeuvre());
        stmt.setBoolean(4, l.getEtat());
        stmt.setInt(5, l.getLikeCount()); // Assuming l.getLikeCount() returns the like count
        stmt.executeUpdate();
    }


    public Boolean verif_like(int idu, int idc) throws SQLException {
        Boolean i = false;
        List<Like> likes = new ArrayList<>();
        String req = "SELECT etat FROM `like` WHERE id_user=? AND id_oeuvre=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idu);
        ps.setInt(2, idc);

        ResultSet rs = ps.executeQuery();


        while (rs.next()) {

            i = rs.getBoolean("etat");

        }

        return i;


    }


    public List<Like> affiche_like(Like l) throws SQLException {
        List<Like> likes = new ArrayList<>();
        String req = "SELECT * FROM `like` WHERE id_user=? AND id_oeuvre=?";
        ;
        PreparedStatement ps = cnx.prepareStatement(req);

        System.out.println("iddddddddd" + l.getId_user());
        ps.setInt(1, l.getId_user());
        ps.setInt(2, l.getId_oeuvre());

        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            Like like = new Like();
            like.setId_user(rs.getInt("id_user"));
            like.setId(rs.getInt("id"));
            like.setId_oeuvre(rs.getInt("id_oeuvre"));
            like.setEtat(rs.getBoolean("etat"));
            likes.add(l);
        }

        return likes;


    }


    public void ModifLike(Like l) throws SQLException {

        String req = "UPDATE `like` SET etat=? WHERE id_user=? AND id_oeuvre=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setBoolean(1, l.getEtat());
        stmt.setInt(2, l.getId_user());
        stmt.setInt(3, l.getId_oeuvre());

        stmt.executeUpdate();

    }

    public int nbrlike(Oeuvre o) throws SQLException {
        int i = 0;
        String req = "SELECT LikeCount FROM `like` WHERE id=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, o.getRef());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            i = rs.getInt("LikeCount");
        }
        return i;
    }


    public List<Like> likefindALL() throws SQLException {
        List<Like> likes = new ArrayList<>();
        String req = "SELECT * FROM `like` ";
        ;
        PreparedStatement ps = cnx.prepareStatement(req);


        ResultSet rs = ps.executeQuery();

        return likes;
    }
}