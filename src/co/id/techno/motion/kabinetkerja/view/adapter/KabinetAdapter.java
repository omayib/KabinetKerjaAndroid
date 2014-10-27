package co.id.techno.motion.kabinetkerja.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import co.id.techno.motion.kabinetkerja.R;
import co.id.techno.motion.kabinetkerja.model.Kabinet;

import com.loopj.android.image.SmartImageView;

public class KabinetAdapter extends ArrayAdapter<Kabinet> {
	private int layout;
	private List<Kabinet> listKabinet;
	private Context context;

	public KabinetAdapter(Context context, int layout, List<Kabinet> objects) {
		super(context, layout, objects);
		this.listKabinet = objects;
		this.layout = layout;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Kabinet currentKabinet = getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, parent, false);

			holder.thumbnail = (SmartImageView) convertView
					.findViewById(R.id.thumbnail);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.position = (TextView) convertView
					.findViewById(R.id.position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.thumbnail.setImageUrl(currentKabinet.getThumbnail());
		holder.name.setText(currentKabinet.getName());
		holder.position.setText(currentKabinet.getPosition());
		return convertView;
	}

	private static class ViewHolder {
		SmartImageView thumbnail;
		TextView name, position;
	}

}
