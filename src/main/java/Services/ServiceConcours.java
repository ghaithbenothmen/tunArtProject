package Services;
import Entites.Concours;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.Date;

import Entites.Type;
import Utils.ConnexionDB;
public class ServiceConcours implements IServiceA<Concours>{

    private Connection con=ConnexionDB.getInstance().getCon();

    private Statement ste;

    public ServiceConcours()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }

    }
    @Override
    public void add(Concours concours) throws SQLException {

        java.util.Date utilDate = concours.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String req="INSERT INTO `concours` ( `date`, `type`, `prix`, `Lien`, `nom`) VALUES ( '"+sqlDate+"', '"+concours.getType()+"', '"+concours.getPrix()+"', '"+concours.getLien()+"', '"+concours.getNom()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(Concours concours) throws SQLException {

        java.util.Date utilDate = concours.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String req="INSERT INTO `concours` ( `date`, `type`, `prix`, `Lien`, `nom`) VALUES ( ?,?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setDate(1,sqlDate);
        pre.setString(2,concours.getType().toString());
        pre.setInt(3,concours.getPrix());
        pre.setString(4,concours.getLien());
        pre.setString(4,concours.getNom());

        pre.executeUpdate();
    }

    @Override
    public void deletea(Concours concours) {
        String query = "DELETE FROM `concours` WHERE refrence="+concours.getReference()+"";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void updatea(Concours concours)  {

        java.util.Date utilDate = concours.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String query = "UPDATE `concours` SET `date`=?,`type`=?,`prix`=?,`lien`=?,`nom`=? WHERE refrence=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, concours.getType().toString());
            preparedStatement.setInt(3, concours.getPrix());
            preparedStatement.setString(4, concours.getLien());
            preparedStatement.setString(5, concours.getNom());
            preparedStatement.setInt(6, concours.getReference());

            int rows = preparedStatement.executeUpdate();
            if(rows > 0) {
                System.out.println("updated");
            } else {
                System.out.println("not updated");
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("ya khra");
        }

    }

    @Override
    public Concours findById(int id) throws SQLException {
        Concours T;
        ResultSet res=ste.executeQuery("select * from concours WHERE refrence=="+id+"");
        T = (Concours) res;
        return T;
    }

    @Override
    public List<Concours> findAll() throws SQLException {

        List<Concours> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from concours");
        while (res.next()) {

            int id = res.getInt(1);
            Date date = res.getDate("date");
            String SType = res.getString("type");
            int prix = res.getInt("prix");
            String lien = res.getString("lien");
            String nom = res.getString("nom");

            Type type = null;
            try {
                type = Type.valueOf(SType);
            } catch (IllegalArgumentException e) {
                System.out.println("invalid value of type");
            }

            Concours p1 = new Concours(id, prix, date, type, lien, nom);

            list.add(p1);
        }


        return list;
    }

    public Concours findByName(String name) {
        String query = "SELECT * FROM concours WHERE nom = '" + name + "'";
        try {

            ResultSet resultSet = ste.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("Refrence ");
                return new Concours(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Concours> diplayListsortedbyMontant() throws SQLException {
        List<Concours> sortedUsers = this.findAll();
        Collections.sort(sortedUsers, Comparator.comparing(Concours::getPrix));
        return sortedUsers;
    }
    public List<Concours> diplayListsortedbyDate() throws SQLException {
        List<Concours> sortedUsers = this.findAll();
        Collections.sort(sortedUsers, Comparator.comparing(Concours::getDate));
        return sortedUsers;
    }

}

