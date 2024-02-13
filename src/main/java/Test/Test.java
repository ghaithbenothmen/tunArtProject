package Test;

import Entites.Formation;
import Entites.Niveau;
import Services.FormationService;

import java.sql.*;

public class Test {


    public static void main(String[] args) {

        FormationService ser=FormationService.getInstance();
        Date dd=Date.valueOf("2000-10-22");
        Date df=Date.valueOf("2000-10-23");

        Formation f1=new Formation("bbb",2, dd,  df, Niveau.DIFFICILE,"hello",2);

        try {
            ser.add(f1);
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }

}

