package Grafica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonModel;
import javax.swing.DebugGraphics;
import javax.swing.event.ChangeListener;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import Logica.FacilityLocation;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import keeptoo.KGradientPanel;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.awt.event.MouseWheelEvent;

public class Ventana {
	private JFrame frmFacilityLocation;
	private KGradientPanel fondo_Gradiente;
	private JPanel panel_cabecera;
	private JPanel panel_centro;
	private JPanel opciones;
	private JPanel panel_explicacion;
	private JPanel panelcombobox;
	private JComboBox<Integer> comboOpciones;
	private JMapViewer jmap;

	private FacilityLocation logica;
	private Coordinate COOR_MAPA;
	private Color colorClientes;
	private Color colorCentros;
	private Color colorSolucion;
	private int xOffset = 0;
	private int yOffset = 0;
	private int zoomMapa = 13 ; // 11, 12, 13
	//"xOffset" y "yOffset" son usados para el movimiento de la ventana
	
	public Ventana() {		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// Si Nimbus no esta disponible se tendria que dar a la Interfaz otro look and feel.
		}
		initialize();
	}
	
	public void setVisible(boolean value) {
		frmFacilityLocation.setVisible(value);
	}

	private void initialize() {
		frmFacilityLocation = new JFrame();
		frmFacilityLocation.setName("frame");
		frmFacilityLocation.setTitle("Facility Location");
		frmFacilityLocation.setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/img/DistribucionGolosa_icono_2.png")));
		frmFacilityLocation.getContentPane().setFocusCycleRoot(true);
		frmFacilityLocation.setUndecorated(true);
		frmFacilityLocation.setResizable(false);
		frmFacilityLocation.getContentPane().setBackground(Color.DARK_GRAY);
		frmFacilityLocation.getContentPane().setLayout(new BorderLayout(0, 0));
		frmFacilityLocation.setBounds(0, 0, 900, 600);
		frmFacilityLocation.setLocationRelativeTo(null); //ubicar la ventana en el centro
		motion_mouseAdapter(frmFacilityLocation);
		
		logica = new FacilityLocation();
		COOR_MAPA = new Coordinate(-34.521, -58.719); // Los Polvorines, San Miguel
		colorClientes = new Color(180, 80, 200);
		colorCentros = new Color(51, 0, 153);
		colorSolucion = new Color(231, 181, 12);
		
		fondoGradiente();
		cabecera();
			panelCentro();
				panelOpciones();
					mensaje_explicacion();
					opcionCantidad();
					porBeneficio();
					porDistancia();
					botos_resultados();
					reset();
				mapa();
		pie();
	
	}

	private void motion_mouseAdapter(JFrame frame) {
		//captura la posicion del mouse
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOffset = e.getX();
				yOffset = e.getY();
			}
		});
		//mueve la ventana a donde se dibuja con el mouse solo si se encuentra en panel_cabecera
		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (panel_cabecera.contains(xOffset, yOffset)) { // Se me paso la calidad con esto
					frmFacilityLocation.setLocation(e.getXOnScreen() - xOffset , e.getYOnScreen() - yOffset);
				}
			}
		});
	}

	private void addMapMarkerWithNames(HashMap<Coordinate, String> elem, Color c) {
		for (Coordinate coord : elem.keySet()) {
			MapMarkerDot marca = new MapMarkerDot(coord);
			marca.setBackColor(c);
			marca.setName(elem.get(coord));
			jmap.addMapMarker(marca);
		}
	}
	
	private void addMapMarker(HashMap<Coordinate, String> elem, Color c) {
		for (Coordinate coord : elem.keySet()) {
			MapMarkerDot marca = new MapMarkerDot(coord);
			marca.setBackColor(c);
			jmap.addMapMarker(marca);
		}
	}
	
	private void removeAllMapMarkers() {
		jmap.removeAllMapMarkers();
	}

	private ActionListener action_RESULTADO() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllMapMarkers();
				addMapMarkerWithNames(logica.centros(), colorCentros);
				addMapMarker(logica.clientes(), colorClientes);
				addMapMarker(logica.solucion(), colorSolucion);
			
			}
		};
	}
	
	private ActionListener action_RESET() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllMapMarkers();
				addMapMarkerWithNames(logica.centros(), colorCentros);
				addMapMarker(logica.clientes(), colorClientes);
			}
		};
	}
	
	private ActionListener action_DISTANCIA() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logica.ResolverPorDistancia((int) comboOpciones.getSelectedItem());
				logica.TraerDatosDeJSON();
			}
		};
	}
	
	private ActionListener action_BENEFICIO() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logica.ResolverPorCategoria( (int)comboOpciones.getSelectedItem());
				logica.TraerDatosDeJSON();
			}
		};
	}
	
	private void fondoGradiente() {
		fondo_Gradiente = new KGradientPanel();
		fondo_Gradiente.kStartColor = new Color(74,74,74);
		fondo_Gradiente.kEndColor = new Color(66,66,66);
		fondo_Gradiente.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frmFacilityLocation.getContentPane().add(fondo_Gradiente, BorderLayout.CENTER);
		fondo_Gradiente.setLayout(new BorderLayout(0, 0));
	}

	private void cabecera() {
		panel_cabecera = new JPanel();
		panel_cabecera.setBackground(new Color(20, 20, 20));
		fondo_Gradiente.add(panel_cabecera, BorderLayout.NORTH);
		panel_cabecera.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_titulo = new JLabel("Facility Location");
		lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_titulo.setFont(new Font("Freehand521 BT", Font.PLAIN, 20));
		lbl_titulo.setForeground(new Color(255, 255, 255));
		panel_cabecera.add(lbl_titulo);
		
		JButton btn_salir = new JButton("");
		btn_salir.setHorizontalAlignment(SwingConstants.RIGHT);
		btn_salir.setHorizontalTextPosition(SwingConstants.RIGHT);
		btn_salir.setContentAreaFilled(false);
		btn_salir.setIcon(new ImageIcon(Ventana.class.getResource("/img/salir_normal.png")));
		btn_salir.setPressedIcon(new ImageIcon(Ventana.class.getResource("/img/salir_pressed.png")));
		btn_salir.setRolloverIcon(new ImageIcon(Ventana.class.getResource("/img/salir_rollover.png")));

		btn_salir.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		btn_salir.setDefaultCapable(false);
		btn_salir.setFocusTraversalKeysEnabled(false);
		btn_salir.setFocusable(false);
		btn_salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_cabecera.add(btn_salir, BorderLayout.EAST);
	}
	
	private void panelCentro() {
		panel_centro = new JPanel();
		panel_centro.setOpaque(false);
		fondo_Gradiente.add(panel_centro, BorderLayout.CENTER);
		GridBagLayout gbl_panel_centro = new GridBagLayout();
		gbl_panel_centro.columnWidths = new int[] {308, 584};
		gbl_panel_centro.rowHeights = new int[] {501};
		gbl_panel_centro.columnWeights = new double[]{1.0, 0.0};
		gbl_panel_centro.rowWeights = new double[]{1.0};
		panel_centro.setLayout(gbl_panel_centro);
	}

	private void panelOpciones() {
		opciones = new JPanel();
		opciones.setOpaque(false);
		opciones.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(105, 105, 105), new Color(0, 0, 0)), "Opciones", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		opciones.setLayout(null);
		GridBagConstraints gbc_opciones = new GridBagConstraints();
		gbc_opciones.fill = GridBagConstraints.BOTH;
		gbc_opciones.insets = new Insets(0, 0, 0, 5);
		gbc_opciones.gridx = 0;
		gbc_opciones.gridy = 0;
		panel_centro.add(opciones, gbc_opciones);
	}

	private void mensaje_explicacion() {
		panel_explicacion = new JPanel();
		panel_explicacion.setOpaque(false);
		panel_explicacion.setBounds(5, 30, 299, 214);
		opciones.add(panel_explicacion);
		panel_explicacion.setLayout(null);
		
		JLabel ilustrativo_clientes = new JLabel("CLIENTES");
		ilustrativo_clientes.setForeground(colorClientes);
		ilustrativo_clientes.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		ilustrativo_clientes.setBounds(95, 46, 67, 16);
		panel_explicacion.add(ilustrativo_clientes);	
		
		JLabel ilustrativo_locales = new JLabel(" CENTROS");
		ilustrativo_locales.setForeground(colorCentros);
		ilustrativo_locales.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		ilustrativo_locales.setBounds(120, 102, 67, 16);
		panel_explicacion.add(ilustrativo_locales);
		
		JLabel ilustrativo_resultados = new JLabel(" resultados");
		ilustrativo_resultados.setForeground(colorSolucion);
		ilustrativo_resultados.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		ilustrativo_resultados.setBounds(25, 138, 67, 16);
		panel_explicacion.add(ilustrativo_resultados);
		
		crearLabel("Implementacion de un algoritmo goloso para una"	    ,   6);		
		crearLabel("variante del problema de \"facility location\"."    ,  24);
		crearLabel("Tenemos unos                que debemos atender, "  ,  46);
		//                          CLIENTES
		crearLabel("tambien tenemos CENTROS de distribucion para"       ,  64);
		crearLabel("los clientes."                                      ,  82);
		crearLabel("Especifique cuantos                DE DISTRIBUCION ", 102);
		//                                 CENTROS
		crearLabel("quiere abrir y segun que criterio. "                , 120);
		crearLabel("Los                 se mostraran en el mapa!!"           , 138);
		//                resultados
	}

	private void crearLabel(String msj, int y) {
		JLabel linea = new JLabel(msj);
		linea.setForeground(new Color(255, 255, 255));
		linea.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		linea.setBounds(6, y, 287, 16);
		panel_explicacion.add(linea);
	}

	private void opcionCantidad() {
		panelcombobox = new JPanel();
		panelcombobox.setOpaque(false);
		panelcombobox.setFocusable(false);
		panelcombobox.setBounds(5, 284, 299, 36);
		opciones.add(panelcombobox);
		
		JLabel Label = new JLabel("Seleccione cuantos locales quiere abrir: ");
		Label.setForeground(new Color(255,255,255));
		Label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		panelcombobox.add(Label);
		
		comboOpciones = new JComboBox<Integer>();
		comboOpciones.setBackground(new Color(15, 15, 15));
		comboOpciones.addItem(4);
		comboOpciones.addItem(3);
		comboOpciones.addItem(2);
		comboOpciones.addItem(1);
		panelcombobox.add(comboOpciones);
	}
	
	private void porBeneficio() {
		JButton btn_xBeneficio = new JButton("SOLUCION POR CATEGORIA");
		btn_xBeneficio.setForeground(Color.WHITE);
		btn_xBeneficio.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		btn_xBeneficio.setFocusable(false);
		btn_xBeneficio.setBackground(new Color(75, 0, 130));
		btn_xBeneficio.setBounds(5, 320, 299, 50);
		btn_xBeneficio.getModel().addChangeListener(new ChangeListener() { 
		    @Override 
		    public void stateChanged(ChangeEvent e) { 
		    	ButtonModel model = (ButtonModel) e.getSource(); 
		    	if (model.isRollover()) { 
		    		btn_xBeneficio.setBackground(new Color(75, 0, 130).brighter()); 
		    	} else if (model.isPressed()) { 	
		    	} else { 
		    		btn_xBeneficio.setBackground(new Color(75, 0, 130)); 
		    	} 
		    } 
		});
		btn_xBeneficio.addActionListener(action_BENEFICIO());
		opciones.add(btn_xBeneficio);
	}

	private void porDistancia() {
		JButton btn_xDistancia = new JButton("SOLUCION POR DISTANCIA");
		btn_xDistancia.setBounds(5, 370, 299, 50);
		btn_xDistancia.setBackground(new Color(75, 0, 130));
		btn_xDistancia.setFocusable(false);
		btn_xDistancia.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		btn_xDistancia.setForeground(new Color(255, 255, 255));
		btn_xDistancia.getModel().addChangeListener(new ChangeListener() { 
		    @Override 
		    public void stateChanged(ChangeEvent e) { 
		    	ButtonModel model = (ButtonModel) e.getSource(); 
		    	if (model.isRollover()) { 
		    		btn_xDistancia.setBackground(new Color(75, 0, 130).brighter()); 
		    	} else if (model.isPressed()) { 
		    	} else { 
		    		btn_xDistancia.setBackground(new Color(75, 0, 130)); 
		    	} 
		    } 
		});
		btn_xDistancia.addActionListener(action_DISTANCIA());
		opciones.add(btn_xDistancia);
	}

	private void botos_resultados() {
		JButton btn_mostrar_resultado = new JButton("MOSTRAR RESULTADOS");
		btn_mostrar_resultado.setForeground(Color.WHITE);
		btn_mostrar_resultado.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		btn_mostrar_resultado.setFocusable(false);
		btn_mostrar_resultado.setBackground(new Color(0, 0, 51));
		btn_mostrar_resultado.setBounds(5, 440, 299, 50);
		btn_mostrar_resultado.getModel().addChangeListener(new ChangeListener() { 
		    @Override 
		    public void stateChanged(ChangeEvent e) { 
		    	ButtonModel model = (ButtonModel) e.getSource(); 
		    	if (model.isRollover()) { 
		    		btn_mostrar_resultado.setBackground(new Color(0, 0, 51).brighter()); 
		    	} else if (model.isPressed()) {  
		    	} else { 
		    		btn_mostrar_resultado.setBackground(new Color(0, 0, 51));
		    	} 
		    } 
		});
		btn_mostrar_resultado.addActionListener(action_RESULTADO());
		opciones.add(btn_mostrar_resultado);
	}

	
	private void reset() {
		JButton btn_reset = new JButton("RESET");
		btn_reset.setBounds(5, 490, 299, 50);
		btn_reset.setBackground(new Color(150, 0, 30));
		btn_reset.setFocusable(false);
		btn_reset.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		btn_reset.setForeground(new Color(255, 255, 255));
		btn_reset.getModel().addChangeListener(new ChangeListener() { 
		    @Override 
		    public void stateChanged(ChangeEvent e) { 
		    	ButtonModel model = (ButtonModel) e.getSource(); 
		    	if (model.isRollover()) { 
		    		btn_reset.setBackground(new Color(150, 0, 30).brighter()); 
		    	} else if (model.isPressed()) {  
		    	} else { 
		    		btn_reset.setBackground(new Color(150, 0, 30));
		    	} 
		    } 
		});
		btn_reset.addActionListener(action_RESET());
		opciones.add(btn_reset);
	}

	private void mapa() {
		JPanel mapa = new JPanel();
		mapa.setOpaque(false);
		mapa.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(105, 105, 105), new Color(0, 0, 0)), "", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_mapa = new GridBagConstraints();
		gbc_mapa.fill = GridBagConstraints.BOTH;
		gbc_mapa.gridx = 1;
		gbc_mapa.gridy = 0;
		mapa.setLayout(new CardLayout(1, 0));	
		panel_centro.add(mapa, gbc_mapa);	
		
		Coordinate coordenada = COOR_MAPA;
		
		jmap = new JMapViewer();
		jmap.setBorder(new LineBorder(new Color(255, 255, 255)));
		jmap.setZoomControlsVisible(false);
		jmap.setDisplayPosition(coordenada, zoomMapa );
		jmap.setVisible(true);
		mapa.add(jmap);
		
		//para bloquear el zoom de la rueda
		jmap.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if ( (e.getWheelRotation() > 0 || e.getWheelRotation() < 0 ) && jmap.getZoom() != zoomMapa) {
					jmap.setDisplayPosition(coordenada, zoomMapa);
				}
			}
		});
		jmap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jmap.setDisplayPosition(coordenada, zoomMapa);
			}
		});
		
		addMapMarkerWithNames(logica.centros(), colorCentros);
		addMapMarker(logica.clientes(), colorClientes);
	}
	
	private void pie() {
		JPanel panel_pie = new JPanel();
		panel_pie.setBackground(new Color(20, 20, 20));
				
		fondo_Gradiente.add(panel_pie, BorderLayout.SOUTH);
	}
	
}
