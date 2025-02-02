package Controllers;

import Entites.Formation;
import Entites.User;
import Services.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static Controllers.LoginController.UserConnected;

public class PayementController {

    @FXML
    private Button PurchaseBtn;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField exprMonthField;

    @FXML
    private TextField exprYearField;
    UserService userService=new UserService();
    private User userId;
    private Formation formationId;
    private int prix;
    public void setData(User userId, Formation formationId,int prix) {
        this.userId = userId;
        this.formationId = formationId;
        this.prix = prix;
    }

    @FXML
    void handlePayment(javafx.event.ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();
        String exprMonth = exprMonthField.getText();
        String exprYear = exprYearField.getText();

        // Vérifier si les champs sont vides
        if (!isValidCardNumber(cardNumber)) {
            showAlert("Numéro de carte invalide", "Veuillez saisir un numéro de carte valide (16 chiffres).");
            return;
        }

        if (!isValidCVV(cvv)) {
            showAlert("CVV invalide", "Veuillez saisir un CVV valide (3 chiffres).");
            return;
        }

        if (!isValidExpirationDate(exprMonth, exprYear)) {
            showAlert("Date d'expiration invalide", "Veuillez saisir une date d'expiration valide (MM/YYYY) dans le futur.");
            return;
        }


        int width = 200;
        int height = 200;

        // Create a PaymentMethod using a test token
        String testToken = "tok_visa"; // Example test token, replace with actual test token
        //String paymentMethodId = createPaymentMethod(testToken);

        // If the PaymentMethod was created successfully, process the payment

            processPayment();

            // Redirect to InscriptionConfirmer.fxml on successful payment
            try {
                System.out.println("hello new useee"+ UserConnected.getId() + ", Formation ID: " + formationId);
                String qrCodeText = "User ID: " + UserConnected.getId() + ", Formation ID: " + formationId.getId();

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
                controller.setData(formationId, qrCodeImage);
                // Close the current window
                Stage currentStage = (Stage) PurchaseBtn.getScene().getWindow();
                currentStage.close();

                // Inscrire l'utilisateur à la formation dans la base de données
                userService.inscrire(UserConnected.getId(), formationId.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidCardNumber(String cardNumber) {
        // Vérifier que le numéro de carte contient uniquement des chiffres et a une longueur de 16 chiffres
        return cardNumber.matches("\\d{16}");
    }

    private boolean isValidCVV(String cvv) {
        // Vérifier que le CVV contient uniquement des chiffres et a une longueur de 3 chiffres
        return cvv.matches("\\d{3}");
    }

    private boolean isValidExpirationDate(String exprMonth, String exprYear) {
        // Vérifier que le mois est un nombre entre 1 et 12 et que l'année est un nombre à 4 chiffres
        if (!exprMonth.matches("0[1-9]|1[0-2]") || !exprYear.matches("\\d{4}")) {
            return false;
        }

        // Récupérer le mois et l'année actuels
        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.MonthDay.now().getMonthValue();

        // Convertir les chaînes en entiers
        int expirationMonth = Integer.parseInt(exprMonth);
        int expirationYear = Integer.parseInt(exprYear);

        // Vérifier si la date d'expiration est dans le futur
        if (expirationYear > currentYear || (expirationYear == currentYear && expirationMonth >= currentMonth)) {
            return true;
        }

        return false;
    }



        public void processPayment() {
            try {
// Set your secret key here
                Stripe.apiKey = "sk_test_51OqD2zFwwP47unkPDwjI0VW2CAMmqra1xmdfGVzzC2SgbMxKc2O36huNoEJiR6qKmlndFVRWRwBqBn03Bsj5PRl500Sy5RjUr8";

// Create a PaymentIntent with other payment details
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount((long) this.prix) // Amount in cents (e.g., $10.00)
                        .setCurrency("usd")
                        .build();

                PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
                System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            } catch (StripeException e) {
// If there was an error processing the payment, display the error message
                System.out.println("Payment failed. Error: " + e.getMessage());
            }
        }



}
