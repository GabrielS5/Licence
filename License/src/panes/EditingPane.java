package panes;

import java.io.File;
import java.nio.file.Paths;

import editors.CodeEditor;
import editors.Editor;
import editors.EditorType;
import editors.GraphEditor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class EditingPane extends StackPane{
	private Editor currentEditor = null;

	public void saveEditor() {
		if (currentEditor != null) {
			currentEditor.saveData();
		}
	}
	
	public void ensureEditorSaved() {
		if (currentEditor != null && currentEditor.modified) {
			Alert saveEnquiry = new Alert(AlertType.NONE, "Do you want to save your progress?" , ButtonType.YES, ButtonType.NO);
			saveEnquiry.showAndWait();

			if (saveEnquiry.getResult() == ButtonType.YES) {
			    this.saveEditor();
			}
		}
	}

	public void loadEditor(EditorType editorType) {
		if(editorType == EditorType.Graph)
			this.loadGraphEditor();
		else
			this.loadCodeEditor();	
	}
	
	public void createEditor(EditorType editorType) {
		if(editorType == EditorType.Graph)
			this.createGraphEditor("");
		else
			this.createCodeEditor("");	
	}
	
	private void loadCodeEditor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("../Data/Programs"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java source files", "*.java"));
		File fileToOpen = fileChooser.showOpenDialog(null);

		if (fileToOpen != null) {
			String path = fileToOpen.getAbsolutePath();
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createCodeEditor(name);
			currentEditor.loadData(path);
		}
	}

	private void loadGraphEditor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("../Data/Graphs"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph Files", "*.graphml"));
		File fileToOpen = fileChooser.showOpenDialog(null);
		if (fileToOpen != null) {

			String path = fileToOpen.getAbsolutePath();
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createGraphEditor(name);
			currentEditor.loadData(path);
		}
	}

	private void createCodeEditor(String name) {
		currentEditor = new CodeEditor( name);
		this.getChildren().clear();
		this.getChildren().add(currentEditor.getDisplay());
	}

	private void createGraphEditor(String name) {
		currentEditor = new GraphEditor(name);
		this.getChildren().clear();
		this.getChildren().add(currentEditor.getDisplay());
	}
}
