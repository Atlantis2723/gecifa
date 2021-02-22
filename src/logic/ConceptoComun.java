package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class ConceptoComun {

	private int codigo;
	private String contenido;
	private Object[][] data;
	private static final transient String PATH = Path.ROOT + "/ConceptosComunes/";

	public ConceptoComun(int codigo, String contenido, Object[][] data) {
		this.codigo = codigo;
		this.contenido = contenido;
		this.data = data;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getContenido() {
		return contenido;
	}

	public Object[][] getData() {
		return data;
	}

	public double getPrecioCliente(String nombre) {
		double res = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][0].equals(nombre)) {
				res = Double.parseDouble("" + data[i][1]);
			}
		}
		return res;
	}

	public void addConcepto() {
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
	}

	public static void delConcepto(String cod) {
		(new File(PATH + cod + ".json")).delete();

	}

	public String toString() {
		String str = "";
		for (int i = 0; i < data.length; i++)
			str += data[i][0] + " ---> " + data[i][1] + " �\n";
		return str;
	}

}
