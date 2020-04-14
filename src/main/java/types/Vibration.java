package types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vibration {
	
	int id;
	int mode;
	float intensity;
	float length;
	long timestamp;
	
	public Vibration(@JsonProperty("id") int id, @JsonProperty("mode") int mode, @JsonProperty("intensity") float intensity,
			@JsonProperty("lenght") float length, @JsonProperty("timestamp") Long timestamp) {
		super();
		this.id = id;
		this.mode = mode;
		this.intensity = intensity;
		this.length = length;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public float getIntensity() {
		return intensity;
	}
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
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
		result = prime * result + Float.floatToIntBits(intensity);
		result = prime * result + Float.floatToIntBits(length);
		result = prime * result + mode;
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
		Vibration other = (Vibration) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(intensity) != Float.floatToIntBits(other.intensity))
			return false;
		if (Float.floatToIntBits(length) != Float.floatToIntBits(other.length))
			return false;
		if (mode != other.mode)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
	
	
}
