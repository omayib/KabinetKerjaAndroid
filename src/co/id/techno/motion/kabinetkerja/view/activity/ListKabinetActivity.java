package co.id.techno.motion.kabinetkerja.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import co.id.techno.motion.kabinetkerja.R;
import co.id.techno.motion.kabinetkerja.application.Configuration;
import co.id.techno.motion.kabinetkerja.controller.KabinetController;
import co.id.techno.motion.kabinetkerja.event.EventPublisher;
import co.id.techno.motion.kabinetkerja.event.EventSubscriber;
import co.id.techno.motion.kabinetkerja.event.KabinetCollectionLoadFailed;
import co.id.techno.motion.kabinetkerja.event.KabinetCollectionLoaded;
import co.id.techno.motion.kabinetkerja.model.Kabinet;
import co.id.techno.motion.kabinetkerja.view.adapter.KabinetAdapter;

public class ListKabinetActivity extends Activity {
	private Configuration conf;
	private KabinetController controller;
	private KabinetAdapter kabinetAdapter;
	private List<Kabinet> listKabinet = new ArrayList<Kabinet>();

	private final List<EventSubscriber<?>> subscribers = new ArrayList<EventSubscriber<?>>();
	private EventPublisher eventPublisher;

	private ListView lvKabinet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_kabinet);
		// setup configurations
		conf = (Configuration) getApplication();
		controller = conf.getController();
		eventPublisher = conf.getEventPublisher();
		kabinetAdapter = new KabinetAdapter(this, R.layout.item_kabinet,
				listKabinet);
		// setup view
		lvKabinet = (ListView) findViewById(R.id.lvKabinet);
		lvKabinet.setAdapter(kabinetAdapter);

		// registering subscribers..
		registerSubscriber();

		// getting content..
		controller.getContent();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeEventSubscribers();
	}

	private void registerSubscriber() {
		addEventSubscriber(new EventSubscriber<KabinetCollectionLoaded>() {

			@Override
			public void handleEvent(KabinetCollectionLoaded event) {
				listKabinet.addAll(event.listKabinet);
				kabinetAdapter.notifyDataSetChanged();
			}
		});
		addEventSubscriber(new EventSubscriber<KabinetCollectionLoadFailed>() {

			@Override
			public void handleEvent(KabinetCollectionLoadFailed event) {

			}
		});
	}

	private void addEventSubscriber(EventSubscriber<?> subscriber) {
		eventPublisher.registerSubscriber(subscriber);
		subscribers.add(subscriber);
	}

	private void removeEventSubscribers() {
		for (EventSubscriber<?> subscriber : subscribers) {
			eventPublisher.unregisterSubsriber(subscriber);
		}

		subscribers.clear();
	}
}
