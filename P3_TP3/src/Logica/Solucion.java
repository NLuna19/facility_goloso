package Logica;

import java.util.ArrayList;

public class Solucion  {
	private ArrayList<Centro> centrosSolver;

	public Solucion() {
		centrosSolver = new ArrayList<Centro> ();
	}
	public void agregarCentro (Centro c) {
		centrosSolver.add(c);
	}
	public ArrayList<Centro> getCentros() {
		return centrosSolver;
	}
	int cardinalidad () {
		return centrosSolver.size();
	}
	public String getNombre(int i) {
		return centrosSolver.get(i).getNombre();
	}	
	public double getDistProm(int indice) {
		return centrosSolver.get(indice).getDistanciaPromedio();
	}
	public double getCategoria(int indice) {
		return centrosSolver.get(indice).getCategoria();
	}
	public double getCoordenadas(int indice) {
		return centrosSolver.get(indice).getCategoria();
	}
	public int cantidadCentros() {
		return centrosSolver.size();
	}
}
