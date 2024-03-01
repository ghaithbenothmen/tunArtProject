package Controllers;

import Entites.*;
import Services.OeuvreService;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static Controllers.LoginController.UserConnected;

public class ModifierOeuvreControler {

    private AfficherController parentController;

    public void setParentController(AfficherController parentController) {
        this.parentController = parentController;
    }


    @FXML
    private Button Importer;

    @FXML
    private ImageView imageView;

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
    private String imagePath;
    private Image image;
    OeuvreService oeuvreService=new OeuvreService();
    private Scene previousScene;

    public UserService userService = new UserService();

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;}
    private  AfficherController afficherController;
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    void importer_image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {

                // Load the selected image file
                Image image = new Image(selectedFile.toURI().toString());
                // Display the image in the ImageView
                imageView.setImage(image);

                // Store the path of the selected image file
                imagePath = selectedFile.getAbsolutePath();
                img.setText(imagePath);
            } catch (Exception e) {
                // Handle any errors that may occur during image loading
                e.printStackTrace();
                // Optionally, display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to load image");
                alert.setContentText("An error occurred while loading the selected image file.");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void ModifierOeuvre(ActionEvent event) throws SQLException {
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
        //updatedOeuvre.setImg(imagePath);
        updatedOeuvre.setDescription(description);
        updatedOeuvre.setTypeOeuvre(type);
        updatedOeuvre.setNote(null);
        updatedOeuvre.setImg(image);
        System.out.println(updatedOeuvre);

        int artisteId = UserConnected.getId();
        User artiste = userService.findById(artisteId);
        updatedOeuvre.setArtiste_id(artiste);


        try {
            oeuvreService.update(updatedOeuvre);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Oeuvre mise à jour", "L'Oeuvre a été mise à jour avec succès.");


            Stage Currentstage = (Stage) txtnom.getScene().getWindow();

            Currentstage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
            Parent root = loader.load();
            afficherController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Afficher Oeuvre");
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Bonjour"+artisteId);
            afficherController.refreshScrollPane(artisteId);


            System.out.println(updatedOeuvre);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", " " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        String path = oeuvre.getImg();

        try {
            image = new Image(new File(path).toURI().toURL().toString(),207,138,false,true);
            imageView.setImage(image);

        } catch (MalformedURLException e){
            e.printStackTrace();
        }


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



    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOeuvre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



}