package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zeonpad.pdfcovertor.ExcelToPdf;

public class Factura {

	private String numero, fecha, nombre, direccion, ciudad, nif, comentarios, provFondos, fPago;
	private ArrayList<Concepto> conceptos;
	private ArrayList<Concepto> suplidos;
	private ArrayList<Integer> unidadesConceptos;
	private ArrayList<Integer> unidadesSuplidos;
	private boolean suplido;

	public Factura(String numero, String fecha, String nombre, String direccion, String ciudad, String nif,
			String comentarios, String provFondos, String fPago, ArrayList<Concepto> conceptos,
			ArrayList<Concepto> suplidos, boolean suplido, ArrayList<Integer> unidadesConceptos,
			ArrayList<Integer> unidadesSuplidos) {
		this.numero = new String(numero);
		this.fecha = new String(fecha);
		this.nombre = new String(nombre);
		this.direccion = new String(direccion);
		this.ciudad = new String(ciudad);
		this.nif = new String(nif);
		this.comentarios = new String(comentarios);
		this.provFondos = new String(provFondos);
		this.fPago = new String(fPago);
		this.conceptos = conceptos;
		this.suplidos = suplidos;
		this.suplido = suplido;
		this.unidadesConceptos = unidadesConceptos;
		this.unidadesSuplidos = unidadesSuplidos;
		new File(Path.ROOT + "/tmp").mkdirs();
	}

	public void genFactura() throws IOException {

		if (suplido) {
			FileInputStream file = new FileInputStream(new File(Path.ROOT + "/modelos/modelo_suplidos.xlsx"));
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);

			sheet.getRow(3).getCell(1).setCellValue(this.numero);
			sheet.getRow(4).getCell(1).setCellValue(this.fecha);
			sheet.getRow(6).getCell(1).setCellValue(this.nombre);
			sheet.getRow(7).getCell(1).setCellValue(this.direccion);
			sheet.getRow(8).getCell(1).setCellValue(this.ciudad);
			sheet.getRow(9).getCell(1).setCellValue(this.nif);
			sheet.getRow(7).getCell(7).setCellValue(this.comentarios);
			sheet.getRow(9).getCell(9).setCellValue(Double.valueOf(this.provFondos));
			sheet.getRow(47).getCell(5).setCellValue(String.valueOf(this.fPago));

			int firstPos = 12;
			for (int i = firstPos; i < firstPos + conceptos.size(); i++) {
				sheet.getRow(i).getCell(0).setCellValue(String.valueOf(conceptos.get(i - firstPos).getCodigo()));
				sheet.getRow(i).getCell(1).setCellValue(String.valueOf(conceptos.get(i - firstPos).getContenido()));
				sheet.getRow(i).getCell(6).setCellValue(unidadesConceptos.get(i - firstPos));
				sheet.getRow(i).getCell(7).setCellValue(conceptos.get(i - firstPos).getPrecio());
			}

			int firstPosSupl = 27;
			for (int i = firstPosSupl; i < firstPosSupl + suplidos.size(); i++) {
				sheet.getRow(i).getCell(0).setCellValue(String.valueOf(suplidos.get(i - firstPosSupl).getCodigo()));
				sheet.getRow(i).getCell(1).setCellValue(String.valueOf(suplidos.get(i - firstPosSupl).getContenido()));
				sheet.getRow(i).getCell(6).setCellValue(Integer.valueOf(unidadesSuplidos.get(i - firstPosSupl)));
				sheet.getRow(i).getCell(7).setCellValue(suplidos.get(i - firstPosSupl).getPrecio());
			}

			XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

			file.close();
			String savePath = Path.ROOT + "Facturas/" + this.numero.replaceAll("/", "_") + "_tmp.xlsx";
			FileOutputStream outFile = new FileOutputStream(new File(savePath));
			workbook.write(outFile);
			outFile.close();

			// Create an Object
			ExcelToPdf excelToPdf = new ExcelToPdf();
			excelToPdf.setIgnorePrintAreas(true);
			// Covert to Pdf
			excelToPdf.convert(savePath,
					Path.ROOT + "/Facturas/" + this.nombre + "_" + this.numero.replaceAll("/", "_") + ".pdf");
			new File(savePath).delete();
		} else {
			FileInputStream file = new FileInputStream(new File(Path.ROOT + "/modelos/modelo_normal.xlsx"));
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);

			sheet.getRow(3).getCell(1).setCellValue(this.numero);
			sheet.getRow(4).getCell(1).setCellValue(this.fecha);
			sheet.getRow(6).getCell(1).setCellValue(this.nombre);
			sheet.getRow(7).getCell(1).setCellValue(this.direccion);
			sheet.getRow(8).getCell(1).setCellValue(this.ciudad);
			sheet.getRow(9).getCell(1).setCellValue(this.nif);
			sheet.getRow(7).getCell(7).setCellValue(this.comentarios);
			sheet.getRow(47).getCell(7).setCellValue(String.valueOf(this.fPago));

			int firstPos = 12;
			for (int i = firstPos; i < firstPos + conceptos.size(); i++) {
				sheet.getRow(i).getCell(0).setCellValue(String.valueOf(conceptos.get(i - firstPos).getCodigo()));
				sheet.getRow(i).getCell(1).setCellValue(String.valueOf(conceptos.get(i - firstPos).getContenido()));
				sheet.getRow(i).getCell(6).setCellValue(unidadesConceptos.get(i - firstPos));
				sheet.getRow(i).getCell(7).setCellValue(conceptos.get(i - firstPos).getPrecio());
			}

			XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

			file.close();
			String savePath = Path.ROOT + "Facturas/" + this.numero.replaceAll("/", "_") + "_tmp.xlsx";
			FileOutputStream outFile = new FileOutputStream(new File(savePath));
			workbook.write(outFile);
			outFile.close();

			// Create an Object
			ExcelToPdf excelToPdf = new ExcelToPdf();
			excelToPdf.setIgnorePrintAreas(true);
			// Covert to Pdf
			excelToPdf.convert(savePath,
					Path.ROOT + "/Facturas/" + this.nombre + "_" + this.numero.replaceAll("/", "_") + ".pdf");
			new File(savePath).delete();
		}
	}

}
