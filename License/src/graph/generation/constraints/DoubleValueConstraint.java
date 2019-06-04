package graph.generation.constraints;

public abstract class DoubleValueConstraint extends Constraint {

	protected int firstValue = Integer.MIN_VALUE;
	protected int secondValue = Integer.MAX_VALUE;

	public void setFirstValue(int firstValue) {
		this.firstValue = firstValue;
	}

	public void setSecondValue(int secondValue) {
		this.secondValue = secondValue;
	}

	public int getFirstValue() {
		return firstValue;
	}

	public int getSecondValue() {
		return secondValue;
	}
}
