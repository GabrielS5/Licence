package graph;

import java.util.ArrayList;
import java.util.List;

import commands.AddEdgeCommand;
import commands.AddNodeCommand;
import commands.ChangeToDoubleEdgeCommand;
import commands.Command;
import commands.GetEdgeCommand;
import commands.GetGraphNodesCommand;
import commands.RemoveEdgeCommand;
import commands.RemoveNodeCommand;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class Graph {
	private boolean directed;
	private static int commandOrder = 0;
	private List<Command> commands = new ArrayList<Command>();

	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public Graph(GraphicGraph graph) {
		this.directed = graph.isDirected();

		for (GraphicNode graphicNode : graph.getNodes()) {
			if (getNodeById(graphicNode.getUniqueId()) == null) {
				Node node = new Node(graphicNode, this);
				this.addNode(node);
				node.initialize(graphicNode, this);
			}
		}

		for (GraphicEdge graphicEdge : graph.getEdges()) {
			if (getEdgeById(graphicEdge.getUniqueId()) == null) {
				Edge edge = new Edge(graphicEdge, this);
				this.addEdge(edge);
				edge.initialize(graphicEdge, this);
			}
		}
	}

	public void addNode(Node graphNode) {
		this.nodes.add(graphNode);
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public ArrayList<Node> getNodes() {
		commands.add(new GetGraphNodesCommand(Graph.getCommandOrder(), nodes));

		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public Node getNodeById(int id) {
		for (Node node : nodes) {
			if (node.getUniqueId() == id)
				return node;
		}

		return null;
	}

	public Edge getEdgeById(int id) {
		for (Edge edge : edges) {
			if (edge.getUniqueId() == id)
				return edge;
		}

		return null;
	}

	public void removeNode(Node node) {

		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(node.getExteriorEdges());
		edges.addAll(node.getInteriorEdges());

		for (Edge edge : edges)
			removeEdge(edge);

		commands.add(new RemoveNodeCommand(Graph.getCommandOrder(), node));

		nodes.remove(node);
	}

	public Edge createEdge(Node source, Node destination) {
		if (source.equals(destination))
			return null;

		for (Edge edge : edges) {
			if (edge.getSource().equals(source) && edge.getDestination().equals(destination))
				return null;
			else if (edge.getSource().equals(destination) && edge.getDestination().equals(source)) {
				edge.changeToDoubleEdge();

				commands.add(new ChangeToDoubleEdgeCommand(Graph.getCommandOrder(), edge));
				return edge;
			}
		}
		Edge edge = new Edge(this, source, destination);
		addEdge(edge);

		commands.add(new AddEdgeCommand(Graph.getCommandOrder(), edge));

		return edge;
	}

	public Edge getEdge(Node source, Node destination) {
		for (Edge edge : edges) {
			if ((edge.getSource().equals(source) && edge.getDestination().equals(destination))
					|| (edge.getSource().equals(destination) && edge.getDestination().equals(source)
							&& edge.isDoubleEdged())) {
				commands.add(new GetEdgeCommand(Graph.getCommandOrder(), edge));
				
				return edge;
			}
		}

		return null;
	}

	public Node createNode(double x, double y) {
		Node node = new Node(this, x, y);
		addNode(node);

		commands.add(new AddNodeCommand(Graph.getCommandOrder(), node));

		return node;
	}

	public void removeEdge(Edge edge) {

		commands.add(new RemoveEdgeCommand(Graph.getCommandOrder(), edge));
		this.getEdges().remove(edge);

		edge.getSource().removeEdge(edge);
		edge.getDestination().removeEdge(edge);
	}

	public List<Command> getCommands() {
		List<Command> result = new ArrayList<Command>();

		result.addAll(commands);

		for (Node node : nodes) {
			result.addAll(node.getCommands());
		}

		for (Edge edge : edges) {
			result.addAll(edge.getCommands());
		}

		return result;
	}

	public static int getCommandOrder() {
		commandOrder++;

		return commandOrder;
	}

	public boolean isDirected() {
		return directed;
	}
}
