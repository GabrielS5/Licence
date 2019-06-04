package graph.generation.constraints;

public abstract class SingleValueConstraint extends Constraint {

	protected int value = 0;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
