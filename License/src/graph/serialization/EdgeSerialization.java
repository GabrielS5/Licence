package graph.serialization;

import java.io.Serializable;

import graph.Edge;

public class EdgeSerialization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int sourceIndex;
	public int destinationIndex;
	public GraphElementValueFieldSerialization valueField;
	
	public EdgeSerialization(Edge edge, int sourceIndex, int destinationIndex) {
		this.sourceIndex = sourceIndex;
		this.destinationIndex = destinationIndex;
		this.valueField = new GraphElementValueFieldSerialization(edge.valueField);
	}

}
