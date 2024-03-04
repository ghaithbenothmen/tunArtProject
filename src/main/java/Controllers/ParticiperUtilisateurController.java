package Controllers;

import Entites.Candidatures;
import Entites.Concours;
import Entites.Votes;
import Services.ServiceCandidatures;
import Services.ServiceConcours;
import Services.ServiceVotes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ParticiperUtilisateurController {
    @FXML
    private TableColumn<Concours, Date> viewdate;

    @FXML
    private TableColumn<Concours, String> viewlien;

    @FXML
    private TableColumn<Concours,Integer> viewprix;

    @FXML
    private TableColumn<Concours, String> viewtype;

    @FXML
    private TableColumn<Concours, String> viewnom;

    @FXML
    private ComboBox<String> Sort;



    @FXML
    private TableView<Concours> tableView;
    @FXML
    private TextField searchFor;

    @FXML
    private Button voter;

    private final ServiceConcours ser=new ServiceConcours();
    private final ServiceCandidatures serV=new ServiceCandidatures();


    @FXML
    void  initialize()
    {
        try {
            List<Concours> list=ser.findAll();
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

        searchFor.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterData(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        initializeUI();
    }
    private void filterData(String searchText) throws SQLException {

        List<Concours> list=ser.findAll();
        ObservableList<Concours> obers= FXCollections.observableList(list);
        if (searchText == null || searchText.isEmpty()) {
            tableView.setItems(obers);
        } else {
            ObservableList<Concours> filteredList = obers.filtered(
                    formation -> formation.getNom().toLowerCase().contains(searchText.toLowerCase())
            );
            tableView.setItems(filteredList);
        }
    }

    public void initializeUI(){
        // Ajoutez les éléments à la ComboBox
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Prix", "Date");
        Sort.setItems(comboBoxOptions);
        Sort.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                switch (newValue) {
                    case "Date":
                        List<Concours> sortedByDate = ser.diplayListsortedbyDate();
                        ObservableList<Concours> obersAdresse = FXCollections.observableList(sortedByDate);
                        tableView.setItems(obersAdresse);
                        break;
                    case "Prix":
                        List<Concours> sortedByPrix = ser.diplayListsortedbyMontant();
                        ObservableList<Concours> obersNbrPlace = FXCollections.observableList(sortedByPrix);
                        tableView.setItems(obersNbrPlace);
                        break;
                    default:
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void Participer(ActionEvent event) {
        Concours c = (Concours)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {

            System.out.println("Chosen concours "+c.getNom());
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Do you really want to participate in this concour");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert1.getButtonTypes().setAll(okButton, cancelButton);
            // Handle button click
            alert1.setResultConverter(buttonType -> {
                if (buttonType == okButton) {
                    int user=4;
                    LocalDate today = LocalDate.now();
                    java.sql.Date sqlDate= java.sql.Date.valueOf(today);
                    Candidatures v=new Candidatures(sqlDate,c.getReference(),user);
                    if (serV.Unique(v)) {
                        try {
                            serV.add(v);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setContentText("You are already registered");
                        alert2.showAndWait();
                    }
                } else if (buttonType == cancelButton) {
                }
                return buttonType;
            });
            alert1.showAndWait();
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a concour");
            alert1.showAndWait();
        }
    }
}
