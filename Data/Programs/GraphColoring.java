import graph.Graph;
import graph.Node;
import tools.Program;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GraphColoring extends Program {

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

  private Color findUnusedColor(List<Node> nodes, List<Color> colors) {

    for (Color color : colors) {
      boolean isUnused = true;

      for (Node node : nodes) {
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

    for (Node node : graph.getNodes()) {
      List<Node> neighbours = node.getAllNeighbours();
      Color color = findUnusedColor(neighbours, colors);
      node.setColor(color);
for(int i = 0;i < 100000;i++){
i++;
}
      println("Ceva printat");
    }
  }
}
