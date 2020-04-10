package types;

public class Distance {

	Integer id;
	Float distance_to_door;
	Boolean isInside;
	Boolean isNearDoor;
	Long timestamp;
	
	
	public Distance(Integer id, Float distance_to_door, Boolean isInside, Long timestamp) {
		super();
		this.id = id;
		this.distance_to_door = distance_to_door;
		this.isInside = isInside;
		this.isNearDoor = distance_to_door<=1;
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsInside() {
		return isInside;
	}
	public void setIsInside(Boolean isInside) {
		this.isInside = isInside;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public float getDistance_to_door() {
		return distance_to_door;
	}

	public void setDistance_to_door(Float distance_to_door) {
		this.distance_to_door = distance_to_door;
	}

	public Boolean getIsNearDoor() {
		return isNearDoor;
	}

	public void setIsNearDoor(Boolean isNearDoor) {
		this.isNearDoor = isNearDoor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(distance_to_door);
		result = prime * result + id;
		result = prime * result + ((isInside == null) ? 0 : isInside.hashCode());
		result = prime * result + ((isNearDoor == null) ? 0 : isNearDoor.hashCode());
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
		Distance other = (Distance) obj;
		if (Float.floatToIntBits(distance_to_door) != Float.floatToIntBits(other.distance_to_door))
			return false;
		if (id != other.id)
			return false;
		if (isInside == null) {
			if (other.isInside != null)
				return false;
		} else if (!isInside.equals(other.isInside))
			return false;
		if (isNearDoor == null) {
			if (other.isNearDoor != null)
				return false;
		} else if (!isNearDoor.equals(other.isNearDoor))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}


	

	
}
