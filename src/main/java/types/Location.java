package types;

public class Location {

	int id;
	float x;
	float y;
	Boolean isInside;
	Boolean isNearDoor;
	long timestamp;
	
	
	
	public Location(int id, float x, float y, Boolean isInside, Boolean isNearDoor, long timestamp) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.isInside = isInside;
		this.isNearDoor = isNearDoor;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public Boolean getIsInside() {
		return isInside;
	}
	public void setIsInside(Boolean isInside) {
		this.isInside = isInside;
	}
	public Boolean getIsNearDoor() {
		return isNearDoor;
	}
	public void setIsNearDoor(Boolean isNearDoor) {
		this.isNearDoor = isNearDoor;
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
		result = prime * result + ((isInside == null) ? 0 : isInside.hashCode());
		result = prime * result + ((isNearDoor == null) ? 0 : isNearDoor.hashCode());
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
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	
	
}
