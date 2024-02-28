package Controllers;


import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Services.CategorieService;
import Services.FormationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class AjouterFormationController {

    private  final FormationService service=new FormationService();
    @FXML
    private ComboBox selectcat;

    @FXML
    private ComboBox selectniveau;

    @FXML
    private ComboBox idartiste;

    @FXML
    private TextField txtdesc;

    @FXML
    private TextField txtnom;

    @FXML
    private DatePicker dateF;

    @FXML
    private DatePicker dateD;
    @FXML
    private ImageView imageFor;
    private Image image;

    private  String imagePath;
    @FXML
    private AnchorPane main_form;

    public CategorieService categorieService = new CategorieService();
    public void initialize() {


        dateF.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Date is picked, do something
                System.out.println("DateF value: " + newValue);
            } else {
                // Date is not picked
                System.out.println("DateF not picked");
            }
        });


        try {
            ObservableList<String> categorieNames = FXCollections.observableArrayList();
            ObservableList<Categorie> categories = categorieService.findAll();
            for (Categorie categorie : categories) {
                categorieNames.add(categorie.getNom());
            }
            selectcat.setItems(categorieNames);
            if (!categorieNames.isEmpty()) {
                selectcat.setValue(categorieNames.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> niveauNames = FXCollections.observableArrayList();
        for (Niveau niveau : Niveau.values()) {
            niveauNames.add(niveau.toString());
        }
        selectniveau.setItems(niveauNames);
        selectniveau.setValue(niveauNames.get(0));

        selectniveau.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Niveau niveau = Niveau.valueOf((String) newValue);
                System.out.println("Selected Niveau: " + niveau);

            }
        });

        selectcat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String categorieName = (String) newValue;
                System.out.println("Selected Categorie: " + categorieName);

            }
        });


    }

    private GestionFormationController parentController;

    public void setParentController(GestionFormationController parentController) {
        this.parentController = parentController;
    }
    @FXML
    void ajouterFormation(ActionEvent event) {
        String nom = txtnom.getText();
        String desc = txtdesc.getText();


        Niveau niveau = Niveau.valueOf((String) selectniveau.getSelectionModel().getSelectedItem());

        String selectedCategorie = (String) selectcat.getSelectionModel().getSelectedItem();
        Categorie selectedCategorieIns = categorieService.findByName(selectedCategorie);

/* nestanew yassine ykml user
        int artisteId;
        try {
            artisteId = (int) idartiste.getSelectionModel().getSelectedItem();
        } catch (ClassCastException | NullPointerException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Artiste invalide", "Veuillez sélectionner un artiste valide.");
            return;
        }*/

        LocalDate dateDebut = dateD.getValue();
        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(dateDebut);

        LocalDate dateFin = dateF.getValue();
        java.sql.Date sqlDateFin= java.sql.Date.valueOf(dateFin);



        Formation f = new Formation(nom, /*artisteId,*/ sqlDateDebut, sqlDateFin, niveau, desc, selectedCategorieIns,imagePath);

        try {
            service.add(f);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Formation mise à jour", "La formation a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

            //refreshi tab
            parentController.refreshTable();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", "Une erreur s'est produite lors de la mise à jour de la formation : " + e.getMessage());
        }
    }

    @FXML
    private void resetForm(ActionEvent event) {
        txtnom.setText("");
        txtdesc.setText("");
        selectniveau.getSelectionModel().clearSelection();
        selectcat.getSelectionModel().clearSelection();
        dateD.setValue(null);
        dateF.setValue(null);
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void importImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

             imagePath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 147, 89, false, true);
            imageFor.setImage(image);
        }
    }

}
