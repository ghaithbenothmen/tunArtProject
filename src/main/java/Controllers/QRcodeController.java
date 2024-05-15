package Controllers;

import Entites.Concours;
import Services.ServiceConcours;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QRcodeController implements Initializable {
    private final ServiceConcours se =new ServiceConcours();

    @FXML
    private Button generateButton;

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private Button RETURN;

    @FXML
    private ComboBox<Concours> tf_combobox; // Assuming you have a ComboBox of Concours objects

    @FXML
    void generateQRCode(ActionEvent event) {
        Concours selectedEvenement = tf_combobox.getValue();
        if (selectedEvenement != null) {
            String data = selectedEvenement.getNom() + "\n"
                    + selectedEvenement.getDate() ;
            // Set the size of the QR code image
            int width = 300;
            int height = 300;

            try {
                // Generate the QR code
                BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);

                // Convert the BitMatrix to an Image
                Image qrCodeImage = toFXImage(bitMatrix);

                // Display the QR code image in the ImageView
                qrCodeImageView.setImage(qrCodeImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Image toFXImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean bit = bitMatrix.get(x, y);
                Color color = bit ? Color.BLACK : Color.WHITE;
                pixelWriter.setColor(x, y, color);
            }
        }

        return writableImage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Concours> list = null;
        try {
            list = (ArrayList<Concours>) se.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> listN = null;
        for (Concours i : list)
        {
            System.out.println(i.getNom());
            //listN.add(i.getNom());
        }
        ObservableList<Concours> observableList = FXCollections.observableList(list);
        tf_combobox.setItems(observableList);
    }
    @FXML
    void RETURNbtn(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/MainContainer.fxml"));
            Parent root=loader.load();
            RETURN.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
