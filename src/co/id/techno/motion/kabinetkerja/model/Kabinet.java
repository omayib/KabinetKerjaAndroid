package co.id.techno.motion.kabinetkerja.model;

public class Kabinet {
	private String name, position, thumbnail, urlProfile, urlDetail;

	private Kabinet(Builder b) {
		super();
		this.name = b.name;
		this.position = b.position;
		this.thumbnail = b.thumbnail;
		this.urlDetail = b.urlDetail;
		this.urlProfile = b.urlProfile;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getUrlProfile() {
		return urlProfile;
	}

	public String getUrlDetail() {
		return urlDetail;
	}

	public static class Builder {
		private String name, position, thumbnail, urlProfile, urlDetail;

		public Builder name(String n) {
			this.name = n;
			return this;
		}

		public Builder position(String p) {
			this.position = p;
			return this;
		}

		public Builder thumbnail(String t) {
			this.thumbnail = t;
			return this;
		}

		public Builder urlProfile(String u) {
			this.urlProfile = u;
			return this;
		}

		public Builder urlDetail(String d) {
			this.urlDetail = d;
			return this;
		}

		public Kabinet build() {
			return new Kabinet(this);
		}
	}

}
