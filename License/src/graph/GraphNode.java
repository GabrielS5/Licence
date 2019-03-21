package graph;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphNode extends Group {

	private ArrayList<Edge> interiorEdges = new ArrayList<Edge>();
	private ArrayList<Edge> exteriorEdges = new ArrayList<Edge>();
	private Circle shape;
	private GraphElementValueField valueField;

	public DoubleProperty xProperty = new SimpleDoubleProperty();
	public DoubleProperty yProperty = new SimpleDoubleProperty();

	public GraphNode(double x, double y) {
		shape = new Circle(0, 0, 15);
		shape.setStrokeWidth(2);
		shape.setFill(Color.TRANSPARENT);
		highlightOff();
		this.setX(x);
		this.setY(y);
		
	    DoubleBinding valueFieldXBinding = Bindings.createDoubleBinding(() -> shape.getCenterX(), this.xProperty);
	    DoubleBinding valueFieldYBinding = Bindings.createDoubleBinding(() -> shape.getCenterY(),   this.yProperty);
	    
	    valueField = new GraphElementValueField(valueFieldXBinding,valueFieldYBinding);
	    
		this.getChildren().add(shape);
		this.getChildren().add(valueField);	   
	}

	public void setX(double x) {
		xProperty.set(this.getLayoutX() + x);
		this.setLayoutX(this.getLayoutX() + x);
	}

	public void setY(double y) {
		yProperty.set(this.getLayoutY() + y);
		this.setLayoutY(this.getLayoutY() + y);
	}

	public double getX() {
		System.out.println(this.getLayoutX());
		return this.getLayoutX();
	}

	public double getY() {
		return this.getLayoutY();
	}

	public void addInteriorEdge(Edge edge) {
		this.interiorEdges.add(edge);
	}

	public void addExteriorEdge(Edge edge) {
		this.exteriorEdges.add(edge);
	}
	
	public void highlightOn() {
		shape.setStroke(Color.RED);
	}
	
	public void highlightOff() {
		shape.setStroke(Color.BLACK);
	}
}
