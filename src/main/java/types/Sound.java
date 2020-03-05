package types;

public class Sound {

	int id;
	int decibels;
	long timestamp;
	
	
	
	public Sound(int id, int decibels, long timestamp) {
		super();
		this.id = id;
		this.decibels = decibels;
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDecibels() {
		return decibels;
	}
	public void setDecibels(int decibels) {
		this.decibels = decibels;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + decibels;
		result = prime * result + id;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
		if (decibels != other.decibels)
			return false;
		if (id != other.id)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
	
	
}
