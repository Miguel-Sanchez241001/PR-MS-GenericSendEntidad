package pe.bn.com.sate.transversal.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.util.IOUtils;

public class Archivo {

	public static byte[] leerArchivo(String ruta) {
		String lectura = "";
		try {
			FileInputStream fis = new FileInputStream(new File(ruta));
			return IOUtils.toByteArray(fis);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
