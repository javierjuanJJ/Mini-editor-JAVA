package es.cipfpbatoi.act2.minieditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sergio
 */
public class AppController implements Initializable {

    @FXML
    private TextArea taEditor;
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnAbrir;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnNuevo;

    private Stage escenario;    
    private File f;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // inicializaciones del controlador
    }

    @FXML
    private void handleNuevo() {        
    }

    @FXML
    private void handleAbrir() {       
    }

    @FXML
    private void handleGuardar() {        
    }

    @FXML
    private void handleCerrar() {
    }
   

    void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

   
}
