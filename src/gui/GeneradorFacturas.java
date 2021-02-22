package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import logic.Cliente;
import logic.Concepto;
import logic.ConceptoComun;
import logic.Factura;
import logic.NumFact;
import logic.Path;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;

public class GeneradorFacturas extends JFrame {

	private JPanel contentPane;
	private ArrayList<Factura> facturas = new ArrayList<Factura>();
	private JTextField textFecha;
	private JTextField textComent;
	private JTextField textProvFondos;
	private ArrayList<ConceptoComun> conceptosComunes = new ArrayList<ConceptoComun>();
	private static DecimalFormat df = new DecimalFormat("#.00");
	private static final String mPagosPATH = Path.ROOT + "/mPagos.json";

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
					GeneradorFacturas frame = new GeneradorFacturas();
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
	public GeneradorFacturas() {
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GeneradorFacturas.class.getResource("/resources/logo.png")));
		setTitle("Generador de facturas");
		setBounds(100, 100, 822, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Modelos de tabla
		DefaultTableModel dtm_Clientes = new DefaultTableModel(new String[] { " ", "NOMBRE", "MENSUAL", "NÚMERO",
				"FECHA", "FORMA DE PAGO", "COMENTARIOS", "PROV. FONDOS", "Dir", "Ciu", "cp", "NIF" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1 || column == 2)
					return false;

				return true;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0)
					return Boolean.class;
				if (column == 7)
					return Double.class;
				return String.class;
			}

		};
		DefaultTableModel dtm_Conceptos = new DefaultTableModel(
				new String[] { "CÓDIGO", "CONCEPTOS", "PRECIO", "CANTIDAD" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0 || column == 3)
					return Integer.class;
				return String.class;
			}

		};
		DefaultTableModel dtm_Suplidos = new DefaultTableModel(
				new String[] { "CÓDIGO", "SUPLIDOS", "PRECIO", "CANTIDAD" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0 || column == 3)
					return Integer.class;
				return String.class;
			}

		};
		DefaultTableModel dtm_ConceptosComunes = new DefaultTableModel(
				new String[] { "CÓDIGO", "CONCEPTOS COMUNES", "DATOS", "CANTIDAD" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 2)
					return false;
				return true;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0 || column == 3)
					return Integer.class;
				return String.class;
			}

		};

		JTable tabla_Clientes = new JTable(dtm_Clientes);
		JTable tabla_Suplidos = new JTable(dtm_Suplidos);
		JTable tabla_Conceptos = new JTable(dtm_Conceptos);
		JTable tabla_ConceptosComunes = new JTable(dtm_ConceptosComunes);
		tabla_ConceptosComunes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					JOptionPane.showMessageDialog(new JFrame(), conceptosComunes.get(row),
							"" + tabla_ConceptosComunes.getValueAt(row, 1), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Cargamos conceptos comunes
		Gson gson3 = new Gson();
		File di = new File(Path.ROOT + "/ConceptosComunes/");
		for (File f : di.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				ConceptoComun c = gson3.fromJson(jsonobj, ConceptoComun.class);
				conceptosComunes.add(c);
				((DefaultTableModel) tabla_ConceptosComunes.getModel())
						.addRow(new Object[] { c.getCodigo(), c.getContenido(), "Doble click", 1 });
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Cargamos conceptos y suplidos
		Gson gson = new Gson();
		File dir = new File(Path.ROOT + "/Conceptos");

		for (File f : dir.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(new FileReader(f));
				Concepto c = gson.fromJson(jsonobj, Concepto.class);
				Object[] concepts = new Object[4];
				concepts[0] = Integer.valueOf(c.getCodigo());
				concepts[1] = c.getContenido();
				concepts[2] = df.format(c.getPrecio());
				concepts[3] = 1;
				if (!c.isSuplido())
					((DefaultTableModel) tabla_Conceptos.getModel()).addRow(concepts);
				else {
					((DefaultTableModel) tabla_Suplidos.getModel()).addRow(concepts);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Object[] temporales = { 0, " ", "0.00", 1 };
		for (int i = 0; i < 5; i++) {
			((DefaultTableModel) tabla_Conceptos.getModel()).addRow(temporales);
			((DefaultTableModel) tabla_Suplidos.getModel()).addRow(temporales);
		}

		// Cargamos los clientes

		Gson gson2 = new Gson();
		File dire = new File(Path.ROOT + "/Clientes/");

		for (File f : dire.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				Cliente c = gson2.fromJson(jsonobj, Cliente.class);
				Object[] clients = new Object[12];
				clients[0] = false;
				clients[1] = c.getNombre() + " " + c.getApellidos();
				clients[2] = "NO";
				if (c.isMensual())
					clients[2] = "SÍ";
				clients[3] = "";
				clients[4] = "";
				clients[5] = "";
				clients[6] = "";
				clients[7] = 0;
				clients[8] = c.getDireccion();
				clients[9] = c.getCiudad();
				clients[10] = c.getCp();
				clients[11] = c.getNif();
				((DefaultTableModel) tabla_Clientes.getModel()).addRow(clients);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		tabla_Clientes.getColumnModel().getColumn(8).setMinWidth(0);
		tabla_Clientes.getColumnModel().getColumn(8).setMaxWidth(0);
		tabla_Clientes.getColumnModel().getColumn(9).setMinWidth(0);
		tabla_Clientes.getColumnModel().getColumn(9).setMaxWidth(0);
		tabla_Clientes.getColumnModel().getColumn(10).setMinWidth(0);
		tabla_Clientes.getColumnModel().getColumn(10).setMaxWidth(0);
		tabla_Clientes.getColumnModel().getColumn(11).setMaxWidth(0);
		tabla_Clientes.getColumnModel().getColumn(11).setMaxWidth(0);
		tabla_Clientes.getColumnModel().getColumn(0).setMinWidth(44);
		tabla_Clientes.getColumnModel().getColumn(0).setMaxWidth(44);

		JPanel panel_Clientes = new JPanel();
		panel_Clientes.setLayout(new BorderLayout(3, 3));
		JPanel panel_Conceptos = new JPanel();

		estiloTablas(tabla_Clientes);
		tabla_Clientes.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		JComboBox<String> combo = new JComboBox<String>();
		combo.setEditable(false);
		for (String s : getMetodosPago())
			combo.addItem(s);
		tabla_Clientes.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(combo));
		JComboBox<String> combo2 = new JComboBox<String>();
		combo2.setEditable(true);
		combo2.addItem("Automático /TT");
		combo2.addItem("Automático /XX");
		tabla_Clientes.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(combo2));
		panel_Clientes.add(new JScrollPane(tabla_Clientes));
		panel_Conceptos.setLayout(new BorderLayout(3, 3));

		contentPane.add(panel_Clientes, BorderLayout.CENTER);

		JPanel facturaMaestra = new JPanel();
		panel_Clientes.add(facturaMaestra, BorderLayout.NORTH);

		JLabel lblNum = new JLabel("NUM");
		lblNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(lblNum);

		JComboBox<String> factMaestraNum = new JComboBox<String>();
		factMaestraNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		factMaestraNum.setEditable(true);
		factMaestraNum.addItem("Automático /TT");
		factMaestraNum.addItem("Automático /XX");
		facturaMaestra.add(factMaestraNum);

		JLabel lblFecha = new JLabel("FECHA");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(lblFecha);

		textFecha = new JTextField();
		textFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(textFecha);
		textFecha.setColumns(10);

		JLabel lblFpago = new JLabel("F.PAGO");
		lblFpago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(lblFpago);

		JComboBox<String> factMaestraFPago = new JComboBox<String>();
		factMaestraFPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		for (String s : getMetodosPago())
			factMaestraFPago.addItem(s);
		facturaMaestra.add(factMaestraFPago);

		JLabel lblComentarios = new JLabel("COMENTARIOS");
		lblComentarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(lblComentarios);

		textComent = new JTextField();
		textComent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(textComent);
		textComent.setColumns(10);

		JLabel lblProvFondos = new JLabel("PROV. FONDOS");
		lblProvFondos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(lblProvFondos);

		textProvFondos = new JTextField();
		textProvFondos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		facturaMaestra.add(textProvFondos);
		textProvFondos.setColumns(10);

		JButton btnAplicar = new JButton("Aplicar a seleccionadas");
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i : tabla_Clientes.getSelectedRows()) {
					tabla_Clientes.setValueAt(true, i, 0);
					tabla_Clientes.setValueAt(factMaestraNum.getSelectedItem(), i, 3);
					tabla_Clientes.setValueAt(textFecha.getText(), i, 4);
					tabla_Clientes.setValueAt(factMaestraFPago.getSelectedItem(), i, 5);
					tabla_Clientes.setValueAt(textComent.getText(), i, 6);
					tabla_Clientes.setValueAt(
							(textProvFondos.getText().equals("")) ? 0 : Double.valueOf(textProvFondos.getText()), i, 7);
				}
			}
		});
		facturaMaestra.add(btnAplicar);
		contentPane.add(panel_Conceptos, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panel_Conceptos.add(panel, BorderLayout.SOUTH);

		JButton btnAplicarConceptos = new JButton("GUARDAR factura");
		btnAplicarConceptos.setPreferredSize(new Dimension(200, 75));
		panel.add(btnAplicarConceptos);

		JButton btnGenerarFacturas = new JButton("GENERAR facturas");
		btnGenerarFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Factura f : facturas) {
					try {
						f.genFactura();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(new JFrame(), "Facturas generadas correctamente.");
			}
		});
		btnGenerarFacturas.setPreferredSize(new Dimension(200, 75));

		panel.add(btnGenerarFacturas);

		estiloTablas(tabla_Suplidos);
		JScrollPane scrollPane = new JScrollPane(tabla_Suplidos);

		estiloTablas(tabla_Conceptos);
		JScrollPane scrollPane_1 = new JScrollPane(tabla_Conceptos);

		estiloTablas(tabla_ConceptosComunes);
		JScrollPane scrollPane_2 = new JScrollPane(tabla_ConceptosComunes);

		JPanel panel2 = new JPanel(new GridLayout(0, 3));
		panel2.add(scrollPane);
		panel2.add(scrollPane_1);
		panel2.add(scrollPane_2);
		panel_Conceptos.add(panel2, BorderLayout.CENTER);

		tabla_ConceptosComunes.getColumnModel().getColumn(0).setMinWidth(85);
		tabla_ConceptosComunes.getColumnModel().getColumn(0).setMaxWidth(90);
		tabla_ConceptosComunes.getColumnModel().getColumn(3).setMinWidth(96);
		tabla_ConceptosComunes.getColumnModel().getColumn(3).setMaxWidth(96);
		tabla_ConceptosComunes.getColumnModel().getColumn(2).setMinWidth(80);
		tabla_ConceptosComunes.getColumnModel().getColumn(2).setMaxWidth(80);
		tabla_Conceptos.getColumnModel().getColumn(0).setMinWidth(85);
		tabla_Conceptos.getColumnModel().getColumn(0).setMaxWidth(90);
		tabla_Conceptos.getColumnModel().getColumn(3).setMinWidth(96);
		tabla_Conceptos.getColumnModel().getColumn(3).setMaxWidth(96);
		tabla_Conceptos.getColumnModel().getColumn(2).setMinWidth(80);
		tabla_Conceptos.getColumnModel().getColumn(2).setMaxWidth(80);
		tabla_Suplidos.getColumnModel().getColumn(0).setMinWidth(85);
		tabla_Suplidos.getColumnModel().getColumn(0).setMaxWidth(90);
		tabla_Suplidos.getColumnModel().getColumn(3).setMinWidth(96);
		tabla_Suplidos.getColumnModel().getColumn(3).setMaxWidth(96);
		tabla_Suplidos.getColumnModel().getColumn(2).setMinWidth(80);
		tabla_Suplidos.getColumnModel().getColumn(2).setMaxWidth(80);
		btnAplicarConceptos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				ArrayList<Concepto> conceptosSeleccionados = new ArrayList<Concepto>();
				ArrayList<Concepto> suplidosSeleccionados = new ArrayList<Concepto>();
				ArrayList<Integer> unidadesConceptos = new ArrayList<Integer>();
				ArrayList<Integer> unidadesSuplidos = new ArrayList<Integer>();

				for (int i : tabla_Conceptos.getSelectedRows()) {
					conceptosSeleccionados.add(new Concepto((int) tabla_Conceptos.getValueAt(i, 0),
							tabla_Conceptos.getValueAt(i, 1) + "",
							Double.parseDouble((tabla_Conceptos.getValueAt(i, 2) + "").replace(",", ".")), false));
					unidadesConceptos.add((int) tabla_Conceptos.getValueAt(i, 3));
				}

				for (int i : tabla_Suplidos.getSelectedRows()) {
					suplidosSeleccionados.add(new Concepto((int) tabla_Suplidos.getValueAt(i, 0),
							tabla_Suplidos.getValueAt(i, 1) + "",
							Double.parseDouble((tabla_Suplidos.getValueAt(i, 2) + "").replace(",", ".")), false));
					unidadesSuplidos.add((int) tabla_Suplidos.getValueAt(i, 3));
				}

				for (int i : tabla_Clientes.getSelectedRows()) {
					ArrayList<Concepto> conceptosComunesSeleccionados = new ArrayList<Concepto>();
					ArrayList<Integer> unidadesConceptosComunesSeleccionados = new ArrayList<Integer>();

					for (int j : tabla_ConceptosComunes.getSelectedRows()) {
						conceptosComunesSeleccionados.add(new Concepto((int) tabla_ConceptosComunes.getValueAt(j, 0),
								tabla_ConceptosComunes.getValueAt(j, 1) + "",
								conceptosComunes.get(j).getPrecioCliente("" + tabla_Clientes.getValueAt(i, 1)), false));
						unidadesConceptosComunesSeleccionados.add((int) tabla_ConceptosComunes.getValueAt(j, 3));
					}
					conceptosComunesSeleccionados.addAll(conceptosSeleccionados);
					unidadesConceptosComunesSeleccionados.addAll(unidadesConceptos);

					String num = "0000";
					try {
						if ((tabla_Clientes.getValueAt(i, 3) + "").equals("Automático /TT"))
							num = String.valueOf(NumFact.poll("TT"));
						else if ((tabla_Clientes.getValueAt(i, 3) + "").equals("Automático /XX"))
							num = String.valueOf(NumFact.poll("XX"));
						else
							num = tabla_Clientes.getValueAt(i, 3) + "";
					} catch (IOException e) {
						e.printStackTrace();
					}

					facturas.add(new Factura(num, tabla_Clientes.getValueAt(i, 4) + "",
							tabla_Clientes.getValueAt(i, 1) + "", tabla_Clientes.getValueAt(i, 8) + "",
							tabla_Clientes.getValueAt(i, 9) + " " + tabla_Clientes.getValueAt(i, 10) + "",
							tabla_Clientes.getValueAt(i, 11) + "", tabla_Clientes.getValueAt(i, 6) + "",
							tabla_Clientes.getValueAt(i, 7) + "", tabla_Clientes.getValueAt(i, 5) + "",
							conceptosComunesSeleccionados, suplidosSeleccionados,
							Double.valueOf(tabla_Clientes.getValueAt(i, 7) + "") != 0
									|| !suplidosSeleccionados.isEmpty(),
							unidadesConceptosComunesSeleccionados, unidadesSuplidos));
				}
				tabla_Conceptos.getSelectionModel().clearSelection();
				tabla_ConceptosComunes.getSelectionModel().clearSelection();
				tabla_Suplidos.getSelectionModel().clearSelection();
				JOptionPane.showMessageDialog(new JFrame(), "Facturas guardadas correctamente.");
			}
		});
	}

	private List<String> getMetodosPago() {
		FileReader reader = null;
		try {
			reader = new FileReader(mPagosPATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JsonArray jsonobj = (JsonArray) new JsonParser().parse(reader);
		Gson gson = new Gson();
		TypeToken<List<String>> token = new TypeToken<List<String>>() {
		};
		List<String> lista = gson.fromJson(jsonobj, token.getType());
		return lista;
	}

	private void estiloTablas(JTable t) {
		t.setAutoCreateRowSorter(true);
		t.getTableHeader().setFont(new Font("Tahome", Font.BOLD, 17));
		t.getTableHeader().setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		t.getTableHeader().setReorderingAllowed(false);
		t.setFont(new Font("Tahome", Font.PLAIN, 15));
		t.setRowHeight(t.getFont().getSize() + 10);
		((DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 1; i < t.getColumnCount(); i++)
			t.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}

}
