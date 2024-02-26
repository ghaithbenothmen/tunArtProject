package Services;

import Entites.Oeuvre;
import Entites.TypeOeuvre;
import Utils.ConnexionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OeuvreService implements IOeuvre<Oeuvre>{

    private Statement ste;

    private static OeuvreService ser;

    public OeuvreService() {
        try {
            Connection con1 = ConnexionDB.getInstance().getCon();
            ste = con1.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static OeuvreService getInstance() {
        if (ser == null) ser = new OeuvreService();
        return ser;
    }

    @Override
    public void add(Oeuvre o) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(o.getDate_Publication());

        // Handle null value for the note field
        Boolean noteValue = o.getNote() != null ? o.getNote() : false;

        String req = "INSERT INTO `Oeuvre` (`Ref`, `nom_Oeuvre`, `img`, `date_Publication`, `description`, `note`, `TypeOeuvre`) " +
                "VALUES (NULL, '"
                +o.getNom_Ouvre() + "', '"
                +o.getImg()+ "', '"
                + formattedDate + "', '"
                +o.getDescription() + "', "
                + noteValue + ", '"
                +o.getTypeOeuvre() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean update(Oeuvre o) throws SQLException {

        String req = "UPDATE `Oeuvre` SET `nom_Oeuvre`='" + o.getNom_Ouvre()
                + "', `img`='" +o.getImg()
                + "', `date_Publication`='" + o.getDate_Publication()
                + "', `description`='" +o.getDescription()
                + "', `note`='" + 33
                + "', `TypeOeuvre`='" + o.getTypeOeuvre()
                + "' WHERE Ref='" + o.getRef() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }


    @Override
    public boolean delete(Oeuvre o) throws SQLException {
        String req2 = "DELETE FROM `Oeuvre` WHERE ref='" + o.getRef() + "';";
        int rowsDeleted = ste.executeUpdate(req2);

        return rowsDeleted > 0;
    }



    @Override
    public Oeuvre findByRef(int reff) throws SQLException {
        String req = "SELECT * FROM `Oeuvre` WHERE ref='" + reff+ "';";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int ref = res.getInt(1);
            int artiste_id = res.getInt(2);
            String nom_oeuvre = res.getString(2);
            String img = res.getString("img");
            java.util.Date date_publication = res.getDate("date_publication");
            String description = res.getString("description");
            Boolean note = res.getBoolean("note");
            TypeOeuvre TypeOeuvre = Entites.TypeOeuvre.valueOf(res.getString("TypeOeuvre"));


            return new Oeuvre(ref,nom_oeuvre,img,date_publication,description,note,TypeOeuvre);
        }

        return null;
    }

    public List<Oeuvre> findAll() throws SQLException {
        List<Oeuvre> lO = new ArrayList<>();

        ResultSet res = ste.executeQuery("select * from oeuvre");

        while (res.next()) {
            int ref = res.getInt("Ref");

//            int artiste_id = res.getInt(2);
            String nom_Oeuvre = res.getString("nom_Oeuvre");
            String img = res.getString("img");
            java.util.Date date_Publication = res.getDate("date_Publication");
            String description = res.getString("description");
            Boolean note = res.getBoolean("note");
            TypeOeuvre typeOeuvre = TypeOeuvre.valueOf(res.getString("TypeOeuvre"));
            Oeuvre o=new Oeuvre(ref,nom_Oeuvre,img,date_Publication,description,note,typeOeuvre);
//            System.out.println(o);
            lO.add(o);

        }
//        System.out.println(lO);
        return lO;
    }
}
