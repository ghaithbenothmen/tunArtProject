package Services;

import Utils.ConnexionDB;

import java.sql.*;

public class RaitingService {
    private Connection connection;
    private static RaitingService instance;

    public RaitingService() {
        connection = ConnexionDB.getInstance().getCon();
    }

    public static RaitingService getInstance() {
        if (instance == null) {
            instance = new RaitingService();
        }
        return instance;
    }

    public void saveRating(int userId, int formationId, double rating) throws SQLException {
        String query = "INSERT INTO ratings (user_id, formation_id, rating_value) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE rating_value = VALUES(rating_value)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, formationId);
            stmt.setDouble(3, rating);
            stmt.executeUpdate();
        }
    }

    public double getRating(int userId, int formationId) throws SQLException {
        String query = "SELECT rating_value FROM ratings WHERE user_id = ? AND formation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, formationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("rating_value");
                }
            }
        }
        return 0; // Par défaut, si aucune évaluation n'est trouvée
    }

    public double getAverageRating(int formationId) throws SQLException {
        String query = "SELECT AVG(rating_value) AS average_rating FROM ratings WHERE formation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, formationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("average_rating");
                }
            }
        }
        return 0; // Par défaut, si aucune évaluation n'est trouvée
    }

    public boolean hasUserRatedFormation(int userId, int formationId) throws SQLException {
        // Requête SQL pour vérifier si l'utilisateur a déjà noté la formation
        String sql = "SELECT COUNT(*) FROM ratings WHERE user_id = " + userId + " AND formation_id = " + formationId;

        try (
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Si le count est supérieur à 0, l'utilisateur a déjà noté la formation
            }
        }

        return false;
    }

    public double getUserRatingForFormation(int userId, int formationId) throws SQLException {
        // Requête SQL pour obtenir la note donnée par l'utilisateur à la formation
        String sql = "SELECT rating_value FROM ratings WHERE user_id = " + userId + " AND formation_id = " + formationId;

        try (
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("rating_value"); // Renvoie la note donnée par l'utilisateur
            }
        }

        return 0.0; // Si l'utilisateur n'a pas encore noté la formation, renvoie 0.0
    }
}
