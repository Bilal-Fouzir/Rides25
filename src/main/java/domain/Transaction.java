package domain;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Transaction implements Serializable{
	@XmlID 
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	private double money;
	private double totalMoney;
	private String date;
	private String concept;
	
	public Transaction(double money, double totalMoney, String concept) {
		this.money = money;
		this.totalMoney = totalMoney;
		date = ""+LocalDate.now();
		this.concept=concept;
	}
	
	public Transaction() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		if(money>=0)
			return " + " + money + "€ | " + (totalMoney-money) + "€ --> "+ totalMoney + "€ | " + date + " | " + ResourceBundle.getBundle("Etiquetas").getString("Concept") + ": " + concept;
		else
			return " - " + -money + "€ | " + (totalMoney-money) + "€ --> "+ totalMoney + "€ | " + date + " | " + ResourceBundle.getBundle("Etiquetas").getString("Concept") + ": " + concept;
	}
	
}
