package Services;

import Entites.Billet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BilletService implements IService<Billet> {
    private Statement ste;
    public static BilletService serBillet;
    private Billet idBillet;

    @Override
    public void add(Billet billet) throws SQLException {
        String req = "INSERT INTO 'billet' ('idBillet ' , 'prixBillet'  " + ")" +
                "VALUES (NULL,'" + billet.getIdBillet() + " ' ,  ' " + billet.getPrixBillet() + "');'";

        ste.executeUpdate(req);
    }


    @Override
    public boolean delete(Billet billet) throws SQLException {

        String req2 = "DELETE FROM ` billet ` WHERE id='" + billet.getIdBillet() + "';";
        int rowsDeleted = ste.executeUpdate(req2);
        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Billet billet) throws SQLException {

        String req = "UPDATE `billet` SET `ID Billet`='" + billet.getIdBillet() +
                "', prix de billet ' = ` = '" + billet.getPrixBillet() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }


    @Override
    public Billet findById(int id) throws SQLException {
        String req = "SELECT * FROM evenement WHERE idEvent  = " + id + " ; ";
        ResultSet res = ste.executeQuery(req);


        if (res.next()) {
            int idBillet = res.getInt(1);
            float prixBillet = res.getFloat(2);

            return new Billet(idBillet, prixBillet);
        }
        return null; }

    @Override
    public List<Billet> findAll() throws SQLException {

        List<Billet> b1 = new ArrayList<>();
        ResultSet res = ste.executeQuery("select  * from billet ");


        while (res.next()) {
            int idBillet = res.getInt(1);
            float prixBillet = res.getFloat("Prix de billet ");

            System.out.println("id Billet :" + idBillet +
                    "Prix de billet : " + prixBillet);


            Billet billet = new Billet(idBillet, prixBillet);
            System.out.println(billet);
            b1.add(billet);

        }
        return b1;
    }

}




