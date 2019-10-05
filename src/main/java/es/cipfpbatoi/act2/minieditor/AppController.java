package es.cipfpbatoi.act2.minieditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
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
    @FXML
    private ChoiceBox archivos_recientes;
    private boolean guardar;    
    private Stage escenario;    
    private File f;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }

  
    public void handleNuevo() { 
    	Boolean texto_vacio=false;
    	texto_vacio=taEditor.getText().isEmpty();
    	taEditor.setDisable(false);
    	if (texto_vacio==false) {
    		
    		try {
    			handleGuardar();
        		taEditor.setText("");
    		}
    		catch (Exception e) {
    			taEditor.setText("");
    			lblInfo.setText("");
    		}
    		
    	}
    	guardar=true;
    }

   
    public void handleAbrir() {
    	
    	Boolean texto_vacio=false;
    	texto_vacio=taEditor.getText().isEmpty();
    	taEditor.setDisable(false);
    	String texto_a_abrir="";
    	String informacion="";
    	int contador=0;
    	if (texto_vacio==false) {
    		
    		try {
    			handleGuardar();
        		taEditor.setText("");
    		}
    		catch (Exception e) {
    			FileChooser eleccion = creaAbrirFileChooser("Abrir un archivo");
    		}
    		
    	guardar=false;
    		
    	}
    	
    	FileChooser eleccion = creaAbrirFileChooser("Abrir un archivo");
		Stage escenario = new Stage();
		f = eleccion.showOpenDialog(escenario);
		Path archivo_a_guardar = Paths.get(f.getAbsolutePath());
		List<String> todoElTexto=null;
		try {
			todoElTexto = Files.readAllLines(archivo_a_guardar);
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		for(String l: todoElTexto) {
			texto_a_abrir=texto_a_abrir+l+"\n";
			contador=contador+1;
		}
		taEditor.setText(texto_a_abrir);
		informacion="Archivo: " + f.getName() + " tiene " + contador + " lineas, Tiene " + (texto_a_abrir.length()-1) + " caracteres.";
		lblInfo.setText(informacion);
    
    }

    
    public void handleGuardar() {
    	int contador=0;
    	if (guardar==false) {
    		Path archivo_a_guardar = Paths.get(f.getAbsolutePath());
    		String[] Texto=taEditor.getText().split("\n");
    		List<String> texto_a_guardar = new ArrayList();
    		for (contador=0;contador<Texto.length;contador++) {
    			texto_a_guardar.add(Texto[contador]);
    		}
    		
    		try {
    			Files.write(archivo_a_guardar, texto_a_guardar, StandardCharsets.UTF_8);
    		} catch (IOException e) {
    			// TODO Bloque catch generado automáticamente
    			e.printStackTrace();
    		}
    	}
    	else {
    		FileChooser eleccion = creaAbrirFileChooser("Guardar un archivo");
    		Stage escenario = new Stage();
    		f = eleccion.showSaveDialog(escenario);
    		contador=0;
    		Path archivo_a_guardar = Paths.get(f.getAbsolutePath());
    		String[] Texto=taEditor.getText().split("\n");
    		List<String> texto_a_guardar = new ArrayList();
    		for (contador=0;contador<Texto.length;contador++) {
    			texto_a_guardar.add(Texto[contador]);
    		}
    		
    		try {
    			Files.write(archivo_a_guardar, texto_a_guardar, StandardCharsets.UTF_8);
    		} catch (IOException e) {
    			// TODO Bloque catch generado automáticamente
    			e.printStackTrace();
    		}
    		
    		archivosRecientes();
    	}
    	
    	
    	
    	
    }

   
    public void handleCerrar() {
    	
    	
    	
    }
    
    public void reciente() {
    	
    	Boolean texto_vacio=false;
    	texto_vacio=taEditor.getText().isEmpty();
    	taEditor.setDisable(false);
    	String texto_a_abrir="";
    	String informacion="";
    	int contador=0;
    	String ruta="";
    	Path archivo_a_guardar = null;
    	ruta=archivos_recientes.getItems().get(archivos_recientes.getSelectionModel().getSelectedIndex()).toString();
    	if (texto_vacio==false) {
    		
    		try {
    			handleGuardar();
        		taEditor.setText("");
    		}
    		catch (Exception e) {
    			f=new File(ruta);
    		}
    		
    		
    		
    	}
    	f=new File(ruta);
		archivo_a_guardar = Paths.get(f.getAbsolutePath());
		List<String> todoElTexto=null;
		try {
			todoElTexto = Files.readAllLines(archivo_a_guardar);
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		for(String l: todoElTexto) {
			texto_a_abrir=texto_a_abrir+l+"\n";
			contador=contador+1;
		}
		taEditor.setText(texto_a_abrir);
		informacion="Archivo: " + f.getName() + " tiene " + contador + " lineas, Tiene " + (texto_a_abrir.length()-1) + " caracteres.";
		lblInfo.setText(informacion);
    	
    }
    
    public void archivosRecientes() {
    	List<String> todoElTexto = new ArrayList();
    	Path archivo_a_guardar = Paths.get(".","archivos_recientes.txt");
    	try {
			Files.write(archivo_a_guardar, todoElTexto);
			todoElTexto.add(f.getAbsolutePath());
			Files.write(archivo_a_guardar, todoElTexto);
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
    	
    }
    public void mostrar_archivosRecientes() {
    	archivos_recientes.getItems().clear();
    	List<String> todoElTexto = new ArrayList();
    	Path archivo_a_guardar = Paths.get(".","archivos_recientes.txt");
    	try {
    		todoElTexto = Files.readAllLines(archivo_a_guardar);
    		for(String l: todoElTexto) {
    			
    			archivos_recientes.getItems().add(l);
    			
    		}

		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
    	guardar=false;
    }
    
    private FileChooser creaAbrirFileChooser(String Titulo) {
		FileChooser fc = new FileChooser();
		fc.setTitle(Titulo);
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		FileChooser.ExtensionFilter filtro2 = new FileChooser.ExtensionFilter("Todos los archivos", "*.*");
		fc.getExtensionFilters().add(filtro2);
		return fc;
	}
  
    void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

   
}
