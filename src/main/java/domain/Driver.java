package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id 
	private String email;
	private String password;
	//private String name;
	private double money;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private LinkedList<Transaction> transactions = new LinkedList<>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private LinkedList<Kotxea> cars = new LinkedList<>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();

	public Driver() {
		
	}

	public Driver(String email, String password) {
		this.email = email;
		this.password = password;
		money = 0.0;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//public String getName() {
	//	return name;
	//}

	//public void setName(String name) {
	//	this.name = name;
	//}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	public LinkedList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(LinkedList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public LinkedList<Kotxea> getCars() {
		return cars;
	}

	public void setCars(LinkedList<Kotxea> cars) {
		this.cars = cars;
	}

	public String toString(){
		return email+";"+rides;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public void addRide(Ride ride)  {
	       rides.add(ride);
		}

	
	public Kotxea addCar(String marka, String modeloa, String matrikula) {
		Kotxea kotxea = new Kotxea(marka, modeloa, matrikula);
		cars.add(kotxea);
		return kotxea;
	}
	
	
	public void removeRide(Ride ride) {
		rides.remove(ride);
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		boolean b=false;
		for (Ride r:rides) {
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 b=true;
		}
		return b;
	}
	public Ride getRide(String from, String to, Date date) {
		Ride b=null;
		for (Ride r:rides) {
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 b=r;
		}
		return b;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (email != other.email)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
	    return java.util.Objects.hash(email);
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<rides.size()) {
			r=rides.get(index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) ) {
				found=true;
			}
			index=index+1;
			
			
		}
			
		if (found) {
			rides.remove(index-1);
			return r;
		} else  return null;
	}
	
	public void addMoney(double mon) {
		this.money+=mon;
	}
	
	public void addTransaction(Transaction trans) {
		this.transactions.add(trans);
	}
	
}
