package Controllers;

import Entites.*;
import Services.OeuvreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ModifierOeuvreControler {

    @FXML
    private Button Importer;

    @FXML
    private Button add;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea desc;

    @FXML
    private TextField img;

    @FXML
    private Button reset;

    @FXML
    private TextField txtnom;

    @FXML
    private ComboBox selectType;
    private Oeuvre oeuvre;
    OeuvreService oeuvreService=new OeuvreService();

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    void importer_image(ActionEvent event) {

    }

    @FXML
    void ModifierOeuvre(ActionEvent event){
        String nom=txtnom.getText();
        String image=img.getText();
        String description=desc.getText();
        TypeOeuvre type = (TypeOeuvre) selectType.getSelectionModel().getSelectedItem();
        LocalDate datedb=date.getValue();

        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(datedb);

        Oeuvre updatedOeuvre = new Oeuvre();
        updatedOeuvre.setRef(oeuvre.getRef());
        updatedOeuvre.setNom_Ouvre(nom);
        updatedOeuvre.setDate_Publication(sqlDateDebut);
        updatedOeuvre.setImg(image);
        updatedOeuvre.setDescription(description);
        updatedOeuvre.setTypeOeuvre(type);
        updatedOeuvre.setNote(null);
        System.out.println(updatedOeuvre);

        try {
            oeuvreService.update(updatedOeuvre);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Oeuvre mise à jour", "L'Oeuvre a été mise à jour avec succès.");


            Stage stage = (Stage) txtnom.getScene().getWindow();

            stage.close();



            System.out.println(updatedOeuvre);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", " " + e.getMessage());
        }
    }
    private OneOeuvreController oneOeuvreController;

    public void setAfficherController(OneOeuvreController oneOeuvreController) {
        this.oneOeuvreController = oneOeuvreController;
    }
    @FXML
    private void annuler(ActionEvent event) {


        txtnom.setText("");
        img.setText("");
        desc.setText("");
        date.setValue(null);
        selectType.getSelectionModel().clearSelection();
    }

    public void initData(Oeuvre oeuvre) throws SQLException {
        this.oeuvre = oeuvre;
        txtnom.setText(oeuvre.getNom_Ouvre());
        desc.setText(oeuvre.getDescription());
        img.setText(oeuvre.getImg());


        ObservableList<TypeOeuvre> TypeList = FXCollections.observableArrayList(TypeOeuvre.values());
        selectType.setItems(TypeList);
        selectType.setValue(oeuvre.getTypeOeuvre());


        Calendar calendarPub = Calendar.getInstance();
        calendarPub.setTime(oeuvre.getDate_Publication());
        date.setValue(calendarPub.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());


    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private ObservableList<Oeuvre> oeuvreListe = FXCollections.observableArrayList();

//    private void refresh() throws SQLException {
//        oeuvreListe.addAll(oeuvreService.findAll());
//    }




}