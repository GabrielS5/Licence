package graph.serialization;

import java.io.Serializable;

import graph.GraphElementValueField;

public class GraphElementValueFieldSerialization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String value;

	public GraphElementValueFieldSerialization(GraphElementValueField valueField) {
		this.value = valueField.getText();
	}
}
