package main;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import editors.CodeEditor;
import editors.Editor;
import editors.EditorType;
import editors.GraphEditor;
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
import panes.EditingPane;
import panes.RunningPane;

public class App extends Application {
	private Stage primaryStage;
	private StackPane content = new StackPane();
	private EditingPane editingPane;
	private RunningPane runningPane;
	private Editor currentEditor = null;
	public AppMode appMode;

	public Stage getStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		editingPane = new EditingPane();
		runningPane = new RunningPane();
		
		AppMenu appMenu = new AppMenu(this);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(appMenu, content);
		final Scene scene = new Scene(layout, 800, 600);

		content.prefWidthProperty().bind(scene.widthProperty());
		content.prefHeightProperty().bind(scene.heightProperty());
		editingPane.prefWidthProperty().bind(scene.widthProperty());
		editingPane.prefHeightProperty().bind(scene.heightProperty());
		runningPane.prefWidthProperty().bind(scene.widthProperty());
		runningPane.prefHeightProperty().bind(scene.heightProperty());
		
		scene.getStylesheets().add(this.getClass().getResource("../java-keywords.css").toExternalForm());

		this.changeAppMode(AppMode.Running);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Aplicatie");
		primaryStage.show();
	}
	
	public void handleSaveCommand() {
		if(appMode == AppMode.Editing) {
			editingPane.saveEditor();
		}
	}
	
	public void handleLoadCommand(EditorType editorType) {
		if(appMode == AppMode.Editing) {
			editingPane.loadEditor(editorType);
		}
	}
	
	public void handleCreateCommand(EditorType editorType) {
		if(appMode == AppMode.Editing) {
			editingPane.createEditor(editorType);
		}
	}
	
	public void handleExitCommand() {
		if(appMode == AppMode.Editing) {
			editingPane.ensureEditorSaved();
			primaryStage.close();
		}
	}
	
	public void changeAppMode(AppMode appMode) {
		this.content.getChildren().clear();
		
		if(appMode == AppMode.Editing) {
			this.content.getChildren().add(editingPane);
		} else {
			this.content.getChildren().add(runningPane);
		}
		
		this.appMode = appMode;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
