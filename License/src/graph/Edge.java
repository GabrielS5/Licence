package graph;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Group {
	private GraphNode source;
	private GraphNode destination;
	private Line shape;
	private GraphElementValueField valueField;
	
	public Edge(GraphNode source, GraphNode destination) {		
		this.source = source;
		this.destination = destination;

		shape = new Line();
		shape.startXProperty().bind(source.xProperty);
		shape.startYProperty().bind( source.yProperty);
		shape.endXProperty().bind(destination.xProperty);
		shape.endYProperty().bind(destination.yProperty);
		
	    DoubleBinding valueFieldXBinding = Bindings.createDoubleBinding(() -> (shape.startXProperty().get() + shape.endXProperty().get()) / 2,  shape.startXProperty(), shape.endXProperty());
	    DoubleBinding valueFieldYBinding = Bindings.createDoubleBinding(() -> (shape.startYProperty().get() + shape.endYProperty().get()) / 2,  shape.startYProperty(), shape.endYProperty());
	    
	    valueField = new GraphElementValueField(valueFieldXBinding,valueFieldYBinding);
	    
	    shape.setOnMouseReleased((event) -> {
			this.valueField.showInput();
			});
		
		this.getChildren().add(shape);
		this.getChildren().add(valueField);
		
		this.source.addExteriorEdge(this);
		this.destination.addInteriorEdge(this);
	}
	
	public void highlightOn() {
		shape.setStroke(Color.RED);
	}
	
	public void highlightOff() {
		shape.setStroke(Color.BLACK);
	}
}
