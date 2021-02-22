package logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NumFact {

	private static final String PATH = System.getProperty("user.home") + "/AppData/Local/GECIFA/numFact.json";

	public static String peek(String tipo) throws IOException {
		crearArchivo();
		if (tipo.equals("TT")) {
			String aux = read("numTT") + "/TT";
			if (read("anoTT") != 0)
				aux += read("anoTT");
			return aux;
		} else
			return read("numXX") + "/" + read("anoXX");
	}

	public static String poll(String tipo) throws IOException {
		crearArchivo();

		if (tipo.equals("TT")) {
			String aux = read("numTT") + "/TT";
			if (read("anoTT") != 0)
				aux += read("anoTT");
			update("numTT", read("numTT") + 1);
			return aux;
		} else {
			String aux = read("numXX") + "/" + read("anoXX");
			update("numXX", read("numXX") + 1);
			return aux;
		}
	}

	public static int read(String key) throws IOException {
		crearArchivo();
		JsonParser parser = new JsonParser();
		FileReader fr = new FileReader(new File(PATH));
		int obj = parser.parse(fr).getAsJsonObject().get(key).getAsInt();
		fr.close();
		return obj;
	}

	public static void write(JsonObject jo) throws IOException {
		FileWriter fw = new FileWriter(PATH);
		fw.write(jo.toString());
		fw.flush();
		fw.close();
	}

	public static void update(String key, int value) throws IOException {
		crearArchivo();
		JsonParser parser = new JsonParser();
		FileReader fr = new FileReader(new File(PATH));
		JsonObject obj = parser.parse(fr).getAsJsonObject();
		fr.close();

		obj.addProperty(key, value);

		write(obj);
	}

	public static void crearArchivo() throws IOException {
		File file = new File(PATH);

		if (file.exists())
			return;
		else {
			JsonObject jo = new JsonObject();
			jo.addProperty("numTT", 0);
			jo.addProperty("anoTT", 0);
			jo.addProperty("numXX", 0);
			jo.addProperty("anoXX", 0);
			write(jo);
		}

	}

}
