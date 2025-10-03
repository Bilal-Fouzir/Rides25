package service;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Transaction;
import domain.Traveler;
import domain.Alerta;
import domain.Balorazioa;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Erreserba;
import domain.Kotxea;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "service.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	private DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		    //dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, String datuak ) throws Exception{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail, datuak);		
		dbManager.close();
		return ride;
   }
   @WebMethod
   public void removeRide(String s) {
	   dbManager.open();
	   dbManager.removeRide(s);
	   dbManager.close();
   }
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	@WebMethod
	public void createDriver(Driver d) {
		dbManager.open();
		dbManager.createDriver(d);
		dbManager.close();
	}
	
	@WebMethod
	public void sartuDiruaD(double money, Driver d) {
		dbManager.open();
		dbManager.sartuDirua(money, d);
		dbManager.close();
	}
	
	@WebMethod
	public void sartuDiruaT(double money, Traveler t) {
		dbManager.open();
		dbManager.sartuDirua(money, t);
		dbManager.close();
	}
	
	@WebMethod
	public void createTraveler(Traveler t) {
		dbManager.open();
		dbManager.createTraveler(t);
		dbManager.close();
	}
	
	@WebMethod
	public Driver badagoDriver(String d) {
		dbManager.open();
		Driver ema = dbManager.badagoDriver(d);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public Traveler badagoTraveler(String t) {
		dbManager.open();
		Traveler ema = dbManager.badagoTraveler(t);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public boolean badagoErreklamazioa(Erreserba e){
		dbManager.open();
		boolean ema = dbManager.badagoErreklamazioa(e);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public boolean badagoBalorazioa(Erreserba e){
		dbManager.open();
		boolean ema = dbManager.badagoBalorazioa(e);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public List<domain.Ride> getAllRides(Driver d){
		dbManager.open();
		List<domain.Ride> ema = dbManager.getAllRides(d);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public List<domain.Erreserba> getAllErreserbakT(Traveler t){
		dbManager.open();
		List<domain.Erreserba> ema = dbManager.getAllErreserbak(t);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public List<domain.Erreserba> getAllErreserbaBukatuak(Traveler t){
		dbManager.open();
		List<domain.Erreserba> ema = dbManager.getAllErreserbaBukatuak(t);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public List<domain.Erreserba> getAllErreserbakD(Driver d){
		dbManager.open();
		List<domain.Erreserba> ema = dbManager.getAllErreserbak(d);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public void erreserbaSortu(Ride r, int place, Traveler t) {
		dbManager.open();
		dbManager.erreserbaSortu(r, place, t);
		dbManager.close();
	}
	
	@WebMethod
	public void erreserbaEzabatu(Erreserba er, Traveler t) {
		dbManager.open();
		dbManager.erreserbaEzabatu(er, t);
		dbManager.close();
	}
	
	@WebMethod
	public void erreserbaOnartu(Erreserba er) {
		dbManager.open();
		dbManager.erreserbaOnartu(er);
		dbManager.close();
	}
	
	@WebMethod
	public Ride getRide(int id) {
		dbManager.open();
		Ride r = dbManager.getRide(id);
		dbManager.close();
		return r;
	}
	
	@WebMethod
	public Erreserba getErreserba(int id) {
		dbManager.open();
		Erreserba er = dbManager.getErreserba(id);
		dbManager.close();
		return er;
	}
	
	@WebMethod
	public boolean erreserbaDauka(Ride r) {
		dbManager.open();
		boolean ema = dbManager.erreserbaDauka(r);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public void bukatuRide(String s) {
		dbManager.open();
		dbManager.bukatuRide(s);
		dbManager.close();
	}
	
	@WebMethod
	public void addTransactionT(Transaction trans, Traveler t) {
		dbManager.open();
		dbManager.addTransaction(trans, t);
		dbManager.close();
	}
	
	@WebMethod
	public void addTransactionD(Transaction trans, Driver d) {
		dbManager.open();
		dbManager.addTransaction(trans, d);
		dbManager.close();
	}
	
	@WebMethod
	public void addCar(String marka, String modeloa, String matrikula, Driver d) {
		dbManager.open();
		dbManager.addCar(marka, modeloa, matrikula, d);
		dbManager.close();
	}
	
	@WebMethod
	public List<Kotxea> getCars(Driver d){
		dbManager.open();
		List<Kotxea> ema = dbManager.getCars(d);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public void createReview(Balorazioa b, int r) {
		dbManager.open();
		dbManager.createReview(b, r);
		dbManager.close();
	}
	
	@WebMethod
	public void createErreklamazioa(Erreklamazioa e, int r) {
		dbManager.open();
		dbManager.createErreklamazioa(e, r);
		dbManager.close();
	}
	
	@WebMethod
	public void createAlerta(Alerta a) {
		dbManager.open();
		dbManager.createAlerta(a);
		dbManager.close();
	}
	
	@WebMethod
	public void removeAlerta(int a) {
		dbManager.open();
		dbManager.removeAlerta(a);
		dbManager.close();
	}
	
	@WebMethod
	public List<domain.Alerta> getAllAlertak(){
		dbManager.open();
		List<Alerta> ema = dbManager.getAllAlertak();
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public List<domain.Alerta> getAlertaAktibatuak(String email){
		dbManager.open();
		List<Alerta> ema = dbManager.getAlertaAktibatuak(email);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	public void aktibatuAlerta(Alerta a, Ride ride) {
		dbManager.open();
		dbManager.aktibatuAlerta(a, ride);
		dbManager.close();
	}
	
	@WebMethod
	   public void rejectClaim(String s) {
		   dbManager.open();
		   dbManager.rejectClaim(s);
		   dbManager.close();
	   }
	
	@WebMethod
	   public void acceptClaim(String s, Driver d) {
		   dbManager.open();
		   dbManager.acceptClaim(s, d);
		   dbManager.close();
	   }
	
	@WebMethod
		public boolean badagoErreserbaBehinBetiko(Driver d) {
		dbManager.open();
		boolean ema = dbManager.badagoErreserbaBehinBetiko(d);
		dbManager.close();
		return ema;
	}
	
	@WebMethod
	   public void deleteAccountT(Traveler t)throws Exception {
		   dbManager.open();
		   dbManager.deleteAccountT(t);
		   dbManager.close();
	   }
	
	@WebMethod
	   public void deleteAccountD(Driver d)throws Exception {
		   dbManager.open();
		   dbManager.deleteAccountD(d);
		   dbManager.close();
	   }
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

}

