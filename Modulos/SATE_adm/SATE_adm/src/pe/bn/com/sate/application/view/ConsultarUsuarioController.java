package pe.bn.com.sate.application.view;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.ConsultarUsuarioModel;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.facade.ConsultasFacade;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Controller("consultarUsuarioController")
@Scope("view")
public class ConsultarUsuarioController {

	private final Logger logger = Logger
			.getLogger(ConsultarUsuarioController.class);

	@Autowired
	private ConsultasFacade consultasFacade;

	private ConsultarUsuarioModel consultarUsuarioModel;

	@PostConstruct
	public void init() {
		consultarUsuarioModel = new ConsultarUsuarioModel();
	}

	public void buscarRepresentantes() {
		logger.info("[ConsultarUsuarioController] - Iniciando método buscarRepresentantes");
		if (consultarUsuarioModel.validarFechas()) {
			try {
				consultarUsuarioModel.setListaRepresentantes(consultasFacade
						.buscarRepresentantes(consultarUsuarioModel.getRuc(),
								consultarUsuarioModel.getFechaInicio(),
								consultarUsuarioModel.getFechaFin()));
				consultarUsuarioModel.setRucVerificado(consultarUsuarioModel
						.getRuc());

				UsefulWebApplication
						.ejecutar("PF('SeleccionarRepresentanteWv').show()");

			} catch (InternalServiceException ise) {
				consultarUsuarioModel.limpiarListaRepresentantes();
				consultarUsuarioModel.limpiarListaUsuarios();
				logger.error(ise.getMessage());
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
						ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
			} catch (ServiceException se) {
				consultarUsuarioModel.limpiarListaRepresentantes();
				consultarUsuarioModel.limpiarListaUsuarios();
				logger.error(se.getMessage());
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR, se.getMessage(),
						se.getMessage());
			} finally {
				UsefulWebApplication
						.actualizarComponente("formConsultaUsuario:pgExporter");
				UsefulWebApplication
						.actualizarComponente("formConsultaUsuario:tablaUsuario");
			}
		} else {
			UsefulWebApplication
					.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
							"Fecha inicial debe estar antes o ser igual que la fecha final.");
		}
		logger.info("[ConsultarUsuarioController] - Finalizando método buscarRepresentantes");
	}

	public void buscarUsuarios() {
		logger.info("[ConsultarUsuarioController] - Iniciando método buscarUsuarios");
		if (consultarUsuarioModel.validarFechas()) {
			if (consultarUsuarioModel.validarRuc()) {
				if (consultarUsuarioModel.getRepresentanteSeleccionado() != null) {
					try {
						consultarUsuarioModel.setListaUsuarios(consultasFacade
								.buscarUsuariosHistoricos(consultarUsuarioModel
										.getFechaInicio(),
										consultarUsuarioModel.getFechaFin(),
										consultarUsuarioModel
												.getRepresentanteSeleccionado()
												.getId()));
						consultarUsuarioModel
								.setBusquedaRealizada(consultarUsuarioModel
										.getListaUsuarios() != null
										&& !consultarUsuarioModel
												.getListaUsuarios().isEmpty());

					} catch (InternalServiceException ise) {
						consultarUsuarioModel.limpiarListaUsuarios();
						logger.error(ise.getMessage());
						UsefulWebApplication.mostrarMensajeJSF(
								ConstantesGenerales.SEVERITY_ERROR,
								ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
								ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
					} catch (ServiceException se) {
						consultarUsuarioModel.limpiarListaUsuarios();
						logger.error(se.getMessage());
						UsefulWebApplication.mostrarMensajeJSF(
								ConstantesGenerales.SEVERITY_ERROR,
								se.getMessage(), se.getMessage());
					} finally {
						UsefulWebApplication
								.actualizarComponente("formConsultaUsuario:pgExporter");
						UsefulWebApplication
								.actualizarComponente("formConsultaUsuario:tablaUsuario");
					}

				} else {
					UsefulWebApplication.mostrarMensajeJSF(
							ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
							"Seleccione un representante.");
				}
			} else {
				consultarUsuarioModel.limpiarListaRepresentantes();
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
						"Seleccione un representante del RUC ingresado.");
			}
		} else {
			UsefulWebApplication
					.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
							"Fecha inicial debe estar antes o ser igual que la fecha final.");
		}
		logger.info("[ConsultarUsuarioController] - Finalizando método buscarUsuarios");
	}

	public void onRowSelect(SelectEvent event) {
		consultarUsuarioModel.limpiarListaUsuarios();
		consultarUsuarioModel
				.setRepresentanteSeleccionado((Representante) event.getObject());
	}

	public void buscarActividades(Long idUsuario, Date fechaInicio,
			Date fechaFin) {
		try {
			consultarUsuarioModel
					.setListaActividadesSeleccionada(consultasFacade
							.buscarActividades(Long.valueOf(idUsuario),
									fechaInicio, fechaFin));
		} catch (InternalServiceException ise) {
			logger.error(ise.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		}

	}

	public ConsultarUsuarioModel getConsultarUsuarioModel() {
		return consultarUsuarioModel;
	}

	public void setConsultarUsuarioModel(
			ConsultarUsuarioModel consultarUsuarioModel) {
		this.consultarUsuarioModel = consultarUsuarioModel;
	}

}
