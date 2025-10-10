package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.*;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			List<Kotxea> kotxeak=d.getCars();
			for(Kotxea k:kotxeak) {
				db.remove(k);
			}
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else {
			return false;
		}
		
		
    }
	public boolean removeTraveler(String travelerEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Traveler t = db.find(Traveler.class, travelerEmail);
		if (t!=null) {
			db.getTransaction().begin();
			db.remove(t);
			db.getTransaction().commit();
			return true;
		} else {
			return false;
		}
		
		
    }
	public Driver createDriver(String email, String pass ) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
	
			
			try {
			    driver=new Driver(email,pass);
			    db.getTransaction().begin();
				db.persist(driver);
				db.getTransaction().commit();
				
			}
			catch (Exception e){
				
			}
			return driver;
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
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Driver addDriverWithRide(String email, String pass, String from, String to,  Date date, int nPlaces, float price) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, email);
					if (driver==null) {
						driver=new Driver(email,pass);
					}
					Ride ride= new Ride(from, to, date, 0, 0, driver, new Kotxea("a", "b", "c"));
				    driver.addRide(ride);
				    db.persist(driver);
					db.getTransaction().commit();
					return driver;
					
				}
				catch (Exception e){
					
				}
				return null;
	    }
		
		
		public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			db.getTransaction().begin();
			System.out.println("beeb");
			Driver d = db.find(Driver.class, email);
			System.out.println(d);
			
			if (d!=null) {
				db.getTransaction().commit();
				return d.doesRideExists(from, to, date);
			} else {
				db.getTransaction().commit();
				return false;
			}
			
		}
		public void removeRide(String email, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			db.getTransaction().begin();
			Driver d = db.find(Driver.class, email);
			
			if (d!=null) {
				System.out.println("lol");
				int rId=d.getRide(from, to, date).getRideNumber();
				this.removeRideB(String.valueOf(rId));
				db.getTransaction().commit();
				

			} else {
				db.getTransaction().commit();
				
			}
			

		}
		public void removeRideB(String s) {
			
			int id = Integer.parseInt(s);
			Ride ride = db.find(Ride.class, id);
			Driver driver = db.find(Driver.class, ride.getDriver().getEmail());
			driver.removeRide(ride);
			db.remove(ride);
			
		}


		
}


