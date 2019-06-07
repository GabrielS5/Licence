import graph.Graph;
import graph.Node;
import graph.Edge;
import tools.Program;
import java.util.List;

public class FloydWarshall extends Program {
  private final double infinite = Double.MAX_VALUE;

  private double getDistance(Graph graph, Node source, Node destination) {
    Edge edge = graph.getEdge(source, destination);

    if (edge != null) return edge.getValue();
    else return infinite;
  }

  private void updateEdge(Graph graph, Node source, Node destination, double value) {
    if (source.equals(destination)) return;

    Edge edge = graph.getEdge(source, destination);

    if (edge != null) {
      edge.setValue(value);
    } else {
      edge = graph.createEdge(source, destination);
      edge.setValue(value);
    }
  }

  @Override
  public void run(Graph graph) {
    List<Node> nodes = graph.getNodes();

    for (Node aux : nodes) {
      for (Node source : nodes) {
        for (Node destination : nodes) {

          double auxDistance1 = getDistance(graph, source, aux);
          double auxDistance2 = getDistance(graph, aux, destination);
          double originalDistance = getDistance(graph, source, destination);

          if (auxDistance1 + auxDistance2 < originalDistance)
            updateEdge(graph, source, destination, auxDistance1 + auxDistance2);
        }
      }
    }
  }
}
