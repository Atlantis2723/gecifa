package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import logic.NumFact;
import logic.Path;

import javax.swing.JScrollPane;

public class Cfg extends JFrame {

	private JPanel contentPane;
	private JTextField numTT;
	private JTextField anoTT;
	private JTextField numXX;
	private JTextField anoXX;
	private JLabel lblActual;
	private JLabel label_1;
	private JLabel actualTT;
	private JLabel actualXX;
	private JLabel lblSiNoQuieres;
	private JButton btnEliminar;
	private JTextField txtMetodosPago;
	private DefaultListModel modeloLista;

	private static final String mPagosPATH = Path.ROOT + "/mPagos.json";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cfg frame = new Cfg();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws FileNotFoundException
	 */
	public Cfg() {
		try {
			crearArchivo();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		setTitle("Configuración");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cfg.class.getResource("/resources/settings.png")));
		setBounds(100, 100, 645, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInicioFacturasTt = new JLabel("Inicio facturas /TT");
		lblInicioFacturasTt.setBounds(51, 52, 128, 16);
		contentPane.add(lblInicioFacturasTt);

		numTT = new JTextField();
		numTT.setBounds(189, 49, 116, 22);
		contentPane.add(numTT);
		numTT.setColumns(10);

		JLabel lbltt = new JLabel("/TT");
		lbltt.setBounds(317, 52, 56, 16);
		contentPane.add(lbltt);

		anoTT = new JTextField();
		anoTT.setColumns(10);
		anoTT.setBounds(350, 49, 116, 22);
		contentPane.add(anoTT);

		JLabel lblInicioFacturasxx = new JLabel("Inicio facturas /XX");
		lblInicioFacturasxx.setBounds(51, 130, 128, 16);
		contentPane.add(lblInicioFacturasxx);

		numXX = new JTextField();
		numXX.setColumns(10);
		numXX.setBounds(189, 127, 116, 22);
		contentPane.add(numXX);

		anoXX = new JTextField();
		anoXX.setColumns(10);
		anoXX.setBounds(350, 127, 116, 22);
		contentPane.add(anoXX);

		JLabel label = new JLabel("/");
		label.setBounds(327, 130, 56, 16);
		contentPane.add(label);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					NumFact.crearArchivo();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					if (String.valueOf(numTT.getText().trim()).equals(""))
						NumFact.update("numTT", 0);
					else
						NumFact.update("numTT", Integer.valueOf(numTT.getText().trim()));
					if (String.valueOf(numXX.getText().trim()).equals(""))
						NumFact.update("numXX", 0);
					else
						NumFact.update("numXX", Integer.valueOf(numXX.getText().trim()));
					if (String.valueOf(anoTT.getText().trim()).equals(""))
						NumFact.update("anoTT", 0);
					else
						NumFact.update("anoTT", Integer.valueOf(anoTT.getText().trim()));
					if (String.valueOf(anoXX.getText().trim()).equals(""))
						NumFact.update("anoXX", 0);
					else
						NumFact.update("anoXX", Integer.valueOf(anoXX.getText().trim()));
					actualizarNumFact();
				} catch (NumberFormatException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGuardar.setBounds(503, 49, 97, 100);
		contentPane.add(btnGuardar);

		lblActual = new JLabel("Actual:");
		lblActual.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblActual.setBounds(68, 87, 56, 16);
		contentPane.add(lblActual);

		label_1 = new JLabel("Actual:");
		label_1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		label_1.setBounds(68, 165, 56, 16);
		contentPane.add(label_1);

		actualTT = new JLabel("");
		actualTT.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		actualTT.setBounds(117, 81, 83, 27);
		contentPane.add(actualTT);

		actualXX = new JLabel("");
		actualXX.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		actualXX.setBounds(117, 159, 89, 30);
		contentPane.add(actualXX);

		lblSiNoQuieres = new JLabel(
				"Si NO quieres modificar un numero vuelve a introducirlo en la casilla antes de guardar");
		lblSiNoQuieres.setForeground(Color.RED);
		lblSiNoQuieres.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblSiNoQuieres.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiNoQuieres.setBounds(12, 482, 599, 29);
		contentPane.add(lblSiNoQuieres);

		modeloLista = new DefaultListModel<String>();
		JScrollPane scrollPane = new JScrollPane();
		JList<String> list;
		scrollPane.setBounds(51, 194, 550, 160);
		contentPane.add(scrollPane);
		list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setFont(new Font("Tahoma", Font.PLAIN, 17));
		list.setModel(modeloLista);
		cargarMetodosPago();

		JButton btnAnadir = new JButton("Añadir");
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeloLista.addElement(txtMetodosPago.getText());
				updateMetodosPago();
			}
		});
		btnAnadir.setBounds(51, 438, 130, 31);
		contentPane.add(btnAnadir);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloLista.remove(list.getSelectedIndex());
				updateMetodosPago();
			}
		});
		btnEliminar.setBounds(205, 438, 130, 31);
		contentPane.add(btnEliminar);

		txtMetodosPago = new JTextField();
		txtMetodosPago.setBounds(51, 395, 549, 30);
		contentPane.add(txtMetodosPago);
		txtMetodosPago.setColumns(10);

		JLabel lblIntroduceAquEl = new JLabel("Introduce aquí el nuevo método de pago:");
		lblIntroduceAquEl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIntroduceAquEl.setBounds(51, 367, 386, 27);
		contentPane.add(lblIntroduceAquEl);

		actualizarNumFact();
	}

	private void cargarMetodosPago() {
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

		for (String s : lista)
			modeloLista.addElement(s);
	}

	private void updateMetodosPago() {
		new File(mPagosPATH).delete();

		ArrayList<String> lista = new ArrayList<String>();

		for (int i = 0; i < modeloLista.size(); i++)
			lista.add((String) modeloLista.getElementAt(i));

		Gson gson = new Gson();
		try {
			FileWriter fw = new FileWriter(mPagosPATH);
			gson.toJson(lista, fw);
			fw.flush();
			fw.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}

	}

	public static void crearArchivo() throws IOException {
		File file = new File(mPagosPATH);

		if (file.exists())
			return;
		else {
			ArrayList<String> lista = new ArrayList<String>();
			Gson gson = new Gson();
			try {
				FileWriter fw = new FileWriter(mPagosPATH);
				gson.toJson(lista, fw);
				fw.flush();
				fw.close();
			} catch (JsonIOException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void actualizarNumFact() {
		try {
			actualTT.setText(NumFact.peek("TT"));
			actualXX.setText(NumFact.peek("XX"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
