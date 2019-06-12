package main;
import editors.EditorType;
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
		Menu browseMenu = new Menu("Browse");

		Menu fileNewMenu = new Menu("New");
		MenuItem newCodeEditorMenuItem = new MenuItem("New Program");
		MenuItem newGraphEditorMenuItem = new MenuItem("New Graph");

		newCodeEditorMenuItem.setOnAction((event) -> app.handleCreateCommand(EditorType.Code));
		newGraphEditorMenuItem.setOnAction((event) -> app.handleCreateCommand(EditorType.Graph));

		fileNewMenu.getItems().addAll(newCodeEditorMenuItem, newGraphEditorMenuItem);

		Menu fileOpenMenu = new Menu("Open");
		MenuItem openCodeEditorMenuItem = new MenuItem("Open Program");
		MenuItem openGraphEditorMenuItem = new MenuItem("Open Graph");

		openCodeEditorMenuItem.setOnAction((event) -> app.handleLoadCommand(EditorType.Code));
		openGraphEditorMenuItem.setOnAction((event) -> app.handleLoadCommand(EditorType.Graph));
		
		fileOpenMenu.getItems().addAll(openCodeEditorMenuItem, openGraphEditorMenuItem);

		MenuItem saveMenuItem = new MenuItem("Save");
		saveMenuItem.setOnAction((event) -> app.handleSaveCommand());

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction((event) -> app.handleExitCommand());

		fileMenu.getItems().addAll(fileNewMenu, fileOpenMenu, saveMenuItem, exitMenuItem);
		
		MenuItem browseProgramsMenuItem = new MenuItem("Browse Programs");
		MenuItem browseGraphsMenuItem = new MenuItem("Browse Graphs");
		
		browseProgramsMenuItem.setOnAction((event) -> app.handleBrowseCommand(EditorType.Code));
		browseGraphsMenuItem.setOnAction((event) -> app.handleBrowseCommand(EditorType.Graph));
		
		browseMenu.getItems().addAll(browseProgramsMenuItem,browseGraphsMenuItem);
		
		this.getMenus().addAll(fileMenu,browseMenu);
	}
}