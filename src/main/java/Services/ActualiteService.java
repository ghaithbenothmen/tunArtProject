package Services;
import Entites.Commentaire;
import Utils.ConnexionDB;
import Entites.Actualite;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ActualiteService implements IServiceCommentaire<Actualite> {

    private Connection connection;
    private Statement ste;


    public ActualiteService() {
        try {
            connection = ConnexionDB.getInstance().getCon();
            ste = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    //done
    public void ajouter(Actualite actualite) {
        String uri = actualite.getImage().replace("\\", "/").trim();
        String req = "INSERT into actualite(titre,text,date,image) values ('" + actualite.getTitre() + "','" + actualite.getText() + "', '" + actualite.getDate() + "', '" + uri + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Actualite ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Actualite actualite) {
        String req = "UPDATE actualite set text = '" + actualite.getText()+ "', date = '" + actualite.getDate() + "' where id = " + actualite.getId()+ ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Actualite modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(Actualite actualite) {
        String req = "DELETE from Actualite where id = " + actualite.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Actualite supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*@Override
public void supprimer(int id) throws SQLException {
    String sql = "DELETE FROM user WHERE id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setObject(1,id);
    preparedStatement.executeUpdate();
}*/

    @Override
    //done
    public List<Actualite> afficher() {
        List<Actualite> actualite = new ArrayList<>();

        String req = "SELECT * from actualite";
        try {
            System.out.println("_____1___");
            Statement st = connection.createStatement();
            System.out.println("_____2___");
            ResultSet rs = st.executeQuery(req);
            System.out.println("_____3___");
            while (rs.next()) {
                String image="C:\\Users\\user\\Desktop\\3A11\\pidev\\PiDevWebProject\\public\\uploads\\"+rs.getString("image");

                System.out.println(rs.getInt("id"));
                actualite.add(new Actualite(rs.getInt("id"), rs.getString("titre"),rs.getString("text"), rs.getDate("date") ,image ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return actualite;
    }

    @Override
    public Actualite findById(int idd) throws SQLException {
        try {
            String req = "SELECT * FROM actualite WHERE id = " + idd;
            ResultSet res = ste.executeQuery(req);
            if (res.next()) {
                int id = res.getInt("id");
                String titre = res.getString("titre");
                Actualite actualite = new Actualite(id,titre);
                System.out.println(actualite);
                return actualite ;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving actuality: " + e.getMessage());
            throw e; // Throw the exception to be handled by the calling code
        }
        return null;
    }

}
