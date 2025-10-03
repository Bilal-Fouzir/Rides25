package configuration;

import domain.*;

public class UnekoDatuak {

	private int language = 3; //1=English 2=Castellano 3=Euskara
    private String rol;
    private Driver driver;
    private Traveler traveler;

    private static UnekoDatuak instance = new UnekoDatuak();

    public static UnekoDatuak getInstance() {
        return instance;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public void clear() {
        this.setRol(null);
        this.setDriver(null);
        this.setTraveler(null);
    }
	
}
