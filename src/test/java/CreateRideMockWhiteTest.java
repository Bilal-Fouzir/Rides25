import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Kotxea;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockWhiteTest {
	
	static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);


		
    }
	@After
    public  void tearDown() {
		persistenceMock.close();


		
    }
	
	
	Driver driver;
	Kotxea kotxe;

	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1() {


        
		String driverEmail="driver1@gmail.com";
		String driverPass="Aitor Fernandez";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
					
			 driver=new Driver(driverEmail,driverPass);
			 driver.addRide(rideFrom, rideTo, rideDate, 0, 0, new Kotxea("a", "b", "c"));
			//configure the state through mocks 

				
		        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
		        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
		      //define Argument captors
		        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
		       
		   
		      
		        //invoke System Under Test (sut)  
				sut.open();
				Ride ride = sut.createRide(rideFrom, rideTo, rideDate, 1,(float) 1.0, driverEmail, "kaka");
				sut.close();	
				
				//verify call numbers and capture parameters
		        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
		       //verify parameter values as usual using JUnit asserts
		       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
		      
			fail();
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
		} catch(Exception e) {
			fail();
		}
	} 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		//define parameters
		String driverName="Aitor Fernandez";
		String driverEmail="driver1@gmail.com";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			Driver driver1=new Driver(driverEmail,driverName);

			//configure the state through mocks 

			
	        Mockito.when(db.find(Driver.class, driver1.getEmail())).thenReturn(driver1);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			Ride ride = sut.createRide(rideFrom, rideTo, rideDate, 1,(float) 1.0, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	      
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			 Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
			
			//ride is in DB. The persist operation has been invoked.
			//boolean existRide=testDA.existRide(driverEmail,ride.getFrom(), ride.getTo(), ride.getDate());
				
			//assertTrue(existRide);
			//testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

			fail();
			//redone state of the system (create object in the database)
			
		}  catch(Exception e) {
			fail();
		}
	} 
	@Test
	//sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test3() {
			try {
		 		
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverEmail=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;
				try {
					rideDate = sdf.parse("05/10/2025");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				Mockito.when(db.find(Driver.class, null)).thenThrow(IllegalArgumentException.class);
				
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverEmail, "kaka");
				System.out.println("ride "+ride);

				//verify the results
				assertNull(ride);
				
				
			   } catch (RideAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();

				} catch (RideMustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
					fail();

				} catch (Exception e) {
				// TODO Auto-generated catch block
					assertTrue(true);

				} finally {
					sut.close();
				}
			
			   } 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test4() {
		//define parameters
		String driverName="Aitor Fernandez";
		String driverEmail="driver1@gmail.com";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try { 
			rideDate = sdf.parse("05/10/2024");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			Driver driver1=new Driver(driverEmail,driverName);

			//configure the state through mocks 

			
	        Mockito.when(db.find(Driver.class, driver1.getEmail())).thenReturn(driver1);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			Ride ride = sut.createRide(rideFrom, rideTo, rideDate, 1,(float) 1.0, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	      
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			 Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
			fail();
			//ride is in DB. The persist operation has been invoked.
			//boolean existRide=testDA.existRide(driverEmail,ride.getFrom(), ride.getTo(), ride.getDate());
				
			//assertTrue(existRide);
			//testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

			assertTrue(true);
			//redone state of the system (create object in the database)
			
		}  catch(Exception e) {
			fail();
		}
	} 


}

