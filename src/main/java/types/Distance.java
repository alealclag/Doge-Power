package types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Distance {

	Integer id;
	Float distance_to_door;
	Boolean is_inside;
	Boolean is_near_door;
	Long timestamp;
	
	public Distance(@JsonProperty("id") Integer id, @JsonProperty("distance_to_door") Float distance_to_door,
			@JsonProperty("is_inside") Boolean is_inside, @JsonProperty("timestamp") Long timestamp) {
		super();
		this.id = id;
		this.distance_to_door = distance_to_door;
		this.is_inside = is_inside;
		this.timestamp = timestamp;
		this.is_near_door = distance_to_door <= 1;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getDistance_to_door() {
		return distance_to_door;
	}
	public void setDistance_to_door(Float distance_to_door) {
		this.distance_to_door = distance_to_door;
	}
	public Boolean getIs_inside() {
		return is_inside;
	}
	public void setIs_inside(Boolean is_inside) {
		this.is_inside = is_inside;
	}
	public Boolean getIs_near_door() {
		return is_near_door;
	}
	public void setIs_near_door(Boolean is_near_door) {
		this.is_near_door = is_near_door;
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
		result = prime * result + ((distance_to_door == null) ? 0 : distance_to_door.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((is_inside == null) ? 0 : is_inside.hashCode());
		result = prime * result + ((is_near_door == null) ? 0 : is_near_door.hashCode());
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
		Distance other = (Distance) obj;
		if (distance_to_door == null) {
			if (other.distance_to_door != null)
				return false;
		} else if (!distance_to_door.equals(other.distance_to_door))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_inside == null) {
			if (other.is_inside != null)
				return false;
		} else if (!is_inside.equals(other.is_inside))
			return false;
		if (is_near_door == null) {
			if (other.is_near_door != null)
				return false;
		} else if (!is_near_door.equals(other.is_near_door))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
	


	

	
}
