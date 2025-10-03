package domain;

import java.io.Serializable;
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
public class Erreklamazioa implements Serializable{

	private boolean eginda;
	private Erreserba erreserba;
	private String email;
	@XmlID 
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	private String title;
	private String description;
	
	public Erreklamazioa(String email, String title, String description, Erreserba erreserba) {
		
		this.email = email;
		this.title = title;
		this.description = description;
		this.erreserba = erreserba;
		eginda=false;
	}
	
	public boolean isEginda() {
		return eginda;
	}


	public void setEginda(boolean eginda) {
		this.eginda = eginda;
	}


	public Erreserba getErreserba() {
		return erreserba;
	}



	public void setErreserba(Erreserba erreserba) {
		this.erreserba = erreserba;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("Err") + " |" + id +"| " + email + ": " + title + ": " + description + " | " + erreserba.getPlaces()*erreserba.getRide().getPrice() +"€";
	}
	
	
	
}
