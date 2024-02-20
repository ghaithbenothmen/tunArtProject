package Services;

import Entites.Formation;
import Utils.ConnexionDB;

import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class FormationService implements IService<Formation> {
    private Statement ste;

    private static FormationService ser;

    private FormationService() {
        try {
            Connection con1 = ConnexionDB.getInstance().getCon();
            ste = con1.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static FormationService getInstance() {
        if (ser == null) ser = new FormationService();
        return ser;
    }

    @Override
    public void add(Formation formation) throws SQLException {
        String req = "INSERT INTO `formation` (`id`,`nom`, `artiste_id`, `dateDebut`, `dateFin`, `niveau`, `description`, `cat_id`) " +
                "VALUES (NULL, '" + formation.getNom() + "', '" + formation.getArtiste_id() + "', '" + formation.getDateDebut() + "', '" + formation.getDateFin() + "', '" + formation.getNiveau() + "', '" + formation.getDescription() + "', '" + formation.getCat_id() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Formation formation) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Formation formation) throws SQLException {
        return false;
    }

    @Override
    public Formation findById(Formation formation) throws SQLException {
        return null;
    }

    @Override
    public List<Formation> findAll() throws SQLException {
        return null;
    }
}
