package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kotxea implements Serializable{

	private static final long serialVersionUID = 1L;
	private String marka;
	private String modeloa;
	@XmlID
	@Id 
	private String matrikula;
	
	public Kotxea(String marka, String modeloa, String matrikula) {
		super();
		this.marka = marka;
		this.modeloa = modeloa;
		this.matrikula = matrikula;
	}
	
	public Kotxea() {
		
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModeloa() {
		return modeloa;
	}

	public void setModeloa(String modeloa) {
		this.modeloa = modeloa;
	}

	public String getMatrikula() {
		return matrikula;
	}

	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}

	@Override
	public String toString() {
		return  matrikula + " | " + marka + " " + modeloa;
	}
	
	
}
