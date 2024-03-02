package Services;

import Entites.Actualite;
import Entites.Commentaire;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.*;
import java.sql.Date;
public class CommentaireService implements IServiceCommentaire<Commentaire>{
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
    public void ajouter(Commentaire commentaire) {
        System.out.println(commentaire);

        String req = "INSERT INTO `commentaire`( `id_user`, `id_act`, `contenuC`) "
                +"values ( '" + commentaire.getId_user()+"', '"+commentaire.getId_act()+"','"+commentaire.getContenuC()+"')";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*    @Override
    public void ajouter(Commentaire commentaire) {

        String req = "INSERT INTO commentaire ( contenuC) VALUES ( ?) " ;
             try (PreparedStatement statement = con.prepareStatement(req)) {
                 //statement.setInt(1,commentaire.getId_user());
                 //statement.setInt(2,commentaire.getId_act());
                 statement.setString(1,commentaire.getContenuC());
                // Exécuter la requête d'insertion
                statement.executeUpdate();
                System.out.println("Nouvel commentaire inséré avec succès.");
            }
            catch (SQLException e) {
                 System.out.println("tneknaa") ;
                e.printStackTrace();
            }
    }*/

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {

    }


    @Override
    //done
    public void supprimer(Commentaire commentaire) {
        String req = "DELETE from commentaire where id_c = " + commentaire.getId_c() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

/*            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(id_act);*/

            return new Commentaire(id_c, id_act, id_user, contenuC);
        }
        return null;
    }

    @Override
    //done
    public List<Commentaire> afficher() throws SQLException {

        List<Commentaire> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from commentaire");
        while (res.next()) {

            int id_c = res.getInt(1);
            int id_act = res.getInt(2);
            int id_user = res.getInt(3);
            String contenuC = res.getString("contenuC");
/*
            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(id_act);*/

            Commentaire c1 =new Commentaire (id_c,id_act,id_user,contenuC);
            list.add(c1);
        }

        return list;
    }


}