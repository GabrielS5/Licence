package graph;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class GraphNode extends Group{

	public GraphNode(double x, double y) {
		this.getChildren().add(new Circle(0,0,10));
		this.setX(x);
		this.setY(y);
	}
	
	public void setX(double x) {
		this.setTranslateX(x);
	}
	
	public void setY(double y) {
		this.setTranslateY(y);
	}
}
