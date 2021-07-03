package JSON;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Logica.Cliente;

public class Cliente_JSON {
		private ArrayList<Cliente> clientes;
		
		public Cliente_JSON(){
			clientes = new ArrayList<Cliente>();
		}
		
		public void agregar(Cliente c) {
			if (!contiene(c)) {
				clientes.add(c);
			}
		}
		
		private boolean contiene(Cliente c) {
			return clientes.contains(c);
		}

		public Cliente obtener(int indice) {
			return clientes.get(indice);
		}
		
		public int tamanio() {
			return clientes.size();
		}
		
		public String GeneraJSON_pretty() {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(this);
			return json;
		}
		
		public void guardarJSON(String json, String ArchivoDestino) {
			try {
				FileWriter writer = new FileWriter(ArchivoDestino);
				writer.write(json);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static Cliente_JSON leerJSON(String archivo) {
			Gson gson = new Gson();
			Cliente_JSON nuevo = null;
			try {
				BufferedReader br = new BufferedReader (new FileReader(archivo));
				nuevo = gson.fromJson(br, Cliente_JSON.class);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return nuevo;
		}
		
		
		
	

}
