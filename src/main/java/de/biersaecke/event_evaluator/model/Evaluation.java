package de.biersaecke.event_evaluator.model;

/**
 * Enum class for different types of evaluation which can apply to an {@link EvaluatedEvent}
 */
public enum Evaluation {

	SUCCESS, FAILURE,

	;

	private double percentage;

	private Evaluation() {
		percentage = 0.0;
	}

	/**
	 * Fluid-style method to set percentage on an evaluation conveniently.
	 * @param percentage Needs to be part of the interval (0.0, 1.0)
	 * @return Updated evaluation reference
	 */
	public Evaluation withPercentage(double percentage) {
		setPercentage(percentage);
		return this;
	}

	public double getPercentage() {
		return percentage;
	}

	private void setPercentage(double percentage) throws IllegalArgumentException {
		if (Double.compare(0.0, percentage) <= 0 && Double.compare(1.0, percentage) >= 0) {
			this.percentage = percentage;
		} else {
			throw new IllegalArgumentException("No valid percentage supplied to evaluation " + this.name());
		}
	}

}
