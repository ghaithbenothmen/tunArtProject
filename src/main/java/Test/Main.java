package Test;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("../GestionCategorie.fxml"));

        Parent root=loader.load();
        Scene scene=new Scene(root);
        stage.setTitle("Gérer formation");
        stage.setScene(scene);
        stage.show();

/*
        System.out.println("PROGRAM ENTRY:##");
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../AjouterEvenement.fxml"));
            System.out.println("TRY CATCH ENTRY:##");

            Parent root1 = loader1.load();
            System.out.println("Lancement de stage");
            Scene scene1 = new Scene(root);
            Stage.setScene(scene1);
            Stage.show();
            System.out.println("Stage lancé");
        } catch (IOException e) {
            e.printStackTrace();
        }



 */
    }



    public static void main(String[] args){
        launch(args);
    }
}
