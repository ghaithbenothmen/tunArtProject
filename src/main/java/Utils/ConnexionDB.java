package Utils;

import java.sql.*;

public class ConnexionDB {

    private Connection con;

    private static ConnexionDB data;
    private String url = "jdbc:mysql://localhost:3306/tunart";
    private String user = "root";
    private String pwd = "";

    private ConnexionDB() {
        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getCon() {
        return con;
    }

    public static ConnexionDB getInstance() {
        if (data == null)
            data = new ConnexionDB();
        return data;
    }
}
