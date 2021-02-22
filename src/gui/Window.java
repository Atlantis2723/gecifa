	package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import logic.Cliente;
import logic.Concepto;
import logic.NumFact;
import logic.Path;

public class Window extends JFrame implements ActionListener {

	private JPanel panelGeneral;
	private JButton btnClientes, btnConceptos, btnFacturaManual, btnConfiguracin, btnModelos, conceptosComunes;
	private JTabbedPane tabbedPane;
	private JPanel panelClientes, panelConceptos, panelFacturaManual, panelMasivos, panelConfiguracin;
	private static DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		/**
		 * Set L&F
		 */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		/**
		 * Creación panel general
		 */
		setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/resources/logo.png")));
		setTitle("GeCiFa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		panelGeneral = new JPanel();
		panelGeneral.setBackground(Color.WHITE);
		panelGeneral.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelGeneral.setLayout(new BorderLayout(5, 0));
		setContentPane(panelGeneral);
		crearDirectorios();
		loadLateralToolBar();
		loadTabbedPane();
		cargarPanelClientes();
		tabbedPane.setSelectedComponent(panelClientes);

	}

	public void loadTabbedPane() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelGeneral.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
	}

	public void loadLateralToolBar() {
		JPanel lateralToolBar = new JPanel();
		lateralToolBar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		lateralToolBar.setBackground(Color.WHITE);
		panelGeneral.add(lateralToolBar, BorderLayout.WEST);
		lateralToolBar.setLayout(new BoxLayout(lateralToolBar, BoxLayout.Y_AXIS));

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		lateralToolBar.add(verticalStrut_5);

		btnClientes = new JButton("");
		btnClientes.setIcon(new ImageIcon(Window.class.getResource("/resources/clientes.png")));
		btnClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClientes.addActionListener(this);
		lateralToolBar.add(btnClientes);

		JLabel lblClientes = new JLabel("CLIENTES");
		lblClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
		lateralToolBar.add(lblClientes);

		Component verticalStrut = Box.createVerticalStrut(20);
		lateralToolBar.add(verticalStrut);

		btnConceptos = new JButton("");
		btnConceptos.setIcon(new ImageIcon(Window.class.getResource("/resources/conceptos.png")));
		btnConceptos.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnConceptos.addActionListener(this);
		lateralToolBar.add(btnConceptos);

		JLabel lblConceptos = new JLabel("CONCEPTOS");
		lblConceptos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConceptos.setAlignmentX(Component.CENTER_ALIGNMENT);
		lateralToolBar.add(lblConceptos);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		lateralToolBar.add(verticalStrut_2);

		btnFacturaManual = new JButton("");
		btnFacturaManual.setIcon(new ImageIcon(Window.class.getResource("/resources/facturaManual.png")));
		btnFacturaManual.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFacturaManual.addActionListener(this);
		lateralToolBar.add(btnFacturaManual);

		JLabel lblFacturaManual = new JLabel("FACTURAS");
		lblFacturaManual.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFacturaManual.setAlignmentX(Component.CENTER_ALIGNMENT);
		lateralToolBar.add(lblFacturaManual);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		lateralToolBar.add(verticalStrut_3);

		btnModelos = new JButton("");
		btnModelos.setIcon(new ImageIcon(Window.class.getResource("/resources/modelos.png")));
		btnModelos.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnModelos.addActionListener(this);
		lateralToolBar.add(btnModelos);

		JLabel btnModelos = new JLabel("MODELOS");
		btnModelos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnModelos.setAlignmentX(Component.CENTER_ALIGNMENT);
		lateralToolBar.add(btnModelos);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		lateralToolBar.add(verticalStrut_4);

		btnConfiguracin = new JButton("");
		btnConfiguracin.setIcon(new ImageIcon(Window.class.getResource("/resources/settings.png")));
		btnConfiguracin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnConfiguracin.addActionListener(this);
		lateralToolBar.add(btnConfiguracin);

		JLabel lblConfiguracin = new JLabel("CONFIGURACIÓN");
		lblConfiguracin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConfiguracin.setAlignmentX(Component.CENTER_ALIGNMENT);
		lateralToolBar.add(lblConfiguracin);

	}

	public void cargarPanelClientes() {
		/**
		 * TABLA
		 */
		// Estilo
		JTable tablaClientes = new JTable();
		setDTM("clientes", tablaClientes);
		tablaClientes.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int col = table.columnAtPoint(point);
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && col == 8) {
					JOptionPane.showMessageDialog(new JFrame(), table.getValueAt(row, col), "Comentarios",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Cargamos los datos

		Gson gson = new Gson();
		File dir = new File(Path.ROOT + "/Clientes/");

		for (File f : dir.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				Cliente c = gson.fromJson(jsonobj, Cliente.class);
				String[] clientes = new String[9];
				clientes[0] = c.getNombre();
				clientes[1] = c.getApellidos();
				clientes[2] = c.getNif();
				clientes[3] = c.getDireccion();
				clientes[4] = c.getCiudad();
				clientes[5] = c.getCp();
				clientes[6] = "NO";
				if (c.isMensual())
					clientes[6] = "SÍ";
				clientes[7] = c.getCorreo();
				clientes[8] = c.getComentarios();
				((DefaultTableModel) tablaClientes.getModel()).addRow(clientes);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * BARRA DE HERRAMIENTAS
		 */
		JPanel barraClientes = new JPanel();
		barraClientes.setLayout(new FlowLayout(FlowLayout.LEFT));
		barraClientes.setBackground(Color.WHITE);
		barraClientes.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		// AddCliente
		JButton addClient = new JButton("Añadir cliente",
				new ImageIcon(Window.class.getResource("/resources/newClient.png")));
		addClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PopUpAddCliente popadd = new PopUpAddCliente("", "", "", "", "", "", false, "", "", tablaClientes);
				popadd.setVisible(true);
			}
		});

		// EditCliente
		JButton editClient = new JButton("<html>Modificar<br/>cliente seleccionado</html>",
				new ImageIcon(Window.class.getResource("/resources/editClient.png")));
		editClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Carga los datos para editarlos
				int row = tablaClientes.getSelectedRow();
				boolean mensual = false;
				if (String.valueOf(tablaClientes.getValueAt(row, 6)).equals("SÍ"))
					mensual = true;
				PopUpAddCliente popedit = new PopUpAddCliente(String.valueOf(tablaClientes.getValueAt(row, 0)),
						String.valueOf(tablaClientes.getValueAt(row, 1)),
						String.valueOf(tablaClientes.getValueAt(row, 2)),
						String.valueOf(tablaClientes.getValueAt(row, 3)),
						String.valueOf(tablaClientes.getValueAt(row, 4)),
						String.valueOf(tablaClientes.getValueAt(row, 5)), mensual,
						String.valueOf(tablaClientes.getValueAt(row, 7)),
						String.valueOf(tablaClientes.getValueAt(row, 8)), tablaClientes);
				popedit.setVisible(true);

				// Elimina la anterior entrada del cliente

				try {
					Cliente.eliminarCliente(String.valueOf(tablaClientes.getValueAt(row, 2)), tablaClientes);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// DelCliente
		JButton delClient = new JButton("<html>Eliminar<br/>cliente seleccionado</html>",
				new ImageIcon(Window.class.getResource("/resources/delClient.png")));
		delClient.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int n = JOptionPane.showConfirmDialog(new JFrame(),
						"¿Estás seguro de que quieres eliminar a este cliente?", "Eliminar", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					try {
						Cliente.eliminarCliente(
								String.valueOf(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 2)),
								tablaClientes);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					PopUpAddConceptoComun p = new PopUpAddConceptoComun();
					p.guardarDatos(false);
				}

			}
		});

		// ShowCliente
		JButton showClientBills = new JButton("Mostrar facturas",
				new ImageIcon(Window.class.getResource("/resources/factCliente.png")));
		showClientBills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openDir(Path.ROOT + "/Facturas/");
			}
		});

		barraClientes.add(addClient);
		barraClientes.add(editClient);
		barraClientes.add(delClient);
		barraClientes.add(showClientBills);

		panelClientes = new JPanel();
		panelClientes.setLayout(new BorderLayout(0, 3));
		panelClientes.add(barraClientes, BorderLayout.NORTH);
		panelClientes.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
		tabbedPane.addTab("Clientes", null, panelClientes, null);

	}

	public void cargarPanelConceptos() {

		JTable tablaConceptos = new JTable();
		setDTM("conceptos", tablaConceptos);

		// Cargamos los datos

		Gson gson = new Gson();
		File dir = new File(Path.ROOT + "/Conceptos");

		for (File f : dir.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				Concepto c = gson.fromJson(jsonobj, Concepto.class);
				Object[] conceptos = new Object[4];
				conceptos[0] = c.getCodigo();
				conceptos[1] = c.getContenido();
				conceptos[2] = df.format(c.getPrecio());
				conceptos[3] = "NO";
				if (c.isSuplido())
					conceptos[3] = "SÍ";
				((DefaultTableModel) tablaConceptos.getModel()).addRow(conceptos);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Barra Conceptos y Suplidos
		 */

		JPanel barraConceptos = new JPanel();
		barraConceptos.setLayout(new FlowLayout(FlowLayout.LEFT));
		barraConceptos.setBackground(Color.WHITE);
		barraConceptos.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		JButton addConcepto = new JButton("Añadir concepto",
				new ImageIcon(Window.class.getResource("/resources/newConcepto.png")));
		addConcepto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PopUpAddConcepto paddconcepto = new PopUpAddConcepto("", "", "", false, tablaConceptos);
				paddconcepto.setVisible(true);
			}
		});

		JButton editConcepto = new JButton("Modificar concepto",
				new ImageIcon(Window.class.getResource("/resources/editConcepto.png")));
		editConcepto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaConceptos.getSelectedRow();
				boolean suplido = false;
				if (String.valueOf(String.valueOf(tablaConceptos.getValueAt(row, 3))).equals("SÍ"))
					suplido = true;

				PopUpAddConcepto popedit = new PopUpAddConcepto(String.valueOf(tablaConceptos.getValueAt(row, 0)),
						String.valueOf(tablaConceptos.getValueAt(row, 1)),
						String.valueOf(tablaConceptos.getValueAt(row, 2)), suplido, tablaConceptos);
				popedit.setVisible(true);

				// Elimina la anterior entrada del cliente

				Concepto.delConcepto(String.valueOf(tablaConceptos.getValueAt(row, 0)), tablaConceptos);
			}
		});

		JButton delConcepto = new JButton("Eliminar concepto",
				new ImageIcon(Window.class.getResource("/resources/delConcepto.png")));
		delConcepto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Concepto.delConcepto(String.valueOf(tablaConceptos.getValueAt(tablaConceptos.getSelectedRow(), 0)),
						tablaConceptos);
			}
		});

		// Conceptos comunes
		conceptosComunes = new JButton("Conceptos comunes",
				new ImageIcon(Window.class.getResource("/resources/conceptos.png")));
		conceptosComunes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PopUpAddConceptoComun p = new PopUpAddConceptoComun();
				p.setVisible(true);
			}
		});

		barraConceptos.add(addConcepto);
		barraConceptos.add(editConcepto);
		barraConceptos.add(delConcepto);
		barraConceptos.add(conceptosComunes);

		panelConceptos = new JPanel();
		panelConceptos.setLayout(new BorderLayout(0, 3));
		panelConceptos.add(barraConceptos, BorderLayout.NORTH);
		panelConceptos.add(new JScrollPane(tablaConceptos), BorderLayout.CENTER);
		tabbedPane.addTab("Conceptos", null, panelConceptos, null);

	}

	public void cargarPanelFacturaManual() {
		panelFacturaManual = new JPanel();
		panelFacturaManual.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Factura Manual", null, panelFacturaManual, null);
	}

	public void cargarPanelMasivas() {
		panelMasivos = new JPanel();
		panelMasivos.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Masivas", null, panelMasivos, null);
	}

	public void cargarPanelConfig() {
		panelConfiguracin = new JPanel();
		panelConfiguracin.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Configuración", null, panelConfiguracin, null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnClientes) {
			if (tabbedPane.indexOfTab("Clientes") == -1) {
				cargarPanelClientes();
				tabbedPane.setSelectedComponent(panelClientes);
			} else
				tabbedPane.setSelectedComponent(panelClientes);
		}
		if (e.getSource() == btnConceptos) {
			if (tabbedPane.indexOfTab("Conceptos") == -1) {
				cargarPanelConceptos();
				tabbedPane.setSelectedComponent(panelConceptos);
			} else
				tabbedPane.setSelectedComponent(panelConceptos);
		}

		if (e.getSource() == btnFacturaManual) {
			if (tabbedPane.indexOfTab("Conceptos") == -1) {
				cargarPanelConceptos();
				tabbedPane.setSelectedComponent(panelConceptos);
			} else
				tabbedPane.setSelectedComponent(panelConceptos);
			if (tabbedPane.indexOfTab("Clientes") == -1) {
				cargarPanelClientes();
				tabbedPane.setSelectedComponent(panelClientes);
			} else
				tabbedPane.setSelectedComponent(panelClientes);
			GeneradorFacturas tg = new GeneradorFacturas();
			tg.setVisible(true);
		}
		if (e.getSource() == btnModelos) {
			openDir(Path.ROOT + "/Modelos");
		}
		if (e.getSource() == btnConfiguracin) {
			try {
				NumFact.crearArchivo();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Cfg cfg = null;
			try {
				cfg = new Cfg();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			cfg.setVisible(true);
		}

	}

	private static void openDir(String path) {
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setDTM(String id, JTable tabla) {
		DefaultTableModel dtm = null;
		if (id.equals("clientes")) {
			dtm = new DefaultTableModel(new String[] { "Nombre", "Apellidos", "NIF", "Dirección", "Ciudad", "CP",
					"Mensual", "Correo", "Comentarios" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		} else if (id.equals("conceptos")) {
			dtm = new DefaultTableModel(new String[] { "Código", "Concepto", "Precio", "Suplido" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

				@Override
				public Class<?> getColumnClass(int column) {
					if (column == 0 || column == 2)
						return Integer.class;
					else
						return String.class;
				}

			};
		}

		tabla.setModel(dtm);
		tabla.setAutoCreateRowSorter(true);
		tabla.getTableHeader().setFont(new Font("Tahome", Font.BOLD, 23));
		tabla.getTableHeader().setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		tabla.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tabla.setFont(new Font("Tahome", Font.PLAIN, 20));
		tabla.setRowHeight(tabla.getFont().getSize() + 10);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tabla.getColumnCount(); i++)
			tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

	}

	private void crearDirectorios() {
		new File(Path.ROOT + "/ConceptosComunes").mkdirs();
		new File(Path.ROOT + "/Clientes").mkdirs();
		new File(Path.ROOT + "/Conceptos").mkdirs();
		new File(Path.ROOT + "/Facturas").mkdirs();
		new File(Path.ROOT + "/Modelos").mkdirs();
	}

}
