package co.id.techno.motion.kabinetkerja.model;

import java.util.List;

public interface DataParser {
	void parsingKabinet(String data, ParsingResponse<List<Kabinet>> callback);
}
