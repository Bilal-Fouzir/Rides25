import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class DeleteAccountTBDWhiteTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	private Driver driver; 

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
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test2() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";

		Traveler t= new Traveler(travelerEmail,travelerPass);
		
		
		boolean existTraveler=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
						
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountT(t);
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
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test3() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		Traveler t= new Traveler(travelerEmail,travelerPass);
		
		
		
		boolean existTraveler=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			testDA.createTraveler(t);
			testDA.close();
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountT(t);
			sut.close();
			
			assertTrue(true);
			
			
		   
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}finally {
				  //Remove the created objects in the database (cascade removing)   
				
		        }
		   } 
	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test4() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		Traveler t= new Traveler(travelerEmail,travelerPass);
		t.addTransaction(null);
		
		
		boolean existTraveler=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			testDA.createTraveler(t);
			testDA.close();
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountT(t);
			sut.close();
			
			assertTrue(true);
			
			
		   
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}finally {
				  //Remove the created objects in the database (cascade removing)   
			testDA.open();
			  testDA.removeTraveler(t.getEmail());
			  testDA.close();
		        }
		   } 
	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test5() {
		String travelerEmail="traveler@gmail.com";
		String travelerPass="Aitor Fernandez";
		Traveler t= new Traveler(travelerEmail,travelerPass);
		Transaction tr= new Transaction(2.0,10.0, "Erosketa");
		t.addTransaction(tr);
		
		
		boolean existTraveler=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			testDA.createTraveler(t);
			testDA.close();
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountT(t);
			sut.close();
			
			assertTrue(true);
			
			
		   
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}finally {
				  //Remove the created objects in the database (cascade removing)   
				
		        }
		   } 
	
}

