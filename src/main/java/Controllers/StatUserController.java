package Controllers;

import Entites.Role;
import Utils.ConnexionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Entites.Role.ARTISTE;
import static Entites.Role.CLIENT;

public class StatUserController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label helo;
    @FXML
    private Label totart;

    @FXML
    private Label totcl;

    private Connection connection; // Declare the Connection variable

    public void initialize() {
        // Initialize the bar chart
        xAxis.setLabel("Status");
        yAxis.setLabel("Nombre d'utilisateurs");
        try {
            // Initialize the database connection
            connection = ConnexionDB.getInstance().getCon();
            // Vérifier si la connexion est null
            if (connection != null) {
                // Query database to get counts
                int Artiste = getCountFromDatabase(ARTISTE);
                int Client = getCountFromDatabase(CLIENT);
                /*int totalCount = getTotalUserCount();*/
                // Create series for approved, blocked, and total clients
                XYChart.Series<String, Number> approvedSeries = new XYChart.Series<>();
                approvedSeries.setName("Artiste");
                approvedSeries.getData().add(new XYChart.Data<>("Artiste", Artiste));
                XYChart.Series<String, Number> blockedSeries = new XYChart.Series<>();
                blockedSeries.setName("Client");
                blockedSeries.getData().add(new XYChart.Data<>("Client", Client));
                // Add series to the bar chart
                barChart.getData().addAll(approvedSeries, blockedSeries);
                // Appeler totUser après avoir initialisé la connexion
                totUser();
                totArtistes();
                totClients();
            } else {
                System.out.println("La connexion à la base de données est null.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while initializing the bar chart: " + e.getMessage());
        }
    }

    private int getCountFromDatabase(Role role) throws SQLException {
        // Perform database query to get count of clients based on approval status
        String query = "SELECT COUNT(*) FROM user WHERE Role = ?";
        int count = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(role));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }

        return count;
    }



   public void totUser() {
        String sql= "SELECT COUNT(id) FROM user WHERE Role = ? OR Role = ?";
        int countData = 0;
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ARTISTE));
            prepare.setString(2, String.valueOf(CLIENT));
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            helo.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totArtistes() {
        String sql= "SELECT COUNT(id) FROM user WHERE Role = ?";
        int countData = 0;
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, String.valueOf(ARTISTE));
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            totart.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totClients() {
        String sql= "SELECT COUNT(id) FROM user WHERE Role = ?";
        int countData = 0;
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, String.valueOf(CLIENT));
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            totcl.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
