package de.biersaecke.event_evaluator.model;

import java.io.Serializable;

import com.google.api.services.calendar.model.Event;

import de.biersaecke.event_evaluator.util.CalendarConnector;

/**
 * Wrapper class for the Google Calendar Event object.<br />
 * Adds EventEvaluator specific fields and helper methods.
 * TODO: An event should be able to support multiple types of evaluations
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

	// Helper methods

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public double getEvaluationPercentage() {
		return this.evaluation.getPercentage();
	}

	public Event getEvent() {
		if (event == null) {
			try {
				// TODO: Remove hard-coded primary calendar ID
				event = CalendarConnector.getCalenderService().events().get("primary", eventId).execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return event;
	}

	protected void setEvent(Event event) {
		this.event = event;
		eventId = this.event.getId();
	}

	@Override
	public String toString() {
		return getEvent().toString() + "\n" + evaluation.toString();
	}

}
