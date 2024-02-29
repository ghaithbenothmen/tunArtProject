package Services;

import Entites.Collaborateur;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollaborateurService implements IService<Collaborateur>{

    Connection Con;

    public CollaborateurService() {
        Con = ConnexionDB.getInstance().getCon();
    }



    public boolean existEmail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM collaborateur WHERE email = ?";
        PreparedStatement ste = Con.prepareStatement(query);
        ste.setString(1, email);
        ResultSet res = ste.executeQuery();
        if (res.next()) {
            exist = true;
        }
        return exist;
    }



    public String getNomComplet(int id) throws SQLException {
        String req = "SELECT nom FROM collaborateur WHERE id=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet res = ste.executeQuery();
        String nom= "";
        if (res.next()){
            nom = res.getString("nom");
        }
        return nom;
    }




    @Override
    public void add(Collaborateur c) throws SQLException {
        String req = "INSERT INTO collaborateur (nomComplet, email) VALUES (?,?)";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, c.getNomComplet());
        ste.setString(2, c.getEmail());

        int result = ste.executeUpdate();

        System.out.println(result + "Ajouté avec succès");

    }

    @Override
    public boolean delete(Collaborateur c) throws SQLException {
        String req = "DELETE FROM collaborateur WHERE id=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setInt(1, c.getId());
        ste.executeUpdate();

        int rowsDeleted = ste.executeUpdate(req);

        return rowsDeleted > 0;

    }

    @Override
    public boolean update(Collaborateur c) throws SQLException {
        String req = "UPDATE collaborateur set nomComplet=?, email=? WHERE id=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, c.getNomComplet());
        ste.setString(2, c.getEmail());

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }

    @Override
    public Collaborateur findById(int id) throws SQLException {
        return null;
    }


    @Override
    public List<Collaborateur> findAll() throws SQLException {
        List<Collaborateur> data = new ArrayList<>();
        String req = "SELECT * FROM collaborateur";
        Statement ste = ConnexionDB.getInstance().getCon().createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Collaborateur c = new Collaborateur();
            c.setId(res.getInt(1));
            c.setNomComplet(res.getString("nom"));
            c.setEmail(res.getString("email"));
            data.add(c);

        }
        return data;
    }
}


