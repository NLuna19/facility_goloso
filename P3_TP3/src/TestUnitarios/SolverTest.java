package TestUnitarios;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Comparator;
import org.junit.Test;
import Logica.Centro;
import Logica.Cliente;
import Logica.Distribucion;
import Logica.Solucion;
import Logica.Solver;

public class SolverTest {

	@Test
	public void testMejorDistancia() {
		Solver solver = new Solver(instanciaDeDistribucion(), 1, comparadorPorDistancia());
		Solucion sol = solver.resolver();
		assertEquals(sol.getNombre(0), "Centro mejor distancia");
		assertTrue(Assert.EqualsDouble(0.7925328899845957, sol.getDistProm(0)));
	}
	
	@Test
	public void testMejorCategoria() {
		Solver solver = new Solver(instanciaDeDistribucion(), 1, comparadorPorCategoria());
		Solucion sol = solver.resolver();
		assertEquals("Hotel La Joya", sol.getNombre(0));
		assertTrue(Assert.EqualsDouble(7,sol.getCategoria(0)));
	}
		
	@Test(expected = ExceptionInInitializerError.class)
	public void centrosExcedidos() {
		Solver solver = new Solver(instanciaDeDistribucion(), 10, comparadorPorDistancia());
		solver.resolver();
	}
	
	@Test
	public void testDosMejoresCentrosPorDistancia() {
		Solver solver = new Solver(instanciaDeDistribucion(), 2, comparadorPorDistancia());
		Solucion sol = solver.resolver();
		assertEquals("Centro mejor distancia", sol.getNombre(0));
		assertEquals("Escuela Sargento Cabral", sol.getNombre(1));
	}
	
	@Test
	public void testComparadorPorDistancia() {
		Solver solver = new Solver(instanciaDeDistribucion(), 7, comparadorPorDistancia());
		Solucion sol = solver.resolver();
		
		assertEquals("Centro mejor distancia", sol.getNombre(0)); 
		assertEquals("Escuela Sargento Cabral", sol.getNombre(1)); 
		assertEquals("Hotel La Joya", sol.getNombre(2));
		assertEquals("Hospital Trauma", sol.getNombre(3));
		assertEquals("Plaza Cementerio", sol.getNombre(4));
		assertEquals("Grand Bourg", sol.getNombre(5));
		assertEquals("Villa de Mayo", sol.getNombre(6));
	}
	
	@Test
	public void testComparadorPorCategoria() {
		Solver solver = new Solver(instanciaDeDistribucion(), 7, comparadorPorCategoria());
		Solucion sol = solver.resolver();
		
		assertEquals("Hotel La Joya", sol.getNombre(0)); 
		assertEquals("Escuela Sargento Cabral", sol.getNombre(1)); 
		assertEquals("Centro mejor distancia", sol.getNombre(2));
		assertEquals("Plaza Cementerio", sol.getNombre(3));
		assertEquals("Villa de Mayo", sol.getNombre(4));
		assertEquals("Grand Bourg", sol.getNombre(5));
		assertEquals("Hospital Trauma", sol.getNombre(6));
	}
	

	private Distribucion instanciaDeDistribucion() {
		ArrayList <Cliente> clientes = new ArrayList <Cliente> (); 
		clientes.add(new Cliente (-34.540948279436975, -58.70908289206982, "San Miguel"));
		clientes.add(new Cliente (-34.53288802603427, -58.744187642191264,"Cementerio"));
		clientes.add(new Cliente (-34.528504051837075, -58.688569358380285,"Ruta 202"));
		clientes.add(new Cliente (-34.518250307910684, -58.68659525262773,"Los Nogales"));
		clientes.add(new Cliente (-34.51888678393089, -58.718524267408114,"Illia y 9 de julio"));
		clientes.add(new Cliente (-34.50721728430441, -58.71105699782238,"Circulo "));
		clientes.add(new Cliente (-34.510258584085086, -58.729167272334905,"Esquina illia y 197"));
		
		ArrayList<Centro> centros = new ArrayList<Centro>();
		centros.add(new Centro(-34.49594648324338,-58.70492935180664 ,"Hospital Trauma", 1));
		centros.add(new Centro(-34.48674993767932,-58.7248420715332  ,"Grand Bourg", 2));
		centros.add(new Centro(-34.50443470200159,-58.67694854736328  ,"Villa de Mayo", 3));
		centros.add(new Centro(-34.53738908867436,-58.73857498168945  ,"Plaza Cementerio", 4));
		centros.add(new Centro(-34.52981365173898, -58.73276911193544 ,"Hotel La Joya", 7));
		centros.add(new Centro(-34.52805569316042,-58.699092864990234  ,"Escuela Sargento Cabral", 6));
		centros.add(new Centro( -34.52225714792779, -58.7135124206543, "Centro mejor distancia", 5));
		
		Distribucion dist = new Distribucion (clientes,centros);
		return dist;
	}

	private Comparator<Centro> comparadorPorDistancia() {
		return new Comparator<Centro>() {
			@Override
			public int compare(Centro uno, Centro otro) {
				if ( uno.getDistanciaPromedio() < otro.getDistanciaPromedio() )
					return 1;
				else if ( uno.getDistanciaPromedio() == otro.getDistanciaPromedio() )
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
