import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Iterator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
	    private static final String BROWSER = "Browser";
	    private static final String EDITOR = "new editor";
	    private static int browserCnt = 1;

	    private Stage primaryStage;
	    private TabPane tabPane = new TabPane();
	    private StackPane stackPane = new StackPane();
	    private Pane paneDisplay = new Pane();
	    //private Vector<SimpleEditor> editors = new Vector();
	    private Editor currentEditor = null;
	    static boolean ignoreNextPress = false;
	    
	    private Stage getStage() {
	        return primaryStage;
	    }
	    
	    @Override
	    public void start(Stage primaryStage) {
	        this.primaryStage = primaryStage;
	        
	        
	        AppMenu appMenu = new AppMenu(this);
	        
	        VBox layout = new VBox(10);
	        layout.getChildren().addAll(appMenu, stackPane);
	        
	        
	        // display the scene
	        final Scene scene = new Scene(layout, 800, 600);
	        // Bind the tab pane width/height to the scene
	        
	         stackPane.prefWidthProperty().bind(scene.widthProperty());
	         stackPane.prefHeightProperty().bind(scene.heightProperty());

	        // Certain keys only come through on key release events
	        // such as backspace, enter, and delete
	        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	                public void handle(KeyEvent ke) {
	                    if(currentEditor != null) {
	                    	currentEditor.modified = true;
	                    }
	                }
	            });

	        

	        createNewCodeEditor("New Program");
	        URL url = this.getClass().getResource("java-keywords.css");
	        String css = url.toExternalForm(); 
	        scene.getStylesheets().add(css);

	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Simple Editor / Browser");
	        primaryStage.show();
	    }
	    
	    private void createNewCodeEditor(String name) {	        
	        currentEditor = new CodeEditor(this);
	        stackPane.getChildren().clear();
	        stackPane.getChildren().add(currentEditor.getDisplay());
	    }
	    
	    private void createNewGraphEditor(String name) {	        
	        currentEditor = new CodeEditor(this);
	        stackPane.getChildren().clear();
	        stackPane.getChildren().add(currentEditor.getDisplay());
	    }
	    
	    public void loadCodeEditor() {
	        FileChooser fc = new FileChooser();
	        File fileToOpen = fc.showOpenDialog(null);
	        if ( fileToOpen != null ) {
	            // Read the file, and set its contents within the editor
	            String openFileName = fileToOpen.getAbsolutePath();
	            createNewCodeEditor(openFileName);
	            currentEditor.loadData(openFileName);
	        }
	    }
	    
	    public void loadGraphEditor() {
	        FileChooser fc = new FileChooser();
	        File fileToOpen = fc.showOpenDialog(null);
	        if ( fileToOpen != null ) {
	            // Read the file, and set its contents within the editor
	            String openFileName = fileToOpen.getAbsolutePath();
	            createNewGraphEditor(openFileName);
	            currentEditor.loadData(openFileName);
	        }
	    }

	    public void saveEditor() {
	        if(currentEditor != null) {
	        	currentEditor.saveData();
	        }
	    }
	    
	    public static void main(String[] args) {
	        launch(args);
	    }
	}
