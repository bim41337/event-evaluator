package de.biersaecke.event_evaluator.model;

import java.io.ObjectInputStream;
import java.io.Serializable;

import com.google.api.services.calendar.model.Event;

import de.biersaecke.event_evaluator.util.CalendarConnector;

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

	/**
	 * Standard deserialization method.<br />
	 * TODO: Remove hard-coded primary calendar ID.
	 * 
	 * @param in
	 * @throws Exception
	 */
	private void readObject(ObjectInputStream in) throws Exception {
		in.defaultReadObject();
		lazyLoadEvent();
	}

	/*
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

	private void lazyLoadEvent() {
		if (event == null) {
			try {
				event = CalendarConnector.getCalenderService().events().get("primary", eventId).execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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

	@Override
	public String toString() {
		lazyLoadEvent();
		return event.toString() + "\n" + evaluation.toString();
	}

}
