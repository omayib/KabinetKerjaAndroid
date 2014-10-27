package co.id.techno.motion.kabinetkerja.event;

import java.util.List;

import co.id.techno.motion.kabinetkerja.model.Kabinet;

public class KabinetCollectionLoaded implements Event {
	public List<Kabinet> listKabinet;

	public KabinetCollectionLoaded(List<Kabinet> listKabinet) {
		super();
		this.listKabinet = listKabinet;
	}

}
