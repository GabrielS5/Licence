package panes;

import editors.Editor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

public abstract class EditorPane extends StackPane {
	protected Editor editor = null;

	public void saveEditor() {
		if (editor != null) {
			editor.saveData();
		}
	}

	public void ensureEditorSaved() {
		if (editor != null && editor.modified) {
			Alert saveEnquiry = new Alert(AlertType.NONE, "Do you want to save your progress?", ButtonType.YES,
					ButtonType.NO);
			saveEnquiry.showAndWait();

			if (saveEnquiry.getResult() == ButtonType.YES) {
				this.saveEditor();
			}
		}
	}

	public abstract void loadEditor();
	public abstract void createEditor(String name);
}
