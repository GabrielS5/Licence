package tools;

import graph.Edge;
import graph.GraphNode;
import javafx.scene.paint.Color;

public class EdgeWrapper extends Edge {
	
	public EdgeWrapper(Edge edge, GraphWrapper graph) {
		super(edge.getSource(),edge.getDestination());
		
		valueField = edge.valueField;
		id = edge.getUniqueId();	
	}
	
	public void initialize(Edge edge, GraphWrapper graph) {
		
		GraphNodeWrapper sourceWrapper = graph.getGraphNodeWrapperById(edge.getUniqueId());
		if(sourceWrapper == null) {
			sourceWrapper = new GraphNodeWrapper(edge.getSource(),graph);
			graph.addGraphNode(sourceWrapper);
			sourceWrapper.initialize(edge.getSource(), graph);
		}	
		this.source = sourceWrapper;
		
		GraphNodeWrapper destinationWrapper = graph.getGraphNodeWrapperById(edge.getUniqueId());
		if(destinationWrapper == null) {
			destinationWrapper = new GraphNodeWrapper(edge.getSource(),graph);
			graph.addGraphNode(sourceWrapper);
			destinationWrapper.initialize(edge.getDestination(), graph);
		}	
		this.destination = destinationWrapper;
	}
	
	public EdgeWrapper(GraphNode source, GraphNode destination) {
		super(source, destination);
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
	public GraphNode getSource() {
		return super.getSource();
	}

	@Override
	public GraphNode getDestination() {
		return super.getDestination();
	}
}
