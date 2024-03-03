package Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.util.HashMap;
import java.util.Map;

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

    @FXML
    void handlePayment(javafx.event.ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();
        String exprMonth = exprMonthField.getText();
        String exprYear = exprYearField.getText();

        // Create a PaymentMethod using a test token
        String testToken = "tok_visa"; // Example test token, replace with actual test token
        String paymentMethodId = createPaymentMethod(testToken);

        // If the PaymentMethod was created successfully, process the payment
        if (paymentMethodId != null) {
            processPayment(paymentMethodId, 1000, "usd");

            // Redirect to InscriptionConfirmer.fxml on successful payment
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../InscriptionConfirmer.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Close the current window
                Stage currentStage = (Stage) PurchaseBtn.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String createPaymentMethod(String token) {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OqD2zFwwP47unkPDwjI0VW2CAMmqra1xmdfGVzzC2SgbMxKc2O36huNoEJiR6qKmlndFVRWRwBqBn03Bsj5PRl500Sy5RjUr8";

            // Create a PaymentMethod using the token
            Map<String, Object> params = new HashMap<>();
            params.put("payment_method", token);

            PaymentMethod paymentMethod = PaymentMethod.create(params);

            // Return the ID of the PaymentMethod if creation is successful
            return paymentMethod.getId();
        } catch (StripeException e) {
            // Handle Stripe API exceptions here
            e.printStackTrace();
            return null;
        }
    }

    public static void processPayment(String paymentMethodId, int amount, String currency) {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OqD2zFwwP47unkPDwjI0VW2CAMmqra1xmdfGVzzC2SgbMxKc2O36huNoEJiR6qKmlndFVRWRwBqBn03Bsj5PRl500Sy5RjUr8";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setPaymentMethod(paymentMethodId)
                    .setAmount((long) amount * 100) // Amount in cents
                    .setCurrency(currency)
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
