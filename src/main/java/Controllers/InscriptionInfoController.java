package Controllers;


import Entites.Formation;
import Services.FormationService;
import Services.RaitingService;
import Services.UserService;
import com.google.zxing.WriterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controllers.LoginController.UserConnected;


public class InscriptionInfoController {

    @FXML
    private Label dateD;

    @FXML
    private Label dateF;

    @FXML
    private Label desc;

    @FXML
    private ImageView imageFor;

    @FXML
    private Label niveau;

    @FXML
    private Label nomCat;

    @FXML
    private Label nomFor;
    @FXML
    private Rating raitingControl;
    @FXML
    private Label ratingLabel;
    private Formation formation;

    private int requestedFormationId ;
    private Image image;

    private RaitingService ratingService=new RaitingService() ;

    UserService userService=new UserService();

    public void setFormation(Formation formation) {
        this.formation = formation;

    }
    @FXML
    void confirmerInsc(ActionEvent event) {
        if (UserConnected != null) {
            try {
                // Vérifie si l'utilisateur est déjà inscrit à cette formation
                if (!userService.isInscrit(UserConnected.getId(), formation.getId())) {
                    // Si l'utilisateur n'est pas déjà inscrit, l'inscrire

                    userService.inscrire(UserConnected.getId(), formation.getId());


                    int width = 300;
                    int height = 300;
                   // BufferedImage qrCodeBufferedImage = QRCodeGenerator.generateQRCodeImage(text, width, height);


                    String qrCodeText = "User ID: " + UserConnected.getId() + ", Formation ID: " + formation.getId();
                    BufferedImage qrCodeBufferedImage = QRCodeGenerator.generateQRCodeImage(qrCodeText, width, height);
                    Image qrCodeImage = SwingFXUtils.toFXImage(qrCodeBufferedImage, null);

                    System.out.println("heloo qr"+qrCodeImage);


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../InscriptionConfirmer.fxml"));
                    Parent root = loader.load();
                    InscriptionConfirmer controller = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    // Passer les informations nécessaires au contrôleur de la page InscriptionConfirmer
                    controller.setData(formation, qrCodeImage);

                    // Afficher un message de succès
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie", "Vous êtes maintenant inscrit à cette formation.");
                } else {
                    // Afficher un message d'erreur si l'utilisateur est déjà inscrit
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Inscription impossible", "Vous êtes déjà inscrit à cette formation.");
                }
            } catch (SQLException e) {
                // Afficher un message d'erreur s'il y a eu une erreur lors de l'inscription
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur d'inscription", "Une erreur s'est produite lors de l'inscription à la formation : " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Si l'utilisateur n'est pas connecté, le rediriger vers la page de connexion
            redirectToLoginPage();
            System.out.println("not connected");
        }
    }

    public void setData(Formation formation) throws SQLException {
        this.formation=formation;
        //System.out.println("hello"+this.formation);
        nomFor.setText(formation.getNom());
        niveau.setText(formation.getNiveau().toString());
        nomCat.setText((formation.getCat_id().getNom()));
        desc.setText(formation.getDescription());
        dateD.setText(formation.getDateDebut().toString());
        dateF.setText(formation.getDateFin().toString());

        String path = formation.getImage();
        try {
            image = new Image(new File(path).toURI().toURL().toString(), 366, 366, false, true);
            imageFor.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        double userRating = ratingService.getUserRatingForFormation(UserConnected.getId(), formation.getId());
        if (userRating > 0) {
            raitingControl.setRating(userRating);
            raitingControl.setDisable(true); // Désactive le contrôle de notation si l'utilisateur a déjà noté
            ratingLabel.setText("Votre note : " + userRating);
        }
        raitingControl.ratingProperty().addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println("Rating: " + newValue);

                // Vérifier si l'utilisateur a déjà noté cette formation
                if (!ratingService.hasUserRatedFormation(UserConnected.getId(), formation.getId())) {
                    // Enregistrer le rating dans la base de données
                    ratingService.saveRating(UserConnected.getId(), formation.getId(), newValue.doubleValue());
                    showAlert(Alert.AlertType.CONFIRMATION, "Confirmer", "Bien noté", "Vous avez bien noté cette formation.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de notation", "Vous avez déjà noté cette formation.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'enregistrement de l'évaluation", "Une erreur s'est produite lors de l'enregistrement de votre évaluation.");
            }
        });
    }



    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void redirectToLoginPage() {
        try {
            requestedFormationId=this.formation.getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            LoginController loginController = loader.getController();
            loginController.setRequestedFormationId(requestedFormationId);

            // Close the current stage
            Stage currentStage = (Stage) dateD.getScene().getWindow();
            currentStage.close();

            // Wait for the login process to complete
            stage.setOnHidden(e -> {
                if (loginController.isLoginSuccessful()) {
                    try {
                        // Redirect to InscriptionInfo.fxml if login is successful
                        FXMLLoader infoLoader = new FXMLLoader(getClass().getResource("../InscriptionInfo.fxml"));
                        Parent infoRoot = infoLoader.load();

                        FormationService formationService =new FormationService();
                       Formation f= formationService.findById(requestedFormationId);

                        InscriptionInfoController infoController = infoLoader.getController();
                        infoController.setData(f);

                        Scene infoScene = new Scene(infoRoot);
                        Stage infoStage = new Stage();
                        infoStage.setScene(infoScene);
                        infoStage.setTitle("Inscription Info");
                        infoStage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
