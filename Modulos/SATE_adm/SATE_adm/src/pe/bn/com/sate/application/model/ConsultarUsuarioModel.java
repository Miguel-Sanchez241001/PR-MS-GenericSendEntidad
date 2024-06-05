package pe.bn.com.sate.application.model;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.Actividad;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;
import pe.bn.com.sate.transversal.util.enums.PerfilUsuario;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;

public class ConsultarUsuarioModel {

	private Date fechaInicio;
	private Date fechaFin;
	private String ruc;
	private String rucVerificado;
	private List<Representante> listaRepresentantes;
	private Representante representanteSeleccionado;
	private List<UsuarioHistoricoRepresentante> listaUsuarios;
	private List<Actividad> listaActividadesSeleccionada;

	private boolean busquedaRealizada;

	public ConsultarUsuarioModel() {
		inicializarObjetos();
	}

	private void inicializarObjetos() {
		ruc = "";
		rucVerificado = "";
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

	public List<UsuarioHistoricoRepresentante> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(
			List<UsuarioHistoricoRepresentante> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Representante getRepresentanteSeleccionado() {
		return representanteSeleccionado;
	}

	public void setRepresentanteSeleccionado(
			Representante representanteSeleccionado) {
		this.representanteSeleccionado = representanteSeleccionado;
	}

	public boolean isBusquedaRealizada() {
		return busquedaRealizada;
	}

	public void setBusquedaRealizada(boolean busquedaRealizada) {
		this.busquedaRealizada = busquedaRealizada;
	}

	public String perfilUsuarioLetras(int perfilUsuario) {
		if (perfilUsuario != 0)
			return PerfilUsuario.perfilUsuarioLetras(perfilUsuario);
		else
			return "";
	}

	public String tipoDocumentoLetras(String tipoDocumento) {
		if (tipoDocumento != null && !tipoDocumento.isEmpty())
			return TipoDocumento.tipoDocumentoBducLetras(tipoDocumento);
		else
			return "";
	}

	public String getRucVerificado() {
		return rucVerificado;
	}

	public void setRucVerificado(String rucVerificado) {
		this.rucVerificado = rucVerificado;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public boolean validarRuc() {
		if (rucVerificado.equals(ruc))
			return true;
		else
			return false;
	}

	public void limpiarListaRepresentantes() {
		busquedaRealizada = false;
		representanteSeleccionado = null;
		listaRepresentantes = null;
	}

	public void limpiarListaUsuarios() {
		listaUsuarios = null;
		busquedaRealizada = false;
	}

	public List<Actividad> getListaActividadesSeleccionada() {
		return listaActividadesSeleccionada;
	}

	public void setListaActividadesSeleccionada(
			List<Actividad> listaActividadesSeleccionada) {
		this.listaActividadesSeleccionada = listaActividadesSeleccionada;
	}

}
