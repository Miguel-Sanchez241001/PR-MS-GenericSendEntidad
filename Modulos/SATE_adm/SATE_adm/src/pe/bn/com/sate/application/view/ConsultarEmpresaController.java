package pe.bn.com.sate.application.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.ConsultarEmpresaModel;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.EmpresaService;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Controller("consultarEmpresaController")
@Scope("view")
public class ConsultarEmpresaController {

	private final static Logger logger = Logger.getLogger(CabeceraController.class);

	private @Autowired
	EmpresaService empresaService;

	private ConsultarEmpresaModel consultarEmpresaModel;

	@PostConstruct
	public void init() {
		consultarEmpresaModel = new ConsultarEmpresaModel();
	}

	public void buscarEmpresas() {
		try {
			logger.info("[ConsultarEmpresaController] - Iniciando método buscarEmpresas");
			if (consultarEmpresaModel.validarFechas()) {
				consultarEmpresaModel.setListaEmpresas(empresaService
						.buscarHistorialEmpresas(
								consultarEmpresaModel.getFechaInicio(),
								consultarEmpresaModel.getFechaFin()));
				consultarEmpresaModel
						.setBusquedaRealizada(consultarEmpresaModel
								.getListaEmpresas() != null
								&& !consultarEmpresaModel.getListaEmpresas()
										.isEmpty());
				if (consultarEmpresaModel.isBusquedaRealizada()) {
					UsefulWebApplication
							.actualizarComponente("formConsultaEmpresa:pgExporter");
					UsefulWebApplication
							.actualizarComponente("formConsultaEmpresa:tablaEmpresa");
				} else {
					UsefulWebApplication
							.mostrarMensajeJSF(
									ConstantesGenerales.SEVERITY_ERROR,
									ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
									"No hay empresas con criterios de busqueda seleccionados");
				}
			} else {
				UsefulWebApplication
						.mostrarMensajeJSF(
								ConstantesGenerales.SEVERITY_ERROR,
								ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
								"Fecha inicial debe estar antes o ser igual que la fecha final.");
			}
			logger.info("[ConsultarEmpresaController] - Finalizando método buscarEmpresas");
		} catch (InternalServiceException ise) {
			logger.error(ise.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR,
					ConstantesGenerales.INTERNAL_PERSISTENCE_ERROR);
		}
	}

	public ConsultarEmpresaModel getConsultarEmpresaModel() {
		return consultarEmpresaModel;
	}

	public void setConsultarEmpresaModel(
			ConsultarEmpresaModel consultarEmpresaModel) {
		this.consultarEmpresaModel = consultarEmpresaModel;
	}
}
