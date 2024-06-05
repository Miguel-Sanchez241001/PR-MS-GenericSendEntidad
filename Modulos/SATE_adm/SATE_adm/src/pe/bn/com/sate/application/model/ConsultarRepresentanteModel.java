package pe.bn.com.sate.application.model;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;

public class ConsultarRepresentanteModel {

	private Date fechaInicio;
	private Date fechaFin;

	private String ruc;
	private List<Representante> listaRepresentantes;

	private boolean busquedaRealizada;

	public ConsultarRepresentanteModel() {
		inicializarObjetos();
	}

	public void inicializarObjetos() {
		ruc = "";
		inicializarTabla();
	}
	
	public void inicializarTabla(){
		listaRepresentantes = null;
		busquedaRealizada = false;
	}

	public boolean validarFechas() {
		if (fechaInicio.after(fechaFin))
			return false;
		else
			return true;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Representante> getListaRepresentantes() {
		return listaRepresentantes;
	}

	public void setListaRepresentantes(List<Representante> listaRepresentantes) {
		this.listaRepresentantes = listaRepresentantes;
	}

	public boolean isBusquedaRealizada() {
		return busquedaRealizada;
	}

	public void setBusquedaRealizada(boolean busquedaRealizada) {
		this.busquedaRealizada = busquedaRealizada;
	}

	public String tipoDocumentoLetras(String tipoDocumento) {
		if (tipoDocumento != null && !tipoDocumento.isEmpty())
			return TipoDocumento.tipoDocumentoBducLetras(tipoDocumento);
		else
			return "-";
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

}
