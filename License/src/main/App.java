package main;

import editors.EditorType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import panes.MainPane;

public class App extends Application {
	private Stage primaryStage;
	private MainPane mainPane;

	public Stage getStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainPane = new MainPane();

		AppMenu appMenu = new AppMenu(this);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(appMenu, mainPane);
		final Scene scene = new Scene(layout, 800, 600);

		mainPane.prefWidthProperty().bind(scene.widthProperty());
		mainPane.prefHeightProperty().bind(scene.heightProperty());

		scene.getStylesheets().add(this.getClass().getResource("/resources/java-keywords.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Aplicatie");
		primaryStage.show();
	}

	public void handleSaveCommand() {
		mainPane.save();
	}

	public void handleLoadCommand(EditorType editorType) {
		mainPane.load(editorType);
	}

	public void handleCreateCommand(EditorType editorType) {
		mainPane.create(editorType);
	}

	public void handleExitCommand() {
		mainPane.exit();
		this.primaryStage.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
