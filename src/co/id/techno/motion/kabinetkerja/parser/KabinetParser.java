package co.id.techno.motion.kabinetkerja.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.id.techno.motion.kabinetkerja.model.DataParser;
import co.id.techno.motion.kabinetkerja.model.Kabinet;
import co.id.techno.motion.kabinetkerja.model.ParsingResponse;

public class KabinetParser implements DataParser {

	@Override
	public void parsingKabinet(String data,
			ParsingResponse<List<Kabinet>> callback) {
		try {
			JSONArray arr = new JSONArray(data);
			List<Kabinet> listKabinet = new ArrayList<Kabinet>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject ob = arr.getJSONObject(i);
				String name = ob.getString("name");
				String thumbnail = ob.getString("thumbnail");
				String urlProfile = ob.getString("url_profile");
				String urlDetail = ob.getString("url_detail");
				String position = ob.getString("position");
				Kabinet kabinet = new Kabinet.Builder().name(name)
						.position(position).thumbnail(thumbnail)
						.urlDetail(urlDetail).urlProfile(urlProfile).build();
				listKabinet.add(kabinet);
			}
			callback.onSuccess(listKabinet);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
		}

	}

}
