package co.id.techno.motion.kabinetkerja.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		lvKabinet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				KabinetAdapter adapter = (KabinetAdapter) arg0.getAdapter();
				dialogAction(adapter.getItem(arg2));
			}
		});

		// registering subscribers..
		registerSubscriber();

		// getting content..
		controller.getContent();
	}

	private void dialogAction(final Kabinet selected) {
		Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setCancelable(true);
		dialogBuilder.setMessage("pilih salah satu");
		dialogBuilder.setPositiveButton("profil", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(selected
						.getUrlProfile()));
				startActivity(i);
				dialog.dismiss();
			}
		});
		dialogBuilder.setNegativeButton("detil", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(selected
						.getUrlDetail()));
				startActivity(i);
				dialog.dismiss();
			}
		});
		dialogBuilder.create();
		dialogBuilder.show();
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
