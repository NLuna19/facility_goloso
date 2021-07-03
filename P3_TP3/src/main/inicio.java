package main;
import java.awt.EventQueue;
import Grafica.Ventana;

public class inicio {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana aplicacion = new Ventana();
					aplicacion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
