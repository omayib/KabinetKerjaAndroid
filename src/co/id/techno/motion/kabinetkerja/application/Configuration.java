package co.id.techno.motion.kabinetkerja.application;

import co.id.techno.motion.kabinetkerja.controller.KabinetController;
import co.id.techno.motion.kabinetkerja.event.DefaultEventPublisher;
import co.id.techno.motion.kabinetkerja.event.EventPublisher;
import co.id.techno.motion.kabinetkerja.parser.KabinetParser;
import android.app.Application;

public class Configuration extends Application {
	private EventPublisher eventPublisher;
	private KabinetController controller;
	private KabinetParser parser;

	@Override
	public void onCreate() {
		super.onCreate();
		eventPublisher = new DefaultEventPublisher();
		parser = new KabinetParser();
		controller = new KabinetController(this, eventPublisher, parser);
	}

	public KabinetController getController() {
		return controller;
	}

	public EventPublisher getEventPublisher() {
		return eventPublisher;
	}

}
