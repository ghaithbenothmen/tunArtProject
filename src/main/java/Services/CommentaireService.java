package Services;

import Entites.Actualite;
import Entites.Commentaire;
import Entites.User;
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

        String req = "INSERT INTO commentaire (user_id, actualite_id, contenuc) "
                + "values ('" + commentaire.getUser_id().getId() + "', '" + commentaire.getActualite_id().getId() + "','" + commentaire.getContenuc() + "')";
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

        String req = "INSERT INTO commentaire ( contenuc) VALUES ( ?) " ;
             try (PreparedStatement statement = con.prepareStatement(req)) {
                 //statement.setInt(1,commentaire.getUser_id());
                 //statement.setInt(2,commentaire.getActualite_id());
                 statement.setString(1,commentaire.getContenuc());
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
            int actualite_id = res.getInt(2);
            int user_id = res.getInt(3);
            String contenuc = res.getString(4);
            Date dateC = res.getDate(5);

            ActualiteService actualiteService = new ActualiteService();
            Actualite actualite = actualiteService.findById(actualite_id);
            UserService userService = new UserService();
            User user = userService.findById(user_id);

            Commentaire c1 =new Commentaire (id_c,actualite,user,contenuc);
        }
        return null;
    }

    @Override
    //done
    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> list = new ArrayList<>();
        try {
            ResultSet res = ste.executeQuery("SELECT user.ID,actualite.id,  user.Prenom, actualite.titre, contenuc FROM commentaire " +
                    "JOIN user ON commentaire.user_id = user.ID " +
                    "JOIN actualite ON commentaire.actualite_id = actualite.id;");

            while (res.next()) {
                System.out.println(res);
                System.out.println("resresresres");
                int user_id = res.getInt(1);
                int actualite_id = res.getInt(2);
                String contenuc = res.getString("contenuc");

                UserService userService = new UserService();
                User user = userService.findById(user_id);

                ActualiteService actualiteService = new ActualiteService();
                Actualite actualite = actualiteService.findById(actualite_id);
                System.out.println(actualite);

                Commentaire c1 = new Commentaire(actualite.getTitre() , contenuc , user.getPrenom());
                list.add(c1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;


    }
}