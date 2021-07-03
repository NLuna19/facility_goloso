package JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Logica.Centro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Centro_JSON {
	
		private ArrayList<Centro> centros;
		
		public Centro_JSON(){
			centros = new ArrayList<Centro>();
		}
		
		public void agregar(Centro c) {
			if (!contiene(c)) {
				centros.add(c);
			}
		}
		
		private boolean contiene(Centro c) {
			return centros.contains(c);
		}

		public Centro obtener(int indice) {
			return centros.get(indice);
		}
		
		public int tamanio() {
			return centros.size();
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
		
		public static Centro_JSON leerJSON(String archivo) {
			Gson gson = new Gson();
			Centro_JSON nuevo = null;
			try {
				BufferedReader br = new BufferedReader (new FileReader(archivo));
				nuevo = gson.fromJson(br, Centro_JSON.class);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return nuevo;
		}
}
