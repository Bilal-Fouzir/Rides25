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
public class Balorazioa implements Serializable{

	private Erreserba erreserba;
	private String email;
	@XmlID 
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	private int balorazioa;
	private String title;
	private String description;
	
	public Balorazioa(String email, int balorazioa, String title, String description) {
		
		this.email = email;
		this.balorazioa = balorazioa;
		this.title = title;
		this.description = description;
	}
	
	public Erreserba getErreserba() {
		return erreserba;
	}

	public void setErreserba(Erreserba erreserba) {
		this.erreserba = erreserba;
	}

	public int getBalorazioa() {
		return balorazioa;
	}

	public void setBalorazioa(int balorazioa) {
		this.balorazioa = balorazioa;
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
	private static String etik = "Etiquetas";
	@Override
	public String toString() {
		String bal;
		String t;
		String d;
		if(balorazioa==1) bal = "★☆☆☆☆";
		else if(balorazioa==2) bal = "★★☆☆☆";
		else if(balorazioa==3) bal = "★★★☆☆";
		else if(balorazioa==4) bal = "★★★★☆";
		else bal = "★★★★★";
		if (title.equals("")) t=ResourceBundle.getBundle(etik).getString("BaloratuGUI.DefaultTitle");
		else t=title;
		if (description.equals("")) d=ResourceBundle.getBundle(etik).getString("BaloratuGUI.DefaultDescription");
		else d=description;
		return ResourceBundle.getBundle(etik).getString("Bal") + " | " + email + ": " + bal + " " + t + ": " + d;
	}
	
	
	
}
