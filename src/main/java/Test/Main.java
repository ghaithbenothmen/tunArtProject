package Test;

import javafx.application.Application;
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
//Jamyla
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../AjouterEvenement.fxml"));

            Parent root1 = loader1.load();
            Scene scene1 = new Scene(root1);
            Stage.setScene(scene1);
            Stage.show();

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../SupprimerEvenement.fxml"));

        stage.setScene(scene);
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            Stage.setScene(scene2);
            Stage.show();


        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../ReserverBillet.fxml"));

        Parent root3 = loader3.load();
        Scene scene3 = new Scene(root3);
        Stage.setScene(scene3);
        Stage.show();
*/


    }

    public static void main(String[] args){
        launch(args);
    }
}
