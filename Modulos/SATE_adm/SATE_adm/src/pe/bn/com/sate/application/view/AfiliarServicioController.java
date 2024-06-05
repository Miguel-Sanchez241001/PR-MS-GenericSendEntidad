package pe.bn.com.sate.application.view;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.AfiliarServicioModel;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceBducException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceBnTablasException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceDatacomException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceIGWException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceMessageException;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.facade.ServicioFacade;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.Fecha;
import pe.bn.com.sate.transversal.util.Reporte;
import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoReporte;

@Controller("afiliarServicioController")
@Scope("view")
public class AfiliarServicioController {

	private final Logger logger = Logger
			.getLogger(AfiliarServicioController.class);

	private AfiliarServicioModel afiliarServicioModel;

	@Autowired
	private ServicioFacade servicioFacade;

	@PostConstruct
	public void init() {
		afiliarServicioModel = new AfiliarServicioModel();
		buscarDepartamentos();
	}

	private void cargarUbigeo() {
		if (StringUtil.isEmpty(afiliarServicioModel.getEmpresa().getUbigeo()))
			afiliarServicioModel.getEmpresa().setUbigeo(
					afiliarServicioModel.getDepartamentoSeleccionado()
							+ afiliarServicioModel.getProvinciaSeleccionado()
							+ afiliarServicioModel.getDistritoSeleccionado());
	}

	public boolean validarEmpresa() {
		EstadoEmpresa estadoEmpresa = servicioFacade
				.buscarEstadoEmpresa(afiliarServicioModel.getRuc());
		if (estadoEmpresa == null
				|| estadoEmpresa.getFechaDesafiliacion() != null) {
			return true;
		} else {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
					"La empresa ya se encuentra afiliada");
			return false;
		}
	}

	public boolean validarRuc() {
		if (afiliarServicioModel.validarRuc()) {
			return true;
		} else {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
					"El RUC no ha sido verificado");
			return false;
		}
	}

	public void limpiarFormulario() {
		afiliarServicioModel.inicializarEmpresa();
	}
	
	public void limpiarFormularioCompleto() {
		afiliarServicioModel.setRuc("");
		afiliarServicioModel.inicializarEmpresa();
	}

	public void buscarEmpresa() {
		logger.info("[AfiliarServicioController] - Iniciando método buscarEmpresa");
		try {
			if (validarEmpresa()) {
				limpiarFormulario();
				Empresa empresaConsultada = servicioFacade
						.obtenerEmpresa(afiliarServicioModel.getRuc());
				afiliarServicioModel.setEmpresa(empresaConsultada);
				afiliarServicioModel.setEmpresaSinUbigeo(afiliarServicioModel
						.getEmpresa().getUbigeo() == null
						|| afiliarServicioModel.getEmpresa().getUbigeo()
								.isEmpty());

				if (afiliarServicioModel.isEmpresaSinUbigeo())
					buscarDepartamentos();
			}
			logger.info("[AfiliarServicioController] - Finalizando método buscarEmpresa");
		} catch (InternalServiceException ise) {
			logger.error(ise.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		} catch (ExternalServiceDatacomException esde) {
			logger.error(esde.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_DATACOM_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_DATACOM_ERROR);
		} catch (ExternalServiceBducException esbe) {
			logger.error(esbe.getMessage());
			UsefulWebApplication
					.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_BDUC_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_BDUC_ERROR);
		} catch (ServiceException se) {
			logger.error(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, se.getMessage(),
					se.getMessage());
		}
	}

	public void obtenerListaFirmantes() {
		try {
			logger.info("[AfiliarServicioController] - Iniciando método obtenerListaFirmantes");
			afiliarServicioModel.setListaFirmantes(servicioFacade
					.obtenerListaFirmantes(afiliarServicioModel.getEmpresa()
							.getRuc()));
			logger.info("[AfiliarServicioController] - Finalizando método obtenerListaFirmantes");
		} catch (ExternalServiceBnTablasException ese) {
			logger.error(ese.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_BNTABLAS_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_BNTABLAS_ERROR);
		}

	}

	public void onRowSelect(SelectEvent event) {
		try {
			Representante representante = servicioFacade
					.convertirARepresentante((Firmante) event.getObject());
			servicioFacade.estaRepresentanteDisponible(representante,
					afiliarServicioModel.getEmpresa());
			afiliarServicioModel.getEmpresa().setRepresentante(representante);
		} catch (ServiceException se) {
			logger.error(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, "", se.getMessage());
		}

	}

	public void mostrarConfirmacion() {
		try {
			if (afiliarServicioModel.validarRepresentante())
				if (servicioFacade.validarCorreo(afiliarServicioModel
						.getEmpresa().getRepresentante().getCorreoLaboral(),
						afiliarServicioModel.getEmpresa().getRepresentante()
								.getTipoDocumento(), afiliarServicioModel
								.getEmpresa().getRepresentante()
								.getNumeroDocumento()))
					if (servicioFacade.validarCelular(
							afiliarServicioModel.getEmpresa()
									.getRepresentante().getTelefonoMovil(),
							afiliarServicioModel.getEmpresa()
									.getRepresentante().getTipoDocumento(),
							afiliarServicioModel.getEmpresa()
									.getRepresentante().getNumeroDocumento()))
						UsefulWebApplication
								.ejecutar("confirmarGuardarWv.show();");
					else
						UsefulWebApplication.mostrarMensajeJSF(
								ConstantesGenerales.SEVERITY_ERROR, "",
								"Teléfono celular pertenece a otro usuario");
				else
					UsefulWebApplication.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR, "",
							"Correo laboral pertenece a otro usuario");
			else
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
						"Seleccione un representante");
		} catch (InternalServiceException ise) {
			logger.error(ise.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		}
	}

	public void avanzarPaso() {
		if (afiliarServicioModel.validarUbigeo()) {
			cargarUbigeo();
			if (validarRuc()) {
				afiliarServicioModel.setPasoActual(1);
				UsefulWebApplication.ejecutar("activar("
						+ afiliarServicioModel.getPasoActual() + ")");
			}
		}
	}

	public void retrocederPaso() {
		UsefulWebApplication.ejecutar("desactivar("
				+ afiliarServicioModel.getPasoActual() + ")");
		afiliarServicioModel.setPasoActual(0);
	}

	public void limpiarUbigeo() {
		afiliarServicioModel.getEmpresa().setUbigeo("");
	}

	public void afiliarServicio() {

		try {
			logger.info("[AfiliarServicioController] - Iniciando método afiliarServicio");
			servicioFacade.registrarEmpresaRepresentante(afiliarServicioModel
					.getEmpresa());
			afiliarServicioModel.setEmpresaPdf(afiliarServicioModel
					.getEmpresa());

			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_INFO, "EXITO!",
					"Empresa y representante afiliados!");
			logger.info("[AfiliarServicioController] - Finalizando método afiliarServicio");
		} catch (InternalServiceException ise) {
			logger.error(ise.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		} catch (ExternalServiceMessageException esme) {
			logger.error(esme.getMessage());
			UsefulWebApplication
					.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_MSG_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_MSG_ERROR);
		} catch (ExternalServiceIGWException esie) {
			logger.error(esie);
			UsefulWebApplication
					.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_IGW_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_IGW_ERROR);
		} catch (ServiceException se) {
			logger.error(se);
			afiliarServicioModel.setMensajeError(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, se.getMessage(),
					se.getMessage());
		} finally {
			limpiarFormularioCompleto();
			retrocederPaso();
			UsefulWebApplication.ejecutar("confirmarGuardarWv.hide();");
			UsefulWebApplication.actualizarComponente("formAfiliacion");
		}
	}

	public void buscarDepartamentos() {
		try {
			logger.info("[AfiliarServicioController] - Iniciando método buscarDepartamentos");
			afiliarServicioModel.setDepartamentos(servicioFacade
					.buscarDepartamentos());
			logger.info("[AfiliarServicioController] - Finalizando método buscarDepartamentos");
		} catch (ExternalServiceBnTablasException ex) {
			logger.error(ex.getMessage(), ex);
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_BNTABLAS_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_BNTABLAS_ERROR);
		}
	}

	public void buscarProvincias() {
		logger.info("[AfiliarServicioController] - Iniciando método AfiliarServicioController");
		afiliarServicioModel.setProvinciaSeleccionado(null);
		afiliarServicioModel.setDistritoSeleccionado(null);
		if (afiliarServicioModel.getDepartamentoSeleccionado().equals(
				ConstantesGenerales.DEPARTAMENTO_VACIO)) {
			afiliarServicioModel.setProvincias(null);
			afiliarServicioModel.setDistritos(null);
		} else {
			afiliarServicioModel.setProvincias(servicioFacade
					.buscarProvinciasPorDepartamento(afiliarServicioModel
							.getDepartamentoSeleccionado()));
			afiliarServicioModel.setDistritos(null);
		}
		logger.info("[AfiliarServicioController] - Finalizando método AfiliarServicioController");
	}

	public void buscarDistritos() {
		logger.info("[AfiliarServicioController] - Iniciando método buscarDistritos");
		afiliarServicioModel.setDistritoSeleccionado(null);
		if (afiliarServicioModel.getProvinciaSeleccionado().equals(
				ConstantesGenerales.PROVINCIA_VACIO)) {
			afiliarServicioModel.setDistritos(null);
		} else {
			afiliarServicioModel.setDistritos(servicioFacade
					.buscarDistritosPorProvincia(
							afiliarServicioModel.getDepartamentoSeleccionado(),
							afiliarServicioModel.getProvinciaSeleccionado()));
		}
		logger.info("[AfiliarServicioController] - Finalizando método buscarDistritos");
	}

	public void generarPdf() {
		if (afiliarServicioModel.validarEmpresaPdf()) {
			logger.info("[AfiliarServicioController] - Iniciando método generarPdf");
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();

			parametros.put("tipoReporte", "1");
			parametros
					.put("ruc", afiliarServicioModel.getEmpresaPdf().getRuc());
			parametros.put("razonSocial", afiliarServicioModel.getEmpresaPdf()
					.getRazonSocial());
			parametros.put("direccion", afiliarServicioModel.getEmpresaPdf()
					.getDireccion());
			parametros.put(
					"ubigeo",
					servicioFacade
							.buscarLocalidad(
									afiliarServicioModel.getEmpresaPdf()
											.getUbigeo(), 1).getDescripcion()
							.trim()
							+ " - "
							+ servicioFacade
									.buscarLocalidad(
											afiliarServicioModel
													.getEmpresaPdf()
													.getUbigeo(), 2)
									.getDescripcion().trim()
							+ " - "
							+ servicioFacade
									.buscarLocalidad(
											afiliarServicioModel
													.getEmpresaPdf()
													.getUbigeo(), 3)
									.getDescripcion().trim());
			parametros.put("referencia", afiliarServicioModel.getEmpresaPdf()
					.getReferencia().trim());
			parametros.put("telefonoFijo", afiliarServicioModel.getEmpresaPdf()
					.getTelefonoFijo());
			parametros.put("razonSocial", afiliarServicioModel.getEmpresaPdf()
					.getRazonSocial());

			parametros.put(
					"tipoDocumento",
					TipoDocumento.tipoDocumentoBducLetras(afiliarServicioModel
							.getEmpresaPdf().getRepresentante()
							.getTipoDocumento()));
			parametros.put("numeroDocumento", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().getNumeroDocumento());
			parametros.put("apellidosNombres", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().nombreCompleto());
			parametros.put("correoLaboral", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().getCorreoLaboral());
			parametros.put("codigoTelefono", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().getCodigoTelefono());
			parametros.put("telefonoLaboral", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().getTelefonoFijo());
			parametros.put("anexo", afiliarServicioModel.getEmpresaPdf()
					.getRepresentante().getAnexo());
			parametros.put("telefonoCelular", afiliarServicioModel
					.getEmpresaPdf().getRepresentante().getTelefonoMovil());
			parametros.put("razonSocialCorto", afiliarServicioModel
					.getEmpresaPdf().getNombreCorto());
			parametros.put("nombres", afiliarServicioModel.getEmpresaPdf()
					.getRepresentante().getNombres());
			parametros.put(
					"apellidos",
					afiliarServicioModel
							.getEmpresaPdf()
							.getRepresentante()
							.getApellidoPaterno()
							.concat(" ".concat(afiliarServicioModel
									.getEmpresaPdf().getRepresentante()
									.getApellidoMaterno())));

			parametros.put("fecha", Fecha.transformarString(new Date()));
			Reporte.mostrarReportePDF(TipoReporte.REPORTE_SOLICITUD_AFILIACION,
					parametros, null, context, servletContext);
			afiliarServicioModel.inicializarEmpresaPdf();
			afiliarServicioModel.inicializarRepresentante();
			logger.info("[AfiliarServicioController] - Finalizando método generarPdf");
		} else {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					afiliarServicioModel.getMensajeError(),
					afiliarServicioModel.getMensajeError());
		}
	}

	public AfiliarServicioModel getAfiliarServicioModel() {
		return afiliarServicioModel;
	}

	public void setAfiliarServicioModel(
			AfiliarServicioModel afiliarServicioModel) {
		this.afiliarServicioModel = afiliarServicioModel;
	}
	
}
