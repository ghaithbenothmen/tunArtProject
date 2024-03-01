package Services;

import Entites.Role;
import Entites.User;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    Connection Con;
    private static Statement ste;
    public static UserService ser;
    public UserService() {
        try {
            Con = ConnexionDB.getInstance().getCon();
            ste = Con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static UserService getInstance() {
        if (ser == null) ser = new UserService();
        return ser;
    }



    @Override
    public void add(User u) throws SQLException {
        String req = "INSERT INTO user (nom, prenom, email, mdp, tel, role,image) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, u.getNom());
        ste.setString(2, u.getPrenom());
        ste.setString(3, u.getEmail());
        ste.setString(4, u.getMdp());
        ste.setInt(5, u.getTel());


        // Convert the enum value to a string
        String roleString = u.getRole().name();
        ste.setString(6, roleString);
        ste.setString(7, u.getImage());

        int result = ste.executeUpdate();

        System.out.println(result + "Ajouté avec succès");


    }

    public boolean existEmail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ste = Con.prepareStatement(query);
        ste.setString(1, email);
        ResultSet res = ste.executeQuery();
        if (res.next()) {
            exist = true;
        }
        return exist;
    }


    @Override
    public boolean update(User u) throws SQLException {

        String req = "UPDATE `user` SET `Nom`='" + u.getNom() /*+ "', `artiste_id`='" + formation.getArtiste_id() */+ "', `Prenom`='" + u.getPrenom()
                +  "', `Email`='" + u.getEmail() +  "', `Mdp`='" + u.getMdp() +  "', `Tel`='" + u.getTel() +  "', `Role`='" + u.getRole()
                + "' WHERE id='" + u.getId() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }

    @Override
    public User findById(int id) throws SQLException {
        return null;
    }


    @Override
    public boolean delete(User u) throws SQLException {
        String req2 = "DELETE FROM `user` WHERE id='" + u.getId() + "';";
        int rowsDeleted = ste.executeUpdate(req2);

        return rowsDeleted > 0;
    }

    public List<User> rechercherParNom(String nom) throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user WHERE (role='Artiste' OR role='Client') AND (nom LIKE'%" + nom + "%' OR email LIKE'%" + nom + "%' OR prenom LIKE'%" + nom + "%')";
        Statement ste = Con.createStatement();
        ResultSet res = ste.executeQuery(req);

        while (res.next()){
            User u = new User();
            u.setId(res.getInt("id"));
            u.setTel(res.getInt("tel"));
            u.setEmail(res.getString("email"));
            u.setNom(res.getString("nom"));
            u.setPrenom(res.getString("prenom"));
            u.setRole(Role.valueOf(res.getString("role")));
            u.setMdp(res.getString("mdp"));
            users.add(u);
        }
        return users;
    }

    public void ModifMDP(String email, String mdp) throws SQLException {
        String req="UPDATE user set mdp=? WHERE email=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, mdp);
        ste.setString(2, email);
        ste.executeUpdate();
    }

    public String getNom(int id) throws SQLException {
        String req = "SELECT nom FROM user WHERE id=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet res = ste.executeQuery();
        String nom= "";
        if (res.next()){
            nom = res.getString("nom");
        }
        return nom;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> data= new ArrayList<>();
        String req = "SELECT * FROM user";
        ResultSet res= ste.executeQuery(req);
        while (res.next()){
            User u = new User();
            u.setId(res.getInt("ID"));
            u.setNom(res.getString("Nom"));
            u.setPrenom(res.getString("Prenom"));
            u.setEmail(res.getString("Email"));
            u.setMdp(res.getString("Mdp"));
            u.setTel(res.getInt("Tel"));
            u.setRole(Role.valueOf(res.getString("Role")));

            data.add(u);

        }
        return data;
    }

    public boolean existemail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = Con.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;

        }
        return exist;
    }


    /*@Override
    public List<User> recuperer() throws SQLException  {
        List<User> users = new ArrayList<>();
        String req="select * from user";
        Statement st = Con.createStatement();
        ResultSet rs =  st.executeQuery(req);
        while(rs.next()){
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setTel(rs.getInt("tel"));
            p.setEmail(rs.getString("email"));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setRole(Role.valueOf(rs.getString("role")));
            // p.setImage(rs.getString("image"));


            //p.setImage(rs.getString("image"));


            p.setMdp(rs.getString("mdp"));

            users.add(p);
        }
        return users;
    }*/


}