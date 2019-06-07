import graph.Graph;
import graph.Node;
import tools.Program;

public class AddingNodes extends Program {

  @Override
  public void run(Graph graph) {
    for(Node node : graph.getNodes()){
	for(Node secondNode : graph.getNodes()){
	graph.createEdge(node,secondNode);
}
	}
  }
}
