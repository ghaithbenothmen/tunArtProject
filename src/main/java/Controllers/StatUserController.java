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

    private Connection connection; // Declare the Connection variable

    public void initialize() {
        // Initialize the bar chart
        xAxis.setLabel("Status");
        yAxis.setLabel("Nombre d'utilisateurs");

        try {
            // Initialize the database connection
            connection = ConnexionDB.getInstance().getCon();

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
/*
            XYChart.Series<String, Number> totalSeries = new XYChart.Series<>();
            totalSeries.setName("Total");
            totalSeries.getData().add(new XYChart.Data<>("Total", totalCount));
*/
            // Add series to the bar chart
            barChart.getData().addAll(approvedSeries, blockedSeries /*totalSeries*/);
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

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainContainer.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /*private int getTotalUserCount() throws SQLException {
        // Perform database query to get total count of users
        String query = "SELECT COUNT(*) FROM user";
        int totalCount = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        }

        return totalCount;
    }*/

}