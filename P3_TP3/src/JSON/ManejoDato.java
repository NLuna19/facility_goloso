package JSON;
import java.util.ArrayList;
import Logica.Cliente;
import Logica.Centro;
import Logica.Solucion;

public class ManejoDato {
		Cliente_JSON clientesJSON;
		Centro_JSON centrosJSON;
		ArrayList<Cliente> listaClientes;
		ArrayList<Centro> listaCentros;
		Centro_JSON solucionJSON;

		public ManejoDato() {	
			clientesJSON = new Cliente_JSON();	
			if (Cliente_JSON.leerJSON("src\\JSON_COORDENADAS\\datosClientes.JSON")!=null){
				clientesJSON = Cliente_JSON.leerJSON("src\\JSON_COORDENADAS\\datosClientes.JSON");
			}
			listaClientes = new ArrayList<Cliente>();
			
			centrosJSON = new Centro_JSON();	
			if (Centro_JSON.leerJSON("src\\JSON_COORDENADAS\\datosCentros.JSON")!=null){
				centrosJSON = Centro_JSON.leerJSON("src\\JSON_COORDENADAS\\datosCentros.JSON");
			}
			listaCentros = new ArrayList<Centro>();
			
			solucionJSON = new Centro_JSON();

		}
			
		public void agregarCliente(Cliente cliente) {
			listaClientes.add(cliente);
		}
		public void agregarCentro(Centro centro) {
			listaCentros.add(centro);
		}
		public void agregarSolucion(Solucion solucion) {
			solucionJSON = new Centro_JSON();
			for (Centro centro : solucion.getCentros()) {
				solucionJSON.agregar(centro);
			}
		}
			
		public void actualizarDatosCliente() {
			for (Cliente cliente : listaClientes) {
				clientesJSON.agregar(cliente);
			}
			String json = clientesJSON.GeneraJSON_pretty();
			clientesJSON.guardarJSON(json, "src\\JSON_COORDENADAS\\datosClientes.JSON");	
		}
		public void actualizarDatosCentro() {		
			for (Centro centro : listaCentros) {
				centrosJSON.agregar(centro);
			}
			String json = centrosJSON.GeneraJSON_pretty();
			centrosJSON.guardarJSON(json, "src\\JSON_COORDENADAS\\datosCentros.JSON");	
		}
		
		public void actualizarSolucion() {
			String json = solucionJSON.GeneraJSON_pretty();
			solucionJSON.guardarJSON(json, "src\\JSON_COORDENADAS\\datosSolucion.JSON");	
		}
				
		public ArrayList<Cliente> listaClientes(){
			ArrayList<Cliente> ret = new ArrayList<Cliente>();
			int indice = 0;
			while(indice < clientesJSON.tamanio()) {
				ret.add(clientesJSON.obtener(indice));
				indice++;
			}
			return ret;
		}
		
		public ArrayList<Centro> listaCentros() {
			ArrayList<Centro> ret = new ArrayList<Centro>();
			int indice = 0;
			while(indice < centrosJSON.tamanio()) {
				ret.add(centrosJSON.obtener(indice));
				indice++;
			}
			return ret;
		}
		
		public ArrayList<Centro> listaSolucion() {
			ArrayList<Centro> ret = new ArrayList<Centro>();
			solucionJSON = Centro_JSON.leerJSON("src\\JSON_COORDENADAS\\datosSolucion.JSON");
			int indice = 0;
			while(indice < solucionJSON.tamanio()) {
				ret.add(solucionJSON.obtener(indice));
				indice++;
			}
			return ret;
		}
		
		public int cantidadPersonasCargadas() {
			return clientesJSON.tamanio()+listaClientes.size();
		}
		public int cantidadCentrosCargadas() {
			return centrosJSON.tamanio()+listaCentros.size();
		}

	
}
