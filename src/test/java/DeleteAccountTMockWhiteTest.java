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

public class DeleteAccountTMockWhiteTest {
	
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
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";

		
		
		
	
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
						
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountT(null);
			sut.close();
			
			fail();
			
			
		   
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}finally {
				  //Remove the created objects in the database (cascade removing)   
				
		        }
		   } 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		try {
			Traveler traveler1=new Traveler(travelerEmail,travelerPass);

			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getEmail())).thenThrow(new Exception());
	        
	        
			sut.open();
			sut.deleteAccountT(traveler1);
			sut.close();
			
			
			
			
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
			assertTrue(true);
		}
	} 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test3() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		try {
			Traveler traveler1=new Traveler(travelerEmail,travelerPass);

			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getEmail())).thenReturn(traveler1);
	        Mockito.when( db.createQuery("DELETE FROM Alerta e WHERE e.email = :s", Alerta.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Balorazioa f WHERE f.email = :s",Balorazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreklamazioa q WHERE q.email = :s",Erreklamazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreserba r WHERE r.onartua = FALSE AND r.traveler.email = :s", Erreserba.class)).thenReturn(Mockito.mock(TypedQuery.class));

			sut.open();
			sut.deleteAccountT(traveler1);
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
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test4() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		try {
			Traveler traveler1=new Traveler(travelerEmail,travelerPass);
			traveler1.addTransaction(null);
			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getEmail())).thenReturn(traveler1);
	        Mockito.when( db.createQuery("DELETE FROM Alerta e WHERE e.email = :s", Alerta.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Balorazioa f WHERE f.email = :s",Balorazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreklamazioa q WHERE q.email = :s",Erreklamazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreserba r WHERE r.onartua = FALSE AND r.traveler.email = :s", Erreserba.class)).thenReturn(Mockito.mock(TypedQuery.class));

			sut.open();
			sut.deleteAccountT(traveler1);
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
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test5() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		try {
			Traveler traveler1=new Traveler(travelerEmail,travelerPass);
			Transaction tr= new Transaction(2.0,10.0, "Erosketa");
			traveler1.addTransaction(tr);
			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getEmail())).thenReturn(traveler1);
	        Mockito.when( db.createQuery("DELETE FROM Alerta e WHERE e.email = :s", Alerta.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Balorazioa f WHERE f.email = :s",Balorazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreklamazioa q WHERE q.email = :s",Erreklamazioa.class)).thenReturn(Mockito.mock(TypedQuery.class));
	        Mockito.when(db.createQuery("DELETE FROM Erreserba r WHERE r.onartua = FALSE AND r.traveler.email = :s", Erreserba.class)).thenReturn(Mockito.mock(TypedQuery.class));

			sut.open();
			sut.deleteAccountT(traveler1);
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

}

