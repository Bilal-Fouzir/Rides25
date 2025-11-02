package service;

import dataAccess.DataAccess;

public class FactoryBLFacade {
	
	public static BLFacade createBLFacade(int type) {
		if(type==1) {
			
			return new BLFacadeImplementation(DataAccess.getInstance());
		}else {
			return null;
		}
		
	}
	
	
}
