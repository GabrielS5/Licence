import graph.Graph;
import graph.GraphNode;
import tools.Program;

public class Blenao implements Program {

  @Override
  public void run(Graph graph) {

    for (GraphNode node : graph.getNodes()) {
      node.setX(node.getX() + 10);
    }
  }
}
