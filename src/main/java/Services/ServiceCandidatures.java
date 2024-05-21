package Services;

import Entites.Concours;
import Entites.Votes;

import java.sql.*;
import java.util.*;
import java.sql.Date;
import Utils.ConnexionDB;
import Entites.Candidatures;

public class ServiceCandidatures implements IServiceA<Candidatures>{

        private Connection con= ConnexionDB.getInstance().getCon();

        private Statement ste;
        private final ServiceConcours ser=new ServiceConcours();


        public ServiceCandidatures()
        {
            try {
                ste= con.createStatement();
            }catch (SQLException e)
            {
                System.out.println(e);
            }
        }
        @Override
        public void add(Candidatures votes) throws SQLException {

            java.util.Date utilDate = votes.getDate();
            Date sqlDate = new Date(utilDate.getTime());
            String req="INSERT INTO `candidature` ( `Date`, `ID_concours`, `ID_user`) VALUES ('"+sqlDate+"', '"+votes.getID_concours()+"', '"+votes.getID_user()+"');";
            ste.executeUpdate(req);
        }
        public void ajouterPST(Votes votes) throws SQLException {

            java.util.Date utilDate = votes.getDate();
            Date sqlDate = new Date(utilDate.getTime());

            String req="INSERT INTO `candidature` ( `Date`, `ID_concours`, `ID_user`) VALUES ( ?,?,?);";
            PreparedStatement pre=con.prepareStatement(req);


            pre.setDate(1,sqlDate);
            pre.setObject(2,votes.getID_concours());
            pre.setObject(3,votes.getID_user());


            pre.executeUpdate();
        }

        @Override
        public void deletea(Candidatures candidatures) {
            String query = "DELETE FROM `candidature` WHERE ID_candidature = "+candidatures.getId()+"";

            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();

            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }

        @Override
        public void updatea(Candidatures votes)  {

            java.util.Date utilDate = votes.getDate();
            Date sqlDate = new Date(utilDate.getTime());

            String query = "UPDATE `candidature` SET `date`=?,`ID_concours`=?,`ID_user`=?, WHERE ID_vote=?";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);

                preparedStatement.setDate(1, sqlDate);
                preparedStatement.setObject(2, votes.getID_concours());
                preparedStatement.setInt(3, votes.getID_user());
                preparedStatement.setInt(4, votes.getId());



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
        public Candidatures findById(int id) throws SQLException {
            Candidatures T;
            ResultSet res=ste.executeQuery("select * from vote WHERE refrence=="+id+"");
            T = (Candidatures) res;
            return T;
        }

        @Override
        public List<Candidatures> findAll() throws SQLException {

            List<Candidatures> list=new ArrayList<>();
            ResultSet res=ste.executeQuery("select * from candidature");
            while (res.next()) {

                int id = res.getInt(1);
                Date Date = res.getDate("Date");
                int ID_concours = res.getInt("idConcours");
                int ID_user = res.getInt("idUser");

                Candidatures p1 = new Candidatures(id, Date, ID_concours, ID_user);

                list.add(p1);
            }
            return list;
        }

        public List<Candidatures> diplayListsortedbyDateVote() throws SQLException {
            List<Candidatures> sortedUsers = this.findAll();
            Collections.sort(sortedUsers, Comparator.comparing(Candidatures::getDate));
            return sortedUsers;
        }
        public boolean Unique(Candidatures ca)
        {
            List<Candidatures> list= null;
            try {
                list = this.findAll();
                System.out.println(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Candidatures i : list)
            {
                if (i.getID_concours()==ca.getID_concours()&&i.getID_user()==ca.getID_user())
                {
                    return false;
                }
            }
            return true;
        }

    public void deletByConcours(Concours concours) {

        List <Candidatures> Listc= new ArrayList<>();
        try {
            Listc=this.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Candidatures i :Listc)
        {
            if (i.getID_concours()==concours.getReference())
                this.deletea(i);
        }

    }

    public int BestConcours(Concours c)
    {

        int candidature_count = 0;
        String query = "SELECT COUNT(*) AS candidature_count FROM candidature WHERE ID_concours = "+c.getReference()+"";
        try {

            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                candidature_count = resultSet.getInt("candidature_count");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println(candidature_count);
        return candidature_count;
    }

}
