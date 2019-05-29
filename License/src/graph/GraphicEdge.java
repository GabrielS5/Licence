package graph;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GraphicEdge extends Group {
	private GraphicNode source;
	private GraphicNode destination;
	private Line shape;
	public GraphElementValueField valueField;
	private int id;
	private Color color = Color.BLACK;

	public GraphicEdge(GraphicNode source, GraphicNode destination) {
		this(source, destination, " ");
	}

	public GraphicEdge(GraphicNode source, GraphicNode destination, String valueFieldInitialValue) {
		this.source = source;
		this.destination = destination;

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
	
	public void setValue(double value) {
		this.valueField.showInput();
		this.valueField.setText(String.valueOf(value));
	}
}
