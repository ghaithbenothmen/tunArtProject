package Services;
import Entites.Candidatures;
import Entites.Concours;
import Entites.Votes;

import java.sql.*;
import java.util.*;
import java.sql.Date;

import Entites.Type;

import Utils.ConnexionDB;
public class ServiceVotes implements IServiceA<Votes>{

    private Connection con=ConnexionDB.getInstance().getCon();

    private Statement ste;
    private final ServiceConcours ser=new ServiceConcours();


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
    public void add(Votes votes) throws SQLException {

        java.util.Date utilDate = votes.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String req="INSERT INTO `vote` ( `Date`, `ID_concours`, `ID_user`) VALUES ('"+sqlDate+"', '"+votes.getID_concours()+"', '"+votes.getID_user()+"');";
        ste.executeUpdate(req);
    }

    public void ajouterPST(Votes votes) throws SQLException {

        java.util.Date utilDate = votes.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String req="INSERT INTO `vote` ( `Date`, `ID_concours`, `ID_user`) VALUES ( ?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);


        pre.setDate(1,sqlDate);
        pre.setObject(2,votes.getID_concours());
        pre.setObject(3,votes.getID_user());


        pre.executeUpdate();
    }

    @Override
    public void deletea(Votes votes) {
        String query = "DELETE FROM `vote` WHERE ID_vote = "+votes.getId()+"";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updatea(Votes votes)  {

        java.util.Date utilDate = votes.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String query = "UPDATE `vote` SET `date`=?,`ID_concours`=?,`ID_user`=?, WHERE ID_vote=?";
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
    public Votes findById(int id) throws SQLException {
        Votes T;
        ResultSet res=ste.executeQuery("select * from vote WHERE refrence=="+id+"");
        T = (Votes) res;
        return T;
    }

    @Override
    public List<Votes> findAll() throws SQLException {

        List<Votes> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from vote");
        while (res.next()) {

            int id = res.getInt(1);
            Date Date = res.getDate("Date");
            int ID_concours = res.getInt("ID_concours");
            int ID_user = res.getInt("ID_user");

            Votes p1 = new Votes(id, Date, ID_concours, ID_user);

            list.add(p1);
        }
        return list;
    }

    public List<Votes> diplayListsortedbyDateVote() throws SQLException {
        List<Votes> sortedUsers = this.findAll();
        Collections.sort(sortedUsers, Comparator.comparing(Votes::getDate));
        return sortedUsers;
    }
    public boolean Unique(Votes vote)
    {
        List<Votes> list= null;
        try {
            list = this.findAll();
            System.out.println(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Votes i : list)
        {
            if (i.getID_concours()==vote.getID_concours()&&i.getID_user()==vote.getID_user())
            {
                return false;
            }
        }
        return true;
    }
    public int BestConcours(Concours c)
    {

        int vote_count = 0;
        String query = "SELECT COUNT(*) AS vote_count FROM vote WHERE ID_concours = "+c.getReference()+"";
        try {

            /*PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();
            */
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                vote_count = resultSet.getInt("vote_count");
            }


        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        /*
        List<Votes> listV= null;
        try {
            listV = this.findAll();
            System.out.println(listV);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Concours> listC= null;
        try {
            listC = ser.findAll();
            System.out.println(listC);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Integer> Map = new HashMap<>(listC.size());
        for (Integer i:Map.keySet())
            i=null;
        for (Integer i:Map.values())
            i=0;
        for (Concours i:listC)
        {
            Map.keySet().add(i.getReference());
        }
        for (Votes i:listV)
        {
            if (Map.containsKey(i.getID_concours()))
                System.out.println(Map.get(i.getID_concours()).getClass());
        }*/
        // bech naamel map key teeha id concours values teeha el nombre taa les votes
        //nparcouri lista taa lvotes ken el id concours kif el key ++ fel value
        //
        return vote_count;
    }

    public void deletByConcours(Concours concours) {

        List <Votes> Listc= new ArrayList<>();
        try {
            Listc=this.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Votes i :Listc)
        {
            if (i.getID_concours()==concours.getReference())
                this.deletea(i);
        }

    }

}

