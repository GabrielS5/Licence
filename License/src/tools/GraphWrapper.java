package tools;

import java.util.ArrayList;

import graph.Edge;
import graph.FlexibleCanvas;
import graph.Graph;
import graph.GraphNode;

public class GraphWrapper extends Graph {

	public GraphWrapper(Graph graph) {
		this.setNodes(graph.getNodes());

		for (GraphNode node : graph.getNodes()) {
			if (getGraphNodeWrapperById(node.getUniqueId()) == null) {
				GraphNodeWrapper nodeWrapper = new GraphNodeWrapper(node, this);
				this.addGraphNode(nodeWrapper);
				nodeWrapper.initialize(node, this);
			}
		}

		for (Edge edge : graph.getEdges()) {
			if (getEdgeWrapperById(edge.getUniqueId()) == null) {
				EdgeWrapper edgeWrapper = new EdgeWrapper(edge, this);
				this.addEdge(edgeWrapper);
				edgeWrapper.initialize(edge, this);
			}
		}
	}

	@Override
	public void addGraphNode(GraphNode graphNode) {

		super.addGraphNode(graphNode);
	}

	@Override
	public void addEdge(Edge edge) {

		super.addEdge(edge);
	}

	@Override
	public FlexibleCanvas getDisplay() {

		return super.getDisplay();
	}

	@Override
	public GraphNode getNodeByCoordinates(double x, double y) {

		return super.getNodeByCoordinates(x, y);
	}

	@Override
	public ArrayList<GraphNode> getNodes() {

		return super.getNodes();
	}

	@Override
	public ArrayList<Edge> getEdges() {

		return super.getEdges();
	}

	public GraphNodeWrapper getGraphNodeWrapperById(int id) {
		for (GraphNode node : this.getNodes()) {
			if (node.getUniqueId() == id)
				return (GraphNodeWrapper) node;
		}

		return null;
	}

	public EdgeWrapper getEdgeWrapperById(int id) {
		for (Edge edge : edges) {
			if (edge.getUniqueId() == id)
				return (EdgeWrapper) edge;
		}

		return null;
	}
}
