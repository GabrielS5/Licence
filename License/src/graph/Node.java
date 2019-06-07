package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.ChangeNodeColorCommand;
import commands.ChangeNodeValueCommand;
import commands.ChangeNodeXCommand;
import commands.ChangeNodeYCommand;
import commands.Command;
import commands.GetNodeNeighboursCommand;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicNode;
import javafx.scene.paint.Color;

public class Node {
	private Graph graph;
	private List<Command> commands = new ArrayList<Command>();
	private ArrayList<Edge> interiorEdges = new ArrayList<Edge>();
	private ArrayList<Edge> exteriorEdges = new ArrayList<Edge>();
	private double value;
	private Color color;
	private double x;
	private double y;
	private int id;

	public Node(GraphicNode node, Graph graph) {
		this.color = node.getColor();
		this.id = node.getUniqueId();
		this.value = node.getValue();
		this.graph = graph;
		this.x = node.getX();
		this.y = node.getY();
	}
	
	public Node(Graph graph, double x, double y) {
		this.graph = graph;
		this.x = x;
		this.y = y;
		this.id = getUniqueId();
		this.color = Color.WHITE;
	}

	public void initialize(GraphicNode node, Graph graph) {
		for (GraphicEdge graphicEdge : node.getInteriorEdges()) {
			Edge edge = graph.getEdgeById(graphicEdge.getUniqueId());

			if (edge == null) {
				edge = new Edge(graphicEdge, graph);
				graph.addEdge(edge);
				edge.initialize(graphicEdge, graph);
			}

			interiorEdges.add(edge);
		}

		for (GraphicEdge graphicEdge : node.getExteriorEdges()) {
			Edge edge = graph.getEdgeById(graphicEdge.getUniqueId());

			if (edge == null) {
				edge = new Edge(graphicEdge, graph);
				graph.addEdge(edge);
				edge.initialize(graphicEdge, graph);
			}

			exteriorEdges.add(edge);
		}

	}

	public void setX(double x) {
		commands.add(new ChangeNodeXCommand(Graph.getCommandOrder(), this.id, x));

		this.x = x;
	}

	public void setY(double y) {
		commands.add(new ChangeNodeYCommand(Graph.getCommandOrder(), this.id, y));

		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void addInteriorEdge(Edge edge) {
		this.interiorEdges.add(edge);
	}

	public void addExteriorEdge(Edge edge) {
		this.exteriorEdges.add(edge);
	}

	public void setColor(Color color) {
		commands.add(new ChangeNodeColorCommand(Graph.getCommandOrder(), this.id, color));

		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		commands.add(new ChangeNodeValueCommand(Graph.getCommandOrder(), id, value));
		this.value = value;
	}

	public int getUniqueId() {
		if (id == 0)
			id = hashCode();

		return id;
	}

	public void setUniqueId(int id) {
		this.id = id;
	}

	public ArrayList<Edge> getInteriorEdges() {
		return interiorEdges;
	}

	public void setInteriorEdges(ArrayList<Edge> interiorEdges) {
		this.interiorEdges = interiorEdges;
	}

	public ArrayList<Edge> getExteriorEdges() {
		return exteriorEdges;
	}

	public void setExteriorEdges(ArrayList<Edge> exteriorEdges) {
		this.exteriorEdges = exteriorEdges;
	}

	public List<Node> getAllNeighbours() {
		List<Node> result = new ArrayList<Node>();

		for (Edge edge : interiorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		for (Edge edge : exteriorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);

		commands.add(new GetNodeNeighboursCommand(Graph.getCommandOrder(), result, this.id));

		return result;
	}

	public List<Node> getIncomingNeighbours() {
		List<Node> result = new ArrayList<Node>();

		for (Edge edge : interiorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		for (Edge edge : exteriorEdges) {
			if (edge.isDoubleEdged()) {
				result.add(edge.getSource());
				result.add(edge.getDestination());
			}
		}

		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);

		commands.add(new GetNodeNeighboursCommand(Graph.getCommandOrder(), result, this.id));

		return result;
	}

	public List<Node> getOutgoingNeighbours() {
		List<Node> result = new ArrayList<Node>();

		for (Edge edge : interiorEdges) {
			if (edge.isDoubleEdged()) {
				result.add(edge.getSource());
				result.add(edge.getDestination());
			}
		}

		for (Edge edge : exteriorEdges) {
			result.add(edge.getSource());
			result.add(edge.getDestination());
		}

		result = result.stream().distinct().collect(Collectors.toList());
		result.remove(this);

		commands.add(new GetNodeNeighboursCommand(Graph.getCommandOrder(), result, this.id));

		return result;
	}

	public boolean isNeighbour(Node node) {
		return getAllNeighbours().contains(node);
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void removeEdge(Edge edge) {
		this.exteriorEdges.remove(edge);
		this.interiorEdges.remove(edge);
	}
}
