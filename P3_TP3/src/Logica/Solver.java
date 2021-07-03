package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Solver {
	private Distribucion distribucion;
	private int cantSoluciones;
	private Comparator<Centro> comparador;
	private ArrayList<Centro> centrosFinal;
	 
	public Solver (Distribucion distribucion, int cantidadSolicitada, Comparator<Centro> comparador) {	
		if (cantidadSolicitada <= distribucion.cantidadCentros()) {
			this.cantSoluciones = cantidadSolicitada;
			this.distribucion = distribucion;
			this.comparador = comparador;
		}else {
			throw new ExceptionInInitializerError("La cantidad de soluciones solicitada no puede exceder la cantidad de centros disponibles");
		}
	}
	
	public Solucion resolver() {
		Solucion ret = new Solucion();
		definirDistancias();
		CentrosOrdenados();
		while(cantSoluciones > 0) {
			ret.agregarCentro(centrosFinal.remove(centrosFinal.size()-1));
			cantSoluciones--;
		}
		return ret;	 
	 }
	 
	private void CentrosOrdenados(){
		Collections.sort(centrosFinal, comparador);
//		Collections.reverse(ret);
	}
	 
	public void definirDistancias() {
		centrosFinal = new ArrayList<Centro>();
		for (Centro unCentro : distribucion.getCentros()) {
			double distancia = 0;
			for (Cliente unCliente : distribucion.getClientes()) {
				distancia += distancia(unCentro.getCoord(), unCliente.getCoord());
			}
			unCentro.setDistanciaPromedio(distancia/distribucion.cantidadClientes());
			centrosFinal.add(unCentro);
		 }
	 }
	 
	public double distancia(Coordinate uno, Coordinate otro) {
	// Calcula la distancia entre dos puntos de la tierra con sus respectivas latitudes y longitudes (SEMIVERSENO)
		double lat1 = uno.getLat(), lon1 = uno.getLon();
		double lat2 = otro.getLat(), lon2 = otro.getLon();
		double R = 6378; //km
		return R*(semiverseno(lat1-lat2) + (Math.cos(lat1)*Math.cos(lat2)*semiverseno(lon1-lon2)));
	}
		
	private  double semiverseno(double arg ) {
		return ((1-Math.cos(arg))/2);
	} 
}
