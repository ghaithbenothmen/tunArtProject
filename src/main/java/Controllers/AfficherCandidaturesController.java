package Controllers;

import Entites.Candidatures;
import Entites.Votes;
import Services.ServiceCandidatures;
import Services.ServiceVotes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCandidaturesController implements Initializable {

    @FXML
    private Button DeleteBtn;

    @FXML
    private ComboBox<String> Sort;

    @FXML
    private TextField searchFor;

    @FXML
    private TableView<Candidatures> tableView;

    @FXML
    private TableColumn<Candidatures, Integer> viewID;

    @FXML
    private TableColumn<Candidatures, Integer> viewIDUser;

    @FXML
    private TableColumn<Candidatures, Integer> viewIDcon;

    @FXML
    private TableColumn<Candidatures, Date> viewdDate;

    private final ServiceCandidatures ser=new ServiceCandidatures();

    @FXML
    void supprimerVotes(ActionEvent event) {
        Candidatures c = (Candidatures)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            this.ser.deletea(c);
        }

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherCandidatures.fxml"));
            Parent root=loader.load();
            DeleteBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Candidatures> list=ser.findAll();
            ObservableList<Candidatures> obers= FXCollections.observableList(list);
            tableView.setItems(obers);
            System.out.println(obers);
            viewID.setCellValueFactory(new PropertyValueFactory<>("id"));
            viewdDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            viewIDcon.setCellValueFactory(new PropertyValueFactory<>("ID_concours"));
            viewIDUser.setCellValueFactory(new PropertyValueFactory<>("ID_user"));

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

    public void initializeUI() {
        // Ajoutez les éléments à la ComboBox
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Date");
        Sort.setItems(comboBoxOptions);
        Sort.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                switch (newValue) {
                    case "Date":
                        List<Candidatures> sortedByAdresse = ser.diplayListsortedbyDateVote();
                        ObservableList<Candidatures> obersAdresse = FXCollections.observableList(sortedByAdresse);
                        tableView.setItems(obersAdresse);
                        break;
                    default:
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void filterData(String searchText) throws SQLException {

        List<Candidatures> list=ser.findAll();
        ObservableList<Candidatures> obers= FXCollections.observableList(list);
        if (searchText == null || searchText.isEmpty()) {
            tableView.setItems(obers);
        } else {
            ObservableList<Candidatures> filteredList = obers.filtered(
                    Candidatures -> Candidatures.getDate().toString().toLowerCase().contains(searchText.toLowerCase())
            );
            tableView.setItems(filteredList);
        }
    }
}
