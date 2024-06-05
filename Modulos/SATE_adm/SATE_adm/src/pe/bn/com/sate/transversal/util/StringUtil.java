package pe.bn.com.sate.transversal.util;

import java.util.Random;

public class StringUtil {
	public static String obtenerCadenaDespuesDePunto(String cadenaConPunto) {
		return cadenaConPunto.substring(cadenaConPunto.lastIndexOf('.') + 1);
	}

	public static String concatenarCadena(Object... objetos) {
		StringBuffer sb = new StringBuffer();
		for (Object objeto : objetos) {
			sb.append(objeto.toString());
		}
		return sb.toString();
	}

	public static String[] obtenerSubCadenas(String cadena, String separador) {
		return cadena.split(separador, 2);
	}

	public static String obtenerCadenaDespuesDe(String cadena, String separador) {
		if (cadena != null && cadena.length() > 0) {
			return cadena.substring(cadena.lastIndexOf(separador) + 1);
		}
		return cadena;
	}

	public static String obtenerCadenaAntesDe(String cadena, String separador) {
		if (cadena != null && cadena.length() > 0) {
			return cadena.substring(0, cadena.lastIndexOf(separador));
		}
		return cadena;
	}

	public static String removerUltimoCaracter(String cadena) {
		if (cadena != null && cadena.length() > 0) {
			cadena = cadena.substring(0, cadena.length() - 1);
		}
		return cadena;
	}

	public static String random() {
		Random a = new Random();
		a.setSeed(System.currentTimeMillis());
		int ia = a.nextInt(900000) + 100000;
		return Integer.toString(ia);
	}

	public static String enmascararTramaEnvio(String value) {
		if (value == null)
			return null;

		// value = value.replaceAll("ñ", "#");
		// value = value.replaceAll("Ñ", "#");
		value = value.replaceAll("/", "Ñ");
		value = value.replaceAll("Á", "A");
		value = value.replaceAll("á", "A");
		value = value.replaceAll("Ä", "A");
		value = value.replaceAll("ä", "A");
		value = value.replaceAll("É", "E");
		value = value.replaceAll("é", "E");
		value = value.replaceAll("Ë", "E");
		value = value.replaceAll("ë", "E");
		value = value.replaceAll("Í", "I");
		value = value.replaceAll("í", "I");
		value = value.replaceAll("Ï", "I");
		value = value.replaceAll("ï", "I");
		value = value.replaceAll("Ó", "O");
		value = value.replaceAll("ó", "O");
		value = value.replaceAll("Ö", "O");
		value = value.replaceAll("ö", "O");
		value = value.replaceAll("Ú", "U");
		value = value.replaceAll("ú", "U");
		value = value.replaceAll("Ü", "U");
		value = value.replaceAll("ü", "U");

		return value;
	}

	public static String enmascararTramaRecepcion(String value) {
		if (value == null)
			return null;

		value = value.replaceAll("#", "Ñ");
		// value = value.replaceAll("/", "Ñ");

		return value;
	}

	public static String repeat(String s, int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String parseString(String cadena) {
		return cadena == null ? "" : cadena;
	}

	public static String agregarEspacios(String cadena, int cantidad) {
		if (cadena.length() > cantidad)
			cadena = cadena.substring(0, cantidad);
		int cantidadFaltante = cantidad - cadena.length();
		for (int i = 0; i < cantidadFaltante; i++)
			cadena += " ";
		return cadena;
	}

	public static boolean isEmpty(String cadena) {
		if (cadena == null || cadena.isEmpty())
			return true;
		else
			return false;
	}

}
