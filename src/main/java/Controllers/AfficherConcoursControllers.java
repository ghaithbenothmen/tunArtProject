package controllers;

import Entites.Concours;
import Entites.Type;
import Service.ServiceConcours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;





public class AfficherConcoursControllers {

    @FXML
    private TableColumn<Concours,String> viewdate;

    @FXML
    private TableColumn<Concours, String> viewlien;

    @FXML
    private TableColumn<Concours,Integer> viewprix;

    @FXML
    private TableColumn<Concours, String> viewtype;

    @FXML
    private TableColumn<Concours, String> viewnom;


    @FXML
    private Button DeleteBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;


    @FXML
    private TableView<Concours> tableView;

    private final ServiceConcours ser=new ServiceConcours();

    @FXML
    void  initialize()
    {

        try {
            List<Concours> list=ser.readAll();
            ObservableList<Concours> obers= FXCollections.observableList(list);
            tableView.setItems(obers);
            System.out.println(obers);
            viewdate.setCellValueFactory(new PropertyValueFactory<>("date"));
            viewtype.setCellValueFactory(new PropertyValueFactory<>("type"));
            viewprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            viewlien.setCellValueFactory(new PropertyValueFactory<>("lien"));
            viewnom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerConcours(ActionEvent event) {

        Concours c = (Concours)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            this.ser.delete(c);
        }

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherConcours.fxml"));
            Parent root=loader.load();
            DeleteBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateConcours(ActionEvent event) {

        Concours c = (Concours)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            try {

                System.out.println(c);
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/UpdateConcours.fxml"));
                Parent root=loader.load();

                UpdateConcoursController dc=loader.getController();
                dc.setLbname(String.valueOf(c.getReference()));

                UpdateConcoursController dc1=loader.getController();
                dc1.setLbname(String.valueOf(c.getDate()));

                updateBtn.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void ajouterconcours(ActionEvent actionEvent) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterConcours.fxml"));
            Parent root=loader.load();

            addBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


