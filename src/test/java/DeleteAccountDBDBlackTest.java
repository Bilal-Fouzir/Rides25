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

public class DeleteAccountDBDBlackTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	private Driver driver; 
 
	 
	
	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		Driver d= new Driver(driverEmail,driverPass);
		Transaction tr= new Transaction(2.0,10.0, "Erosketa");
		d.addTransaction(tr);
		d.addCar("SEAT","IBIZA", "1234BCD");
		
		boolean existdriver=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			testDA.createDriver(d);
			testDA.close();
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.deleteAccountD(d);
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
	public void test2() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";

		
		
		
		boolean existdriver=false;
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

