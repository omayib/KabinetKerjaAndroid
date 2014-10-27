package co.id.techno.motion.kabinetkerja.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import co.id.techno.motion.kabinetkerja.event.EventPublisher;
import co.id.techno.motion.kabinetkerja.event.KabinetCollectionLoadFailed;
import co.id.techno.motion.kabinetkerja.event.KabinetCollectionLoaded;
import co.id.techno.motion.kabinetkerja.model.Kabinet;
import co.id.techno.motion.kabinetkerja.model.ParsingResponse;
import co.id.techno.motion.kabinetkerja.model.UserAction;
import co.id.techno.motion.kabinetkerja.parser.KabinetParser;

public class KabinetController implements UserAction {
	private Context context;
	private EventPublisher eventPublisher;
	private AssetManager assetManager;
	private String fileName = "data_source.txt";
	private KabinetParser parser;

	@Override
	public void getContent() {
		InputStream input;
		String respon = "";
		try {
			assetManager = context.getAssets();
			input = assetManager.open(fileName);
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			respon = new String(buffer);
			parser.parsingKabinet(respon, new ParsingResponse<List<Kabinet>>() {

				@Override
				public void onSuccess(List<Kabinet> result) {
					eventPublisher.publish(new KabinetCollectionLoaded(result));
				}

				@Override
				public void onFailure() {
					eventPublisher.publish(new KabinetCollectionLoadFailed());

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public KabinetController(Context context, EventPublisher eventPublisher,
			KabinetParser parser) {
		super();
		this.context = context;
		this.eventPublisher = eventPublisher;
		this.parser = parser;
	}

}
