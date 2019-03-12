import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
	private Stage primaryStage;
	private StackPane stackPane = new StackPane();
	private Editor currentEditor = null;

	public Stage getStage() {
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

		createNewCodeEditor("");
		URL url = this.getClass().getResource("java-keywords.css");
		String css = url.toExternalForm();
		scene.getStylesheets().add(css);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Simple Editor / Browser");
		primaryStage.show();
	}

	public void createNewCodeEditor(String name) {
		currentEditor = new CodeEditor(this, name);
		stackPane.getChildren().clear();
		stackPane.getChildren().add(currentEditor.getDisplay());
	}

	public void createNewGraphEditor(String name) {
		currentEditor = new GraphEditor(this, name);
		stackPane.getChildren().clear();
		stackPane.getChildren().add(currentEditor.getDisplay());
	}

	public void loadCodeEditor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("../Data/Programs"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java source files", "*.java"));
		File fileToOpen = fileChooser.showOpenDialog(null);

		if (fileToOpen != null) {
			String path = fileToOpen.getAbsolutePath();
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createNewCodeEditor(name);
			currentEditor.loadData(path);
		}
	}

	public void loadGraphEditor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("../Data/Graphs"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph Files", "*.graph"));
		File fileToOpen = fileChooser.showOpenDialog(null);
		if (fileToOpen != null) {

			String openFileName = fileToOpen.getAbsolutePath();
			createNewGraphEditor(openFileName);
			currentEditor.loadData(openFileName);
		}
	}

	public void saveEditor() {
		if (currentEditor != null) {
			currentEditor.saveData();
		}
	}
	
	public void ensureEditorSaved() {
		System.out.println(currentEditor.modified);
		if (currentEditor.modified) {
			Alert saveEnquiry = new Alert(AlertType.NONE, "Do you want to save your progress?" , ButtonType.YES, ButtonType.NO);
			saveEnquiry.showAndWait();

			if (saveEnquiry.getResult() == ButtonType.YES) {
			    this.saveEditor();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
