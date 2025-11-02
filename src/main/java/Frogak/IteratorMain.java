package Frogak;
import service.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import gui.LoginGUI;
public class IteratorMain {
	
	public static void main(String[]	args)	{
		
//		the	BL	is	local
		
	

	try {
		
		boolean isLocal = true;
		
		
		BLFacade bl= FactoryBLFacade.createBLFacade(1);
		Driver d= new Driver("b","a");
		bl.createDriver(d);
		bl.addCar("marka", "modeloa", "matrikula", d);
		Kotxea k= new Kotxea("marka", "modeloa", "matrikula");
		bl.createRide("Madrid", "Mandril", new Date(2030, 02, 11), 2, 2, "b", k.toString());
		bl.createRide("Mandril", "Madrid", new Date(2030, 02, 11), 2, 2, "b", k.toString());
	  
		
		
		System.out.println("Bai");
		ExtendedIterator  i = bl.getDepartingCitiesIterator();
		String c;
		
		System.out.println("_____________________");
		System.out.println("FROM	LAST	TO	FIRST");
		i.goLast();	//	Go	to	last	element
		while (i.hasPrevious())	{
		c =	(String) i.previous();
		System.out.println(c);
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("FROM	FIRST	TO	LAST");
		i.goFirst();	//	Go	to	first	element
		
		while (i.hasNext())	{
		c =	(String) i.next();
		System.out.println(c);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}

	}
}
