package pe.bn.com.sate.application.model;

import java.util.Date;

import pe.bn.com.sate.transversal.configuration.security.SecurityContextFacade;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.enums.TipoEmpresa;

public class DesafiliarEmpresaModel {

	private String rucVerificado;

	private String detalle;
	private String motivo;
	private Empresa empresa;
	private Empresa empresaPdf;
	private Representante representante;
	private EstadoEmpresa estadoEmpresa;

	public DesafiliarEmpresaModel() {
		inicializarEmpresa();
		inicializarEmpresaPdf();
		inicializarRepresentante();
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		if (detalle.length() > 150) {
			this.detalle = detalle.substring(0, 150);
		} else {
			this.detalle = detalle;
		}
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public EstadoEmpresa getEstadoEmpresa() {
		return estadoEmpresa;
	}

	public void setEstadoEmpresa(EstadoEmpresa estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void inicializarEmpresa() {
		estadoEmpresa = null;
		rucVerificado = "";
		detalle = "";
		motivo = "";
		empresa = new Empresa();
	}

	public void inicializarEmpresaPdf() {
		empresaPdf = new Empresa();
	}

	public void inicializarRepresentante() {
		representante = new Representante();
	}

	public String getRucVerificado() {
		return rucVerificado;
	}

	public void setRucVerificado(String rucVerificado) {
		this.rucVerificado = rucVerificado;
	}

	public void iniciarDatosBloqueoEmpresa(Long idEstadoEmpresa) {
		estadoEmpresa = new EstadoEmpresa();
		estadoEmpresa.setIdEmpresa(empresa.getId());
		estadoEmpresa.setDetalle(detalle);
		estadoEmpresa.setMotivo(motivo);
		estadoEmpresa.setFechaDesafiliacion(new Date());
		estadoEmpresa.setUsuarioDesafiliacion(UsefulWebApplication
				.obtenerUsuario().getUsername());
		estadoEmpresa.setId(idEstadoEmpresa);
	}

	public Empresa getEmpresaPdf() {
		return empresaPdf;
	}

	public void setEmpresaPdf(Empresa empresaPdf) {
		this.empresaPdf = empresaPdf;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public boolean validarRuc() {
		if (empresa.getRuc().equals(rucVerificado)) {
			return true;
		} else {
			return false;
		}
	}

	public String tipoEmpresaLetras() {
		if (empresa != null && empresa.getTipo() != null
				&& !empresa.getTipo().isEmpty())
			return TipoEmpresa.tipoEmpresaLetras(empresa.getTipo());
		else
			return "";
	}

	public boolean validarEmpresaPdf() {
		if (empresaPdf == null || empresaPdf.getRuc() == null
				|| empresaPdf.getRuc().isEmpty())
			return false;
		else
			return true;
	}
}
