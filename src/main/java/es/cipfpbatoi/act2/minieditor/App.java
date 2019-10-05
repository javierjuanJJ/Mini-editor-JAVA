package es.cipfpbatoi.act2.minieditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sergio
 */
public class App extends Application {
    
    private Stage escenario;
    private AnchorPane escena;    
    
    @Override
    public void start(Stage primaryStage) {
        this.escenario = primaryStage;
        this.escenario.setTitle("MINI EDITOR DE TEXTOS");
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("appvista.fxml"));
            escena = (AnchorPane) loader.load();
            
            AppController ac = loader.getController();
            ac.setEscenario(escenario);

            // Show the scene containing the root layout.            
            escenario.setScene(new Scene(escena));
            escenario.show();
        } catch (IOException e) {
            System.err.println("Error principal!"+e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
