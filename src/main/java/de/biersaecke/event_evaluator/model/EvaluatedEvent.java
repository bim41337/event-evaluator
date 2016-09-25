package de.biersaecke.event_evaluator.model;

import java.io.Serializable;

import com.google.api.services.calendar.model.Event;

/**
 * Wrapper class for the Google Calendar Event object.<br />
 * Adds EventEvaluator specific fields and helper methods.
 * TODO: An event must support multiple types of evaluations
 */
public class EvaluatedEvent implements Serializable {

	// Fields

	/**
	 * Serialization unique ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The wrapped Google Calendar event object
	 */
	private transient Event event;
	/**
	 * The unique Google event identifier, since the {@link Event} class is not serializable
	 */
	private String eventId;
	/**
	 * The evaluation assigned to this event wrapper
	 */
	private Evaluation evaluation;

	// Constructors

	private EvaluatedEvent() {
	}

	public EvaluatedEvent(Event event, Evaluation evaluation) {
		this();
		setEvent(event);
		setEvaluation(evaluation);
	}

	// Methods

	/*
	 * Serialization methods
	 * private void readObject(ObjectInputStream in) throws Exception {
	 * in.defaultReadObject();
	 * // TODO: deserialize event object
	 * }
	 * 
	 * private void writeObject(ObjectOutputStream out) throws Exception {
	 * out.defaultWriteObject();
	 * // TODO: serialize event object
	 * }
	 * 
	 * @Override
	 * public String toString() {
	 * return "EVENT:\n" + getEvent().toString() + "\nEVALUATION: " + getEvaluation().toString() + "\n";
	 * }
	 */

	// Helper methods

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Event getEvent() {
		return event;
	}

	protected void setEvent(Event event) {
		this.event = event;
		eventId = this.event.getId();
	}

}
