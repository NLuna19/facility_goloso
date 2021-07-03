package Logica;
import org.openstreetmap.gui.jmapviewer.Coordinate;
public class Centro {
	private double latitud;
	private double longitud;
	private String nombre;
	private double distanciaPromedio;
	private int categoria; // posible beneficio del centro
	
	public Centro (double latitud , double longitud, String nombre, int categoria) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.nombre = nombre;
		this.distanciaPromedio = 0;
		this.categoria = categoria;
	}
		
	public void setDistanciaPromedio(double promedio) {
		this.distanciaPromedio = promedio;
	}
	
	public double getDistanciaPromedio() {
		return distanciaPromedio;
	}

	public int getCategoria() {
		return categoria;
	}
	
	public String getNombre() {
		return nombre;
	}
	public  Coordinate getCoord() {
		return new Coordinate(latitud, longitud);
	}
	
}
