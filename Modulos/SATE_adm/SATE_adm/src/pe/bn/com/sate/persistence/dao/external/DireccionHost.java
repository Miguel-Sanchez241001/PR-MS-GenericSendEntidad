package pe.bn.com.sate.persistence.dao.external;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pe.bn.com.sate.persistence.exception.PersistenciaExcepcion;
import pe.bn.com.sate.transversal.dto.host.Direccion;

public class DireccionHost {

	private ConnectionPool pool;

	public DireccionHost(String driver, String db, String url, String port,
			String user, String pass, String system, String app, String server,
			String maxConnections, String waittime, String initialConnections) {
		try {
			pool = new ConnectionPool(driver, db, url, port, user, pass,
					system, app, server, maxConnections, waittime,
					initialConnections);
		} catch (SQLException e) {
			throw new PersistenciaExcepcion(e.getMessage(),e);
		}
	}

	public Direccion obtenerDatosDireccion(String cic) {
		try {
			Direccion direccion = new Direccion();
			Connection cnx = pool.getConnection();

			Statement stmt = cnx.createStatement();
			String query = "SELECT * FROM BNUCF04_DIRECCION WHERE F04_CINTERNO_CIC="
					+ cic
					+ " AND F04_TDIRECCION = '8' AND  F04_FULTMODIF_DIR = (SELECT MAX(F04_FULTMODIF_DIR) FROM BNUCF04_DIRECCION WHERE F04_CINTERNO_CIC="
					+ cic + " AND F04_TDIRECCION = '8' )";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				direccion.setTelefonoFijo(rs.getString(15).trim());
				direccion.setDireccion(rs.getString(5).trim() + " "
						+ rs.getString(6).trim());
				direccion.setUbigeo(rs.getString(19).trim());
				direccion.setReferencia(rs.getString(13).trim());
			}
			cnx.close();
			return direccion;

		} catch (SQLException e) {
			throw new PersistenciaExcepcion(e.getMessage(),e);
		}
	}
}
