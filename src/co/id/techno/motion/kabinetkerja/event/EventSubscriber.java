package co.id.techno.motion.kabinetkerja.event;

public interface EventSubscriber<T extends Event> {
	void handleEvent(T event);
}
