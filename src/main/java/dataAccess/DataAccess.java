package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.*;

/**
 * It implements the data access to the objectDb database  db
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;
 
	ConfigXML c = ConfigXML.getInstance();
//beeb
	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();
			// emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			// db = emf.createEntityManager();
			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				boolean b=fileToDeleteTemp.delete();
                if(b) {}
				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "+ c.isDatabaseInitialized());
		close();

	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create rides
			/*
			 * driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			 * driver1.addRide("Donostia", "Gasteiz", UtilDate.newDate(year,month,6), 4, 8);
			 * driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);
			 * 
			 * driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			 * 
			 * driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			 * driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			 * driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);
			 * 
			 * driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);
			 * 
			 * 
			 * 
			 * db.persist(driver1); db.persist(driver2); db.persist(driver3);
			 * 
			 */
			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",
				String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList();
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws Exception 
	 */
	
	public Ride createRide(CreateRideParametroak kl, String driverEmail,String datuak) throws Exception {
		try {
			db.getTransaction().begin();
			Driver driver = this.createRideKonprobaketak(kl.getFrom(), kl.getTo(), kl.getDate(), driverEmail);
			String[] zatiak = datuak.split(" \\|");
			Kotxea kotxea = db.find(Kotxea.class, zatiak[0]);
			Ride ride = new Ride(kl.getFrom(), kl.getTo(), kl.getDate(), kl.getnPlaces(), kl.getPrice(),driver, kotxea);
			driver.addRide(ride);
			db.persist(driver);
			db.getTransaction().commit();
			return ride;
		} catch (NullPointerException e) {
			db.getTransaction().commit();
			return null;
		}

	}
	public Driver createRideKonprobaketak(String from, String to, Date date,String driverEmail) throws Exception{
		if (new Date().compareTo(date) > 0) {
			throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
		}
		if(driverEmail==null) {throw new Exception();}
		Driver driver = db.find(Driver.class, driverEmail);
		if(driver==null) {throw new Exception();}
		if (driver.doesRideExists(from, to, date)) {
			db.getTransaction().commit();
			throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
		}
		return driver;
	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * this method is crazy
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);

		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
				Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			res.add(ride);
		}
		return res;
	} 

	public List<Kotxea> getCars(Driver d) {

		List<Kotxea> carsL = new ArrayList<>();
		Driver driver = db.find(Driver.class, d.getEmail());
		for (Kotxea kotxea : driver.getCars()) {
			carsL.add(kotxea);
		}
		return carsL;
	}

	public void removeRide(String s) {
		db.getTransaction().begin();
		int id = Integer.parseInt(s);
		Ride ride = db.find(Ride.class, id);
		Driver driver = db.find(Driver.class, ride.getDriver().getEmail());
		driver.removeRide(ride);
		db.remove(ride);
		db.getTransaction().commit();
	}
//bebesita
	public void bukatuRide(String s) {
		float money = 0;
		int id = Integer.parseInt(s);
		float price = this.getRide(id).getPrice();
		List<Erreserba> erreserbak = null;
		db.getTransaction().begin();
		try {
			TypedQuery<Erreserba> query = db.createQuery(
					"SELECT r FROM Erreserba r WHERE r.ride.rideNumber = :id AND r.onartua = TRUE", Erreserba.class);
			query.setParameter("id", id);
			erreserbak = query.getResultList();
			TypedQuery<Alerta> query5 = db.createQuery("DELETE FROM Alerta p WHERE p.ride.rideNumber = :s",
					Alerta.class);
			query5.setParameter("s", id);
			query5.executeUpdate();
			db.getTransaction().commit();
		} catch (Exception e) {
			db.getTransaction().rollback();
			
		}
		for (Erreserba er : erreserbak) {
			money += (er.getPlaces() * price);
		}
		String email = this.getRide(id).getDriver().getEmail();
		db.getTransaction().begin();
		Driver d = db.find(Driver.class, email);
		Ride ride = db.find(Ride.class, id);
		ride.setBukatuta(true);
		d.setMoney(d.getMoney() + money);
		db.getTransaction().commit();
		ezabatuBehinBehineko(id);
		addTransaction(new Transaction(money, d.getMoney(), "Payment/Pago/Ordainketa"), d);
	}

	public void ezabatuBehinBehineko(int id) {
		db.getTransaction().begin();
		List<Erreserba> erreserbak = null;
		try {
			TypedQuery<Erreserba> query = db.createQuery(
					"SELECT r FROM Erreserba r WHERE r.ride.rideNumber = :id AND r.onartua = FALSE", Erreserba.class);
			query.setParameter("id", id);
			erreserbak = query.getResultList();
		} catch (Exception e) {
			
		}
		for (Erreserba e : erreserbak)
			db.remove(e);
		db.getTransaction().commit();
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",
				Date.class);

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			res.add(d);
		}
		return res;
	}

	public void createDriver(Driver d) {
		// try{
		db.getTransaction().begin();
		db.persist(d);
		db.getTransaction().commit();
		// } catch (Exception e) {
		// throw new EmailAlreadyExistsException("Email already in use");
		// }
	}

	public void createTraveler(Traveler t) {
		// try{
		db.getTransaction().begin();
		db.persist(t);
		db.getTransaction().commit();
		// } catch (Exception e) {
		// throw new EmailAlreadyExistsException("Email already in use");
		// }
	}

	public void createReview(Balorazioa b, int r) {

		db.getTransaction().begin();
		Ride ride = db.find(Ride.class, r);
		ride.addBalorazioa(b);
		db.getTransaction().commit();
	}

	public void createErreklamazioa(Erreklamazioa e, int r) {

		db.getTransaction().begin();
		Ride ride = db.find(Ride.class, r);
		ride.addErreklamazioa(e);
		db.getTransaction().commit();
	}

	public void createAlerta(Alerta a) {

		db.getTransaction().begin();
		db.persist(a);
		db.getTransaction().commit();
	}

	public void removeAlerta(int a) {

		db.getTransaction().begin();
		Alerta alerta = db.find(Alerta.class, a);
		Alerta alerta2 = db.merge(alerta);
		if (alerta2 != null)
			db.remove(alerta2);
		db.getTransaction().commit();
	}

	public List<domain.Alerta> getAllAlertak() {
		List<Alerta> alertak = null;
		try {
			TypedQuery<Alerta> query = db.createQuery("SELECT r FROM Alerta r", Alerta.class);
			alertak = query.getResultList();
		} catch (Exception e) {
			
		}
		return alertak;
	}

	public List<domain.Alerta> getAlertaAktibatuak(String email) {
		List<Alerta> alertak = null;
		try {
			TypedQuery<Alerta> query = db
					.createQuery("SELECT r FROM Alerta r WHERE r.email = :s AND r.aktibatua = true", Alerta.class);
			query.setParameter("s", email);
			alertak = query.getResultList();
		} catch (Exception e) {
			
		}
		return alertak;
	}

	public void aktibatuAlerta(Alerta a, Ride ride) {
		Alerta alerta;
		db.getTransaction().begin();
		alerta = db.find(Alerta.class, a.getId());
		alerta.setAktibatua(true);
		alerta.setRide(ride);
		db.getTransaction().commit();
	}

	public Driver badagoDriver(String d) {
		Driver ema;
		db.getTransaction().begin();
		ema = db.find(Driver.class, d);
		db.getTransaction().commit();
		return ema;
	}

	public Traveler badagoTraveler(String t) {
		Traveler ema;
		db.getTransaction().begin();
		ema = db.find(Traveler.class, t);
		db.getTransaction().commit();
		return ema;
	}

	public List<domain.Ride> getAllRides(Driver d) {
		List<Ride> rides = null;
		String s = d.getEmail();
		try {
			TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver.email = :s", Ride.class);
			query.setParameter("s", s);
			rides = query.getResultList();
		} catch (Exception e) {
			
		}
		return rides;
	}

	public List<domain.Erreserba> getAllErreserbak(Traveler t) {
		List<Erreserba> erreserbak = null;
		String s = t.getEmail();
		try {
			TypedQuery<Erreserba> query = db.createQuery("SELECT r FROM Erreserba r WHERE r.traveler.email = :s",
					Erreserba.class);
			query.setParameter("s", s);
			erreserbak = query.getResultList();
		} catch (Exception e) {
			
		}
		return erreserbak;
	}

	public List<domain.Erreserba> getAllErreserbaBukatuak(Traveler t) {
		List<Erreserba> erreserbak = null;
		String s = t.getEmail();
		try {
			TypedQuery<Erreserba> query = db.createQuery(
					"SELECT r FROM Erreserba r WHERE r.traveler.email = :s AND r.ride.bukatuta = TRUE AND r.onartua = TRUE",
					Erreserba.class);
			query.setParameter("s", s);
			erreserbak = query.getResultList();
		} catch (Exception e) {
			
		}
		return erreserbak;
	}

	public List<domain.Erreserba> getAllErreserbak(Driver d) {
		List<Erreserba> erreserbak = null;
		String s = d.getEmail();
		try {
			TypedQuery<Erreserba> query = db.createQuery("SELECT r FROM Erreserba r WHERE r.ride.driver.email = :s",
					Erreserba.class);
			query.setParameter("s", s);
			erreserbak = query.getResultList();
		} catch (Exception e) {
			
		}
		return erreserbak;
	}

	public boolean badagoErreserbaBehinBetiko(Driver d) {
		List<Erreserba> erreserbak = null;
		String s = d.getEmail();
		try {
			TypedQuery<Erreserba> query = db.createQuery(
					"SELECT r FROM Erreserba r WHERE r.ride.driver.email = :s AND r.onartua = TRUE AND r.ride.bukatuta = FALSE",
					Erreserba.class);
			query.setParameter("s", s);
			erreserbak = query.getResultList();
		} catch (Exception e) {
			
		}
		return (!erreserbak.isEmpty());
	}

	public void sartuDirua(double money, Driver d) {
		Driver driver = badagoDriver(d.getEmail());
		db.getTransaction().begin();
		driver.addMoney(money);
		db.getTransaction().commit();
	}

	public void sartuDirua(double money, Traveler t) {
		Traveler traveler = badagoTraveler(t.getEmail());
		db.getTransaction().begin();
		traveler.addMoney(money);
		db.getTransaction().commit();
	}

	public void erreserbaSortu(Ride r, int place, Traveler t) {
		Erreserba er = new Erreserba(r, place, t);
		Traveler trav = badagoTraveler(t.getEmail());
		db.getTransaction().begin();
		db.persist(er);
		trav.setMoney(t.getMoney() - (r.getPrice() * place));
		db.getTransaction().commit();
	}

	public void erreserbaEzabatu(Erreserba er, Traveler t) {
		Traveler trav = badagoTraveler(t.getEmail());
		db.getTransaction().begin();
		Erreserba erre = db.find(Erreserba.class, er.getErreserbaNumber());
		db.remove(erre);
		trav.setMoney(t.getMoney() + (er.getRide().getPrice() * er.getPlaces()));
		db.getTransaction().commit();
	}

	public void erreserbaOnartu(Erreserba er) {
		db.getTransaction().begin();
		Erreserba erre = db.find(Erreserba.class, er.getErreserbaNumber());
		Ride r = db.find(Ride.class, erre.getRide().getRideNumber());
		r.setBetMinimum(r.getnPlaces() - erre.getPlaces());
		erre.setOnartua(true);
		db.getTransaction().commit();
	}

	public Ride getRide(int id) {
		db.getTransaction().begin();
		Ride r = db.find(Ride.class, id);
		db.getTransaction().commit();
		return r;
	}

	public Erreserba getErreserba(int id) {
		db.getTransaction().begin();
		Erreserba er = db.find(Erreserba.class, id);
		db.getTransaction().commit();
		return er;
	}

	public boolean erreserbaDauka(Ride r) {
		int s = r.getRideNumber();
		List<Erreserba> erreserbak = null;
		try {
			TypedQuery<Erreserba> query = db.createQuery("SELECT r FROM Erreserba r WHERE r.ride.rideNumber = :s",
					Erreserba.class);
			query.setParameter("s", s);
			erreserbak = query.getResultList();
		} catch (Exception e) {
		
		}
		return !erreserbak.isEmpty();
	}

	public void addTransaction(Transaction trans, Traveler t) {
		Traveler traveler = badagoTraveler(t.getEmail());
		db.getTransaction().begin();
		traveler.addTransaction(trans);
		db.getTransaction().commit();
	}

	public void addTransaction(Transaction trans, Driver d) {
		Driver driver = badagoDriver(d.getEmail());
		db.getTransaction().begin();
		driver.addTransaction(trans);
		db.getTransaction().commit();
	}

	public void addCar(String marka, String modeloa, String matrikula, Driver d) {
		Driver driv = badagoDriver(d.getEmail());
		db.getTransaction().begin();
		driv.addCar(marka, modeloa, matrikula);
		db.getTransaction().commit();
	}

	public void rejectClaim(String s) {
		db.getTransaction().begin();
		int id = Integer.parseInt(s);
		Erreklamazioa err = db.find(Erreklamazioa.class, id);
		err.setEginda(true);
		db.getTransaction().commit();
	}

	public boolean badagoErreklamazioa(Erreserba err) {
		List<Erreklamazioa> erreklamazioak = null;
		try {
			TypedQuery<Erreklamazioa> query = db.createQuery(
					"SELECT r FROM Erreklamazioa r WHERE r.erreserba.erreserbaNumber = :s", Erreklamazioa.class);
			query.setParameter("s", err.getErreserbaNumber());
			erreklamazioak = query.getResultList();
		} catch (Exception e) {
			
		}
		return (erreklamazioak.size() > 0);
	}

	public boolean badagoBalorazioa(Erreserba err) {
		List<Balorazioa> balorazioak = null;
		try {
			TypedQuery<Balorazioa> query = db
					.createQuery("SELECT r FROM Balorazioa r WHERE r.erreserba.erreserbaNumber = :s", Balorazioa.class);
			query.setParameter("s", err.getErreserbaNumber());
			balorazioak = query.getResultList();
		} catch (Exception e) {
		
		}
		return (balorazioak.size() > 0);
	}

	public void acceptClaim(String s, Driver d) {
		Driver driv = badagoDriver(d.getEmail());
		db.getTransaction().begin();
		Erreklamazioa erre = db.find(Erreklamazioa.class, Integer.parseInt(s));
		erre.setEginda(true);
		driv.setMoney(driv.getMoney() - (erre.getErreserba().getRide().getPrice() * erre.getErreserba().getPlaces()));
		db.getTransaction().commit();
		Traveler trav = badagoTraveler(erre.getEmail());
		db.getTransaction().begin();
		trav.setMoney(trav.getMoney() + (erre.getErreserba().getRide().getPrice() * erre.getErreserba().getPlaces()));
		db.getTransaction().commit();
	}
	/**
	 * This method deletes a Traveler account 
	 * 
	 * @param t         The traveler that we want to remove
	 * @throws Exception if the traveler is null or the traveler does not exist in the DB
	 */ 

	public void deleteAccountT(Traveler t) throws Exception {
		db.getTransaction().begin();
		try {
			deleteTran(t,null);
			kontsultakT(t);
			db.remove(db.find(Traveler.class, t.getEmail()));
			db.getTransaction().commit();
		} catch (Exception e) {
			db.getTransaction().rollback();
			throw new Exception(); 
		}
	} 
	
	/**
	 * This method deletes a Driver account 
	 * 
	 * @param d         The driver that we want to remove
	 * @throws Exception if the driver is null or the driver does not exist in the DB
	 */
	public void deleteAccountD(Driver d) throws Exception {
		db.getTransaction().begin();
		try {
			deleteTran(null,d);
			deleteKotx(d);
			kontsultakD(d);
			db.remove(db.find(Driver.class, d.getEmail()));
			db.getTransaction().commit();
		} catch (Exception e) {
			db.getTransaction().rollback();
			throw new Exception();
		}
	}
	public void kontsultakD(Driver d) {
		TypedQuery<Alerta> query5 = db.createQuery("DELETE FROM Alerta p WHERE p.ride.driver.email = :s",Alerta.class);
		query5.setParameter("s", d.getEmail());
		query5.executeUpdate();
		TypedQuery<Balorazioa> query3 = db.createQuery("DELETE FROM Balorazioa f WHERE f.erreserba.ride.driver.email = :s", Balorazioa.class);
		query3.setParameter("s", d.getEmail());
		query3.executeUpdate();
		TypedQuery<Erreklamazioa> query4 = db.createQuery("DELETE FROM Erreklamazioa q WHERE q.erreserba.ride.driver.email = :s", Erreklamazioa.class);
		query4.setParameter("s", d.getEmail());
		query4.executeUpdate();
		TypedQuery<Erreserba> query = db.createQuery("DELETE FROM Erreserba r WHERE r.ride.driver.email = :s",Erreserba.class);
		query.setParameter("s", d.getEmail());
		query.executeUpdate();
		TypedQuery<Ride> query2 = db.createQuery("DELETE FROM Ride w WHERE w.driver.email = :s", Ride.class);
		query2.setParameter("s", d.getEmail());
		query2.executeUpdate();
	}
	public void kontsultakT(Traveler t) {
		TypedQuery<Alerta> query2 = db.createQuery("DELETE FROM Alerta e WHERE e.email = :s", Alerta.class);
		query2.setParameter("s", t.getEmail());
		query2.executeUpdate();
		TypedQuery<Balorazioa> query3 = db.createQuery("DELETE FROM Balorazioa f WHERE f.email = :s",Balorazioa.class);
		query3.setParameter("s", t.getEmail());
		query3.executeUpdate();
		TypedQuery<Erreklamazioa> query4 = db.createQuery("DELETE FROM Erreklamazioa q WHERE q.email = :s",Erreklamazioa.class);
		query4.setParameter("s", t.getEmail());
		query4.executeUpdate();
		TypedQuery<Erreserba> query = db.createQuery("DELETE FROM Erreserba r WHERE r.onartua = FALSE AND r.traveler.email = :s", Erreserba.class);
		query.setParameter("s", t.getEmail());
		query.executeUpdate();
	}
	public void deleteTran(Traveler t, Driver d) {
		List<Transaction> transac= new LinkedList<>();
		Transaction tran;
		if (t!=null) {
			transac=t.getTransactions();
		}else {
			transac=d.getTransactions();
		}
		for (Transaction tr : transac) {
			tran = db.find(Transaction.class, tr.getId());
			if (tran != null)
				db.remove(tran);
		}
	}
	public void deleteKotx(Driver d) {
		Kotxea ko;
		for (Kotxea k : d.getCars()) {
			ko = db.find(Kotxea.class, k.getMatrikula());
			if (ko != null)
				db.remove(ko);
		}
	}
	public void open() {

		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public void close() {
		db.close();
		emf.close();
		System.out.println("DataAcess closed");
	}

}
