package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import logic.Cliente;
import java.awt.Color;

public class PopUpAddCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtapellidos;
	private JTextField txtnif;
	private JTextField txtdir;
	private JTextField txtciudad;
	private JTextField txtCP;
	private JCheckBox chkmensual;
	private JTextField txtCorreo;
	private JTextArea txtComentarios;

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
					PopUpAddCliente frame = new PopUpAddCliente("", "", "", "", "", "", false, "", "", null);
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
	public PopUpAddCliente(String nombre, String apellidos, String nif, String dir, String ciudad, String cp,
			boolean mensual, String correo, String comentarios, JTable tablaClientes) {
		setTitle("Formulario añadir clientes");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(PopUpAddCliente.class.getResource("/resources/newClient.png")));
		setBounds(100, 100, 575, 591);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(55, 55, 55, 40);
		contentPane.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setBounds(320, 55, 55, 40);
		contentPane.add(lblApellidos);

		JLabel lblNif = new JLabel("NIF");
		lblNif.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNif.setBounds(55, 108, 55, 40);
		contentPane.add(lblNif);

		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccion.setBounds(320, 108, 55, 40);
		contentPane.add(lblDireccion);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCiudad.setBounds(55, 161, 55, 40);
		contentPane.add(lblCiudad);

		JLabel lblCp = new JLabel("CP");
		lblCp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCp.setBounds(320, 161, 55, 40);
		contentPane.add(lblCp);

		JLabel lblMensual = new JLabel("Mensual");
		lblMensual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMensual.setBounds(320, 223, 55, 40);
		contentPane.add(lblMensual);

		txtNombre = new JTextField();
		txtNombre.setText(nombre);
		txtNombre.setBounds(136, 64, 116, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		txtapellidos = new JTextField();
		txtapellidos.setText(apellidos);
		txtapellidos.setColumns(10);
		txtapellidos.setBounds(401, 64, 116, 22);
		contentPane.add(txtapellidos);

		txtnif = new JTextField();
		txtnif.setText(nif);
		txtnif.setColumns(10);
		txtnif.setBounds(136, 117, 116, 22);
		contentPane.add(txtnif);

		txtdir = new JTextField();
		txtdir.setText(dir);
		txtdir.setColumns(10);
		txtdir.setBounds(401, 117, 116, 22);
		contentPane.add(txtdir);

		txtciudad = new JTextField();
		txtciudad.setText(ciudad);
		txtciudad.setColumns(10);
		txtciudad.setBounds(136, 170, 116, 22);
		contentPane.add(txtciudad);

		txtCP = new JTextField();
		txtCP.setText(cp);
		txtCP.setColumns(10);
		txtCP.setBounds(401, 170, 116, 22);
		contentPane.add(txtCP);

		chkmensual = new JCheckBox("");
		chkmensual.setSelected(mensual);
		chkmensual.setBounds(401, 231, 113, 25);
		contentPane.add(chkmensual);

		JButton btnSave = new JButton("GUARDAR");

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtNombre.getText().trim().isEmpty() && !txtapellidos.getText().trim().isEmpty()
						&& !txtnif.getText().trim().isEmpty() && !txtdir.getText().trim().isEmpty()
						&& !txtciudad.getText().trim().isEmpty() && !txtCP.getText().trim().isEmpty()) {
					Cliente newCliente = new Cliente(txtNombre.getText().trim(), txtapellidos.getText().trim(),
							txtnif.getText().trim(), txtdir.getText().trim(), txtciudad.getText().trim(),
							txtCP.getText().trim(), chkmensual.isSelected(), txtCorreo.getText().trim(),
							txtComentarios.getText().trim());
					newCliente.addCliente(tablaClientes);
					makeEmpty();

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Llena todos los campos correctamente", "Confirmación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setIcon(new ImageIcon(PopUpAddCliente.class.getResource("/resources/logo.png")));
		btnSave.setBounds(136, 408, 250, 92);
		contentPane.add(btnSave);

		JLabel lblFormularioAadirClientes = new JLabel("FORMULARIO A\u00D1ADIR CLIENTES");
		lblFormularioAadirClientes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFormularioAadirClientes.setBounds(30, 13, 271, 25);
		contentPane.add(lblFormularioAadirClientes);

		JLabel lblRecuerdaGuardarAntes = new JLabel(
				"Recuerda guardar antes de salir para no perder los datos en caso de modificar un cliente");
		lblRecuerdaGuardarAntes.setForeground(Color.RED);
		lblRecuerdaGuardarAntes.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRecuerdaGuardarAntes.setBounds(8, 504, 545, 29);
		contentPane.add(lblRecuerdaGuardarAntes);

		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCorreo.setBounds(55, 214, 55, 40);
		contentPane.add(lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setText(correo);
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(136, 223, 116, 22);
		contentPane.add(txtCorreo);

		JLabel lblComentarios = new JLabel("Comentarios");
		lblComentarios.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComentarios.setBounds(30, 267, 80, 40);
		contentPane.add(lblComentarios);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(136, 276, 381, 106);
		contentPane.add(scrollPane_1);

		txtComentarios = new JTextArea();
		txtComentarios.setText(comentarios);
		scrollPane_1.setViewportView(txtComentarios);
		txtComentarios.setLineWrap(true);
		txtComentarios.setWrapStyleWord(true);
	}

	private void makeEmpty() {
		txtNombre.setText("");
		txtapellidos.setText("");
		txtnif.setText("");
		txtdir.setText("");
		txtciudad.setText("");
		txtCP.setText("");
		chkmensual.setSelected(false);
		txtComentarios.setText("");
		txtCorreo.setText("");
	}
}
