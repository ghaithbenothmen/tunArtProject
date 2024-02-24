package Services;

import Entites.Billet;
import Entites.Categorie;
import Entites.Evenement;
import Entites.Niveau;
import Utils.ConnexionDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class EvenementService  implements IService<Evenement> {

    private Statement ste;
    private static EvenementService serEv;
    private Billet idBillet;


    @Override
    public void add(Evenement evenement) throws SQLException {
        String req = "INSERT INTO `evenement` (`idEvent`,`lieu`, `nom_Event`," +
                " `nb_publique`," + " `date` " + " ) " +
                "VALUES ( NULL, '" + evenement.getLieu() + "', '" +
                evenement.getNom_Event() + "', '" + evenement.getNb_publique() + "', '"
                + evenement.getDate() +  "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Evenement evenement) throws SQLException {

        String req2 = "DELETE FROM ` evenement ` WHERE id='" + evenement.getIdEvent() + "';";
        int rowsDeleted = ste.executeUpdate(req2);
        return rowsDeleted >0 ;}


    @Override
    public boolean update(Evenement evenement) throws SQLException {
    String req = "UPDATE `evenement` SET `nom`='" + evenement.getNom_Event() +
            "', `Lieu de l evenement ` = '" + evenement.getDate()+
             "', `date de le evenement `='" + evenement.getDate()+
            "', `Nombre de public :  `='" + evenement.getNb_publique()+
            "' WHERE id='" + evenement.getIdEvent() + "';" ;

    int rowsUpdated = ste.executeUpdate(req);
        return rowsUpdated > 0;
}


    @Override
    public Evenement findById(int id) throws SQLException {

        String req = "SELECT * FROM evenement WHERE idEvent  = " + id + " ; ";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int idEvent = res.getInt(1);
            String lieu = res.getString(2);
            String nom_Event = res.getString(3);
            Integer nb_publique = res.getInt(4);
            Date date = res.getDate(5);

            return new Evenement( idEvent, lieu, nom_Event, nb_publique,date,idBillet); }
        return null;
    }

        @Override
        public List<Evenement> findAll () throws SQLException {

            List<Evenement> E1 = new ArrayList<>();
            ResultSet res = ste.executeQuery("select  * from evenement");
            while (res.next()) {
                int idEvent = res.getInt(1);
                String lieu= res.getString(2);
                String nom_Event = res.getString(3);
                java.util.Date date = res.getDate("date de l evenement");
                int nb_publique = res.getInt("Nombre de publics");

                System.out.println("id Evenement :" + idEvent +
                        "Lieu de l'evenement : " + lieu +
                        "nom de l'Evenemnt :" + nom_Event +
                        "Nombre de public : " + nb_publique );


               Evenement event = new Evenement(idEvent,lieu, nom_Event,nb_publique,date,idBillet);
                System.out.println(event);
                E1.add(event);

            }
            return E1;
        }
 }