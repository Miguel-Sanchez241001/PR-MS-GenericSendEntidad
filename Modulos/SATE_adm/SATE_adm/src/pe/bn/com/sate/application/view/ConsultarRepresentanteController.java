package pe.bn.com.sate.application.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.ConsultarRepresentanteModel;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.facade.ConsultasFacade;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Controller("consultarRepresentanteController")
@Scope("view")
public class ConsultarRepresentanteController {

	private final static Logger logger = Logger.getLogger(ConsultarRepresentanteController.class);

	@Autowired
	private ConsultasFacade consultasFacade;

	private ConsultarRepresentanteModel consultarRepresentanteModel;

	@PostConstruct
	public void init() {
		consultarRepresentanteModel = new ConsultarRepresentanteModel();
	}

	public void buscarRepresentantes() {
		logger.info("[ConsultarRepresentanteController] - Iniciando método buscarRepresentantes");
		if (consultarRepresentanteModel.validarFechas()) {
			try {

				consultarRepresentanteModel
						.setListaRepresentantes(consultasFacade
								.buscarHistorialRepresentantes(
										consultarRepresentanteModel.getRuc(),
										consultarRepresentanteModel
												.getFechaInicio(),
										consultarRepresentanteModel
												.getFechaFin()));

				consultarRepresentanteModel
						.setBusquedaRealizada(consultarRepresentanteModel
								.getListaRepresentantes() != null
								&& !consultarRepresentanteModel
										.getListaRepresentantes().isEmpty());

			} catch (InternalServiceException ise) {
				consultarRepresentanteModel.inicializarTabla();
				logger.error(ise.getMessage());
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
						ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
			} catch (ServiceException se) {
				consultarRepresentanteModel.inicializarTabla();
				logger.error(se.getMessage());
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR, se.getMessage(),
						se.getMessage());
			} finally {
				UsefulWebApplication
						.actualizarComponente("formConsultaRepresentante:pgExporter");
				UsefulWebApplication
						.actualizarComponente("formConsultaRepresentante:tablaRepresentante");
			}
		} else {
			UsefulWebApplication
					.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR, "",
							"Fecha inicial debe estar antes o ser igual que la fecha final.");
		}
		logger.info("[ConsultarRepresentanteController] - Finalizando método buscarRepresentantes");

	}

	public ConsultarRepresentanteModel getConsultarRepresentanteModel() {
		return consultarRepresentanteModel;
	}

	public void setConsultarRepresentanteModel(
			ConsultarRepresentanteModel consultarRepresentanteModel) {
		this.consultarRepresentanteModel = consultarRepresentanteModel;
	}

}
