package editors;
import javafx.scene.Node;

public abstract class Editor {
	public abstract Node getDisplay();
	public abstract void loadData(String path);
	public abstract void saveData();
	public boolean modified = false;
	public String name = null;
}
