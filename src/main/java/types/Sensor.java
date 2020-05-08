package types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sensor {
	
	Integer iddevice;
	Integer idsensor;
	String name;
	
	public Sensor(@JsonProperty("iddevice") Integer iddevice, @JsonProperty("idsensor") Integer idsensor, @JsonProperty("name") String name) {
		super();
		this.iddevice = iddevice;
		this.idsensor = idsensor;
		this.name = name;
	}

	public Integer getIddevice() {
		return iddevice;
	}

	public void setIddevice(Integer iddevice) {
		this.iddevice = iddevice;
	}

	public Integer getIdsensor() {
		return idsensor;
	}

	public void setIdsensor(Integer idsensor) {
		this.idsensor = idsensor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iddevice == null) ? 0 : iddevice.hashCode());
		result = prime * result + ((idsensor == null) ? 0 : idsensor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Sensor other = (Sensor) obj;
		if (iddevice == null) {
			if (other.iddevice != null)
				return false;
		} else if (!iddevice.equals(other.iddevice))
			return false;
		if (idsensor == null) {
			if (other.idsensor != null)
				return false;
		} else if (!idsensor.equals(other.idsensor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	

}
