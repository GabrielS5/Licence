package commands;

import graph.Graph;
import javafx.scene.paint.Color;

public class ChangeGraphNodeColorCommand implements ICommand {
	int id;
	Color color;

	
	public ChangeGraphNodeColorCommand(int id, Color color) {
		this.id = id;
		this.color = color;
	}


	@Override
	public void run(Graph graph) {
		// TODO Auto-generated method stub
		
	}

}
