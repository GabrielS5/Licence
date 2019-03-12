import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class AppMenu extends MenuBar {
	App app;

	public AppMenu(App app) {
		this.app = app;
		init();
	}

	private void init() {

		Menu fileMenu = new Menu("File");

		Menu fileNewMenu = new Menu("New");
		MenuItem newCodeEditorMenuItem = new MenuItem("New Program");
		MenuItem newGraphEditorMenuItem = new MenuItem("New Graph");

		newCodeEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.ensureEditorSaved();
				app.createNewCodeEditor("");
			}
		});

		newGraphEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.ensureEditorSaved();
				app.createNewGraphEditor("");
			}
		});

		fileNewMenu.getItems().addAll(newCodeEditorMenuItem, newGraphEditorMenuItem);

		Menu fileOpenMenu = new Menu("Open");
		MenuItem openCodeEditorMenuItem = new MenuItem("Open Program");
		MenuItem openGraphEditorMenuItem = new MenuItem("Open Graph");

		openCodeEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.ensureEditorSaved();
				app.loadCodeEditor();
			}
		});

		openGraphEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.ensureEditorSaved();
				app.loadGraphEditor();
			}
		});
		
		fileOpenMenu.getItems().addAll(openCodeEditorMenuItem, openGraphEditorMenuItem);

		MenuItem saveMenuItem = new MenuItem("Save");
		saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.saveEditor();
			}
		});

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				app.ensureEditorSaved();
				app.getStage().close();
			}
		});

		fileMenu.getItems().addAll(fileNewMenu, fileOpenMenu, saveMenuItem, exitMenuItem);

		this.getMenus().addAll(fileMenu);
	}
}
