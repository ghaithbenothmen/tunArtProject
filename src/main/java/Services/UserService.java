package Services;

import Entites.*;
import Utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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



    public void add(User t) throws SQLException {

        String uri = t.getImage().replace("\\", "/");
        String req = "INSERT INTO user (Nom,Prenom,Email,Mdp,Tel,Role,image) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement stmt = Con.prepareStatement(req);
        stmt.setString(1, t.getNom());
        stmt.setString(2, t.getPrenom());
        stmt.setString(3, t.getEmail());
        stmt.setString(4, t.getMdp());
        stmt.setInt(5, t.getTel());
        String roleString = t.getRole().name();
        stmt.setString(6, roleString);
        stmt.setString(7, uri);
        int result = stmt.executeUpdate();


        System.out.println(result + " enregistrement ajouté.");


    }
    /*@Override
    public void add(User u) throws SQLException {
        String uri = u.getImage().replace("\\", "/");
        String req = "INSERT INTO user (Nom, Prenom, Email, Mdp, Tel, Role,image) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, u.getNom());
        ste.setString(2, u.getPrenom());
        ste.setString(3, u.getEmail());
        ste.setString(4, u.getMdp());
        ste.setInt(5, u.getTel());


        // convert the enum value to a string
        String roleString = u.getRole().name();
        ste.setString(6, roleString);
        ste.setString(7, uri);
        System.out.println(u);
        System.out.println(uri);

        int result = ste.executeUpdate();

        System.out.println(result + "Ajouté avec succès");


    }*/

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
        String uri = u.getImage();
        System.out.println(u);
            uri = uri.replace("\\", "/");


        String req = "UPDATE `user` SET `Nom`='" + u.getNom() + "', `Prenom`='" + u.getPrenom()
                +  "', `Email`='" + u.getEmail() +  "', `Mdp`='" + u.getMdp() +   "', `Tel`='" + u.getTel() +  "', `Role`='" + u.getRole() +  "', `image`='" + uri
                + "' WHERE id='" + u.getId() + "';";

        int rowsUpdated = ste.executeUpdate(req);

        return rowsUpdated > 0;
    }


    @Override
    public User findById(int idd) throws SQLException {

        String req = "SELECT * FROM `user` WHERE id='" +idd + "';";
        ResultSet res = ste.executeQuery(req);

        if (res.next()) {
            int id = res.getInt(1);
            String Nom = res.getString(2);
            String Prenom = res.getString(3);
            String Email = res.getString(4);
            String Mdp = res.getString(5);
            int Tel = res.getInt(6);
            Role role = Role.valueOf(res.getString(7));
            String image = res.getString(8);






            return new User( id,  Tel, Nom, Prenom, Email, Mdp, image,role);
        }

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
        List<User> data = new ArrayList<>();
        String req = "SELECT * FROM user";
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            User u = new User();
            u.setId(res.getInt("ID"));
            u.setNom(res.getString("Nom"));
            u.setPrenom(res.getString("Prenom"));
            u.setEmail(res.getString("Email"));
            u.setMdp(res.getString("Mdp"));
            u.setTel(res.getInt("Tel"));
            u.setImage(res.getString("image"));

            // Get the role string from the database
            String roleString = res.getString("Role");

            // Convert the role string to the corresponding enum constant
            if (roleString != null) {
                try {
                    Role role = Role.valueOf(roleString);
                    u.setRole(role);
                } catch (IllegalArgumentException e) {
                    // Handle the case where the role string does not match any enum constant
                    // For example, you can set a default role or log a warning
                    // u.setRole(Role.DEFAULT_ROLE); // Set a default role
                    // logger.warn("Invalid role: " + roleString); // Log a warning
                }
            } else {}

            data.add(u);
        }
        return data;
    }
    public List<User> findAll1() throws SQLException {
        List<User> data= new ArrayList<>();
        String ch="ARTISTE" ;
        String req = "SELECT * FROM user where role=?";
        PreparedStatement ste = Con.prepareStatement(req);
        ste.setString(1, ch);
        System.out.println(req);
        ResultSet res= ste.executeQuery();
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

    public boolean inscrire(int userId, int formationId) throws SQLException {
        // Vérifie si l'utilisateur est déjà inscrit à la formation
        if (isInscrit(userId, formationId)) {
            System.out.println("L'utilisateur est déjà inscrit à cette formation.");
            return false;
        }

        // Ajoute l'inscription à la base de données
        String req = "INSERT INTO inscription (user_id, formation_id) VALUES (?, ?)";
        try (PreparedStatement pst = Con.prepareStatement(req)) {
            pst.setInt(1, userId);
            pst.setInt(2, formationId);
            pst.executeUpdate();
            System.out.println("Inscription réussie pour l'utilisateur avec l'ID " + userId + " à la formation avec l'ID " + formationId);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'inscription de l'utilisateur avec l'ID " + userId + " à la formation avec l'ID " + formationId + ": " + e.getMessage());
            throw e;
        }
    }
    public boolean isInscrit(int userId, int formationId) throws SQLException {
        String query = "SELECT COUNT(*) FROM inscription WHERE user_id = ? AND formation_id = ?";
        try (PreparedStatement statement = Con.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, formationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
