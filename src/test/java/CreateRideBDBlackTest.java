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
import domain.CreateRideParametroak;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class CreateRideBDBlackTest {
	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();
 
	private Driver driver; 

	@Test
	public void test1() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
				
			assertTrue(exist);
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	
	
	@Test
	public void test2() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom=null;
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			System.out.println(ride);
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
				
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	@Test
	public void test3() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo=null;
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
				
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.close();
			
		        }
		   }
	@Test
	public void test4() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		Date rideDate=null;
			
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
			fail();
			System.out.println(exist);
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test5() {
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
				  if(testDA.existRide(driverEmail,rideFrom, rideTo, rideDate)) {
				  testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
				  
				  }
				  testDA.removeDriver(driverEmail);
				  testDA.close();
		   } 
	}
	@Test
	public void test6() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, (Integer) null, (float) 2.5);
			ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
				
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	@Test
	public void test7() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
			System.out.println(exist);
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	@Test
	public void test8() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (Float) null);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
			System.out.println(exist);
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	@Test
	public void test9() {
		String driverEmail="driver@gmail.com";
		String driverPass="12345678";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		boolean existDriver=false;
		testDA.open();
		if(!testDA.existDriver(driverEmail)) {
			Driver d=testDA.createDriver(driverEmail, driverPass);
			d.addCar("SEAT","IBIZA","1234BCD");
		}
		existDriver=testDA.existDriver(driverEmail);
		testDA.close();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) -2.5);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();			
			
			//verify the results
			
			
			//q datubasean dago
			testDA.open();
			boolean exist=testDA.existRide(driverEmail,rideFrom, rideTo, rideDate);
			System.out.println(exist);
			fail();
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				assertTrue(true);
			}
		
		
		finally {   

			testDA.open();
			if (testDA.existRide(driverEmail,rideFrom, rideTo, rideDate))
				testDA.removeRide(driverEmail, rideFrom, rideTo, rideDate);
			testDA.removeDriver(driverEmail);
			testDA.close();
			
		        }
		   }
	
	@Test
    //sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
        public void test10() {
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
	
	
	
}
