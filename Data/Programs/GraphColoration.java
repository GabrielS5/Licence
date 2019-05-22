import graph.Graph;
import graph.GraphNode;
import tools.Program;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GraphColoration implements Program {

  private Color generateColor() {
    Random rand = new Random();
    return new Color(
        rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, rand.nextInt(255) / 255.0, 1);
  }

  private List<Color> generateColors() {
    List<Color> result = new ArrayList<Color>();

    for (int i = 0; i < 10; i++) {
      result.add(generateColor());
    }

    return result;
  }

  private Color findUnusedColor(List<GraphNode> nodes, List<Color> colors) {
    for (Color color : colors) {
      boolean isUnused = true;

      for (GraphNode node : nodes) {
        if (color.equals(node.getColor())) {
          isUnused = false;
          break;
        }
      }

      if (isUnused) {
        return color;
      }
    }

    Color color = generateColor();
    colors.add(color);
    return color;
  }

  @Override
  public void run(Graph graph) {
    List<Color> colors = generateColors();

    for (GraphNode node : graph.getNodes()) {
      List<GraphNode> neighbours = node.getNeighbours();

      Color color = findUnusedColor(neighbours, colors);
      node.setColor(color);
    }
  }
}
