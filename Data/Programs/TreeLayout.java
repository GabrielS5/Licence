import graph.Graph;
import graph.Node;
import tools.Program;
import java.util.List;
import java.util.ArrayList;

public class TreeLayout extends Program {
  private List<Node> visitedNodes = new ArrayList<Node>();
  private int screenWidth = 600;

  private List<Node> getNextLevel(List<Node> currentLevel) {
    List<Node> nextLevel = new ArrayList<Node>();

    for (Node node : currentLevel) {
      nextLevel.addAll(node.getAllNeighbours());
    }

    nextLevel.removeAll(visitedNodes);

    return nextLevel;
  }

  private void layout(Graph graph) {}

  @Override
  public void run(Graph graph) {
    int depth = 1;
    List<Node> currentLevel = new ArrayList<Node>();
    currentLevel.add(graph.getNodes().get(0));

    while (currentLevel.size() != 0) {
      int xIndent = screenWidth / (currentLevel.size() + 1);
      int xPosition = xIndent;

      for (Node node : currentLevel) {
        visitedNodes.add(node);
        node.setY(depth * 80);
        node.setX(xPosition);
        xPosition += xIndent;
      }
      depth++;
      currentLevel = getNextLevel(currentLevel);
    }
  }
}
