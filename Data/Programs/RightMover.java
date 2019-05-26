import graph.Graph;
import graph.Node;
import tools.Program;

public class RightMover extends Program {

  @Override
  public void run(Graph graph) {
    for (Node node : graph.getNodes()) {
      node.setX(node.getX() + 50);
    }
  }
}
