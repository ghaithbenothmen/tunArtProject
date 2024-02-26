package Service;
import Entites.Votes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import Entites.Type;

import Utils.DataSource;
public class ServiceVotes implements IService<Votes>{

    private Connection con=DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceVotes()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }

    }
    @Override
    public void ajouter(Votes votes) throws SQLException {

        java.util.Date utilDate = votes.getDate_votes();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String req="INSERT INTO `vote` ( `Description`, `Date`, `ID_concours`, `ID_user`) VALUES ( '"+votes.getDescription()+"', '"+sqlDate+"', '"+votes.getID_concours()+"', '"+votes.getID_concours()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(Votes votes) throws SQLException {

        java.util.Date utilDate = votes.getDate_votes();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String req="INSERT INTO `vote` ( `Description`, `Date`, `ID_concours`) VALUES ( ?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setString(1,votes.getDescription());
        pre.setDate(2,sqlDate);
        pre.setObject(3,votes.getID_concours());


        pre.executeUpdate();
    }

    @Override
    public void delete(Votes votes) {
        String query = "DELETE FROM `vote` WHERE ID_vote = "+votes.getID_vote()+"";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void update(Votes votes)  {

        java.util.Date utilDate = votes.getDate_votes();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String query = "UPDATE `vote` SET `Description`=?,`Date`=?,`ID_concours `=? ,`ID_user `=? WHERE ID_vote =?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, votes.getDescription());
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setObject(3, votes.getID_concours());
            preparedStatement.setInt(4, votes.getID_vote());
            preparedStatement.setInt(5, votes.getID_user());



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
    public Votes findById(int id) throws SQLException {
        Votes T;
        ResultSet res=ste.executeQuery("select * from vote WHERE refrence=="+id+"");
        T = (Votes) res;
        return T;
    }

    @Override
    public List<Votes> readAll() throws SQLException {

        List<Votes> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from vote");
        while (res.next()) {

            int id = res.getInt(1);
            String Description = res.getString(2);
            Date Date = res.getDate(3);
            int ID_concours = res.getInt(4);
            int ID_user = res.getInt(5);

            /*Type type = null;
            try {
                type = Type.valueOf(SType);
            } catch (IllegalArgumentException e) {
                System.out.println("invalid value of type");
            }*/

            Votes p1 = new Votes(id, Description, Date, ID_concours, ID_user);

            list.add(p1);
        }


        return list;
    }

}

