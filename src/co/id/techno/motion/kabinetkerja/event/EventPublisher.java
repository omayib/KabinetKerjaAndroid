package co.id.techno.motion.kabinetkerja.event;

public interface EventPublisher {
	void publish(Event event);

	<T extends Event> void registerSubscriber(EventSubscriber<T> subscriber);

	<T extends Event> void unregisterSubsriber(EventSubscriber<T> subscriber);
}
