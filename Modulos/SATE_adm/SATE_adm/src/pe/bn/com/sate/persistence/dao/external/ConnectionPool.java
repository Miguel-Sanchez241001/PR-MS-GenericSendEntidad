package pe.bn.com.sate.persistence.dao.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;

public class ConnectionPool implements Runnable {

	private final static Logger logger = Logger.getLogger(ConnectionPool.class);

	private String driver, url;
	private int maxConnections, initialConnections;
	private long waittime;
	private Vector availableConnections, busyConnections;
	private int errorConnections;
	private boolean connectionPending = false;

	@Autowired
	ParametrosComp parametrosComp;

	public ConnectionPool(String driver, String db, String url, String port,
			String user, String pass, String system, String app, String server,
			String maxConnections, String waittime, String initialConnections)
			throws SQLException {

		logger.info("driver : " + driver);

		logger.info("db : " + db);
		logger.info("url : " + url);
		logger.info("port : " + port);
		logger.info("user : " + user);
		logger.info("pass : " + pass);
		logger.info("system : " + system);
		logger.info("app : " + app);
		logger.info("server : " + server);
		logger.info("maxConnections : " + maxConnections);
		logger.info("waittime : " + waittime);
		logger.info("initialConnections : " + initialConnections);

		dataSource(driver, db, url, port, user, pass, system, app, server,
				maxConnections, waittime, initialConnections);

		errorConnections = this.initialConnections;

		availableConnections = new Vector(this.initialConnections);

		busyConnections = new Vector();

		for (int i = 0; i < this.initialConnections; i++) {
			Connection con = null;
			con = makeNewConnection();
			if (con != null) {
				availableConnections.addElement(con);
				errorConnections--;
			}
		}

	}

	public void dataSource(String driver, String db, String url, String port,
			String user, String pass, String system, String app, String server,
			String maxConnections, String waittime, String initialConnections)
			throws SQLException {

		this.driver = driver;

		this.url = "jdbc:" + db + ((url.length() > 0) ? ("://" + url) : "")
				+ ":" + port + "/ServerName=" + server + ",SystemID=" + system
				+ ",ApplicationID=" + app + ",UserID=" + user + ",Password="
				+ pass;

		this.maxConnections = Integer.parseInt(maxConnections);
		this.waittime = Integer.parseInt(waittime) * 1000;
		this.initialConnections = Integer.parseInt(initialConnections);

		if (this.initialConnections > this.maxConnections) {
			this.initialConnections = this.maxConnections;
		}

	}

	public synchronized Connection getConnection() throws SQLException {

		if (!availableConnections.isEmpty()) {
			Connection existingConnection = (Connection) availableConnections
					.lastElement();
			if (existingConnection != null) {
				availableConnections.removeElement(existingConnection);
			}
			if (estaInvalida(existingConnection) != 100) {
				errorConnections++;
				notifyAll();
				return getConnection();
			} else {
				errorConnections--;
				busyConnections.addElement(existingConnection);
				return existingConnection;
			}
		} else {
			Calendar cal = Calendar.getInstance();
			if (errorConnections >= maxConnections) {
				throw new SQLException("Numero de errores superado");
			}
			if (totalConnections() < maxConnections && !connectionPending) {
				makeBackgroundConnection();
			}
			cal.setTime(new Date());
			int ini = cal.get(Calendar.SECOND);
			try {
				wait(waittime);
			} catch (InterruptedException ie) {
				logger.error(ie.getMessage());
			}

			cal.setTime(new Date());
			int fin = cal.get(Calendar.SECOND);
			int dif = (fin >= ini ? fin - ini : ((60 - ini) + fin));

			long difNum = dif * 1000;
			if (difNum >= waittime) {
				throw new SQLException(
						"Sobrepaso el Limite de Tiempo de Espera:" + difNum
								+ " s.");
			} else {

			}
			return getConnection();
		}
	}

	private void makeBackgroundConnection() {

		connectionPending = true;
		try {
			Thread connectThread = new Thread(this);
			connectThread.start();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void run() {

		Connection connection = makeNewConnection();
		synchronized (this) {
			if (connection != null) {
				availableConnections.addElement(connection);
			} else {
				errorConnections++;
			}
			connectionPending = false;
			notifyAll();
		}
	}

	private Connection makeNewConnection() {

		Connection connection = null;
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url);
			logger.info("enviando conexion url obtenida desde la app com: "
					+ url);

		} catch (Exception cnfe) {
			logger.error(cnfe.getMessage() + ": url : " + url);
			connection = null;
		}
		return connection;

	}

	public synchronized void free(Connection connection) {
		if (connection != null) {
			if (busyConnections.removeElement(connection)) {
				availableConnections.addElement(connection);
				notifyAll();
			}
		}
	}

	public synchronized int totalConnections() {
		return busyConnections.size() + availableConnections.size();
	}

	public synchronized String ConnectionsInfo() {
		return "Total de conexiones disponibles: "
				+ availableConnections.size() + ", conexiones ocupadas: "
				+ busyConnections.size();
	}

	public synchronized boolean reloadConnection() {

		boolean reload = false;

		if (errorConnections >= maxConnections) {
			errorConnections = 0;
			cerrarConexiones();
			reload = true;
			notifyAll();
		}
		return reload;
	}

	private int estaInvalida(Connection conexion) {

		int invalida = -1;

		Statement sentencia = null;

		try {
			if (!conexion.isClosed()) {
				sentencia = conexion.createStatement();
				sentencia
						.execute("select * from bndjf04 fetch first 1 row only;");
				invalida = 100;
			} else {
				invalida = -3;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			invalida = -2;
		} finally {
			try {
				if (sentencia != null) {
					sentencia.close();
				}
			} catch (Exception i) {
				logger.error(i.getMessage());
			}
		}

		if (invalida != 100) {
			try {
				if (conexion != null) {
					if (!conexion.isClosed()) {
						conexion.close();
					}
				}
			} catch (Exception i) {
				logger.error(i.getMessage());
			}
			conexion = null;
		}

		return invalida;
	}

	public void cerrarConexiones() {

		try {
			Vector cnxs = availableConnections;
			if (cnxs != null) {
				for (int i = 0; i < cnxs.size(); i++) {
					Connection conelement = (Connection) cnxs.get(i);
					if (conelement != null) {
						try {
							if (!conelement.isClosed())
								conelement.close();
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
						conelement = null;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

}
