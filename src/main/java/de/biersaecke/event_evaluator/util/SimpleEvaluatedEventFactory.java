package de.biersaecke.event_evaluator.util;

import com.google.api.services.calendar.model.Event;

import de.biersaecke.event_evaluator.model.EvaluatedEvent;
import de.biersaecke.event_evaluator.model.Evaluation;

/**
 * Factory for {@link EvaluatedEvent} objects.<br />
 * Will only parse necessary members from Google Event object
 */
public class SimpleEvaluatedEventFactory implements IEvaluatedEventFactory {

	@Override
	public EvaluatedEvent wrapEvent(Event event, Evaluation evaluation) {
		EvaluatedEvent wrapper = new EvaluatedEvent(event, evaluation);
		return wrapper;
	}

}
