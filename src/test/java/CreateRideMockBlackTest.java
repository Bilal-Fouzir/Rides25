import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

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
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockBlackTest {
	
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
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test1() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate,1 ,(float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	       assertEquals(ride,((Driver)driverCaptor.getValue()).getRide(rideFrom, rideTo, rideDate));
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test2() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom=null;
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
			Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	    
			
	        //invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	     
			fail();
			//verify the results
		
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test3() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo=null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
			Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();		
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	      
			//verify the results
			fail();
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test4() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		
		Date rideDate=null;;
			
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
			Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	    
			
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1,(float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       
			fail();
			//verify the results
		
			
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
   }
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test5() {
		//define parameters
		String driverPass="Aitor Fernandez";
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
			Driver driver1=new Driver(driverEmail,driverPass);

			//configure the state through mocks 
			Mockito.when(db.find(Driver.class, driver1.getEmail())).thenReturn(driver1);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	        
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
					
			//invoke System Under Test (sut)  
			sut.open();
			CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1,(float) 1.0);
			Ride ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			
			//verify call numbers and capture parameters
		        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
		       //verify parameter values as usual using JUnit asserts
		       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
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
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test6() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, (Integer)null, 0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test7() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
		   	 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, -1, (float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	       fail();
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test8() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (Float)null);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
			
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
   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test9() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) -1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	       fail();
			
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
   }
	@Test
	public void test10() {
		String driverPass="Aitor Fernandez"; 

		String driverEmail="driver1@gmail.com";
		String rideFrom="Bilbao";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(null,driverPass);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Kotxea.class, "kaka")).thenReturn(kotxe);
	      //define Argument captors
	        ArgumentCaptor<Driver> driverCaptor =ArgumentCaptor.forClass(Driver.class);
	       
	   
	      
	        //invoke System Under Test (sut)  
			sut.open();
			 CreateRideParametroak p= new CreateRideParametroak(rideFrom, rideTo , rideDate, 1, (float) 1.0);
			 ride=sut.createRide(p, driverEmail, "kaka");
			sut.close();	
			
			//verify call numbers and capture parameters
	        Mockito.verify(db,Mockito.times(1)).persist(driverCaptor.capture());
	       //verify parameter values as usual using JUnit asserts
	       assertEquals(driverEmail,((Driver)driverCaptor.getValue()).getEmail());
	       fail();
			
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
   }
	
	


}

