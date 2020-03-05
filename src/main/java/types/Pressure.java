package types;

public class Pressure {
	
	int id;
	int value;
	long timestamp;
	public Pressure(int id, int value, long timestamp) {
		super();
		this.id = id;
		this.value = value;
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
		result = prime * result + id;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + value;
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
		Pressure other = (Pressure) obj;
		if (id != other.id)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
	
}
