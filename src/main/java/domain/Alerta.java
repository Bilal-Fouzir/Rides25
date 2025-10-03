package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Alerta implements Serializable{

	private Ride ride;
	private String email;
	@XmlID 
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	private String jatorria;
	private String helburua;
	private Date noiztik;
	private Date nora;
	private boolean aktibatua;
	
	public Alerta(String email, String jatorria, String helburua, Date noiztik, Date nora) {
		
		this.email = email;
		this.jatorria = jatorria;
		this.helburua = helburua;
		this.aktibatua = false;
		this.ride = null;
		this.noiztik=noiztik;
		this.nora=nora;
	}
	
	public Date getNoiztik() {
		return noiztik;
	}



	public void setNoiztik(Date noiztik) {
		this.noiztik = noiztik;
	}



	public Date getNora() {
		return nora;
	}



	public void setNora(Date nora) {
		this.nora = nora;
	}



	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJatorria() {
		return jatorria;
	}

	public void setJatorria(String jatorria) {
		this.jatorria = jatorria;
	}

	public String getHelburua() {
		return helburua;
	}

	public void setHelburua(String helburua) {
		this.helburua = helburua;
	}

	public boolean isAktibatua() {
		return aktibatua;
	}

	public void setAktibatua(boolean aktibatua) {
		this.aktibatua = aktibatua;
	}

	public String toString() {
		return ride.toString();
	}
	
}