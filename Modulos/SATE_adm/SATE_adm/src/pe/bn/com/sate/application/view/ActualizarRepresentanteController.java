package pe.bn.com.sate.application.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.ActualizarRepresentanteModel;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceIGWException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceMessageException;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.facade.ServicioFacade;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Controller("actualizarRepresentanteController")
@Scope("view")
public class ActualizarRepresentanteController {

	private final Logger logger = Logger
			.getLogger(ActualizarRepresentanteController.class);

	private ActualizarRepresentanteModel actualizarRepresentanteModel;

	@Autowired
	private ServicioFacade servicioFacade;

	@PostConstruct
	public void init() {
		actualizarRepresentanteModel = new ActualizarRepresentanteModel();
	}

	public void buscarEmpresa() {
		try {
			logger.info("[ActualizarRepresentanteController] - Iniciando método buscarEmpresa");
			actualizarRepresentanteModel
					.setEmpresa(servicioFacade
							.obtenerEmpresaRepresentanteActual(actualizarRepresentanteModel
									.getEmpresa().getRuc()));

			actualizarRepresentanteModel
					.setRucVerificado(actualizarRepresentanteModel.getEmpresa()
							.getRuc());
			logger.info("[ActualizarRepresentanteController] - Finalizando método buscarEmpresa - ");
		} catch (InternalServiceException ie) {
			logger.error(ie.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		} catch (ServiceException se) {
			limpiarFormularioMenosRuc();
			logger.error(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, se.getMessage(),
					se.getMessage());
		}
	}

	public void buscarDocumentosPorTipoDocumento() {
		logger.info("[ActualizarRepresentanteController] - Iniciando método buscarDocumentosPorTipoDocumento");
		actualizarRepresentanteModel
				.setListaNumeroDocumento(actualizarRepresentanteModel
						.getListaDocumentosPersonas().get(
								actualizarRepresentanteModel
										.getTipoDocumentoSeleccionado()));
		logger.info("[ActualizarRepresentanteController] - Finalizando método buscarDocumentosPorTipoDocumento");
	}

	public void limpiarFormulario() {
		actualizarRepresentanteModel.inicializarEmpresa();
	}

	public void limpiarFormularioMenosRuc() {
		String rucTemporal = actualizarRepresentanteModel.getEmpresa().getRuc();
		actualizarRepresentanteModel.inicializarEmpresa();
		actualizarRepresentanteModel.getEmpresa().setRuc(rucTemporal);
	}

	public void actualizarRepresentante() {
		try {
			logger.info("[ActualizarRepresentanteController] - Iniciando método actualizarRepresentante");
			servicioFacade
					.actualizarEmpresaRepresentante(actualizarRepresentanteModel
							.getEmpresa());
			limpiarFormulario();
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_INFO, "EXITO!",
					"Representante actualizado!");
			logger.info("[ActualizarRepresentanteController] - Finalizando método actualizarRepresentante");
		} catch (InternalServiceException ie) {
			logger.error(ie.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		} catch (ExternalServiceIGWException esie) {
			logger.error(esie.getMessage());
			UsefulWebApplication
					.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_IGW_ERROR,
							ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_IGW_ERROR);
		} catch (ServiceException ex) {
			logger.error(ex.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, ex.getMessage(),
					ex.getMessage());
		}
	}

	public void obtenerListaFirmantes() {
		try {
			logger.info("[ActualizarRepresentanteController] - Iniciando método obtenerListaFirmantes");
			actualizarRepresentanteModel.setListaFirmantes(servicioFacade
					.obtenerListaFirmantes(actualizarRepresentanteModel
							.getEmpresa().getRuc()));
			logger.info("[ActualizarRepresentanteController] - Finalizando método obtenerListaFirmantes");
		} catch (ExternalServiceMessageException ese) {
			logger.error(ese.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_SARASIGN_ERROR,
					ConstantesGenerales.EXTERNAL_PERSISTENCE_SARASIGN_ERROR);
		}
	}

	public void onRowSelect(SelectEvent event) {
		try {
			Representante representante = servicioFacade
					.convertirARepresentante((Firmante) event.getObject());
			servicioFacade.estaRepresentanteDisponible(representante,
					actualizarRepresentanteModel.getEmpresa());
			actualizarRepresentanteModel.getEmpresa().setRepresentante(
					representante);
		} catch (ServiceException se) {
			logger.error(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR, "", se.getMessage());
		}
	}

	public void mostrarConfirmacion() {
		try {
			if (actualizarRepresentanteModel.validarRepresentante())
				if (actualizarRepresentanteModel.validarRuc())
					if (servicioFacade.validarCorreo(
							actualizarRepresentanteModel.getEmpresa()
									.getRepresentante().getCorreoLaboral(),
							actualizarRepresentanteModel.getEmpresa()
									.getRepresentante().getTipoDocumento(),
							actualizarRepresentanteModel.getEmpresa()
									.getRepresentante().getNumeroDocumento()))
						if (servicioFacade.validarCelular(
								actualizarRepresentanteModel.getEmpresa()
										.getRepresentante().getTelefonoMovil(),
								actualizarRepresentanteModel.getEmpresa()
										.getRepresentante().getTipoDocumento(),
								actualizarRepresentanteModel.getEmpresa()
										.getRepresentante()
										.getNumeroDocumento()))

							UsefulWebApplication
									.ejecutar("confirmarGuardarWv.show();");

						else
							UsefulWebApplication
									.mostrarMensajeJSF(
											ConstantesGenerales.SEVERITY_ERROR,
											"",
											"Teléfono celular pertenece a otro usuario");
					else
						UsefulWebApplication.mostrarMensajeJSF(
								ConstantesGenerales.SEVERITY_ERROR, "",
								"Correo laboral pertenece a otro usuario");
				else
					UsefulWebApplication.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
							"El RUC no ha sido verificado");
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

	public ActualizarRepresentanteModel getActualizarRepresentanteModel() {
		return actualizarRepresentanteModel;
	}

	public void setActualizarRepresentanteModel(
			ActualizarRepresentanteModel actualizarRepresentanteModel) {
		this.actualizarRepresentanteModel = actualizarRepresentanteModel;
	}

}
