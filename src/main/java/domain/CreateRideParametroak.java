package domain;

import java.util.Date;

public class CreateRideParametroak {
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private float price;
	public CreateRideParametroak(String from, String to, Date date, int nPlaces, float price) {
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getnPlaces() {
		return nPlaces;
	}
	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
}
