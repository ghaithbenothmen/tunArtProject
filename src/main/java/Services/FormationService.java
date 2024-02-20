package Services;

import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Utils.ConnexionDB;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormationService implements IService<Formation> {
    private Statement ste;

    private static FormationService ser;

    public FormationService() {
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
        String req = "INSERT INTO `formation` (`id`,`nom`, `dateDebut`, `dateFin`, `niveau`, `description`, `cat_id`) " +
                "VALUES (NULL, '" + formation.getNom() + /*"', '" + formation.getArtiste_id() + */ "', '" + formation.getDateDebut() + "', '" + formation.getDateFin() + "', '" + formation.getNiveau() + "', '" + formation.getDescription() + "', '" + formation.getCat_id().getId() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Formation formation) throws SQLException {
        String req2 = "DELETE FROM `formation` WHERE id='" + formation.getId() + "';";
        int rowsDeleted = ste.executeUpdate(req2);

        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Formation formation) throws SQLException {
        String req = "UPDATE `formation` SET `nom`='" + formation.getNom() /*+ "', `artiste_id`='" + formation.getArtiste_id() */+ "', `dateDebut`='" + formation.getDateDebut()
                +  "', `dateFin`='" + formation.getDateFin() +  "', `niveau`='" + formation.getNiveau() +  "', `description`='" + formation.getDescription() +  "', `cat_id`='" + formation.getCat_id().getId()
                + "' WHERE id='" + formation.getId() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }

    @Override
    public Formation findById(int idd) throws SQLException {
        String req = "SELECT * FROM `formation` WHERE id='" +idd + "';";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int id = res.getInt(1);
            String nom = res.getString(2);
            int artiste_id = res.getInt(3);
            Date dateDebut = res.getDate("dateDebut");
            Date dateFin = res.getDate("dateFin");
            Niveau niveau = Niveau.valueOf(res.getString("niveau"));

            String description = res.getString("description");
            int cat_id = res.getInt("cat_id");

            CategorieService categorieService = new CategorieService();
            Categorie categorie = categorieService.findById(cat_id);


            return new Formation(id, nom, dateDebut,dateFin,niveau,description,categorie);
        }

        return null;
    }

    @Override
    public List<Formation> findAll() throws SQLException {

            List<Formation> l1 = new ArrayList<>();
            ResultSet res = ste.executeQuery("select  * from formation");
            while (res.next()) {
                int id = res.getInt(1);
                String nom = res.getString(2);
                //int artiste_id = res.getInt(3);
                Date dateDebut = res.getDate("dateDebut");
                Date dateFin = res.getDate("dateFin");
                Niveau niveau = Niveau.valueOf(res.getString("niveau"));
                String description = res.getString("description");
                int cat_id = res.getInt("cat_id");
                /*System.out.println("id :" + id + "nom :" + nom + "artiste :" + artiste_id + " dateDebut :"
                        + dateDebut+ " dateFin :" +dateFin+ " niveau :" +niveau+ " description :" +description+ " cat_id :" +cat_id);*/

                CategorieService categorieService = new CategorieService();
                Categorie categorie = categorieService.findById(cat_id);

                Formation f = new Formation(id, nom, dateDebut,dateFin,niveau,description,categorie);
                //System.out.println(f);
                l1.add(f);

            }
            return l1;
        }

}
