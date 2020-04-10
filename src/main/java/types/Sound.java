package types;

public class Sound {

	Integer id;
	Float decibels;
	Long timestamp;
	
	public Sound(Integer id, Float decibels, Long timestamp) {
		super();
		this.id = id;
		this.decibels = decibels;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getDecibels() {
		return decibels;
	}

	public void setDecibels(Float decibels) {
		this.decibels = decibels;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decibels == null) ? 0 : decibels.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sound other = (Sound) obj;
		if (decibels == null) {
			if (other.decibels != null)
				return false;
		} else if (!decibels.equals(other.decibels))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
	
	
	
}
