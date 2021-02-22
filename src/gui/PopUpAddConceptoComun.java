package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import logic.Cliente;
import logic.ConceptoComun;
import logic.Path;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PopUpAddConceptoComun extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtCodigo;
	private JTextField txtContenido;
	private JButton btnNewButton;

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
					PopUpAddConceptoComun frame = new PopUpAddConceptoComun();
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
	public PopUpAddConceptoComun() {
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"¿Guardar antes de salir? Si ya se ha guardado no es necesario.");
				if (dialogResult == JOptionPane.YES_OPTION)
					btnNewButton.doClick();

			}
		});
		new File(Path.ROOT + "/ConceptosComunes/").mkdirs();
		setBounds(100, 100, 1051, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		panel.add(lblCdigo);

		txtCodigo = new JTextField();
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblConcepto = new JLabel("Concepto");
		panel.add(lblConcepto);

		txtContenido = new JTextField();
		panel.add(txtContenido);
		txtContenido.setColumns(10);

		JButton btnAdd = new JButton("AÑADIR CONCEPTO COMÚN");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefaultTableModel) table.getModel()).addColumn(txtCodigo.getText() + ": " + txtContenido.getText());
			}
		});
		panel.add(btnAdd);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		DefaultTableModel dtm = new DefaultTableModel(new String[] { "CLIENTES" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0)
					return false;
				return true;
			}
		};

		table = new JTable(dtm);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE && table.getSelectedColumn() != 0) {
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Se va a borrar el concepto y todos sus datos");
					if (dialogResult == JOptionPane.YES_OPTION) {
						ConceptoComun.delConcepto(table.getColumnName(table.getSelectedColumn()).substring(0,
								table.getColumnName(table.getSelectedColumn()).indexOf(":")));
						table.removeColumn(table.getColumnModel().getColumn(table.getSelectedColumn()));
						JOptionPane.showMessageDialog(new JFrame(), "Concepto eliminado correctamente", "INFORMACIÓN",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		});
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setFont(new Font("Tahome", Font.BOLD, 23));
		table.getTableHeader().setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		table.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		table.setFont(new Font("Tahome", Font.PLAIN, 20));
		table.setRowHeight(table.getFont().getSize() + 10);

		Gson gson2 = new Gson();
		File dire = new File(Path.ROOT + "/Clientes/");
		for (File f : dire.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				Cliente c = gson2.fromJson(jsonobj, Cliente.class);
				Object[] clients = new Object[table.getColumnCount()];
				clients[0] = c.getNombre() + " " + c.getApellidos();
				for (int i = 1; i < table.getColumnCount() - 1; i++) {
					clients[i] = "";
				}
				((DefaultTableModel) table.getModel()).addRow(clients);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		cargarDatos();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		scrollPane.setViewportView(table);

		btnNewButton = new JButton("GUARDAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarDatos(true);
			}
		});
		btnNewButton.setPreferredSize(new Dimension(200, 70));
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
	}

	private void cargarDatos() {
		Gson gson2 = new Gson();
		File dire = new File(Path.ROOT + "/ConceptosComunes/");
		for (File f : dire.listFiles()) {
			try {
				FileReader reader = new FileReader(f);
				JsonObject jsonobj = (JsonObject) new JsonParser().parse(reader);
				ConceptoComun c = gson2.fromJson(jsonobj, ConceptoComun.class);

				Object[] clientes = new Object[table.getRowCount()];
				Object[] resultado = new Object[clientes.length];
				Object[][] data = c.getData();
				boolean encontrado = false;
				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < data.length; j++) {
						if (table.getValueAt(i, 0).equals(data[j][0]) && !encontrado) {
							resultado[i] = data[j][1];
							encontrado = true;
						}
					}
					if (!encontrado)
						resultado[i] = " ";
					encontrado = false;
				}
				((DefaultTableModel) table.getModel()).addColumn(c.getCodigo() + ": " + c.getContenido(), resultado);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void guardarDatos(boolean notificar) {
		Object[][] data = new Object[table.getRowCount()][2];
		for (int i = 1; i < table.getColumnCount(); i++) {
			ConceptoComun.delConcepto(table.getColumnName(i).substring(0, table.getColumnName(i).indexOf(":")));
		}
		for (int i = 1; i < table.getColumnCount(); i++) {
			for (int j = 0; j < table.getRowCount(); j++) {
				data[j][0] = table.getValueAt(j, 0);
				data[j][1] = table.getValueAt(j, i);
			}
			new ConceptoComun(Integer.valueOf(table.getColumnName(i).substring(0, table.getColumnName(i).indexOf(":"))),
					table.getColumnName(i).substring(table.getColumnName(i).indexOf(":") + 2,
							table.getColumnName(i).length()),
					data).addConcepto();
		}
		if (notificar)
			JOptionPane.showMessageDialog(new JFrame(), "Conceptos guardados correctamente", "Conceptos comunes",
					JOptionPane.INFORMATION_MESSAGE);

	}

}
