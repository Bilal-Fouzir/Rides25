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
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.*;

public class CreateRideBDWhiteTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	private Driver driver; 

	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1() {
		String driverEmail="driver1@gmail.com";
		String driverName="Aitor Fernandez";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		boolean existDriver=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			 existDriver=testDA.existDriver(driverEmail);
			testDA.addDriverWithRide(driverEmail, driverName, rideFrom, rideTo, rideDate, 0, 0);
			testDA.close();			
			
			//invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 0, 0);
		    sut.createRide(p, driverEmail, "kaka");
			sut.close();
			
			fail();
			
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}finally {
				  //Remove the created objects in the database (cascade removing)   
			 testDA.open();
			 testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			 testDA.removeDriver(driverEmail);
			 testDA.close();
		        }
		   } 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		//define paramaters
		String driverPass="12345678";
		String driverEmail="driver1010@gmail.com";
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		testDA.close();
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
			//Check if exist this ride for this driver, and if exist, remove it.
			
			testDA.open();
			boolean b=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
			if (b) testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.close();
			
			
			//invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 0, 0);
			Ride ride=sut.createRide(p , driverEmail, "1234BCD");
			sut.close();
			
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			//ride datubasean dago
			testDA.open();
			boolean existRide=testDA.existRide(driverEmail,ride.getFrom(), ride.getTo(), ride.getDate());
			System.out.println(existRide);
			
			assertTrue(true);
			
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			//redone state of the system (create object in the database)
			testDA.open();
			driver = testDA.addDriverWithRide(driverEmail, driverPass, rideFrom, rideTo, rideDate, 0, 0);
			testDA.close();	
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  
 
			fail();
			//redone state of the system (create object in the database)
			testDA.open();
			driver = testDA.addDriverWithRide(driverEmail, driverPass, rideFrom, rideTo, rideDate, 0, 0);
			testDA.close();	
		}catch(Exception e) { 
			fail();
		}finally {
			 testDA.open();
			 testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			 testDA.removeDriver(driverEmail);
			 testDA.close();
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
                Date rideDate=null;;
                try {
                    rideDate = sdf.parse("05/10/2025");
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }    
                
                
                
                //invoke System Under Test (sut)  
                sut.open();
                CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 0, 0);
                Ride ride=sut.createRide(p, driverEmail, "kaka");
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
		//define paramaters
		String driverPass="12345678";
		String driverEmail="driver1010@gmail.com";
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		testDA.close();
		String rideFrom="Bilbao";
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
			//Check if exist this ride for this driver, and if exist, remove it.
			
			
			
			
			//invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 0, 0);
			Ride ride=sut.createRide(p, driverEmail, "1234BCD");
			sut.close();
			System.out.println(ride);
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			//ride datubasean dago
			testDA.open();
			boolean existRide=testDA.existRide(driverEmail,ride.getFrom(), ride.getTo(), ride.getDate());
			System.out.println(existRide);
			
			fail();
			
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			//redone state of the system (create object in the database)
			testDA.open();
			driver = testDA.addDriverWithRide(driverEmail, driverPass, rideFrom, rideTo, rideDate, 0, 0);
			
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  
 
				assertTrue(true);
			//redone state of the system (create object in the database)
			testDA.open();
			driver = testDA.addDriverWithRide(driverEmail, driverPass, rideFrom, rideTo, rideDate, 0, 0);
			
		}catch(Exception e) { 
			fail();
		}finally {
			 testDA.open();
			 testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			 testDA.removeDriver(driverEmail);
			 testDA.close();
		   } 
	}
	
	
}

