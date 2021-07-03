package Logica;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Cliente {
	private double latitud;
	private double longitud;
	private String nombre;
	
	public Cliente (double latitud , double longitud, String nombre) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	public  Coordinate getCoord() {
		return new Coordinate(latitud, longitud);
	}
	
	

}
