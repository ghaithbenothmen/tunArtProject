package Controllers;

import Entites.Concours;
import Entites.Type;
import Services.ServiceCandidatures;
import Services.ServiceConcours;
import Services.ServiceVotes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.Date;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;





public class AfficherConcoursControllers {

    @FXML
    private TableColumn<Concours,Date> viewdate;

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
    private Button DeleteBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;
    @FXML
    private Button RETURN;

    @FXML
    private Button print;

    @FXML
    private TableView<Concours> tableView;
    @FXML
    private TextField searchFor;

    private final ServiceConcours ser=new ServiceConcours();
    private final ServiceVotes serV=new ServiceVotes();
    private final ServiceCandidatures serC=new ServiceCandidatures();


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

    public void initializeUI() {
        // Ajoutez les éléments à la ComboBox
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Prix", "Date");
        Sort.setItems(comboBoxOptions);
        Sort.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                switch (newValue) {
                    case "Date":
                        List<Concours> sortedByAdresse = ser.diplayListsortedbyDate();
                        ObservableList<Concours> obersDate = FXCollections.observableList(sortedByAdresse);
                        tableView.setItems(obersDate);
                        break;
                    case "Prix":
                        List<Concours> sortedByNbrPlace = ser.diplayListsortedbyMontant();
                        ObservableList<Concours> obersNbrprix = FXCollections.observableList(sortedByNbrPlace);
                        tableView.setItems(obersNbrprix);
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
    void supprimerConcours(ActionEvent event) {

        Concours c = (Concours)this.tableView.getSelectionModel().getSelectedItem();
        if (c != null) {

            /*
            List <User> ListU= new ArrayList<>();
            try {
                ListU=serU.findAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (User i :ListU)
            {
                if (i.getID_concours()==c.getReference())
                    serC.deletea(i);
            }
            */


            serV.deletByConcours(c);
            serC.deletByConcours(c);
            this.ser.deletea(c);
            // bech ygueti el id taa lconcours yemchi yfetchi fel votes welandidature
            // eli aandhom id concours hedheka w baaed yfassakhom
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
                dc.setTxtnom(c.getNom());
                dc.setTxtlien(String.valueOf(c.getLien()));
                dc.setTxtprix(Integer.valueOf(c.getPrix()));
                dc.setChoiceType(Type.valueOf(c.getSType()));
                dc.setDateinput(String.valueOf(c.getDate()));


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

    @FXML
    void exporttoexcel(ActionEvent event) throws SQLException {
        try {
            String filename="Concours.xlsx";
            File file=new File("C:\\Users\\user\\Desktop\\"+filename);
            ExportToExcel.exportToExcel(ser.findAll(), file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void RETURNbtn(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/MainContainer.fxml"));
            Parent root=loader.load();
            RETURN.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}