package de.biersaecke.event_evaluator.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import de.biersaecke.event_evaluator.MainApp;

/**
 * Connector class providing access to the Google Calendar object.<br />
 * Built using the Google Calendar API setup tutorial.<br />
 * Implements a Singleton-style getter ({@link #getCalenderService()}).
 */
public class CalendarConnector {

	private static Calendar calendarInstance;
	private static Credential credentialInstance;

	/**
	 * Directory to store user credentials for this application
	 */
	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"),
			".credentials/calender-java-quickstart");
	/**
	 * Global instance of the {@link FileDataStoreFactory}
	 */
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	/**
	 * Global instance of the JSON factory
	 */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	/**
	 * Global instance of the HTTP transport
	 */
	private static HttpTransport HTTP_TRANSPORT;
	/**
	 * Global instance of the scopes required by this quickstart
	 * 
	 * If modifying these scopes, delete your previously saved credentials at ~/.credentials/calender-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.<br />
	 * Implements the lazy singleton pattern privately.
	 * 
	 * @return an authorized Credential object
	 * @throws IOException
	 */
	private static Credential getCredentials() throws IOException {
		if (credentialInstance == null) {
			// Load client secrets
			InputStream in = CalendarConnector.class.getResourceAsStream("../resources/client_secret.json");
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
			// Build flow and trigger user authorization request
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
			credentialInstance = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
			// System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		}
		return credentialInstance;
	}

	/**
	 * Build and return an authorized Calender client service.<br />
	 * Implements the lazy singleton pattern, uses the {@link #getCredentials()} method (also lazy).
	 * 
	 * @return an authorized Calender client service
	 * @throws IOException
	 */
	public static Calendar getCalenderService() throws IOException {
		if (calendarInstance == null) {
			Credential credential = getCredentials();
			calendarInstance = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName(MainApp.APPLICATION_NAME).build();
		}
		return calendarInstance;
	}

}
