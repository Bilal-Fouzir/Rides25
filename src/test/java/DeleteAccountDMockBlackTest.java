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
import javax.persistence.TypedQuery;

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
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class DeleteAccountDMockBlackTest {
	
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
	
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test1() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		Driver d= new Driver(driverEmail,driverPass);
		Transaction tr= new Transaction(2.0,10.0, "Erosketa");
		d.addTransaction(tr);
		d.addCar("SEAT","IBIZA", "1234BCD");
		try {
			
			
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, d.getEmail())).thenReturn(d);
	        Mockito.when(db.createQuery("DELETE FROM Alerta p WHERE p.ride.driver.email = :s",Alerta.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Balorazioa f WHERE f.erreserba.ride.driver.email = :s", Balorazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreklamazioa q WHERE q.erreserba.ride.driver.email = :s", Erreklamazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreserba r WHERE r.ride.driver.email = :s",Erreserba.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Ride w WHERE w.driver.email = :s", Ride.class)).thenReturn(Mockito.mock(TypedQuery.class));

	        
			sut.open();
			sut.deleteAccountD(d);
			sut.close();
			assertTrue(true);
			
			
			
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
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test2() {
		
		
		
	
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
						
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountD(null);
			sut.close();
			
			fail();
			
			
		   
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}finally {
				  //Remove the created objects in the database (cascade removing)   
				
		        }
		   } 
	
	
}

