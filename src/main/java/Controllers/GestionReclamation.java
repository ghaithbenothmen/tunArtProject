package Controllers;

import Entites.*;
import Services.ReclamationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static Controllers.LoginController.UserConnected;

public class GestionReclamation {
    boolean deleted;
    @FXML
    private TextField cher;
    @FXML
    private TableView<Reclamation> reclamationTable;
    @FXML
    private TableColumn<Reclamation, String> userColumn;
    @FXML
    private TableColumn<Reclamation, String> typeColumn;
    @FXML
    private TableColumn<Reclamation, String> textColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private final ReclamationService reclamationService = ReclamationService.getInstance();

    private ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();
    private List<Reclamation> reclamationUserList = FXCollections.observableArrayList();

    public List<Reclamation> getAllReclamation() throws SQLException {
        reclamationList.addAll(reclamationService.findAll());
        reclamationUserList = reclamationList.stream().filter(reclamation -> reclamation.getId_user().equals(UserConnected)).collect(Collectors.toList());
        System.out.println(reclamationUserList);
        return reclamationUserList;
    }



    public void initialize() throws SQLException {
        // Set up table columns
        userColumn.setCellValueFactory(cellData -> {
            Reclamation reclamation = cellData.getValue();
            User user = reclamation.getId_user();
            if (user != null) {
                return new SimpleStringProperty(user.getNom());
            } else {
                return new SimpleStringProperty(""); // Return empty string if user is null
            }
        }); // Assuming you have a getter for user name
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Check user role
        boolean isAdmin = UserConnected.getRole()== Role.ADMIN;

        // Show appropriate data in table
        if (isAdmin) {
            showAllReclamations();
        } else {
            // Assuming you have a variable currentUserRole to determine user role
            getAllReclamation();
        }

        // Set button visibility based on user role
        addButton.setVisible(!isAdmin);
        updateButton.setVisible(!isAdmin);

        // Set up button actions
        addButton.setOnAction(event -> ajouter());
        updateButton.setOnAction(event -> updateRec());
        deleteButton.setOnAction(event -> deleteRec());
        refreshScrollPane(UserConnected.getId());
        reclamationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textColumn.setText(newSelection.getText());
            }
        });

        cher.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData(newValue);
        });
    }

    private void showAllReclamations() {
        try {
            List<Reclamation> allReclamations = reclamationService.findAll();
            populateTable(allReclamations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void showUserReclamations(int idU) {
        try {
            idU=UserConnected.getId();
            List<Reclamation> userReclamations = reclamationService.findAllByIdUser(idU);
            populateTable(userReclamations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTable(List<Reclamation> reclamations) {
        ObservableList<Reclamation> data = FXCollections.observableArrayList(reclamations);
        reclamationTable.setItems(data);
        System.out.println(data);
    }


    @FXML
    private void ajouter() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterReclamation.fxml"));
                Parent root = loader.load();
                AjouterReclamationController controller = loader.getController();
                controller.setParentController(this); // Pass the current controller to the AddFormationController
                Stage stage = new Stage();
                stage.setTitle("Add Oeuvre");
                stage.setScene(new Scene(root));


                stage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    private void openUpdateFormationPage(Reclamation reclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierReclamation.fxml"));
            Parent root = loader.load();
            ModifierReclamationController controller = loader.getController();
            controller.initData(reclamation);

            controller.setGestionReclamationController(this); // Pass a reference to GestionFormationController
            Stage stage = new Stage();
            stage.setTitle("Update Formation");
            stage.setScene(new Scene(root));
            stage.show();

            initData(UserConnected.getId());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void closeGestionReclamationPage() {
        Stage stage = (Stage) reclamationTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateRec() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {

            openUpdateFormationPage(selectedReclamation);
            closeGestionReclamationPage();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select a reclamation to update.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteRec() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                reclamationService.delete(selectedReclamation);
                reclamationTable.getItems().remove(selectedReclamation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select a reclamation to delete.");
            alert.showAndWait();
        }
        deleted=true;
        if (deleted) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+" "+"a supprimer une reclamation ")
                    .showInformation();
        }
    }
    public void initData(int userId) throws SQLException {
        reclamationTable.getItems().clear();
        // Retrieve formations for the given user_id
        reclamationList.addAll(reclamationService.findById(userId));
        // Populate the table with the formations
        reclamationTable.setItems(reclamationList);
    }

    public void refreshScrollPane(int idUser) throws SQLException {
        reclamationTable.getItems().clear();
        reclamationTable.getItems().addAll(reclamationService.findAllByIdUser(idUser));
    }


    private void filterData(String searchText) {
        int userId = UserConnected.getId();
        ObservableList<Reclamation> filteredList = FXCollections.observableArrayList();
        for (Reclamation reclamation : reclamationList) {
            if (reclamation.getText().toLowerCase().contains(searchText.toLowerCase())&&(reclamation.getId_user().getId()==userId)) {
                filteredList.add(reclamation);
            }
        }
        reclamationTable.getItems().clear();
        reclamationTable.getItems().addAll(filteredList);
    }
}

