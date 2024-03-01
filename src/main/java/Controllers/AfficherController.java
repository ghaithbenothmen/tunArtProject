package Controllers;


import Entites.Oeuvre;
import Entites.User;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;

public class AfficherController implements Initializable {
    @FXML
    private Label Categorie;


    @FXML
    private Label contactArtiste;


    @FXML
    private ImageView imgArtiste;

    @FXML
    private Label nomArtiste;



    @FXML
    private Button add;

//    @FXML
//    private Button delete;

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchOeuvre;

//    @FXML
//    private Button update;






    @FXML
    void ajouterOeuvre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterOeuvre.fxml"));
            Parent root = loader.load();
            AjouterOeuvreControler controller = loader.getController();
            controller.setParentController(this); // Pass the current controller to the AddFormationController
            Stage stage = new Stage();
            stage.setTitle("Add Oeuvre");
            stage.setScene(new Scene(root));


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
//    public void refreshScrollPane() throws SQLException {
//        grid.getChildren().clear();
//        getAllOeuvreCard();
////       oeuvreList.addAll(oeuvreService.findAll());
//
//    }
    public void refreshScrollPane(int id) throws SQLException {
        grid.getChildren().clear();
        List<Oeuvre> oeuvre = oeuvreService.findByUserId(id);
        displayAllOeuvreCard(oeuvre);
    }

    private final OeuvreService oeuvreService = new OeuvreService();
    private ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();

    public ObservableList<Oeuvre> getAllOeuvreCard() throws SQLException {
        oeuvreList.addAll(oeuvreService.findAll());
        return oeuvreList;
    }
//    int row = 0;
//    int column=0;

    public void displayAllOeuvreCard(List<Oeuvre> oeuvreList) {
//        oeuvreList.clear();
//        oeuvreList.addAll(oeuvreService.findAll());

        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
        int row = 0;
        int column = 0;

        for(Oeuvre oeuvre : oeuvreList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../OeuvreCard.fxml"));
                AnchorPane pane = loader.load();
                OeuvreCardController OCard = loader.getController();
                OCard.setData(oeuvre);
                OCard.setOeuvre(oeuvre);
                grid.addRow(row);
                grid.add(pane, column, row);

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            if (UserConnected.getRole().toString().equals("ARTISTE")){
                add.setVisible(true);
            }else {
                add.setVisible(false);
            }
            getAllOeuvreCard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        displayAllOeuvreCard(oeuvreList);
        searchOeuvre.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);

        });

    }
    private void filterData(String searchText)  {
        int artisteId = UserConnected.getId();
        ObservableList<Oeuvre> filteredList = FXCollections.observableArrayList();
        for (Oeuvre oeuvre : oeuvreList) {
            if (oeuvre.getNom_Ouvre().toLowerCase().contains(searchText.toLowerCase())&&(oeuvre.getArtiste_id().getId()==artisteId)) {
                filteredList.add(oeuvre);
            }
        }
        grid.getChildren().clear();
        displayAllOeuvreCard(filteredList);
    }

    private Image image;
    public void serData(User user){

        nomArtiste.setText(user.getNom()+" "+user.getPrenom());

        contactArtiste.setText(user.getEmail());
        Categorie.setText(user.getRole().toString());

//        String path = user.getImage();
//        System.out.println(path) ;
//        try {
//
//            image = new Image(new File(path).toURI().toURL().toString(),207,138,false,true);
//
//            imgArtiste.setImage(image);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }




}
