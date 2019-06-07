package graph;

import java.util.ArrayList;
import java.util.List;

import commands.ChangeEdgeColorCommand;
import commands.ChangeEdgeValueCommand;
import commands.Command;
import graph.graphic.GraphicEdge;
import javafx.scene.paint.Color;

public class Edge {
	private Graph graph;
	private boolean doubleEdged = false;
	private List<Command> commands = new ArrayList<Command>();
	private Node source;
	private Node destination;
	private double value;
	private int id;
	private Color color;

	public Edge(GraphicEdge edge, Graph graph) {
		value = edge.getValue();
		id = edge.getUniqueId();
		this.graph = graph;
		this.color = edge.getColor();
		this.doubleEdged = edge.isDoubleEdged();
	}
	
	public Edge(Graph graph, Node source, Node destination) {
		this.graph = graph;
		this.source = source;
		this.destination = destination;
		this.id = getUniqueId();
		this.color = Color.BLACK;
		this.source.addExteriorEdge(this);
		this.destination.addInteriorEdge(this);
	}

	public void initialize(GraphicEdge edge, Graph graph) {

		Node sourceNode = graph.getNodeById(edge.getSource().getUniqueId());
		if (sourceNode == null) {
			sourceNode = new Node(edge.getSource(), graph);
			graph.addNode(sourceNode);
			sourceNode.initialize(edge.getSource(), graph);
		}
		this.source = sourceNode;

		Node destinationNode = graph.getNodeById(edge.getDestination().getUniqueId());
		if (destinationNode == null) {
			destinationNode = new Node(edge.getDestination(), graph);
			graph.addNode(destinationNode);
			destinationNode.initialize(edge.getDestination(), graph);
		}
		this.destination = destinationNode;
	}

	public Node getSource() {
		return source;
	}

	public Node getDestination() {
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		commands.add(new ChangeEdgeValueCommand(Graph.getCommandOrder(), id, value));
		this.value = value;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setColor(Color color) {
		commands.add(new ChangeEdgeColorCommand(Graph.getCommandOrder(), id, color));
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public boolean isDoubleEdged() {
		return doubleEdged || ! graph.isDirected();
	}

	public void changeToDoubleEdge() {
		this.doubleEdged = true;
		
	}
}
