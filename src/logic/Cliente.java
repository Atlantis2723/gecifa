package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Cliente {

	private String nombre, apellidos, nif, direccion, ciudad, cp;
	private boolean mensual;
	private String correo, comentarios;

	public Cliente(String nombre, String apellidos, String nif, String direccion, String ciudad, String cp,
			boolean mensual, String correo, String comentarios) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.nif = nif;
		this.ciudad = ciudad;
		this.cp = cp;
		this.correo = correo;
		this.comentarios = comentarios;
		this.mensual = mensual;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNif() {
		return nif;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getCp() {
		return cp;
	}

	public String getCorreo() {
		return correo;
	}

	public String getComentarios() {
		return comentarios;
	}

	public boolean isMensual() {
		return mensual;
	}

	public void addCliente(JTable table) {
		// Crear archivo
		Gson gson = new Gson();
		try {
			FileWriter fw = new FileWriter(Path.ROOT + "/Clientes/" + this.nif + ".json");
			gson.toJson(this, fw);
			fw.flush();
			fw.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		// Añadir a tabla
		String[] clientes = new String[9];
		clientes[0] = getNombre();
		clientes[1] = getApellidos();
		clientes[2] = getNif();
		clientes[3] = getDireccion();
		clientes[4] = getCiudad();
		clientes[5] = getCp();
		String mensual = "NO";
		if (isMensual())
			mensual = "SI";
		clientes[6] = mensual;
		clientes[7] = correo;
		clientes[8] = comentarios;
		((DefaultTableModel) table.getModel()).addRow(clientes);
	}

	public static void eliminarCliente(String nif, JTable table) throws IOException {
		((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
		new File(Path.ROOT + "/Clientes/" + nif + ".json").delete();
	}
}
