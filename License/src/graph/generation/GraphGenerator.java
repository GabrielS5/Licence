package graph.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph.generation.constraints.Constraint;
import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.implementations.EdgesValueConstraint;
import graph.generation.constraints.implementations.NodesValueConstraint;
import graph.generation.steps.AddEdgeStep;
import graph.generation.steps.AddEdgesValueStep;
import graph.generation.steps.AddNodeStep;
import graph.generation.steps.AddNodesValueStep;
import graph.generation.steps.IStep;
import graph.graphic.GraphicGraph;

public class GraphGenerator {
	private Random random;

	public GraphGenerator() {
		random = new Random();
	}

	public GraphicGraph generate(GraphicGraph graph, List<Constraint> constraints) {
		List<IStep> postSteps = getPostSteps(constraints);
		int tries = 0;

		while (!checkConstraints(graph, constraints)) {
			IStep step = getRandomStep();

			boolean executionResult = step.execute(graph);

			if (!executionResult)
				continue;

			boolean illegalStep = false;

			for (Constraint constraint : constraints) {
				if (constraint.check(graph) == ConstraintFeedback.Revert) {
					illegalStep = true;
					break;
				}
			}

			if (illegalStep)
				step.revert(graph);

			if (tries++ == 5000)
				break;
		}

		for (IStep step : postSteps) {
			step.execute(graph);
		}

		return graph;
	}

	private boolean checkConstraints(GraphicGraph graph, List<Constraint> constraints) {
		boolean response = true;

		for (Constraint constraint : constraints) {
			if (constraint.check(graph) != ConstraintFeedback.Complete)
				response = false;
		}

		return response;
	}

	private List<IStep> getPostSteps(List<Constraint> constraints) {
		List<IStep> postSteps = new ArrayList<IStep>();

		for (int i = constraints.size() - 1; i >= 0; i--) {
			if (constraints.get(i) instanceof NodesValueConstraint) {

				postSteps.add(new AddNodesValueStep((NodesValueConstraint) constraints.get(i)));
				constraints.remove(i);
			} else if (constraints.get(i) instanceof EdgesValueConstraint) {

				postSteps.add(new AddEdgesValueStep((EdgesValueConstraint) constraints.get(i)));
				constraints.remove(i);
			}
		}

		return postSteps;
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
