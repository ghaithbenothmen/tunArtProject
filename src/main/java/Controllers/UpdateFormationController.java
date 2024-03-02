package Controllers;

import Entites.Categorie;
import Entites.Formation;
import Entites.Niveau;
import Entites.User;
import Services.CategorieService;
import Services.FormationService;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import static Controllers.LoginController.UserConnected;

public class UpdateFormationController {
    @FXML
    private ImageView imageFor;

    @FXML
    private AnchorPane main_form;

    @FXML
    private DatePicker dateD;

    @FXML
    private DatePicker dateF;

    @FXML
    private ComboBox selectcat;

    @FXML
    private ComboBox selectniveau;

    @FXML
    private TextField txtdesc;

    @FXML
    private TextField txtnom;

    private Formation formation;

    private String img;
    CategorieService categorieService=new CategorieService();

    FormationService formationService=new FormationService();
    private Image image;
    private Image imageGet;
    private  String imagePath;
    public UserService userService=new UserService();

    private GestionFormationController parentController;

    public void setParentController(GestionFormationController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void updateFormation(ActionEvent event) throws SQLException {

        String nom = txtnom.getText();
        String desc = txtdesc.getText();
        Niveau niveau = (Niveau) selectniveau.getSelectionModel().getSelectedItem();
        //String img=image.toString();

        String selectedCategorie = (String) selectcat.getSelectionModel().getSelectedItem();
        Categorie selectedCategorieIns = categorieService.findByName(selectedCategorie);
        System.out.println(selectedCategorieIns);

        LocalDate dateDebut = dateD.getValue();
        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(dateDebut);

        LocalDate dateFin = dateF.getValue();
        java.sql.Date sqlDateFin= java.sql.Date.valueOf(dateFin);


        Formation updatedFormation = new Formation();
        updatedFormation.setId(formation.getId());
        updatedFormation.setNom(nom);
        updatedFormation.setDateDebut(sqlDateDebut);
        updatedFormation.setDateFin(sqlDateFin);
        updatedFormation.setNiveau(niveau);
        updatedFormation.setDescription(desc);
        updatedFormation.setCat_id(selectedCategorieIns);

        int artisteId = UserConnected.getId();
        System.out.println(artisteId);
        User artiste = userService.findById(artisteId);
        System.out.println(artiste);
        updatedFormation.setArtiste_id(artiste);

        updatedFormation.setImage(img);
        System.out.println("hello mmmm");

        try {
            formationService.update(updatedFormation);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Formation mise à jour", "La formation a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

            //refreshi tab
            gestionFormationController.initData(artisteId);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", "Une erreur s'est produite lors de la mise à jour de la formation : " + e.getMessage());
        }
    }
    private GestionFormationController gestionFormationController;

    public void setGestionFormationController(GestionFormationController gestionFormationController) {
        this.gestionFormationController = gestionFormationController;
    }
    @FXML
    void resetForm(ActionEvent event) {
        txtnom.setText("");
        txtdesc.setText("");
        selectniveau.getSelectionModel().clearSelection();
        selectcat.getSelectionModel().clearSelection();
        dateD.setValue(null);
        dateF.setValue(null);
    }


    public void initData(Formation formation) throws SQLException {
        this.formation = formation;
        txtnom.setText(formation.getNom());
        txtdesc.setText(formation.getDescription());
        this.img= formation.getImage();
       // String img=formation.getImage();

        ObservableList<Niveau> niveauList = FXCollections.observableArrayList(Niveau.values());
        selectniveau.setItems(niveauList);
        selectniveau.setValue(formation.getNiveau());


        Calendar calendarDeb = Calendar.getInstance();
        calendarDeb.setTime(formation.getDateDebut());
        dateD.setValue(calendarDeb.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(formation.getDateFin());
        dateF.setValue(calendarFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());


        String path = formation.getImage();
        try {
            imageGet = new Image(new File(path).toURI().toURL().toString(), 207, 138, false, true);
            imageFor.setImage(imageGet);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(formation.getImage());

        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        categories.addAll(categorieService.findAll());

        ObservableList<String> categorieNames = FXCollections.observableArrayList();
        for (Categorie categorie : categories) {
            categorieNames.add(categorie.getNom());
        }
        selectcat.setItems(categorieNames);


        for (Categorie categorie : categories) {
            if (categorie.getId() == formation.getCat_id().getId()) {
                selectcat.setValue(categorie.getNom());
                break;
            }
        }

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

            this.img = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 147, 89, false, true);
            imageFor.setImage(image);
            System.out.println(imagePath);
        }
    }
}
