package pe.bn.com.sate.application.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import pe.bn.com.sate.transversal.dto.external.bntablas.Ubigeo;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.DiscadoDirecto;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoEmpresa;

@FacesValidator("afiliarServicioValidator")
public class AfiliarServicioModel implements Validator {

	private String ruc;
	private Empresa empresa;
	private Empresa empresaPdf;
	private String mensajeError;

	private Map<String, List<String>> listaDocumentosPersonas;
	private List<TipoDocumento> listaTipoDocumento;
	private List<DiscadoDirecto> listaDiscadoDirecto;
	private List<TipoEmpresa> listaTipoEmpresa;

	private List<Ubigeo> departamentos;
	private List<Ubigeo> provincias;
	private List<Ubigeo> distritos;
	private String departamentoSeleccionado;
	private String provinciaSeleccionado;
	private String distritoSeleccionado;

	private boolean empresaSinUbigeo;

	private int pasoActual;

	private List<Firmante> listaFirmantes;

	public AfiliarServicioModel() {
		listaTipoDocumento = Arrays.asList(TipoDocumento.values());
		listaDiscadoDirecto = Arrays.asList(DiscadoDirecto.values());
		listaTipoEmpresa = Arrays.asList(TipoEmpresa.values());
		inicializarEmpresa();
		inicializarRepresentante();
		inicializarEmpresaPdf();
		empresaSinUbigeo = false;
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

	public void inicializarEmpresa() {
		empresa = new Empresa();
		departamentoSeleccionado = null;
		provinciaSeleccionado = null;
		distritoSeleccionado = null;
		empresaSinUbigeo = false;
		departamentoSeleccionado = null;
		provinciaSeleccionado = null;
		distritoSeleccionado = null;
		empresaSinUbigeo = false;
	}

	public void inicializarEmpresaPdf() {
		empresaPdf = new Empresa();
	}

	public void inicializarRepresentante() {
		empresa.setRepresentante(new Representante());
	}

	public boolean validarRuc() {
		if (empresa != null && empresa.getRuc() != null
				&& empresa.getRuc().equals(ruc)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validarRepresentante() {
		if (empresa.getRepresentante() != null
				&& empresa.getRepresentante().getTipoDocumento() != null
				&& empresa.getRepresentante().getNumeroDocumento() != null
				&& empresa.getRepresentante().getNombres() != null) {
			return true;
		} else {
			return false;
		}
	}

	public Map<String, List<String>> getListaDocumentosPersonas() {
		return listaDocumentosPersonas;
	}

	public void setListaDocumentosPersonas(
			Map<String, List<String>> listaDocumentosPersonas) {
		this.listaDocumentosPersonas = listaDocumentosPersonas;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<DiscadoDirecto> getListaDiscadoDirecto() {
		return listaDiscadoDirecto;
	}

	public void setListaDiscadoDirecto(List<DiscadoDirecto> listaDiscadoDirecto) {
		this.listaDiscadoDirecto = listaDiscadoDirecto;
	}

	public int getPasoActual() {
		return pasoActual;
	}

	public void setPasoActual(int pasoActual) {
		this.pasoActual = pasoActual;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public List<TipoEmpresa> getListaTipoEmpresa() {
		return listaTipoEmpresa;
	}

	public void setListaTipoEmpresa(List<TipoEmpresa> listaTipoEmpresa) {
		this.listaTipoEmpresa = listaTipoEmpresa;
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

	public Empresa getEmpresaPdf() {
		return empresaPdf;
	}

	public void setEmpresaPdf(Empresa empresaPdf) {
		this.empresaPdf = empresaPdf;
	}

	public boolean validarEmpresaPdf() {
		if (empresaPdf == null || empresaPdf.getRuc() == null
				|| empresaPdf.getRuc().isEmpty())
			return false;
		else
			return true;
	}

	public boolean isEmpresaSinUbigeo() {
		return empresaSinUbigeo;
	}

	public void setEmpresaSinUbigeo(boolean empresaSinUbigeo) {
		this.empresaSinUbigeo = empresaSinUbigeo;
	}

	public List<Ubigeo> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Ubigeo> departamentos) {
		this.departamentos = departamentos;
	}

	public List<Ubigeo> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Ubigeo> provincias) {
		this.provincias = provincias;
	}

	public List<Ubigeo> getDistritos() {
		return distritos;
	}

	public void setDistritos(List<Ubigeo> distritos) {
		this.distritos = distritos;
	}

	public String getProvinciaSeleccionado() {
		return provinciaSeleccionado;
	}

	public void setProvinciaSeleccionado(String provinciaSeleccionado) {
		this.provinciaSeleccionado = provinciaSeleccionado;
	}

	public String getDistritoSeleccionado() {
		return distritoSeleccionado;
	}

	public void setDistritoSeleccionado(String distritoSeleccionado) {
		this.distritoSeleccionado = distritoSeleccionado;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public boolean validarUbigeo() {
		if ((StringUtil.isEmpty(empresa.getUbigeo()))
				&& (StringUtil.parseString(departamentoSeleccionado).equals(
						ConstantesGenerales.DEPARTAMENTO_VACIO)
						|| StringUtil.parseString(distritoSeleccionado).equals(
								ConstantesGenerales.DISTRITO_VACIO) || StringUtil
						.parseString(provinciaSeleccionado).equals(
								ConstantesGenerales.PROVINCIA_VACIO))) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (((String) value).equals(ConstantesGenerales.DEPARTAMENTO_VACIO)) {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, "",
					"Seleccione un departamento");
		}
		if (((String) value).equals(ConstantesGenerales.PROVINCIA_VACIO)) {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, "",
					"Seleccione una provincia");
		}
		if (((String) value).equals(ConstantesGenerales.DISTRITO_VACIO)) {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, "",
					"Seleccione un distrito");
		}
	}

}
