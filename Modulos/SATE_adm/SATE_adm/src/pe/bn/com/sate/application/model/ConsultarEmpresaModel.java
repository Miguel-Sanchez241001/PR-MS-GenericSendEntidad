package pe.bn.com.sate.application.model;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.EmpresaResumen;
import pe.bn.com.sate.transversal.util.enums.TipoEmpresa;

public class ConsultarEmpresaModel {

	private Date fechaInicio;
	private Date fechaFin;
	private List<EmpresaResumen> listaEmpresas;

	private boolean busquedaRealizada;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public boolean validarFechas() {
		if (fechaInicio.after(fechaFin))
			return false;
		else
			return true;
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

	public List<EmpresaResumen> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<EmpresaResumen> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public boolean isBusquedaRealizada() {
		return busquedaRealizada;
	}

	public void setBusquedaRealizada(boolean busquedaRealizada) {
		this.busquedaRealizada = busquedaRealizada;
	}

	public String tipoEmpresaLetras(String tipoEmpresa) {
		if (tipoEmpresa != null && !tipoEmpresa.isEmpty())
			return TipoEmpresa.tipoEmpresaLetras(tipoEmpresa);
		else
			return "";
	}

}
