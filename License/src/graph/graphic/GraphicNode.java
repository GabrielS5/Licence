package graph.graphic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicNode extends Group {

	private ArrayList<GraphicEdge> interiorEdges = new ArrayList<GraphicEdge>();
	private ArrayList<GraphicEdge> exteriorEdges = new ArrayList<GraphicEdge>();
	private Circle shape;
	public GraphElementValueField valueField;
	private Color color = Color.WHITE;

	public DoubleProperty xProperty = new SimpleDoubleProperty();
	public DoubleProperty yProperty = new SimpleDoubleProperty();
	private int id;

	public GraphicNode(double x, double y) {
		this(x, y, " ");
	}

	public GraphicNode(double x, double y, String valueFieldInitialValue) {
		Random rand = new Random();
		List<Color> lista = new ArrayList<Color>();
		lista.add(new Color(rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, 1));
		shape = new Circle(0, 0, 20);
		shape.setStrokeWidth(2);
		shape.setFill(color);
		id = 0;
		this.id = hashCode();
		highlightOff();
		this.setX(x);
		this.setY(y);

		DoubleBinding valueFieldXBinding = Bindings.createDoubleBinding(() -> shape.getCenterX(), this.xProperty);
		DoubleBinding valueFieldYBinding = Bindings.createDoubleBinding(() -> shape.getCenterY(), this.yProperty);

		valueField = new GraphElementValueField(valueFieldXBinding, valueFieldYBinding, valueFieldInitialValue);

		this.getChildren().add(shape);
		this.getChildren().add(valueField);
	}

	public void setX(double x) {
		xProperty.set(this.getTranslateX() + x);
		this.setTranslateX(this.getTranslateX() + x);
	}

	public void setY(double y) {
		yProperty.set(this.getTranslateY() + y);
		this.setTranslateY(this.getTranslateY() + y);
	}

	public double getX() {
		return this.getLayoutX() + getTranslateX();
	}

	public double getY() {
		return this.getLayoutY() + getTranslateY();
	}

	public void addInteriorEdge(GraphicEdge edge) {
		this.interiorEdges.add(edge);
	}

	public void addExteriorEdge(GraphicEdge edge) {
		this.exteriorEdges.add(edge);
	}

	public void highlightOn() {
		shape.setStroke(Color.RED);
	}

	public void highlightOff() {
		shape.setStroke(Color.BLACK);
	}

	public void setColor(Color color) {
		this.color = color;
		this.shape.setFill(color);
	}

	public Color getColor() {
		return this.color;
	}

	public int getUniqueId() {
		if (id == 0)
			id = hashCode();

		return id;
	}

	public void setUniqueId(int id) {
		this.id = id;
	}

	public ArrayList<GraphicEdge> getInteriorEdges() {
		return interiorEdges;
	}

	public void setInteriorEdges(ArrayList<GraphicEdge> interiorEdges) {
		this.interiorEdges = interiorEdges;
	}

	public ArrayList<GraphicEdge> getExteriorEdges() {
		return exteriorEdges;
	}

	public void setExteriorEdges(ArrayList<GraphicEdge> exteriorEdges) {
		this.exteriorEdges = exteriorEdges;
	}

	public List<GraphicNode> getAllNeighbours() {
		List<GraphicNode> result = new ArrayList<GraphicNode>();

		for (GraphicEdge edge : interiorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		for (GraphicEdge edge : exteriorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);
		
		return result;
	}
	
	public List<GraphicNode> getIncomingNeighbours() {
		List<GraphicNode> result = new ArrayList<GraphicNode>();

		for (GraphicEdge edge : interiorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		for (GraphicEdge edge : exteriorEdges) {
			if(edge.isDoubleEdged()) {
				result.add(edge.getSource());
				result.add(edge.getDestination());
			}
		}

		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);
		
		return result;
	}
	
	public List<GraphicNode> getOutgoingNeighbours() {
		List<GraphicNode> result = new ArrayList<GraphicNode>();

		for (GraphicEdge edge : interiorEdges) {
			if(edge.isDoubleEdged()) {
				result.add(edge.getSource());
				result.add(edge.getDestination());
			}
		}

		for (GraphicEdge edge : exteriorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}
		
		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);
		
		return result;
	}

	public boolean isNeighbour(GraphicNode node) {
		return getAllNeighbours().contains(node);
	}

	public Circle getShape() {
		return shape;
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
