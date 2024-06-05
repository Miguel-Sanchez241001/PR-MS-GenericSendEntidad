package pe.bn.com.sate.transversal.util.componentes;

import org.springframework.stereotype.Component;

@Component("parametros")
public class ParametrosComp {

	private String urlComp;
	private String errorComp;
	private String desErrorComp;

	// PARAMETROS BDUC
	private String perfil2BDUC;
	private String perfilBDUC;
	private String tipoDocBDUC;
	private String transac2BDUC;
	private String transacBDUC;
	private String userBDUC;

	// PARAMETROS DATACOM
	private String driverDatacom;
	private String dbDatacom;
	private String urlDatacom;
	private String portDatacom;
	private String userDatacom;
	private String passDatacom;
	private String systemDatacom;
	private String appDatacom;
	private String serverDatacom;
	private String maxconnectionsDatacom;
	private String waittimeDatacom;
	private String initialconnectionsDatacom;

	// PARAMETROS TIEMPO
	private String sesionExpiradaTiempo;
	private String conexionTiempo;
	private String respuestaTiempo;

	public String getMaxconnectionsDatacom() {
		return maxconnectionsDatacom;
	}

	public void setMaxconnectionsDatacom(String maxconnectionsDatacom) {
		this.maxconnectionsDatacom = maxconnectionsDatacom;
	}

	public String getWaittimeDatacom() {
		return waittimeDatacom;
	}

	public void setWaittimeDatacom(String waittimeDatacom) {
		this.waittimeDatacom = waittimeDatacom;
	}

	public String getInitialconnectionsDatacom() {
		return initialconnectionsDatacom;
	}

	public void setInitialconnectionsDatacom(String initialconnectionsDatacom) {
		this.initialconnectionsDatacom = initialconnectionsDatacom;
	}

	public String getDriverDatacom() {
		return driverDatacom;
	}

	public void setDriverDatacom(String driverDatacom) {
		this.driverDatacom = driverDatacom;
	}

	public String getDbDatacom() {
		return dbDatacom;
	}

	public void setDbDatacom(String dbDatacom) {
		this.dbDatacom = dbDatacom;
	}

	public String getUrlDatacom() {
		return urlDatacom;
	}

	public void setUrlDatacom(String urlDatacom) {
		this.urlDatacom = urlDatacom;
	}

	public String getPortDatacom() {
		return portDatacom;
	}

	public void setPortDatacom(String portDatacom) {
		this.portDatacom = portDatacom;
	}

	public String getUserDatacom() {
		return userDatacom;
	}

	public void setUserDatacom(String userDatacom) {
		this.userDatacom = userDatacom;
	}

	public String getPassDatacom() {
		return passDatacom;
	}

	public void setPassDatacom(String passDatacom) {
		this.passDatacom = passDatacom;
	}

	public String getSystemDatacom() {
		return systemDatacom;
	}

	public void setSystemDatacom(String systemDatacom) {
		this.systemDatacom = systemDatacom;
	}

	public String getAppDatacom() {
		return appDatacom;
	}

	public void setAppDatacom(String appDatacom) {
		this.appDatacom = appDatacom;
	}

	public String getServerDatacom() {
		return serverDatacom;
	}

	public void setServerDatacom(String serverDatacom) {
		this.serverDatacom = serverDatacom;
	}

	public String getSesionExpiradaTiempo() {
		return sesionExpiradaTiempo;
	}

	public void setSesionExpiradaTiempo(String sesionExpiradaTiempo) {
		this.sesionExpiradaTiempo = sesionExpiradaTiempo;
	}

	public String getConexionTiempo() {
		return conexionTiempo;
	}

	public void setConexionTiempo(String conexionTiempo) {
		this.conexionTiempo = conexionTiempo;
	}

	public String getRespuestaTiempo() {
		return respuestaTiempo;
	}

	public void setRespuestaTiempo(String respuestaTiempo) {
		this.respuestaTiempo = respuestaTiempo;
	}

	public String getErrorComp() {
		return errorComp;
	}

	public void setErrorComp(String errorComp) {
		this.errorComp = errorComp;
	}

	public String getDesErrorComp() {
		return desErrorComp;
	}

	public void setDesErrorComp(String desErrorComp) {
		this.desErrorComp = desErrorComp;
	}

	public String getUrlComp() {
		return urlComp;
	}

	public void setUrlComp(String urlComp) {
		this.urlComp = urlComp;
	}

	public String getPerfil2BDUC() {
		return perfil2BDUC;
	}

	public void setPerfil2BDUC(String perfil2bduc) {
		perfil2BDUC = perfil2bduc;
	}

	public String getPerfilBDUC() {
		return perfilBDUC;
	}

	public void setPerfilBDUC(String perfilBDUC) {
		this.perfilBDUC = perfilBDUC;
	}

	public String getTipoDocBDUC() {
		return tipoDocBDUC;
	}

	public void setTipoDocBDUC(String tipoDocBDUC) {
		this.tipoDocBDUC = tipoDocBDUC;
	}

	public String getTransac2BDUC() {
		return transac2BDUC;
	}

	public void setTransac2BDUC(String transac2bduc) {
		transac2BDUC = transac2bduc;
	}

	public String getTransacBDUC() {
		return transacBDUC;
	}

	public void setTransacBDUC(String transacBDUC) {
		this.transacBDUC = transacBDUC;
	}

	public String getUserBDUC() {
		return userBDUC;
	}

	public void setUserBDUC(String userBDUC) {
		this.userBDUC = userBDUC;
	}

}
