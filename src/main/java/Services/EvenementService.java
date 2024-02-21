package Services;

import Entites.Evenement;
import Entites.Formation;
import Utils.ConnexionDB;

import java.sql.SQLException;
import java.util.List;

import java.sql.*;

public class EvenementService  implements IService<Evenement> {

    private Statement ste;
    private static EvenementService serEv;


    @Override
    public void add(Evenement evenement) throws SQLException {
        String req = "INSERT INTO `evenement` (`idEvent`,`lieu`, `nom_Event`," +
                " `nb_publique`," + " `date`) " +
                "VALUES (NULL, '" + evenement.getLieu() + "', '" +
                evenement.getNom_Event() + "', '" + evenement.getNb_publique() + "', '"
                + evenement.getDate() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Evenement evenement) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Evenement evenement) throws SQLException {
        return false;
    }

    @Override
    public Evenement findById(int id) throws SQLException {
        String req = "SELECT * FROM evenement WHERE idEvent  = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int idEvent = res.getInt(1);
            String lieu = res.getString(2);
            String nom_Event = res.getString(3);
            Integer nb_publique = res.getInt(4);
            Date date = res.getDate(5);
            return new Evenement(idEvent, lieu, nom_Event, nb_publique, date);

        }
        return null;
    }

        @Override
        public List<Evenement> findAll () throws SQLException {
            return null;
        }
}