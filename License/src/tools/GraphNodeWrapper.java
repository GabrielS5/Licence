package tools;

import java.util.ArrayList;
import java.util.List;

import commands.ChangeGraphNodeColorCommand;
import commands.ICommand;
import graph.Edge;
import graph.GraphNode;
import javafx.scene.paint.Color;

public class GraphNodeWrapper extends GraphNode{
	private List<ICommand> commands = new ArrayList<ICommand>();
	
	public GraphNodeWrapper(GraphNode node, GraphWrapper graph) {
		super(node.getX(), node.getY());
		
		this.color = node.getColor();
		this.id = node.getUniqueId();
		this.valueField = node.valueField;	
	}
	
	public void initialize(GraphNode node, GraphWrapper graph) {
		for(Edge edge : interiorEdges) {
			EdgeWrapper edgeWrapper = graph.getEdgeWrapperById(edge.getUniqueId());
			if(edgeWrapper == null) {
				edgeWrapper = new EdgeWrapper(edge, graph);
				graph.addEdge(edgeWrapper);
			}
			interiorEdges.add(edgeWrapper);
		}
		
		for(Edge edge : exteriorEdges) {
			EdgeWrapper edgeWrapper = graph.getEdgeWrapperById(edge.getUniqueId());
			if(edgeWrapper == null) {
				edgeWrapper = new EdgeWrapper(edge, graph);
				graph.addEdge(edgeWrapper);
			}
			exteriorEdges.add(edgeWrapper);
		}
	}
	
	public GraphNodeWrapper(double x, double y) {
		super(x, y);
	}

	@Override
	public void setX(double x) {
		super.setX(x);
	}

	@Override
	public void setY(double y) {
		super.setY(y);
	}

	@Override
	public double getX() {
		return super.getX();
	}

	@Override
	public double getY() {
		return super.getY();
	}

	@Override
	public void addInteriorEdge(Edge edge) {
		super.getInteriorEdges().add(edge);
	}

	@Override
	public void addExteriorEdge(Edge edge) {
		super.getExteriorEdges().add(edge);
	}

	@Override
	public void highlightOn() {
		super.highlightOn();
	}

	@Override
	public void highlightOff() {
		super.highlightOff();
	}

	@Override
	public void setColor(Color color) {
		
		commands.add(new ChangeGraphNodeColorCommand(super.getUniqueId(),color));
		
		super.setColor(color);
	}

	@Override
	public Color getColor() {
		return super.getColor();
	}

	@Override
	public ArrayList<Edge> getInteriorEdges() {
		return super.getInteriorEdges();
	}

	@Override
	public void setInteriorEdges(ArrayList<Edge> interiorEdges) {
		super.setInteriorEdges(interiorEdges);
	}

	@Override
	public ArrayList<Edge> getExteriorEdges() {
		return super.getExteriorEdges();
	}

	@Override
	public void setExteriorEdges(ArrayList<Edge> exteriorEdges) {
		super.setExteriorEdges(exteriorEdges);
	}

	@Override
	public List<GraphNode> getNeighbours() {
		return super.getNeighbours();
	}

	@Override
	public boolean isNeighbour(GraphNode node) {
		return super.isNeighbour(node);
	}
}
