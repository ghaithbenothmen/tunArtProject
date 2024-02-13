package Test;

import Utils.ConnexionDB;

import java.sql.Connection;

public class TestSingleton {

    public static void main(String[] args) {

        Connection con1 = ConnexionDB.getInstance().getCon();
        Connection con2 = ConnexionDB.getInstance().getCon();
        System.out.println(con1);
        System.out.println(con2);
    }
}