package Services;


import Entites.Reclamation;

import Entites.User;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IService<Reclamation> {
    Connection con;



    private Statement ste;

    private static ReclamationService ser;


    public ReclamationService() {
        try {
            con = ConnexionDB.getInstance().getCon(); // Assign the connection obtained from ConnexionDB to con
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ReclamationService getInstance() {
        if (ser == null) ser = new ReclamationService();
        return ser;
    }

    @Override
    public void add(Reclamation reclamation) throws SQLException {
        String req = "INSERT INTO Reclamation (id_user, type, text) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setInt(1, reclamation.getId_user().getId());
        pstmt.setString(2, reclamation.getType());
        pstmt.setString(3, reclamation.getText());
        pstmt.executeUpdate();

    }





    @Override
    public boolean delete(Reclamation reclamation) throws SQLException {
        String req2 = "DELETE FROM `reclamation` WHERE id='" + reclamation.getId() + "';";
        int rowsDeleted = ste.executeUpdate(req2);

        return rowsDeleted > 0;
    }


    @Override
    public boolean update(Reclamation reclamation) throws SQLException {

        String req = "UPDATE `reclamation` SET `type`='" + reclamation.getType()
                + "', `text`='" +reclamation.getText()
                + "', `id_user`='" + reclamation.getId_user().getId()
                + "' WHERE id='" + reclamation.getId() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }

    @Override
    public Reclamation findById(int idd) throws SQLException {
        String req = "SELECT * FROM `reclamation` WHERE id='" + idd+ "';";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int id = res.getInt(1);
            int id_user = res.getInt(2);
            String type = res.getString(3);
            String text = res.getString(4);
            UserService userService = new UserService();
            User user = userService.findById(id_user);


            return new Reclamation(id,user,type,text);
        }
        return null;
    }

    @Override
    public List<Reclamation> findAll() throws SQLException {
        List<Reclamation> lR = new ArrayList<>();

        ResultSet res = ste.executeQuery("select * from reclamation");

        while (res.next()) {
            int id = res.getInt("id");

            int id_user = res.getInt(2);
            String type = res.getString("type");
            String text = res.getString("text");
            UserService userService = new UserService();
            User user = userService.findById(id_user);


            Reclamation reclamation=new Reclamation(id,user,type,text);
//            System.out.println(o);
            lR.add(reclamation);
        }
        return lR;
    }

    public List<Reclamation> findAllByIdUser(int idUser) throws SQLException {
        List<Reclamation> reclamation = new ArrayList<>();
        String query = "SELECT * FROM reclamation WHERE id_user = " + idUser;
        ResultSet resultSet = ste.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int id_user = resultSet.getInt("id_user");
            String type = resultSet.getString("type");
            String text = resultSet.getString("text");
            UserService userService = new UserService();
            User user = userService.findById(id_user);

            Reclamation reclamation1 = new Reclamation(id,user,type,text);
            reclamation.add(reclamation1);
        }
        return reclamation;
    }




}
