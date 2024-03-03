package Controllers;

import Entites.Formation;
import Services.FormationService;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;

import static Controllers.LoginController.UserConnected;

public class InscriptionConfirmer implements Initializable {

    @FXML
    private Label dateD;

    @FXML
    private Label dateF;

    @FXML
    private Label nomFor;

    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private Button imprimerButton;
    private Formation formation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imprimerButton.setOnAction(event -> {
            try {
                imprimerPDF();
            } catch (IOException | WriterException e) {
                e.printStackTrace();
            }
        });
    }

    public void setData(Formation formation, Image qrCodeImage) {
        this.formation = formation;
        int width = 200;
        int height = 200;
        nomFor.setText(formation.getNom());
        dateD.setText(formation.getDateDebut().toString());
        dateF.setText(formation.getDateFin().toString());
        qrCodeImageView.setImage(qrCodeImage); // Assuming qrCodeImageView is the ImageView in your FXML
    }


    private BufferedImage generateQRCodeImage(String text) throws WriterException, IOException {
        com.google.zxing.Writer writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage qrCodeImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return qrCodeImage;
    }

    private void imprimerPDF() throws IOException, WriterException {
        // Créer un document PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            float x = 100;
            float y = 700;

            // Créer un flux de contenu pour écrire dans le document PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                // Ajouter du texte au document PDF
                // Ajouter du texte au document PDF
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 12);
                contentStream.beginText();

// Positionner le premier élément de texte

                contentStream.newLineAtOffset(x, y);
                contentStream.showText("Payement effectué par : " + UserConnected.getNom());
// Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Nom de la formation : " + formation.getNom());
// Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Date de début : " + formation.getDateDebut());

// Passer à la ligne suivante
                y -= 20; // Ajuster cette valeur en fonction de l'espacement souhaité
                contentStream.newLineAtOffset(0, 20);
                contentStream.showText("Date de fin : " + formation.getDateFin());

                contentStream.newLineAtOffset(0, 20);
// Terminer l'ajout de texte
                contentStream.endText();

                // Ajouter l'image du code QR au document PDF
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(qrCodeImageView.getImage(), null);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", os);
                byte[] data = os.toByteArray();
                ByteArrayInputStream is = new ByteArrayInputStream(data);
                PDImageXObject imageXObject = PDImageXObject.createFromByteArray(document, data, "QR Code");
                contentStream.drawImage(imageXObject, 100, 500, 200, 200);
            }

            // Enregistrer le document PDF sur le disque
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("inscription.pdf");
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                document.save(file);
            }
        }
    }
}
