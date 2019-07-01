import graph.Graph;
import tools.Program;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import graph.Node;

public class DFSColor extends Program {

  private void dfsStep(Node node, List<Node> visitedNodes) {
    if (visitedNodes.contains(node)) return;
    visitedNodes.add(node);

    node.setValue(visitedNodes.size());

    for (Node neighbour : node.getOutgoingNeighbours()) {
      dfsStep(neighbour, visitedNodes);
    }
  }

  @Override
  public void run(Graph graph) {
    List<Node> visitedNodes = new ArrayList<Node>();

    for (Node node : graph.getNodes()) dfsStep(node, visitedNodes);
  }
}
