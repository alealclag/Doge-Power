package types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

	int id;
	String dog;
	String iduser;
	
	public Device(@JsonProperty("id") int id, @JsonProperty("dog") String dog, @JsonProperty("iduser") String iduser) {
		super();
		this.id = id;
		this.dog = dog;
		this.iduser = iduser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDog() {
		return dog;
	}

	public void setDog(String dog) {
		this.dog = dog;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dog == null) ? 0 : dog.hashCode());
		result = prime * result + id;
		result = prime * result + ((iduser == null) ? 0 : iduser.hashCode());
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
		Device other = (Device) obj;
		if (dog == null) {
			if (other.dog != null)
				return false;
		} else if (!dog.equals(other.dog))
			return false;
		if (id != other.id)
			return false;
		if (iduser == null) {
			if (other.iduser != null)
				return false;
		} else if (!iduser.equals(other.iduser))
			return false;
		return true;
	}
	
	
	
}
