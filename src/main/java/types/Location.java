package types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	Integer id;
	Float x;
	Float y;
	Long timestamp;
	
	public Location(@JsonProperty("id") Integer id, @JsonProperty("x") Float x, @JsonProperty("y") Float y,
			@JsonProperty("timestamp") Long timestamp) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	
	
}
