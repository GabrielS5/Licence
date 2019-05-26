package graph;

import java.util.ArrayList;
import java.util.List;

import commands.Command;

public class Edge {
	private Graph graph;
	private List<Command> commands = new ArrayList<Command>();
	private Node source;
	private Node destination;
	private double value;
	private int id;

	public Edge(GraphicEdge edge, Graph graph) {
		value = 0;
		id = edge.getUniqueId();
		this.graph = graph;
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
		this.value = value;
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	
}
