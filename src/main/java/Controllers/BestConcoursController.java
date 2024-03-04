package Controllers;

import Entites.Concours;
import Services.ServiceCandidatures;
import Services.ServiceConcours;
import Services.ServiceVotes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Entites.Type.valueOf;

public class BestConcoursController {



    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label nom;

    @FXML
    private Label nombre;

    @FXML
    private Button best;

    @FXML
    private Label nombre1;

    @FXML
    private Label nom1;

    private final ServiceVotes serV = new ServiceVotes();
    private final ServiceConcours ser = new ServiceConcours();
    private final ServiceCandidatures serC = new ServiceCandidatures();


    @FXML
    void TapOnBest(ActionEvent event) {
        // best servic counts number of votes for concour
        List<Concours> list = new ArrayList<>();
        try {
            list = ser.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<Integer> listInt = new ArrayList<>(list.size());
        for (Concours i : list) {
            listInt.add(serV.BestConcours(i));
        }
        System.out.println(listInt);

        int highestIndex = 0; // Start with the index of the first element
        int highestNumber = listInt.get(0); // Track the highest number found

        for (int i = 1; i < listInt.size(); i++) {  // Start from index 1
            int currentNumber = listInt.get(i);
            if (currentNumber > highestNumber) {
                highestNumber = currentNumber;
                highestIndex = i; // Update the index if a higher number is found
            }
        }
        System.out.println(highestNumber);
        System.out.println(list.get(highestIndex).getNom());
        nom.setText(list.get(highestIndex).getNom());
        nombre.setText(String.valueOf(highestNumber));

            //participation
            List<Integer> listIntC = new ArrayList<>(list.size());
            for (Concours i : list) {
                listIntC.add(serC.BestConcours(i));
            }
            System.out.println(listIntC);

            int highestIndexC = 0; // Start with the index of the first element
            int highestNumberC = listIntC.get(0); // Track the highest number found


            for (int i = 1; i < listIntC.size(); i++) {  // Start from index 1
                int currentNumber = listIntC.get(i);
                if (currentNumber > highestNumberC) {
                    highestNumberC = currentNumber;
                    highestIndexC = i; // Update the index if a higher number is found
                }
            }
            nom1.setText(list.get(highestIndexC).getNom());
            nombre1.setText(String.valueOf(highestNumberC));
    }
}
