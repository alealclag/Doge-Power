package types;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Led {

	int id;
	int mode;
	float luminosity;
	float length;
	long timestamp;
	

	public Led(@JsonProperty("id") int id, @JsonProperty("mode") int mode, @JsonProperty("luminosity") float luminosity, @JsonProperty("length") float length, @JsonProperty("timestamp") long timestamp) {
		super();
		this.id=id;
		this.mode = mode;
		if(mode==0) {
			this.luminosity = 0;
			this.length = 0;
		}else if(mode==1){
			this.luminosity = luminosity;
			this.length = 0;
		}else {
			this.luminosity = luminosity;
			this.length = length;
		}
		
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
	public float getLuminosity() {
		return luminosity;
	}
	public void setLuminosity(float luminosity) {
		this.luminosity = luminosity;
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
		result = prime * result + Float.floatToIntBits(length);
		result = prime * result + Float.floatToIntBits(luminosity);
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
		Led other = (Led) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(length) != Float.floatToIntBits(other.length))
			return false;
		if (Float.floatToIntBits(luminosity) != Float.floatToIntBits(other.luminosity))
			return false;
		if (mode != other.mode)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
	
	
}
