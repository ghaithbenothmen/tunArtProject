package Test;

import Entites.Formation;
import Entites.Niveau;
import Services.FormationService;

import java.sql.*;
import java.util.List;

public class Test {


    public static void main(String[] args) {

        FormationService ser=FormationService.getInstance();
        Date dd=Date.valueOf("2000-10-22");
        Date df=Date.valueOf("2000-10-23");

        Formation f1=new Formation("bbb",2, dd,  df, Niveau.DIFFICILE,"hello",2);

        //add
       try {
            ser.add(f1);
            System.out.println("added");
        }catch (SQLException e)
        {
            System.out.println(e);
        }

       //show all
        try {
         List<Formation> l= ser.findAll();
            System.out.println(l);
        }catch (SQLException e)
        {
            System.out.println(e);
        }

        //delete
/*   hedhya l delete ye jme3a f comm
        try {

            FormationService formationService = FormationService.getInstance();


            Formation formationToDelete = new Formation();
            formationToDelete.setId(1);


            boolean deleted = formationService.delete(formationToDelete);

            if (deleted) {
                System.out.println("Formation deleted successfully");
            } else {
                System.out.println("Failed to delete formation");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

*/


        //update
        try {

            FormationService formationService = FormationService.getInstance();

            Formation formationToUpdate = new Formation();
            formationToUpdate.setId(2);
            formationToUpdate.setNom("foulen2");
            formationToUpdate.setArtiste_id(2);

            Date ddU=Date.valueOf("2000-10-30");
            formationToUpdate.setDateDebut(ddU);

            Date dfU=Date.valueOf("2000-11-23");
            formationToUpdate.setDateFin(dfU);

            formationToUpdate.setNiveau(Niveau.FACILE);
            formationToUpdate.setDescription("desc update");
            formationToUpdate.setCat_id(5);

            // Mettez à jour la formation
            boolean updated = formationService.update(formationToUpdate);

            if (updated) {
                System.out.println("Formation "+formationToUpdate.getNom()+"mise à jour avec succès");
            } else {
                System.out.println("Échec de la mise à jour de la formation");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }
    }

}

