package Controllers;

import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

import Entites.Formation;
import Entites.Oeuvre;
import Entites.TypeOeuvre;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOeuvreControler {


    private final OeuvreService oeuvreService = new OeuvreService();

    @FXML
    private GridPane grid;
    @FXML
    private ImageView imagePreview;
    @FXML
    private TableColumn<Oeuvre, Integer> Ref;
    @FXML
    private TableView<Oeuvre> OeuvreTable;

    @FXML
    private TableColumn<Oeuvre, String> nom_Oeuvre;

    @FXML
    private TableColumn<Oeuvre, String> img;

    @FXML
    private TableColumn<Oeuvre, String> description;

    @FXML
    private TableColumn<Oeuvre, TypeOeuvre> TypeOeuvre;

    @FXML
    private TableColumn<Oeuvre, Date> date_Publication;

    @FXML
    private TableColumn<Oeuvre, Boolean> note;

    @FXML
    private TextField searchOeuvre;

    private ObservableList<Oeuvre> OeuvreList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        OeuvreList.addAll(oeuvreService.findAll());
        System.out.println(oeuvreService.findAll());
        Ref.setCellValueFactory(new PropertyValueFactory<>("Ref"));
        nom_Oeuvre.setCellValueFactory(new PropertyValueFactory<>("nom_Ouvre"));
        OeuvreList.forEach(oeuvre -> {
            // Retrieve image path from the database
            String imagePath = oeuvre.getImg().toString(); // Assuming getImage() returns the path to the image file

            // Load the image using the path
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath);
                oeuvre.setImg(String.valueOf(image)); // Assuming you have a setImage method in your Oeuvre class
            }
        });
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        TypeOeuvre.setCellValueFactory(new PropertyValueFactory<>("TypeOeuvre"));
        date_Publication.setCellValueFactory(new PropertyValueFactory<>("date_Publication"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));





        OeuvreTable.setItems(OeuvreList);

        refreshTable();
        //search
        searchOeuvre.textProperty().

                addListener((observable, oldValue, newValue) ->

                {
                    filterData(newValue);
                });

        OeuvreTable.setRowFactory(tv ->

        {
            TableRow<Oeuvre> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Oeuvre oeuvre = row.getItem();
                    openUpdateOeuvrePage(oeuvre);
                }
            });
            return row;
        });
//        try {
//
//
//            List<Oeuvre> o = oeuvreService.findAll();
//            int row = 0;
//            int column = 0;
//
//
//
//            System.out.println(o);
//
//
//            for (int i = 0; i < o.size(); i++){
//
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
//                AnchorPane pane = loader.load();
//                AfficherOeuvreControler controller = loader.getController();
////                if(o.get(i).getRole().equals("Artiste")||users.get(i).getRole().equals("simple utilisateur")){
//                    Oeuvre oeuvre=o.get(i);
//
//                    controller.setOeuvre(oeuvre);
//
//
//                    grid.add(pane, column, row);
//                    row++;
//                    if (column > 0) {
//                        column = 0;
//                        row++;
//                    }
//
////                }
//            }







//        } catch (SQLException | IOException ex) {
//            System.out.println(ex.getMessage());
//
//
//
//        }

    }
    private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            OeuvreTable.setItems(OeuvreList);
        } else {
            ObservableList<Oeuvre> filteredList = OeuvreList.filtered(
                    oeuvre -> oeuvre.getNom_Ouvre().toLowerCase().contains(searchText.toLowerCase())
            );
            OeuvreTable.setItems(filteredList);
        }
    }
    private void openUpdateOeuvrePage(Oeuvre oeuvre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierOeuvre.fxml"));
            Parent root = loader.load();
            Controlers.ModifierOeuvreControler controller = loader.getController();
            controller.initData(oeuvre);
            controller.setAfficherOeuvreControler(this); // Pass a reference to GestionFormationController
            Stage stage = new Stage();
            stage.setTitle("Update Oeuvre");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
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
    @FXML
    void delete(ActionEvent event) {
        Oeuvre selectedOeuvre = OeuvreTable.getSelectionModel().getSelectedItem();
        if (selectedOeuvre != null) {
            try {
                oeuvreService.delete(selectedOeuvre);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }
    @FXML
    void ModifierOeuvre(ActionEvent event) {
        Oeuvre selectedOeuvre = OeuvreTable.getSelectionModel().getSelectedItem();
        if (selectedOeuvre != null) {
            openUpdateOeuvrePage(selectedOeuvre);
        }else {
            System.out.println("Aucune oueuvre choisit");
        }
    }
    public void refreshTable() throws SQLException {
        OeuvreTable.getItems().clear();
        OeuvreTable.getItems().addAll(oeuvreService.findAll());
    }


}