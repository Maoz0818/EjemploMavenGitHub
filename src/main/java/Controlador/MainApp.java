package Controlador;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Modelo.GenerarClave;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Principal.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX, Maven y GitHub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        GenerarClave g = new GenerarClave();
        String dato = g.GenerarClave();
        System.out.println(dato);
        launch(args);
    }

}
