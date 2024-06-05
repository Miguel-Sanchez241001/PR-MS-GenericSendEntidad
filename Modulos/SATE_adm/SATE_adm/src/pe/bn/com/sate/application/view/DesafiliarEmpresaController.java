package pe.bn.com.sate.application.view;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.DesafiliarEmpresaModel;
import pe.bn.com.sate.infrastructure.facade.ServicioFacade;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;
import pe.bn.com.sate.transversal.util.Fecha;
import pe.bn.com.sate.transversal.util.Reporte;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoReporte;

@Controller("desafiliarEmpresaController")
@Scope("view")
public class DesafiliarEmpresaController {

	private final Logger logger = Logger
			.getLogger(DesafiliarEmpresaController.class);

	private DesafiliarEmpresaModel desafiliarEmpresaModel;

	@Autowired
	private ServicioFacade servicioFacade;

	@PostConstruct
	public void init() {
		desafiliarEmpresaModel = new DesafiliarEmpresaModel();
	}

	public void bloquearEmpresa() {
		try {
			logger.info("[DesafiliarEmpresaController] - Iniciando método bloquearEmpresa");
			EstadoEmpresa estadoEmpresa = servicioFacade
					.buscarEstadoEmpresa(desafiliarEmpresaModel.getEmpresa()
							.getRuc());
			if (estadoEmpresa != null
					&& estadoEmpresa.getFechaDesafiliacion() == null) {
				desafiliarEmpresaModel.iniciarDatosBloqueoEmpresa(estadoEmpresa
						.getId());

				servicioFacade.bloquearEmpresa(desafiliarEmpresaModel
						.getEstadoEmpresa());

				desafiliarEmpresaModel.setEmpresaPdf(desafiliarEmpresaModel
						.getEmpresa());

				limpiarFormulario();
				UsefulWebApplication.ejecutar("confirmDesafiliacionWv.hide();");
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_INFO, "EXITO!",
						"Empresa desafiliada!");
				UsefulWebApplication.actualizarComponente("formEmpresa");
			}
			logger.info("[DesafiliarEmpresaController] - Finalizando método bloquearEmpresa");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void buscarEmpresa() {
		logger.info("[DesafiliarEmpresaController] - Iniciando método buscarEmpresa");
		EstadoEmpresa estadoEmpresa = servicioFacade
				.buscarEstadoEmpresa(desafiliarEmpresaModel.getEmpresa()
						.getRuc());

		if (estadoEmpresa != null
				&& estadoEmpresa.getFechaDesafiliacion() == null) {
			desafiliarEmpresaModel
					.setEmpresa(servicioFacade
							.buscarEmpresa(desafiliarEmpresaModel.getEmpresa()
									.getRuc()));
			desafiliarEmpresaModel.setRepresentante(servicioFacade
					.obtenerRepresentanteActual(desafiliarEmpresaModel
							.getEmpresa().getId()));

			desafiliarEmpresaModel.setRucVerificado(desafiliarEmpresaModel
					.getEmpresa().getRuc());

		} else {
			limpiarFormularioMenosRuc();
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
					"La empresa no se encuentra afiliada");
		}
		logger.info("[DesafiliarEmpresaController] - Finalizando método buscarEmpresa");
	}

	public void mostrarConfirmacion() {
		if (desafiliarEmpresaModel.validarRuc())
			UsefulWebApplication.ejecutar("confirmDesafiliacionWv.show();");
		else {
			UsefulWebApplication.mostrarMensajeJSF(
					ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.TITULO_ERROR_AGREGAR_PARAMETRO,
					"El RUC no ha sido verificado");
		}
	}

	public void limpiarFormulario() {
		desafiliarEmpresaModel.inicializarEmpresa();
	}

	public void limpiarFormularioMenosRuc() {
		String rucTemporal = desafiliarEmpresaModel.getEmpresa().getRuc();
		desafiliarEmpresaModel.inicializarEmpresa();
		desafiliarEmpresaModel.getEmpresa().setRuc(rucTemporal);
	}

	public void generarPdf() {
		if (desafiliarEmpresaModel.validarEmpresaPdf()) {
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();

			parametros.put("tipoReporte", "1");
			parametros.put("ruc", desafiliarEmpresaModel.getEmpresaPdf()
					.getRuc());
			parametros.put("razonSocial", desafiliarEmpresaModel
					.getEmpresaPdf().getRazonSocial());
			parametros.put("direccion", desafiliarEmpresaModel.getEmpresaPdf()
					.getDireccion());
			parametros.put(
					"ubigeo",
					servicioFacade
							.buscarLocalidad(
									desafiliarEmpresaModel.getEmpresaPdf()
											.getUbigeo(), 1).getDescripcion()
							.trim()
							+ " - "
							+ servicioFacade
									.buscarLocalidad(
											desafiliarEmpresaModel
													.getEmpresaPdf()
													.getUbigeo(), 2)
									.getDescripcion().trim()
							+ " - "
							+ servicioFacade
									.buscarLocalidad(
											desafiliarEmpresaModel
													.getEmpresaPdf()
													.getUbigeo(), 3)
									.getDescripcion().trim());
			parametros.put("referencia", desafiliarEmpresaModel.getEmpresaPdf()
					.getReferencia().trim());
			parametros.put("telefonoFijo", desafiliarEmpresaModel
					.getEmpresaPdf().getTelefonoFijo());
			parametros.put("razonSocial", desafiliarEmpresaModel
					.getEmpresaPdf().getRazonSocial());

			parametros.put("tipoDocumento", TipoDocumento
					.tipoDocumentoBducLetras(desafiliarEmpresaModel
							.getRepresentante().getTipoDocumento()));
			parametros.put("numeroDocumento", desafiliarEmpresaModel
					.getRepresentante().getNumeroDocumento());
			parametros.put("apellidosNombres", desafiliarEmpresaModel
					.getRepresentante().nombreCompleto());
			parametros.put("correoLaboral", desafiliarEmpresaModel
					.getRepresentante().getCorreoLaboral());
			parametros.put("codigoTelefono", desafiliarEmpresaModel
					.getRepresentante().getCodigoTelefono());
			parametros.put("telefonoLaboral", desafiliarEmpresaModel
					.getRepresentante().getTelefonoFijo());
			parametros.put("anexo", desafiliarEmpresaModel.getRepresentante()
					.getAnexo());
			parametros.put("telefonoCelular", desafiliarEmpresaModel
					.getRepresentante().getTelefonoMovil());

			parametros.put("razonSocialCorto", desafiliarEmpresaModel
					.getEmpresaPdf().getNombreCorto());
			parametros.put("nombres", desafiliarEmpresaModel.getRepresentante()
					.getNombres());
			parametros.put(
					"apellidos",
					desafiliarEmpresaModel
							.getRepresentante()
							.getApellidoPaterno()
							.concat(" ".concat(desafiliarEmpresaModel
									.getRepresentante().getApellidoMaterno())));

			parametros.put("fecha", Fecha.transformarString(new Date()));
			Reporte.mostrarReportePDF(
					TipoReporte.REPORTE_SOLICITUD_DESAFILIACION, parametros,
					null, context, servletContext);

			desafiliarEmpresaModel.inicializarEmpresaPdf();
			desafiliarEmpresaModel.inicializarRepresentante();
		}
	}

	public DesafiliarEmpresaModel getDesafiliarEmpresaModel() {
		return desafiliarEmpresaModel;
	}

	public void setDesafiliarEmpresaModel(
			DesafiliarEmpresaModel desafiliarEmpresaModel) {
		this.desafiliarEmpresaModel = desafiliarEmpresaModel;
	}

}
