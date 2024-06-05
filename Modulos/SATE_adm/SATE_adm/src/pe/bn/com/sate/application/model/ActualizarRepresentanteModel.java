package pe.bn.com.sate.application.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.enums.DiscadoDirecto;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoEmpresa;

public class ActualizarRepresentanteModel {

	private String rucVerificado;

	private Empresa empresa;
	// private Representante representante;
	private String tipoDocumentoSeleccionado;
	private String numDocumentoSeleccionado;

	private Map<String, List<String>> listaDocumentosPersonas;
	private List<TipoDocumento> listaTipoDocumento;
	private List<DiscadoDirecto> listaDiscadoDirecto;
	private List<String> listaNumeroDocumento;
	private List<TipoEmpresa> listaTipoEmpresa;

	private List<Firmante> listaFirmantes;

	public ActualizarRepresentanteModel() {
		listaTipoDocumento = Arrays.asList(TipoDocumento.values());
		listaDiscadoDirecto = Arrays.asList(DiscadoDirecto.values());
		listaTipoEmpresa = Arrays.asList(TipoEmpresa.values());
		inicializarEmpresa();
	}

	public boolean validarRepresentante() {
		if (empresa.getRepresentante() == null)
			return true;
		else if (empresa.getRepresentante().getTipoDocumento() != null
				&& empresa.getRepresentante().getNumeroDocumento() != null
				&& empresa.getRepresentante().getNombres() != null)
			return true;
		else
			return false;
	}

	public boolean validarRuc() {
		if (empresa != null && empresa.getRuc() != null
				&& empresa.getRuc().equals(rucVerificado)) {
			return true;
		} else {
			return false;
		}
	}

	public void inicializarEmpresa() {
		empresa = new Empresa();
	}

	public List<Firmante> getListaFirmantes() {
		return listaFirmantes;
	}

	public void setListaFirmantes(List<Firmante> listaFirmantes) {
		this.listaFirmantes = listaFirmantes;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	// public Representante getRepresentante() {
	// return representante;
	// }
	//
	// public void setRepresentante(Representante representante) {
	// this.representante = representante;
	// }

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<String> getListaNumeroDocumento() {
		return listaNumeroDocumento;
	}

	public void setListaNumeroDocumento(List<String> listaNumeroDocumento) {
		this.listaNumeroDocumento = listaNumeroDocumento;
	}

	public String getTipoDocumentoSeleccionado() {
		return tipoDocumentoSeleccionado;
	}

	public void setTipoDocumentoSeleccionado(String tipoDocumentoSeleccionado) {
		this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
	}

	public Map<String, List<String>> getListaDocumentosPersonas() {
		return listaDocumentosPersonas;
	}

	public void setListaDocumentosPersonas(
			Map<String, List<String>> listaDocumentosPersonas) {
		this.listaDocumentosPersonas = listaDocumentosPersonas;
	}

	public String getNumDocumentoSeleccionado() {
		return numDocumentoSeleccionado;
	}

	public void setNumDocumentoSeleccionado(String numDocumentoSeleccionado) {
		this.numDocumentoSeleccionado = numDocumentoSeleccionado;
	}

	public List<DiscadoDirecto> getListaDiscadoDirecto() {
		return listaDiscadoDirecto;
	}

	public void setListaDiscadoDirecto(List<DiscadoDirecto> listaDiscadoDirecto) {
		this.listaDiscadoDirecto = listaDiscadoDirecto;
	}

	public String tipoDocumentoRepresentanteLetras() {
		if (empresa.getRepresentante() != null
				&& empresa.getRepresentante().getTipoDocumento() != null
				&& !empresa.getRepresentante().getTipoDocumento().isEmpty())
			return TipoDocumento.tipoDocumentoBducLetras(empresa
					.getRepresentante().getTipoDocumento());
		else
			return "";
	}

	public String tipoDocumentoFirmanteLetras(String tipoDocumento) {
		if (tipoDocumento != null)
			return TipoDocumento.tipoDocumentoSignLetras(tipoDocumento);
		else
			return "";
	}

	public String getRucVerificado() {
		return rucVerificado;
	}

	public void setRucVerificado(String rucVerificado) {
		this.rucVerificado = rucVerificado;
	}

	public List<TipoEmpresa> getListaTipoEmpresa() {
		return listaTipoEmpresa;
	}

	public void setListaTipoEmpresa(List<TipoEmpresa> listaTipoEmpresa) {
		this.listaTipoEmpresa = listaTipoEmpresa;
	}
}
