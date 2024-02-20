package Test;

import Entites.Evenement;
import Entites.Formation;
import Entites.Niveau;
import Services.EvenementService;
import Services.FormationService;
import java.sql.*;
import java.sql.SQLException;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        FormationService ser=FormationService.getInstance();
        Date dd= java.sql.Date.valueOf("2000-10-22");
        Date df= java.sql.Date.valueOf("2000-10-23");

        Formation f1=new Formation("bbb",2, dd,  df, Niveau.DIFFICILE,"hello",2);

        try { ser.add(f1);}catch (SQLException e)
        { System.out.println(e);}


        EvenementService serEv=new EvenementService();
        Date dEv= java.sql.Date.valueOf("25-02-2024");

        Evenement e1 =new Evenement(1,"Tunis","Soirée Rap",520,dEv);

        try { serEv.add(e1); }
        catch (SQLException e)
        { System.out.println(e); }

    }

}

