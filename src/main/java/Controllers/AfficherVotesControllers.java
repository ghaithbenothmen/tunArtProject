package Controllers;

import Entites.Votes;
import Services.ServiceVotes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherVotesControllers implements Initializable {

    @FXML
    private Button DeleteBtn;

    @FXML
    private ComboBox<String> Sort;

    @FXML
    private TextField searchFor;

    @FXML
    private TableView<Votes> tableView;

    @FXML
    private TableColumn<Votes, Integer> viewID;

    @FXML
    private TableColumn<Votes, Integer> viewIDUser;

    @FXML
    private TableColumn<Votes, Integer> viewIDcon;

    @FXML
    private TableColumn<Votes, Date> viewdDate;

    private final ServiceVotes ser=new ServiceVotes();

    @FXML
    void supprimerVotes(ActionEvent event) {
        Votes c = (Votes)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            this.ser.deletea(c);
        }

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherVotes.fxml"));
            Parent root=loader.load();
            DeleteBtn.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Votes> list=ser.findAll();
            ObservableList<Votes> obers= FXCollections.observableList(list);
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
                        List<Votes> sortedByAdresse = ser.diplayListsortedbyDateVote();
                        ObservableList<Votes> obersAdresse = FXCollections.observableList(sortedByAdresse);
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

        List<Votes> list=ser.findAll();
        ObservableList<Votes> obers= FXCollections.observableList(list);
        if (searchText == null || searchText.isEmpty()) {
            tableView.setItems(obers);
        } else {
            ObservableList<Votes> filteredList = obers.filtered(
                    votes -> votes.getDate().toString().toLowerCase().contains(searchText.toLowerCase())
            );
            tableView.setItems(filteredList);
        }
    }
}
