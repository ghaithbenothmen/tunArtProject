package Test;
import javafx.application.*;
import javafx.fxml.*;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("PROGRAM ENTRY:##");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterEvenement.fxml"));
            System.out.println("TRY CATCH ENTRY:##");

            Parent root = loader.load();
            System.out.println("Lancement de stage");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            //tawa hedhika li tal3e
            // ey probléme f service kenet, hani sal7t'ha, nfaskhlk fichiet li aamlthm? eyy ey
            System.out.println("Stage lancé");
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../SupprimerEvenement.fxml"));
        System.out.print("PROGRAM ENTRY:##");
        try {
            System.out.println("TRY CATCH ENTRY:##");

            Parent root1 = loader1.load();
            System.out.println("Lancement de stage 1 ");
            Scene scene1 = new Scene(root1);
            primaryStage.setScene(scene1);
            primaryStage.show();
            System.out.println("Stage lancé 1 ");
        } catch (IOException e) {
            e.printStackTrace();
        }


*/

    }
    public static void  main(String[]args ){
        launch(args);
    }
}

