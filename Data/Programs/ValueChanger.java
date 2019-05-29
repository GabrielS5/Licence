import graph.Graph;
import graph.Node;
import graph.Edge;
import tools.Program;

public class ValueChanger extends Program {

  @Override
  public void run(Graph graph) {
    for (Node node : graph.getNodes()) {
      node.setValue(node.getValue() + 50);
    }
    for (Edge edge : graph.getEdges()) {
      edge.setValue(edge.getValue() + 50);
    }
  }
}
