package Controllers;

import Entites.Role;
import Entites.User;

import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static Controllers.LoginController.UserConnected;

public class AfficherArtistesController implements Initializable{

    @FXML
    private GridPane gridart;

    @FXML
    private TextField searchart;


    private final UserService userService = new UserService();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private List<User> userListArtiste = FXCollections.observableArrayList();

    public List<User> getAllUserCard() throws SQLException {
        userList.addAll(userService.findAll());
        userListArtiste = userList.stream().filter(user -> user.getRole().equals(Role.ARTISTE)).collect(Collectors.toList());
        System.out.println(userListArtiste);
        return userListArtiste;
    }



    public void displayAllUserCard(List<User> userList) {


        gridart.getRowConstraints().clear();
        gridart.getColumnConstraints().clear();
        int row = 0;
        int column = 0;

        for(User user : userList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ArtisteCard.fxml"));
                AnchorPane pane = loader.load();
                ArtisteCardController ACard = loader.getController();
                ACard.setData(user);
                ACard.setUser(user);
                gridart.addRow(row);
                gridart.add(pane, column, row);

                column++;
                if(column == 3){
                    column = 0;
                    row +=1;
                }
            } catch (Exception e){
                System.out.println(""+e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            getAllUserCard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayAllUserCard(userListArtiste);

        searchart.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);

        });
    }
    private void filterData(String searchText)  {


        ObservableList<User> filteredList = FXCollections.observableArrayList();
        for (User user : userListArtiste) {
            if (user.getNom().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(user);
            }
        }
        gridart.getChildren().clear();
        displayAllUserCard(filteredList);

    }
}

