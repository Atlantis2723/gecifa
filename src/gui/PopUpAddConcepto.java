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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import logic.Concepto;

public class PopUpAddConcepto extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JCheckBox chckbxSuplido;

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
					PopUpAddConcepto frame = new PopUpAddConcepto("", "", "", false, null);
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
	public PopUpAddConcepto(String cod, String content, String precio, boolean suplido, JTable tabla) {
		setTitle("A\u00F1adir conceptos y suplidos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PopUpAddConcepto.class.getResource("/resources/logo.png")));
		setBounds(700, 300, 432, 554);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCdigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCdigo.setBounds(180, 87, 56, 16);
		contentPane.add(lblCdigo);

		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setHorizontalAlignment(SwingConstants.CENTER);
		lblConcepto.setBounds(180, 158, 56, 16);
		contentPane.add(lblConcepto);

		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecio.setBounds(180, 260, 56, 16);
		contentPane.add(lblPrecio);

		textField = new JTextField();
		textField.setText(cod);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(146, 116, 129, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setText(content);
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(59, 187, 297, 50);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setText(precio);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(146, 289, 129, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		chckbxSuplido = new JCheckBox("Suplido");
		chckbxSuplido.setSelected(suplido);
		chckbxSuplido.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxSuplido.setBounds(169, 333, 79, 25);
		contentPane.add(chckbxSuplido);

		JLabel lblAadirConceptosuplido = new JLabel("Formulario a\u00F1adir concepto/suplido");
		lblAadirConceptosuplido.setHorizontalAlignment(SwingConstants.CENTER);
		lblAadirConceptosuplido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAadirConceptosuplido.setBounds(12, 13, 390, 74);
		contentPane.add(lblAadirConceptosuplido);

		JButton btnSave = new JButton("GUARDAR");

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean numCode = true, numPrecio = true;
				for (char c : textField.getText().trim().toCharArray()) {
					if (!Character.isDigit(c))
						numCode = false;
				}
				for (char c : textField_2.getText().trim().toCharArray()) {
					if (!Character.isDigit(c) && c!=46)
						numPrecio = false;
				}
				if (numCode && !textField_1.getText().trim().isEmpty() && numPrecio) {
					Concepto c = new Concepto(Integer.valueOf(textField.getText().trim()), textField_1.getText().trim(),
							Double.valueOf(textField_2.getText().trim()), chckbxSuplido.isSelected());
					c.addConcepto(tabla);
					makeEmpty();
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"<html>Llena todos los campos correctamente:<br/> CÓDIGO y NÚMERO contienen números y CONCEPTO ha de tener contenido</html>  ",
							"Confirmación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setIcon(new ImageIcon(PopUpAddCliente.class.getResource("/resources/logo.png")));
		btnSave.setBounds(87, 388, 250, 92);
		contentPane.add(btnSave);
	}

	private void makeEmpty() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		chckbxSuplido.setSelected(false);
	}
}
