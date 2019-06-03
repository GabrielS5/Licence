import graph.Graph;
import graph.Node;
import tools.Program;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

public class UndirectedConexComponents extends Program {

  private Color generateColor() {
    Random rand = new Random();
    return new Color(
        rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, 1);
  }

  private List<Color> generateColors(int length) {
    List<Color> result = new ArrayList<Color>();

    for (int i = 0; i < length; i++) {
      result.add(generateColor());
    }

    return result;
  }

  private void insertAllReachableNodes(
      Node node, HashMap<Node, Integer> nodeGroups, int currentGroup) {
    if (!nodeGroups.containsKey(node)) {
      nodeGroups.put(node, currentGroup);
      for (Node neighbour : node.getAllNeighbours()) {
        insertAllReachableNodes(neighbour, nodeGroups, currentGroup);
      }
    }
  }

  @Override
  public void run(Graph graph) {
    List<Node> nodes = graph.getNodes();
    HashMap<Node, Integer> nodeGroups = new HashMap<Node, Integer>();
    int currentGroup = 0;
    for (Node node : nodes) {
      if (!nodeGroups.containsKey(node)) {
        insertAllReachableNodes(node, nodeGroups, currentGroup);
        currentGroup++;
      }
    }

    List<Color> colors = generateColors(currentGroup);

    for (Node node : nodeGroups.keySet()) {
	print(nodeGroups.get(node));
      node.setColor(colors.get(nodeGroups.get(node)));
    }
  }
}
