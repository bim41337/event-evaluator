package de.biersaecke.event_evaluator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import de.biersaecke.event_evaluator.model.EvaluatedEvent;
import de.biersaecke.event_evaluator.model.Evaluation;

public class MainApp {

	public static final String APPLICATION_NAME = "Event Evaluator";
	public static final File SERILIZATION_TARGET_FILE = new File(
			"src/main/java/de/biersaecke/event_evaluator/resources/events.ser");

	public static void main(String[] args) throws Exception {

		// Build a new authorized API client service.
		// Note: Do not confuse this class with the com.google.api.services.calendar.model.Calendar class.
		Calendar service = CalendarConnector.getCalenderService();
		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		List<EvaluatedEvent> valItems = items.stream().map(e -> new EvaluatedEvent(e, Evaluation.SUCCESS))
				.collect(Collectors.toList());
		ArrayList<EvaluatedEvent> serializableEventList = new ArrayList<>(valItems);
		serializableEventList.stream().forEach(event -> System.out.print(event.toString()));
		List<EvaluatedEvent> deserializedEventList;
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERILIZATION_TARGET_FILE));
		out.writeObject(serializableEventList);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(SERILIZATION_TARGET_FILE));
		deserializedEventList = (List<EvaluatedEvent>) in.readObject();
		// deserializedEventList.stream().forEach(event -> System.out.print(event.toString()));
	}

}
