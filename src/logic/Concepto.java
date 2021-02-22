package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Concepto {

	@Override
	public String toString() {
		return "Concepto [codigo=" + codigo + ", contenido=" + contenido + ", precio=" + precio + ", suplido=" + suplido
				+ "]";
	}

	private int codigo;
	private String contenido;
	private double precio;
	private boolean suplido;
	private static final transient String PATH = Path.ROOT + "/Conceptos/";

	public Concepto(int codigo, String contenido, double precio, boolean suplido) {
		this.codigo = codigo;
		this.contenido = contenido;
		this.precio = precio;
		this.suplido = suplido;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getContenido() {
		return contenido;
	}

	public double getPrecio() {
		return precio;
	}

	public boolean isSuplido() {
		return suplido;
	}

	public void addConcepto(JTable tabla) {
		// Creamos el archivo
		Gson gson = new Gson();
		try {
			FileWriter fw = new FileWriter(PATH + codigo + ".json");
			gson.toJson(this, fw);
			fw.flush();
			fw.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		// Lo añadimos a la tabla

		String[] conceptos = new String[4];
		conceptos[0] = String.valueOf(getCodigo());
		conceptos[1] = getContenido();
		conceptos[2] = String.valueOf(getPrecio());
		conceptos[3] = "NO";
		if (isSuplido())
			conceptos[3] = "SÍ";
		((DefaultTableModel) tabla.getModel()).addRow(conceptos);
	}

	public static void delConcepto(String cod, JTable table) {
		if ((new File(PATH + cod + ".json")).delete()) {
			((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
		}
	}

}
