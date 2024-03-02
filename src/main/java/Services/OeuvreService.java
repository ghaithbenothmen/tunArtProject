package Services;

import Entites.Oeuvre;
import Entites.TypeOeuvre;
import Entites.User;
import Utils.ConnexionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OeuvreService implements IOeuvre<Oeuvre>{

    Connection con;

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

        String uri = o.getImg().replace("\\", "/");

        String req = "INSERT INTO Oeuvre (nom_Oeuvre, img, date_Publication, description, note, TypeOeuvre, artiste_id) " +
                "VALUES ('" + o.getNom_Ouvre() + "', '" + uri + "', '" + formattedDate + "', '" +
                o.getDescription() + "', " + o.getNote() + ", '" + o.getTypeOeuvre() + "', " + o.getArtiste_id().getId() + ")";

        ste.executeUpdate(req);
    }

    @Override
    public boolean update(Oeuvre o) throws SQLException {
        String uri = o.getImg().replace("\\", "/");

        String req = "UPDATE `Oeuvre` SET `nom_Oeuvre`='" + o.getNom_Ouvre()
                + "', `img`='" +uri
                + "', `date_Publication`='" + o.getDate_Publication()
                + "', `description`='" +o.getDescription()
                + "', `note`='" + o.getNote()
                + "', `TypeOeuvre`='" + o.getTypeOeuvre()
                + "', `artiste_id`='" + o.getArtiste_id().getId()
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
            int note = res.getInt("note");
            TypeOeuvre TypeOeuvre = Entites.TypeOeuvre.valueOf(res.getString("TypeOeuvre"));

            UserService userService = new UserService();
            User artiste = userService.findById(artiste_id);


            return new Oeuvre(ref,nom_oeuvre,img,date_publication,description,note,TypeOeuvre,artiste);
        }

        return null;
    }

    public List<Oeuvre> findAll() throws SQLException {
        List<Oeuvre> lO = new ArrayList<>();

        ResultSet res = ste.executeQuery("select * from oeuvre");

        while (res.next()) {
            int ref = res.getInt("Ref");

            int artiste_id = res.getInt("artiste_id");
            String nom_Oeuvre = res.getString("nom_Oeuvre");
            String img = res.getString("img");
            java.util.Date date_Publication = res.getDate("date_Publication");
            String description = res.getString("description");
            int note = res.getInt("note");
            TypeOeuvre typeOeuvre = TypeOeuvre.valueOf(res.getString("TypeOeuvre"));

            UserService userService = new UserService();
            User artiste = userService.findById(artiste_id);


            Oeuvre o=new Oeuvre(ref,nom_Oeuvre,img,date_Publication,description,note,typeOeuvre,artiste);
//            System.out.println(o);
            lO.add(o);

        }
//        System.out.println(lO);
        return lO;
    }

    public List<Oeuvre> findByUserId(int userId) throws SQLException {
        List<Oeuvre> oeuvre = new ArrayList<>();
        String query = "SELECT * FROM oeuvre WHERE artiste_id = " + userId;
        ResultSet resultSet = ste.executeQuery(query);

        while (resultSet.next()) {
            int ref = resultSet.getInt("Ref");
            String nom_Oeuvre = resultSet.getString("nom_Oeuvre");
            int artiste_id = resultSet.getInt("artiste_id");
            Date date_Publication = resultSet.getDate("date_Publication");
            String description = resultSet.getString("description");
            int note = resultSet.getInt("note");
            TypeOeuvre typeOeuvre = TypeOeuvre.valueOf(resultSet.getString("TypeOeuvre"));


            String img = resultSet.getString("img");



            UserService userService = new UserService();
            User artiste = userService.findById(artiste_id);

            Oeuvre o = new Oeuvre(ref,nom_Oeuvre,img,date_Publication,description,note,typeOeuvre,artiste);
            oeuvre.add(o);
        }

        return oeuvre;
    }

    public int nbrlike(Oeuvre o) throws SQLException{
        con = ConnexionDB.getInstance().getCon();
        int i = 0;
        String req = "SELECT note FROM `oeuvre` WHERE Ref=?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setInt(1, o.getRef());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            i = rs.getInt("note");
        }
        return i;
    }
}
