import graph.Graph;
import graph.Node;
import graph.Edge;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import tools.Program;
import java.util.Random;
import javafx.scene.paint.Color;

public class DirectedConexComponents extends Program {

  private Color generateColor() {
    Random rand = new Random();
    return new Color(
        rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, 1);
  }

  private void colorConexGroup(List<Node> nodes) {
    Color color = generateColor();
    for (Node node : nodes) {
      node.setColor(color);
    }
  }

  private void normalDfsStep(Node node, Stack<Node> nodeStack, List<Node> visitedNodes) {
    if (visitedNodes.contains(node)) return;
    visitedNodes.add(node);

    for (Node neighbour : node.getOutgoingNeighbours()) {
      normalDfsStep(neighbour, nodeStack, visitedNodes);
    }
    nodeStack.push(node);
  }

  private void transposedDfsStep(Node node, List<Node> conexGroup, List<Node> visitedNodes) {
    if (visitedNodes.contains(node)) return;
    visitedNodes.add(node);
    conexGroup.add(node);

    for (Node neighbour : node.getIncomingNeighbours()) {
      transposedDfsStep(neighbour, conexGroup, visitedNodes);
    }
  }

  @Override
  public void run(Graph graph) {
    List<Node> nodes = graph.getNodes();
    Stack<Node> nodeStack = new Stack<Node>();
    List<Node> visitedNodes = new ArrayList<Node>();

    for (Node node : nodes) {
      if (!nodeStack.contains(node)) {
        normalDfsStep(node, nodeStack, visitedNodes);
      }
    }
    visitedNodes = new ArrayList<Node>();
    List<Node> conexGroup;

    for (int i = nodeStack.size(); i > 0; i--) {
      Node node = nodeStack.pop();
      if (!visitedNodes.contains(node)) {
        conexGroup = new ArrayList<Node>();
        transposedDfsStep(node, conexGroup, visitedNodes);
        colorConexGroup(conexGroup);
      }
    }
  }
}
