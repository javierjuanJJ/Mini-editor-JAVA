package es.cipfpbatoi.act2.minieditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	/**
	 * Limite de los archivos recientes
	 * en el archivo archivos_recientes.txt
	 */
	static final int klimite = 5;
	/**
	 * El campo de texto que se usa para escribir.
	 */
	@FXML
	private TextArea taEditor;
	/**
	 * Etiqueta que muestra los caracteres 
	 * y las lineas del fichero.
	 */
	@FXML
	private Label lblInfo;
	/**
	 * El boton abrir.
	 */
	@FXML
	private Button btnAbrir;
	/**
	 * El boton cerrar.
	 */
	@FXML
	private Button btnCerrar;
	/**
	 * El boton guardar.
	 */
	@FXML
	private Button btnGuardar;
	/**
	 * El boton nuevo.
	 */
	@FXML
	private Button btnNuevo;
	/**
	 * Desplegable que muestra los archivos recientes.
	 */
	@FXML
	private ChoiceBox<String> archivos_recientes;
	/**
	 * Variable que dice si el archivo se ha guardado o no.
	 */
	private boolean guardar;
	/**
	 * El escenario de la aplicacion
	 */
	private static Stage escenario;
	/**
	 * El file que se usa en la aplicacion.
	 */
	private static File f;

	
	/**
	 *@param URL url 
	 *@param ResourceBundle rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	/**
	 * Metodo que crea un fichero nuevo.
	 */
	public void handleNuevo() {
		Vacio();
		taEditor.setDisable(false);
		taEditor.setText("");
		guardar = true;
	}

	/**
	 * Metodo que abre un documento con la clase Filechooser para elegir los archivos.
	 */
	public void handleAbrir() {

		Vacio();
		guardar = false;

		FileChooser eleccion = creaAbrirFileChooser("Abrir un archivo");
		Stage escenario = new Stage();
		f = eleccion.showOpenDialog(escenario);
		Abrir();
		archivosRecientes();

	}

	/**
	 * Guarda un documento llamando a la funcion guardar.
	 */
	public void handleGuardar() {

		Guardar();
		archivosRecientes();
		Cambiar_titulo_aplicacion();
		
		/*
		 * Cambia guardar a false para que no pida guardar en un documento
		 * nuevo cada vez.
		 */
		
		guardar = false;

	}

	/**
	 * Guarda en un documento nuevo.
	 */
	public void Guardarcomo() {
		guardar = true;
		Guardar();
		guardar = false;

	}

	/**
	 * Cierra el documento y desabilita el editor de textos.
	 */
	public void handleCerrar() {
		Vacio();
		handleNuevo();
		taEditor.setDisable(true);
		f=new File("");
		Cambiar_titulo_aplicacion();
	}

	/**
	 * Pone un documento reciente elegido en el desplegable.
	 */
	public void reciente() {
		
		try {
			String ruta = "";
			
			/*
			 * Pone la ruta del desplegable.
			 */
			
			ruta = archivos_recientes.getItems().get(archivos_recientes.getSelectionModel().getSelectedIndex())
					.toString();
			Vacio();
			f = new File(ruta);
			Abrir();
			
		} catch (NullPointerException e) {
			System.err.print("");
		} catch (Exception e) {
			System.err.println("Error general");
		}

	}

	/**
	 * Gestiona los archivos recientes.
	 */
	public void archivosRecientes() {

		try {
			Path archivo_a_guardar = Paths.get(".", "archivos_recientes.txt");
			List<String> contenido = null;
			List<String> todoElTexto = new ArrayList();
			List<String> todoElTexto2 = new ArrayList();
			List<String> todoElTexto_vacio = new ArrayList();
			HashSet hs = null;
			int contador = 0;
			contenido = Leer_en_fichero(archivo_a_guardar);
			
			/*
			 * hs elimina las rutas repetidas.
			 */
			
			hs = new HashSet();
			hs.addAll(contenido);
			contenido.clear();
			contenido.addAll(hs);
			
			/*
			 * Anyade las lineas del fichero al arraylist.
			 */
			

			for (String l : contenido) {
				todoElTexto.add(l);
			}
			
			/*
			 * Anyade la ruta actual.
			 */
			
			todoElTexto.add(f.getAbsolutePath());
			Escribir_en_fichero(archivo_a_guardar, todoElTexto_vacio);
			
			
			/*
			 * Si esta debajo del limite lo escribe en el fichero.
			 */

			if (todoElTexto.size() < klimite) {
				Escribir_en_fichero(archivo_a_guardar, todoElTexto);
			} else {
				
				/*
				 * Si no coge las ultimas rutas definidas en la constante y lo anyade
				 * en el ArrayList.
				 */

				for (contador = klimite; contador > 0; contador--) {
					todoElTexto2.add(todoElTexto.get(todoElTexto.size() - contador));
				}
				
				/*
				 * Lo escribe en el fichero.
				 */

				Escribir_en_fichero(archivo_a_guardar, todoElTexto2);
			}
		} catch (NullPointerException e) {
			System.err.println("No se ha elegido un archivo");
			guardar=true;
			Guardar();
		} catch (Exception e) {
			System.err.println("Error general");
		}

	}

	/**
	 * Muestra los archivos recientes en el desplegable.
	 */
	public void mostrar_archivosRecientes() {

		archivos_recientes.getItems().clear();
		List<String> todoElTexto = new ArrayList();
		Path archivo_a_guardar = Paths.get(".", "archivos_recientes.txt");

		todoElTexto = Leer_en_fichero(archivo_a_guardar);
		
		/*
		 * Anyade las lineas en el ArrayList.
		 */
		
		for (String l : todoElTexto) {

			archivos_recientes.getItems().add(l);

		}
		
		/*
		 * Cambia guardable a false para que no pregunte si se guarda en otro
		 * documento.
		 */
		
		guardar = false;
	}

	/**
	 * @param Titulo El titulo que tendra el elemento FileChooser
	 * @return fc Devuelve el FileChhoser
	 */
	public FileChooser creaAbrirFileChooser(String Titulo) {
		FileChooser fc = new FileChooser();
		fc.setTitle(Titulo);
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		FileChooser.ExtensionFilter filtro2 = new FileChooser.ExtensionFilter("Todos los archivos", "*.*");
		fc.getExtensionFilters().add(filtro2);
		return fc;
	}

	/**
	 * @param archivo_a_guardar La variable del archivo en la que se escribira
	 * @param texto_a_guardar El ArrayList del texto que se guardara en el fichero
	 */
	public void Escribir_en_fichero(Path archivo_a_guardar, List<String> texto_a_guardar) {
		
		/*
		 * Crea el fichero.
		 */
		
		try (BufferedWriter bw = Files.newBufferedWriter(archivo_a_guardar)) {

			/*
			 * Escribe el fichero linea a linea.
			 */
			
			for (String linea : texto_a_guardar) {
				bw.write(linea);
				bw.newLine();
			}

		} catch (IOException e) {
			System.err.println("Error en el sistema de entrada / salida con el mensaje: " + e.getMessage());
		}

	}

	/**
	 * Abre el documento.
	 */
	public void Abrir() {

		try {
			String texto_a_abrir = "";
			Path archivo_a_guardar = Paths.get(".", "archivos_recientes.txt");
			List<String> todoElTexto = new ArrayList();
			int contador = 0;
			
			taEditor.setDisable(false);
			archivo_a_guardar = Paths.get(f.getAbsolutePath());
			todoElTexto = Leer_en_fichero(archivo_a_guardar);
			
			/*
			 * Escribe todas las lineas en el fichero.
			 */
			
			for (String l : todoElTexto) {
				texto_a_abrir = texto_a_abrir + l + "\n";
				contador = contador + 1;
			}
			
			Cambiar_titulo_aplicacion();
			taEditor.setText(texto_a_abrir);
			Mostrar_informacion();
			
		} catch (NullPointerException e) {
			System.err.print("");
		} catch (Exception e) {
			System.err.println("Error general");
		}

	}

	/**
	 * Guarda un fichero
	 */
	public void Guardar() {
		int contador = 0;

		try {
			
			/*
			 * Si la variable guardar es true se preguntara
			 * si se quiere guardar en otro fichero y si no se sobreescribira
			 * en el fichero.
			 */
			
			if (guardar == true) {
				FileChooser eleccion = creaAbrirFileChooser("Guardar un archivo");
				Stage escenario = new Stage();
				f = eleccion.showSaveDialog(escenario);
			}
			contador = 0;
			Path archivo_a_guardar = Paths.get(f.getAbsolutePath());
			String[] Texto = taEditor.getText().split("\n");
			List<String> texto_a_guardar = new ArrayList();
			
			/*
			 * Se anyadira en el ArrayList las lineas del elemento TextArea.
			 */
			
			for (contador = 0; contador < Texto.length; contador++) {
				texto_a_guardar.add(Texto[contador]);
			}
			
			/*
			 * Escribe el el fichero y muestra la informacion.
			 */
			
			Escribir_en_fichero(archivo_a_guardar, texto_a_guardar);
			Mostrar_informacion();

		} catch (Exception e) {
			System.err.print("");
			guardar = true;
		}

	}

	/**
	 * El metodo que decide si el campo de texto esta vacio.
	 */
	public void Vacio() {
		boolean texto_vacio = taEditor.getText().isEmpty();
		
		/*
		 * Si esta vacio presenta una alerta preguntando si quieres guardarlo.
		 */
		
		if (texto_vacio == false) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pregunta");
			alert.setHeaderText("¿Quieres guardar el archivo?");
			
			Optional<ButtonType> option = alert.showAndWait();

			/*
			 * Si se aprieta al boton de OK pregunta donde ser guardado
			 * y si no hace nada.
			 */
			
			if (option.get() == ButtonType.OK) {
				guardar=false;
				Guardar();
			} else {
				taEditor.setText("");
			}
		}
	}

	/**
	 * @param archivo_a_guardar El path donde se guardara el fichero.
	 * @return texto_del_fichero El texto en forma de ArrayList que almacenara el fichero.
	 */
	public List<String> Leer_en_fichero(Path archivo_a_guardar) {
		
		List<String> texto_del_fichero = new ArrayList();
		
		/*
		 * El archivo de lectura.
		 */
		
		try (BufferedReader br = Files.newBufferedReader(archivo_a_guardar)) {
			
			String linea = null;
			
			/*
			 * Anyade todas las lineas leidas al ArrayList.
			 */
			
			while ((linea = br.readLine()) != null) {
				texto_del_fichero.add(linea);
			}
		
		} catch (IOException e) {

			System.err.println("Error en el sistema de entrada / salida con el mensaje: " + e.getMessage());
		}
		return texto_del_fichero;

	}

	/**
	 * @param escenario que tiene la aplicacion
	 */
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	/**
	 * Muestra las lineas y los caracteres del fichero.
	 */
	public void Mostrar_informacion() {
		String informacion = "";
		informacion = "Archivo: " + f.getName() + " tiene " + taEditor.getText().split("\n").length + " lineas, Tiene "
				+ (taEditor.getText().length() - 1) + " caracteres.";
		lblInfo.setText(informacion);
	}

	/**
	 * Cambia el titulo de la aplicacion
	 */
	public static void Cambiar_titulo_aplicacion() {
		try {
			escenario.setTitle("MINI EDITOR DE TEXTOS" + " " + f.getName());

		} catch (Exception e) {
			System.err.print("");
		}

	}

}
