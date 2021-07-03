package Logica;

import java.util.ArrayList;

public class Distribucion {
	private ArrayList<Centro> centros ;
	private ArrayList<Cliente> clientes;
	
	public Distribucion (ArrayList<Cliente> clientes , ArrayList<Centro> centros) {
		this.centros = centros;		
		this.clientes = clientes;	
	}
	
	public void agregarCentro(Centro c) {
			centros.add(c);
	}

	public ArrayList<Centro> getCentros() {
		return centros;
	}
	
	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
	
	public int cantidadCentros() {
		return centros.size();
	}
	
	public int cantidadClientes() {
		return clientes.size();
	}
	


}
