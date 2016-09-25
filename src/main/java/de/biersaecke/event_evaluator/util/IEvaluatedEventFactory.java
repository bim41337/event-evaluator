package de.biersaecke.event_evaluator.util;

import com.google.api.services.calendar.model.Event;

import de.biersaecke.event_evaluator.model.EvaluatedEvent;
import de.biersaecke.event_evaluator.model.Evaluation;

/**
 * Factory interface for creating the Google event wrapper objects
 */
public interface IEvaluatedEventFactory {

	public EvaluatedEvent wrapEvent(Event event, Evaluation evaluation);

}
