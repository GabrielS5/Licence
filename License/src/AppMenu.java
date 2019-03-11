import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class AppMenu extends MenuBar{
	App app;
	
	public AppMenu(App app) {
		this.app = app;
		init();
	}
	
	private void init() {
        
        Menu fileMenu = new Menu("File");
        
        Menu newMenu = new Menu("New");
        MenuItem newCodeEditorMenuItem = new MenuItem("New Program");
        MenuItem newGraphEditorMenuItem = new MenuItem("New Graph");
        
        newCodeEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //createNew(EDITOR);
            }
        });
        
        newGraphEditorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //createNew(EDITOR);
            }
        });
        
        newMenu.getItems().addAll(
        		newCodeEditorMenuItem,
        		newGraphEditorMenuItem);
        
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                app.loadCodeEditor();
            }
        });
        
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                app.saveEditor();
            }
        });
        
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //stop();
                //getStage().close();
            }
        });
        
        fileMenu.getItems().addAll(
        		newMenu,
        		openMenuItem,
        		saveMenuItem,
        		exitMenuItem);
        

        this.getMenus().addAll(fileMenu);
	}
}
