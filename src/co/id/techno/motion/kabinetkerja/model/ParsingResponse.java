package co.id.techno.motion.kabinetkerja.model;
public interface ParsingResponse<T> {
	void onSuccess(T result);
	void onFailure();
}