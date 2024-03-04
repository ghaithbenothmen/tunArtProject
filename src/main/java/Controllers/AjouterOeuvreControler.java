package Controllers;

import Entites.Oeuvre;
import Entites.TypeOeuvre;
import java.io.File;

import Entites.User;
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
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

import static Controllers.LoginController.UserConnected;

public class AjouterOeuvreControler {
    Boolean oeuvreAdded;
    private UserService userService = new UserService();

    private  final OeuvreService oeuvre=new OeuvreService();
    @FXML
    private Button add;
    @FXML
    private ImageView imagePreview;

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
    private ComboBox  selectType;
    private String imagePath;
    private AfficherController afficherController ;

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
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
                imagePreview.setImage(image);
                // Store the path of the selected image file
                imagePath = selectedFile.getAbsolutePath();
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
        }else {
            showAlert(Alert.AlertType.ERROR,"Erreur","Aucune image ajoutée ","Veuiller importer une image");
        }
    }
    public void initialize() {
        ObservableList<String> TypeNames = FXCollections.observableArrayList();

        for (TypeOeuvre type : TypeOeuvre.values()) {
            TypeNames.add(type.toString());
        }
        selectType.setItems(TypeNames);
        selectType.setValue(TypeNames.get(0));

        selectType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TypeOeuvre type = TypeOeuvre.valueOf((String) newValue);
                System.out.println("Selected type: " + type);

            }
        });
    }


    private AfficherController parentController;



    public void setParentController(AfficherController parentController) {
        this.parentController = parentController;
    }
    // Event handler for adding Oeuvre
    @FXML
    void AjouterOeuvre(ActionEvent event) throws SQLException {





        String nom = txtnom.getText();
//        String image = imagePreview.getImage().getUrl();
        String description = desc.getText();

        // TypeOeuvre type = TypeOeuvre.valueOf(String) selectType.getSelectionModel().getSelectedItem(); // Use getValue() for ComboBox
        TypeOeuvre type = TypeOeuvre.valueOf((String) selectType.getSelectionModel().getSelectedItem());

        LocalDate datedb = date.getValue();

        java.sql.Date sqlDatePublication = java.sql.Date.valueOf(datedb);

        int artisteId = UserConnected.getId();
        System.out.println(artisteId);
        User artiste = userService.findById(artisteId);
        System.out.println(artiste);

        Oeuvre o = new Oeuvre(nom, imagePath, description, type, sqlDatePublication,artiste);
        try {
            if (nom.isEmpty()){
                showAlert(Alert.AlertType.ERROR,"Erreur","Le champ nom est vide ","Veuiller saisir le nom de l'oeuvre");
            }else if (description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le champ description est vide ", "Veuiller saisir la description de l'oeuvre");
            }else
                {oeuvreAdded=true;

                    oeuvre.add(o);

                    if (oeuvreAdded) {
                        Notifications.create()
                                .title("Notification Title")
                                .text(UserConnected.getNom()+"a ajouté une nouvelle oeuvre")
                                .showInformation();
                    }

                    showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Oeuvre Ajouté", "L'Oeuvre a été ajouté avec succès.");


                    Stage stage = (Stage) txtnom.getScene().getWindow();

                    stage.close();
                    // FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
//            Parent root = loader.load();
//            afficherController = loader.getController();
//            Stage Rstage = new Stage();
//            Rstage.setTitle("Afficher Oeuvre");
//            Rstage.setScene(new Scene(root));
//            Rstage.show();
                    parentController.refreshScrollPane(artisteId);
                }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de mise à jour", " " + e.getMessage());
        }
    }
    @FXML
    private void annuler(ActionEvent event) {

        imagePreview.setImage(null);
        txtnom.setText("");
        img.setText("");
        desc.setText("");
        date.setValue(null);
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void Retour(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOeuvre.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
        stage.close();

    }

}

