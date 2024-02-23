package Services;

import Entites.Actualite;
import Entites.Commentaire;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.*;
import java.sql.Date;
public class CommentaireService implements IService<Commentaire>{
    private Connection con= ConnexionDB.getInstance().getCon();

    private Statement ste;

    public CommentaireService()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
    @Override
    public void add(Commentaire commentaire) {
        System.out.println(commentaire);

        String req = "INSERT INTO `commentaire`( `id_user`, `id_act`, `contenuC`, `dateC`) "
                +"values ( '" + null +"', '"+commentaire.getId_act().getId()+"','"+commentaire.getContenuC()+"', '"+commentaire.getDateC()+"')";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean delete(Commentaire commentaire) throws SQLException {
        String req = "DELETE from commentaire where id_c = " + commentaire.getId_c() + ";";
        int rowsDeleted = ste.executeUpdate(req);

        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire set id_act= '" + commentaire.getId_act() + "', id_user = '"
                + commentaire.getId_user() + "', contenuC = '" + commentaire.getContenuC() + "', dateC = '" + commentaire.getDateC()
                + "' where id_c = " + commentaire.getId_c() + ";";
        int rowsDeleted = ste.executeUpdate(req);

        return rowsDeleted > 0;
    }

    @Override
    public Commentaire findById(int id) throws SQLException {
        String req = "SELECT * FROM commentaire WHERE id_c = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int id_c = res.getInt(1);
            int id_act = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString(4);
            Date dateC = res.getDate(5);

            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(id_act);

            return new Commentaire(id_c, actualite, id_user, contenuC, dateC);
        }
        return null;
    }

    @Override
    public List<Commentaire> findAll() throws SQLException {

        List<Commentaire> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from commentaire");
        while (res.next()) {

            int id_c = res.getInt(1);
            int id_act = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString("contenuC");
            Date dateC = res.getDate(5);

            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(id_act);

            Commentaire c1 =new Commentaire (id_c,actualite,id_user,contenuC,dateC);
            list.add(c1);
        }

        return list;
    }
}