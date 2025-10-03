package service;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.*;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, String datuak) throws Exception;
	
	@WebMethod public void removeRide(String s);
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	@WebMethod public void createDriver(Driver d);
	
	@WebMethod public void createTraveler(Traveler t);
	
	@WebMethod public Driver badagoDriver(String d);
	
	@WebMethod public Traveler badagoTraveler(String t);
	
	@WebMethod public boolean badagoErreklamazioa(Erreserba e);
	
	@WebMethod public boolean badagoBalorazioa(Erreserba e);
	
	@WebMethod 	public void sartuDiruaD(double money, Driver d);
	
	@WebMethod 	public void sartuDiruaT(double money, Traveler t);
	
	@WebMethod public List<domain.Ride> getAllRides(Driver d);
	
	@WebMethod public void erreserbaSortu(Ride r, int place, Traveler t);
	
	@WebMethod public void erreserbaEzabatu(Erreserba er, Traveler t);
	
	@WebMethod public void erreserbaOnartu(Erreserba er);
	
	@WebMethod public Ride getRide(int id);
	
	@WebMethod public Erreserba getErreserba(int id);
	
	@WebMethod public List<domain.Erreserba> getAllErreserbakT(Traveler traveler);
	
	@WebMethod public List<domain.Erreserba> getAllErreserbaBukatuak(Traveler t);
	
	@WebMethod public List<domain.Erreserba> getAllErreserbakD(Driver d);
	
	@WebMethod public boolean erreserbaDauka(Ride r);
	
	@WebMethod public void bukatuRide(String s);
	
	@WebMethod public void addTransactionT(Transaction trans, Traveler t);
	
	@WebMethod public void addTransactionD(Transaction trans, Driver d);
	
	@WebMethod public void addCar(String marka, String modeloa, String matrikula, Driver d);
	
	@WebMethod public List<Kotxea> getCars(Driver d);
	
	@WebMethod public void createReview(Balorazioa b, int r);
	
	@WebMethod public void createErreklamazioa(Erreklamazioa e, int r);
	
	@WebMethod public void createAlerta(Alerta a);
	
	@WebMethod public void removeAlerta(int a);
	
	@WebMethod public List<domain.Alerta> getAllAlertak();
	
	@WebMethod public List<domain.Alerta> getAlertaAktibatuak(String email);
	
	@WebMethod public void aktibatuAlerta(Alerta a, Ride ride);
	
	@WebMethod public void rejectClaim(String s);
	
	@WebMethod public void acceptClaim(String s, Driver d);
	
	@WebMethod public void deleteAccountT(Traveler t)throws Exception;
	
	@WebMethod public void deleteAccountD(Driver d)throws Exception;
	
	@WebMethod public boolean badagoErreserbaBehinBetiko(Driver d);
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();


	
}
