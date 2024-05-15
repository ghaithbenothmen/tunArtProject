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
import org.controlsfx.control.Notifications;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static Controllers.LoginController.UserConnected;

public class ModifierOeuvreControler {
    boolean updated;

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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (file != null) {
            try {
                // Convert file to byte array
                byte[] fileContent = Files.readAllBytes(file.toPath());

                // Send the image data to Symfony backend
                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:8000/upload-image"))
                        .header("Content-Type", "application/octet-stream")
                        .POST(HttpRequest.BodyPublishers.ofByteArray(fileContent))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

                // If the response is successful, set the image to the ImageView
                if (response.statusCode() == 200) {
                    Image image = new Image(new ByteArrayInputStream(fileContent));
                    System.out.println(image);
                    imageView.setImage(image);
                    imagePath=response.body();

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
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
        updatedOeuvre.setImg(imagePath);
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

        updated=true;
        if (updated) {
            Notifications.create()
                    .title("Notification Title")
                    .text(UserConnected.getNom()+" "+"a modifier l'oeuvre"+" "+this.oeuvre.getNom_Ouvre())
                    .showInformation();
        }
    }
    private OneOeuvreController oneOeuvreController;
    private OneOeuvreController oneController;

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
    void Retour(ActionEvent event) throws IOException {

        try {
            Stage Currentstage = (Stage) txtnom.getScene().getWindow();
            Currentstage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../OneOeuvre.fxml"));
            Parent root = loader.load();
            oneController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("One Oeuvre");
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Bonjour"+oeuvre.getArtiste_id().getId());
            oneController.setData(this.oeuvre);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



}