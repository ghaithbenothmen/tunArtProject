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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class UpdateFormationController {

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

    CategorieService categorieService=new CategorieService();

    FormationService formationService=new FormationService();
    @FXML
    void updateFormation(ActionEvent event) {

        String nom = txtnom.getText();
        String desc = txtdesc.getText();
        Niveau niveau = (Niveau) selectniveau.getSelectionModel().getSelectedItem();


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


        try {
            formationService.update(updatedFormation);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Formation mise à jour", "La formation a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

            //refreshi tab
            gestionFormationController.refreshTable();
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

        ObservableList<Niveau> niveauList = FXCollections.observableArrayList(Niveau.values());
        selectniveau.setItems(niveauList);
        selectniveau.setValue(formation.getNiveau());


        Calendar calendarDeb = Calendar.getInstance();
        calendarDeb.setTime(formation.getDateDebut());
        dateD.setValue(calendarDeb.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(formation.getDateFin());
        dateF.setValue(calendarFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());



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


}
