package Frogak;

import service.BLFacade;
import service.FactoryBLFacade;

import java.util.Date;

import domain.*;
import gui.*;
public class AdapterMain {
	public static void main(String[]	args)	{
//		the	BL	is	local

	try {
		boolean isLocal =	true;
		BLFacade	bl =	FactoryBLFacade.createBLFacade(1);
		Driver d= new Driver("Markel","a");
		bl.createDriver(d);
		bl.addCar("marka", "modeloa", "matrikula", d);
		Kotxea k= new Kotxea("marka", "modeloa", "matrikula");
		bl.createRide("Madrid", "Mandril", new Date(2030, 02, 11), 2, 2, "Markel", k.toString());
		bl.createRide("Mandril", "Madrid", new Date(2030, 02, 11), 2, 2, "Markel", k.toString());
		Driver	d1= bl.badagoDriver("Markel");
		DriverTable	dt=new	DriverTable(d1);
		dt.setVisible(true);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//me mat
	
	}
}
