package graph.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph.generation.constraints.Constraint;
import graph.generation.steps.AddEdgeStep;
import graph.generation.steps.AddNodeStep;
import graph.generation.steps.IStep;
import graph.graphic.GraphicGraph;

public class GraphGenerator {
	private Random random;

	public GraphGenerator() {
		random = new Random();
	}

	public GraphicGraph generate(GraphicGraph graph, List<Constraint> constraints) {
		List<IStep> steps = new ArrayList<IStep>();

		while (!checkConstraints(graph, constraints)) {
			IStep step = getRandomStep();

			boolean executionResult = step.execute(graph);

			if (!executionResult)
				continue;

			boolean illegalStep = false;

			for (Constraint constraint : constraints) {
				if (constraint.isAlreadyCompleted() && !constraint.check(graph)) {
					illegalStep = true;
					break;
				}
			}

			if (illegalStep)
				step.revert(graph);
			else
				steps.add(step);

		}

		return graph;
	}

	private boolean checkConstraints(GraphicGraph graph, List<Constraint> constraints) {
		boolean response = true;

		for (Constraint constraint : constraints) {
			if (!constraint.check(graph))
				response = false;
		}

		return response;
	}

	private IStep getRandomStep() {
		int choice = random.nextInt(2);

		switch (choice) {
		case 0:
			return new AddNodeStep();
		case 1:
			return new AddEdgeStep();
		default:
			return null;
		}

	}
}
