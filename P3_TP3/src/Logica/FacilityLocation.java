package Logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import JSON.ManejoDato;

public class FacilityLocation {
	private ManejoDato datos;
	private ArrayList<Cliente> clientes;	
	private ArrayList<Centro> centros;	
	private Distribucion distribucion;
	private Solver solver;
	private ArrayList<Centro> solucion;
	
	public FacilityLocation() {
		iniciarJSON();
		TraerDatosDeJSON();
		distribucion = new Distribucion(clientes, centros);
	}
	
	public void ResolverPorDistancia(int cantidadSoluciones) {
		solver = new Solver(distribucion, cantidadSoluciones, comparadorPorDistancia());
		datos.agregarSolucion(solver.resolver());
		datos.actualizarSolucion();
	}
	
	public void ResolverPorCategoria(int cantidadSoluciones) {
		solver = new Solver(distribucion, cantidadSoluciones, comparadorPorCategoria());
		datos.agregarSolucion(solver.resolver());
		datos.actualizarSolucion();
	}

	public void iniciarJSON() {
		datos = new ManejoDato();
	}
	
	public ArrayList<Centro>  getCentrosACrear(){
		return solver.resolver().getCentros();
	}
	

	public void TraerDatosDeJSON() {
		//trae los datos de los JSON y los guarda en clientes y  centros
		clientes = new ArrayList<Cliente>();
		clientes = datos.listaClientes();
		centros = new ArrayList<Centro>();
		centros = datos.listaCentros();
		solucion = new ArrayList<Centro>();
		solucion = datos.listaSolucion();
	}
	
	public HashMap<Coordinate, String> clientes() {
		HashMap<Coordinate, String> ret = new HashMap<Coordinate, String>();;
		clientes.stream().forEach(c -> ret.put(c.getCoord(), c.getNombre()));
		return ret;
	}
	
	public HashMap<Coordinate, String> centros() {
		HashMap<Coordinate, String> ret = new HashMap<Coordinate, String>();;
		centros.stream().forEach(c -> ret.put(c.getCoord(), c.getNombre()));
		return ret;
	}
	
	public HashMap<Coordinate, String> solucion() {
		HashMap<Coordinate, String> ret = new HashMap<Coordinate, String>();;
		solucion.stream().forEach(c -> ret.put(c.getCoord(), c.getNombre()));
		return ret;
	}
	

	
	private Comparator<Centro> comparadorPorDistancia() {
		return new Comparator<Centro>() {
			@Override
			public int compare(Centro uno, Centro otro) {
				if ( uno.getDistanciaPromedio() < otro.getDistanciaPromedio() )
					return 1;
				else if ( uno.getDistanciaPromedio() == otro.getDistanciaPromedio())
					return 0;
				else
					return -1;
			}
		};
	}
	
	private Comparator<Centro> comparadorPorCategoria() {
		return new Comparator<Centro>() {
			@Override
			public int compare(Centro uno, Centro otro) {
				return uno.getCategoria() - otro.getCategoria();
			}
		};
	}
	
}
