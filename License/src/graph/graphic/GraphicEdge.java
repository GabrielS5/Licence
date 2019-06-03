package graph.graphic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import tools.MiscTools;

public class GraphicEdge extends Group {
	private boolean doubleEdged = false;
	private GraphicNode source;
	private GraphicNode destination;
	private Line shape;
	public GraphElementValueField valueField;
	private int id;
	private Color color = Color.BLACK;
	private GraphicGraph graph;
	private List<Line> arrows = new ArrayList<Line>();

	public GraphicEdge(GraphicGraph graph, GraphicNode source, GraphicNode destination) {
		this(graph, source, destination, " ");
	}

	public GraphicEdge(GraphicGraph graph, GraphicNode source, GraphicNode destination, String valueFieldInitialValue) {
		this.source = source;
		this.destination = destination;
		this.graph = graph;

		shape = new Line();
		shape.startXProperty().bind(source.xProperty);
		shape.startYProperty().bind(source.yProperty);
		shape.endXProperty().bind(destination.xProperty);
		shape.endYProperty().bind(destination.yProperty);
		shape.setStroke(color);

		DoubleBinding valueFieldXBinding = Bindings.createDoubleBinding(
				() -> (shape.startXProperty().get() + shape.endXProperty().get()) / 2, shape.startXProperty(),
				shape.endXProperty());
		DoubleBinding valueFieldYBinding = Bindings.createDoubleBinding(
				() -> (shape.startYProperty().get() + shape.endYProperty().get()) / 2, shape.startYProperty(),
				shape.endYProperty());

		valueField = new GraphElementValueField(valueFieldXBinding, valueFieldYBinding, valueFieldInitialValue);

		this.source.addExteriorEdge(this);
		this.destination.addInteriorEdge(this);

		Pair<Line, Line> arrowHead = MiscTools.createArrowHead(source, destination);

		this.arrows.add(arrowHead.getKey());
		this.arrows.add(arrowHead.getValue());
		this.getChildren().addAll(shape, valueField, arrowHead.getKey(), arrowHead.getValue());
	}

	public void highlightOn() {
		shape.setStroke(Color.RED);
	}

	public void highlightOff() {
		shape.setStroke(Color.BLACK);
	}

	public GraphicNode getSource() {
		return source;
	}

	public GraphicNode getDestination() {
		return destination;
	}

	public int getUniqueId() {
		if (id == 0)
			id = hashCode();

		return this.id;
	}

	public void setUniqueId(int id) {
		this.id = id;
	}

	public Line getShape() {
		return shape;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public double getValue() {
		try {
			return Double.parseDouble(valueField.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public boolean isDoubleEdged() {
		return doubleEdged || ! graph.isDirected();
	}
	
	public void setArrowsVisibility(boolean visibility) {
		for(Line arrow : arrows) {
			arrow.setVisible(visibility);
		}
	}

	public void setValue(double value) {
		this.valueField.showInput();
		this.valueField.setText(String.valueOf(value));
	}

	public void changeToDoubleEdge() {
		doubleEdged = true;
		this.destination.addExteriorEdge(this);
		this.source.addInteriorEdge(this);

		Pair<Line, Line> arrowHead = MiscTools.createArrowHead(destination, source);

		this.arrows.add(arrowHead.getKey());
		this.arrows.add(arrowHead.getValue());
		this.getChildren().addAll(arrowHead.getKey(), arrowHead.getValue());
	}

}
