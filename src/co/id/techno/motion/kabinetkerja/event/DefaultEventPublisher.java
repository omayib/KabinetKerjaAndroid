package co.id.techno.motion.kabinetkerja.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The default implementation of {@link EventPublisher}. This class does it jobs
 * by maintaining this object is its usage of a {@link Map} that contains
 * {@link Event} class name as its key and {@link List} of
 * {@link EventSubscriber} for that {@link Event} as its value.
 * 
 * @author angga
 * 
 */
public class DefaultEventPublisher implements EventPublisher {
	private final Map<String, List<EventSubscriber<? super Event>>> subscribers;

	public DefaultEventPublisher() {
		subscribers = new ConcurrentHashMap<String, List<EventSubscriber<? super Event>>>(
				16, 0.9f, 1);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Event> void registerSubscriber(
			EventSubscriber<T> subscriber) {
		// First, get the generic superclass. For example, when subscriber
		// is `EventSubscriber<CommentPosted>`, we need to get the
		// `EventSubscriber`.
		Type t = subscriber.getClass().getGenericInterfaces()[0];

		// Of course, we know that `EventSubscriber` is really a type of
		// `ParameterizedType` instead of generic type. So, let's cast it.
		ParameterizedType pt = (ParameterizedType) t;

		// Now that we know it's an `ParameterizedType`, let's get its
		// arguments.
		// For example, let's say the `subscriber` above is an instance of
		// `EventSubscriber<CommentPosted>`, then calling
		// `getActualTypeArguments`
		// will get the `CommentPosted`.
		Type tArg = pt.getActualTypeArguments()[0];

		// Get the Class name of the type argument.
		String eventClassName = tArg.toString().split(" ")[1];

		// The last step would be to insert the subscriber to the map. BUT:
		// we need to check whether the subscribers Map contains the
		// eventClassName. If it isn't, we create a new List for it.
		if (!subscribers.containsKey(eventClassName)) {
			List<EventSubscriber<? super Event>> l = Collections
					.synchronizedList(new ArrayList<EventSubscriber<? super Event>>());
			subscribers.put(eventClassName, l);
		}
		// Get the List of the previous subscribers.
		List<EventSubscriber<? super Event>> subscriberList = subscribers
				.get(eventClassName);
		// Finally, really do the insertion.
		subscriberList.add((EventSubscriber<? super Event>) subscriber);
	}

	@Override
	public <T extends Event> void unregisterSubsriber(
			EventSubscriber<T> subscriber) {
		for (List<EventSubscriber<? super Event>> subscriberList : subscribers
				.values()) {
			synchronized (subscriberList) {
				Iterator<EventSubscriber<? super Event>> it = subscriberList
						.listIterator();
				while (it.hasNext()) {
					EventSubscriber<? super Event> e = it.next();
					if (e.equals(subscriber)) {
						it.remove();
					}
				}
			}

		}
	}

	@Override
	public void publish(Event event) {
		String eventClassName = event.getClass().getName();
		if (subscribers.containsKey(eventClassName)) {
			List<EventSubscriber<? super Event>> subscriberList = subscribers
					.get(eventClassName);
			synchronized (subscriberList) {
				for (EventSubscriber<? super Event> eventSubscriber : subscriberList) {
					eventSubscriber.handleEvent(event);
				}
			}
		}
	}

}
