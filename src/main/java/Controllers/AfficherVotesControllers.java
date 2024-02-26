package controllers;

import Entites.Concours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Entites.Votes;
import Service.ServiceVotes;
import javafx.stage.Stage;

import java.util.Date;

public class AfficherVotesControllers {

    @FXML
    private Button DeleteBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Votes, Concours> viewconcours;

    @FXML
    private TableColumn<Votes, Date> viewdate;

    @FXML
    private TableColumn<Votes, String> viewdescription;

    @FXML
    private TableColumn<Votes, Integer> viewid;

    @FXML
    private TableColumn<Votes, Integer> viewuser;

    @FXML
    private TableView<Votes> tableView;


    private final ServiceVotes ser=new ServiceVotes();
    private ObservableList<Votes> voteList = FXCollections.observableArrayList();
    @FXML
    public void initialize() throws SQLException {
        voteList.addAll(ser.readAll());
        viewid.setCellValueFactory(new PropertyValueFactory<>("ID_vote"));
        viewdescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        viewdate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        //viewuser.setCellValueFactory(new PropertyValueFactory<>("dateFin"));


        viewconcours.setCellValueFactory(new PropertyValueFactory<>("ID_concours")); //naamlo get l nom mtaa objet brk moch objet kemel
        viewconcours.setCellFactory(column -> {
            return new TableCell<Votes, Concours>() {
                @Override
                protected void updateItem(Concours item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getNom()); // Assuming getNom() returns the name of the category
                    }
                }
            };
        });
        tableView.setItems(voteList);

        refreshTable();
        //search
     /*   searchFor.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });*/

        tableView.setRowFactory(tv -> {
            TableRow<Votes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Votes votes = row.getItem();
                    //openUpdateFormationPage(votes);
                }
            });
            return row;
        });
    }
   /* private void filterData(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            tableView.setItems(voteList);
        } else {
            ObservableList<Votes> filteredList = voteList.filtered(
                    vote -> vote.getNom().toLowerCase().contains(searchText.toLowerCase())
            );
            FormationTable.setItems(filteredList);
        }
    }*/

   /* private void openUpdateFormationPage(Votes votes) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UpdateVotes.fxml"));
            Parent root = loader.load();
            UpdateFormationController controller = loader.getController();
            controller.initData(formation);
            controller.setGestionFormationController(this); // Pass a reference to GestionFormationController
            Stage stage = new Stage();
            stage.setTitle("Update Formation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/

    @FXML
    void ajouterVote(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterVotes.fxml"));
            Parent root = loader.load();
            AjouterVotesController controller = loader.getController();
            //controller.setParentController(this); // Pass the current controller to the AddFormationController
            Stage stage = new Stage();
            stage.setTitle("Add Formation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerVote(ActionEvent event) {
        Votes selectedFormation = tableView.getSelectionModel().getSelectedItem();
        if (selectedFormation != null) {
            try {
                ser.delete(selectedFormation);

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

        }
    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void updateVote(ActionEvent event) {
        Votes selectedFormation = tableView.getSelectionModel().getSelectedItem();
        if (selectedFormation != null) {
            //openUpdateFormationPage(selectedFormation);
        }
    }

    public void refreshTable() throws SQLException {
        tableView.getItems().clear();
        tableView.getItems().addAll(ser.readAll());
    }

/*
    @FXML
    void  initialize()
    {

        try {
            List<Votes> list=ser.readAll();
            ObservableList<Votes> obers= FXCollections.observableList(list);
            tableView.setItems(obers);
            System.out.println(obers);
            viewdate.setCellValueFactory(new PropertyValueFactory<>("date"));
            viewdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            viewid.setCellValueFactory(new PropertyValueFactory<>("id"));
            viewconcours.setCellValueFactory(new PropertyValueFactory<>("ID_concours"));
            viewuser.setCellValueFactory(new PropertyValueFactory<>("ID_user"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterVote(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterVotes.fxml"));
            Parent root=loader.load();

            addBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerVote(ActionEvent event) {
        Votes c = (Votes)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            this.ser.delete(c);
        }

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherVotes.fxml"));
            Parent root=loader.load();
            DeleteBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateVote(ActionEvent event) {

        Votes c = (Votes)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            try {

                System.out.println(c);
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/UpdateVotes.fxml"));
                Parent root=loader.load();

                UpdateConcoursController dc=loader.getController();
                dc.setLbname(String.valueOf(c.getID_vote()));

                updateBtn.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }*/

}


