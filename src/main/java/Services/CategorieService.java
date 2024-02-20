package Services;

import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Utils.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategorieService implements IService<Categorie> {
    private Statement ste;
    private Connection connection;

    private static CategorieService ser;

    public CategorieService() {
        try {
            Connection con1 = ConnexionDB.getInstance().getCon();
            ste = con1.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static CategorieService getInstance() {
        if (ser == null) ser = new CategorieService();
        return ser;
    }

    @Override
    public void add(Categorie formation) throws SQLException {
        String req = "INSERT INTO `categorie` (`id`,`nom`) " +
                "VALUES (NULL, '" + formation.getNom() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Categorie formation) throws SQLException {
        String req2 = "DELETE FROM `categorie` WHERE id='" + formation.getId() + "';";
        int rowsDeleted = ste.executeUpdate(req2);

        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Categorie categorie) throws SQLException {
        String query = "UPDATE categorie SET nom = '" + categorie.getNom() + "' WHERE id = " + categorie.getId();


        int rowsUpdated = ste.executeUpdate(query);

        return rowsUpdated > 0;
    }

    public Categorie findById(int idd) throws SQLException{
        String req = "SELECT * FROM `categorie` WHERE id='" +idd + "';";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int id = res.getInt(1);
            String nom = res.getString(2);



            return new Categorie(id, nom);
        }

        return null;
    }
    public Categorie findByName(String name) {
        String query = "SELECT * FROM categorie WHERE nom = '" + name + "'";
        try {

            ResultSet resultSet = ste.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return new Categorie(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Categorie> findAll() throws SQLException {

        ObservableList<Categorie> l1 = FXCollections.observableArrayList();
        ResultSet res = ste.executeQuery("select  * from categorie");
        while (res.next()) {
            int id = res.getInt(1);
            String nom = res.getString(2);

            Categorie f = new Categorie(id, nom);
            System.out.println(f);
            l1.add(f);

        }
        return l1;
    }



}
