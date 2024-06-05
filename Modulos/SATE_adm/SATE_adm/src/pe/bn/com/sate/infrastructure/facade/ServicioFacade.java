package pe.bn.com.sate.infrastructure.facade;

import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceBducException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceBnTablasException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceDatacomException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceIGWException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceMessageException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceSaraSignException;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.service.external.BducService;
import pe.bn.com.sate.infrastructure.service.external.ClaveService;
import pe.bn.com.sate.infrastructure.service.external.DatacomService;
import pe.bn.com.sate.infrastructure.service.external.FirmanteService;
import pe.bn.com.sate.infrastructure.service.external.UbigeoService;
import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;
import pe.bn.com.sate.infrastructure.service.internal.EmpresaService;
import pe.bn.com.sate.infrastructure.service.internal.NombreCortoService;
import pe.bn.com.sate.infrastructure.service.internal.RepresentanteService;
import pe.bn.com.sate.infrastructure.service.internal.UsuarioService;
import pe.bn.com.sate.infrastructure.thread.NotificacionThread;
import pe.bn.com.sate.transversal.dto.external.bntablas.Ubigeo;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.host.ClaveRespuesta;
import pe.bn.com.sate.transversal.dto.host.Direccion;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.NombreCorto;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.enums.RespuestaInterfaceGateway;
import pe.bn.com.sate.transversal.util.enums.TipoEmpresa;
import pe.bn.com.sate.transversal.util.enums.TipoEstadoUsuario;
import pe.bn.com.sate.transversal.util.enums.TipoOperacionIG;

@Component
public class ServicioFacade {
	
	private final Logger logger = Logger
			.getLogger(ServicioFacade.class);

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private RepresentanteService representanteService;

	@Autowired
	private ClaveService claveService;

	@Autowired
	private NotificacionThread notificacionThread;

	@Autowired
	private BducService bducService;

	@Autowired
	private DatacomService datacomService;

	@Autowired
	private NombreCortoService nombreCortoService;

	@Autowired
	private FirmanteService firmanteService;

	@Autowired
	private UbigeoService ubigeoService;

	@Autowired
	private UsuarioService usuarioService;

	public Empresa obtenerEmpresaRepresentanteActual(String ruc) {

		try {
			Empresa empresaAfiliada = empresaService.buscarEmpresa(ruc);

			if (empresaAfiliada != null) {
				if (empresaService.buscarEstadoEmpresa(ruc)
						.getFechaDesafiliacion() == null) {

					empresaAfiliada
							.setRepresentante(representanteService
									.obtenerRepresentanteActual(empresaAfiliada
											.getId()));
				} else {
					throw new ServiceException(
							"La empresa se encuentra desafiliada.");
				}
			} else {
				throw new ServiceException("El n�mero de RUC no pertenece a una empresa afiliada.");
			}
			return empresaAfiliada;
		} catch (InternalServiceException ise) {
			throw new ServiceException(ise.getMessage(), ise);
		}
	}

	public void estaRepresentanteDisponible(Representante representante,
			Empresa empresa) {

		Representante usuarioDisponible = usuarioService.buscarUsuario(
				representante.getTipoDocumento(),
				representante.getNumeroDocumento());
		Empresa empresaBuscada;
		String mensaje = "";

		if (usuarioDisponible != null
				&& usuarioDisponible.getEstado().equals(
						TipoEstadoUsuario.ACTIVO)) {
			if (usuarioDisponible.getRepresentanteCreador() == null) {
				empresaBuscada = empresaService.buscarEmpresaPorRepresentante(
						representante.getTipoDocumento(),
						representante.getNumeroDocumento());
			} else {
				empresaBuscada = empresaService.buscarEmpresaPorUsuario(
						representante.getTipoDocumento(),
						representante.getNumeroDocumento());
			}
			if (!empresa.getRuc().equals(empresaBuscada.getRuc())) {
				mensaje = "la "
						+ TipoEmpresa.tipoEmpresaLetras(empresa.getTipo())
						+ " " + empresa.getRazonSocial() + " con RUC : "
						+ empresa.getRuc();
			} else {
				mensaje = "esta "
						+ TipoEmpresa.tipoEmpresaLetras(empresa.getTipo());
			}
			throw new ServiceException(
					"La persona seleccionada no est� disponible. Es "
							+ usuarioDisponible.getPerfilUsuarioDescripcion()
							+ " en " + mensaje);
		}
	}

	public void registrarEmpresaRepresentante(Empresa empresa) {
		try {

			TipoOperacionIG tipoOperacionIG = representanteService
					.buscarRepresentante(empresa.getRepresentante()
							.getTipoDocumento(), empresa.getRepresentante()
							.getNumeroDocumento()) == null ? TipoOperacionIG.GENERAR_CLAVE
					: TipoOperacionIG.CAMBIAR_CLAVE_OLVIDO;

			String clave = StringUtil.random();
			//System.out.println("clave : " + clave);

			ClaveRespuesta claveRespuesta = claveService.enviarSolicitud(
					empresa, empresa.getRepresentante(), clave, clave,
					tipoOperacionIG);

			if (claveRespuesta.getcError().equals(
					RespuestaInterfaceGateway.EXITO.getCodigo())) {
				empresaService.afiliarEmpresa(empresa);
				notificacionThread.enviarMailUsuarioClave(
						empresa.getRepresentante(), clave);
			} else {
				claveRespuesta = claveService
						.enviarSolicitud(
								empresa,
								empresa.getRepresentante(),
								clave,
								clave,
								tipoOperacionIG
										.equals(TipoOperacionIG.GENERAR_CLAVE) ? TipoOperacionIG.CAMBIAR_CLAVE_OLVIDO
										: TipoOperacionIG.GENERAR_CLAVE);

				if (claveRespuesta.getcError().equals(
						RespuestaInterfaceGateway.EXITO.getCodigo())) {
					empresaService.afiliarEmpresa(empresa);
					notificacionThread.enviarMailUsuarioClave(
							empresa.getRepresentante(), clave);
				} else {
					logger.error("Error en la rutina de HOST que consume el service Interface Gateway :"+claveRespuesta.getcError()+":"+claveRespuesta.getMsj());
					throw new ServiceException("Error-012. Consulte con el administrador");
				}
			}

		} catch (InternalServiceException ise) {
			throw ise;
		} catch (ExternalServiceMessageException esme) {
			throw esme;
		} catch (ExternalServiceIGWException esie) {
			throw esie;
		}

	}

	public void actualizarEmpresaRepresentante(Empresa empresa) {
		try {			
			TipoOperacionIG tipoOperacionIG = representanteService
					.buscarRepresentante(empresa.getRepresentante()
							.getTipoDocumento(), empresa.getRepresentante()
							.getNumeroDocumento()) == null ? TipoOperacionIG.GENERAR_CLAVE
					: TipoOperacionIG.CAMBIAR_CLAVE_OLVIDO;
			String clave = StringUtil.random();
			System.out.println("clave : " + clave);

			ClaveRespuesta claveRespuesta = claveService.enviarSolicitud(
					empresa, empresa.getRepresentante(), clave, clave,
					tipoOperacionIG);

			if (claveRespuesta.getcError().equals(
					RespuestaInterfaceGateway.EXITO.getCodigo())) {
				empresaService.actualizarEmpresa(empresa);
				notificacionThread.enviarMailUsuarioClave(
						empresa.getRepresentante(), clave);
			} else {
				claveRespuesta = claveService
						.enviarSolicitud(
								empresa,
								empresa.getRepresentante(),
								clave,
								clave,
								tipoOperacionIG
										.equals(TipoOperacionIG.GENERAR_CLAVE) ? TipoOperacionIG.CAMBIAR_CLAVE_OLVIDO
										: TipoOperacionIG.GENERAR_CLAVE);
				if (claveRespuesta.getcError().equals(
						RespuestaInterfaceGateway.EXITO.getCodigo())) {
					empresaService.actualizarEmpresa(empresa);
					notificacionThread.enviarMailUsuarioClave(
							empresa.getRepresentante(), clave);
				} else {
					logger.error("Error en la rutina de HOST que consume el service Interface Gateway :"+claveRespuesta.getcError()+":"+claveRespuesta.getMsj());
					throw new ServiceException("Error-012. Consulte con el administrador");
				}
			}

		} catch (InternalServiceException ise) {
			throw ise;
		} catch (ExternalServiceMessageException esme) {
			throw esme;
		} catch (ExternalServiceIGWException esie) {
			throw esie;
		}
	}

	public Empresa obtenerEmpresa(String ruc) {
		BnCliente bnCliente = null;
		Direccion direccion = null;
		NombreCorto nombreCorto = null;
		String mensajeError = "";
		try {
			if (empresaService.buscarEmpresa(ruc) == null
					|| empresaService.buscarEstadoEmpresa(ruc)
							.getFechaDesafiliacion() != null) {

				bnCliente = bducService.obtenerBnCliente(ruc);

				if (bnCliente == null)
					mensajeError = "No se encontr� ninguna empresa con el RUC ingresado en BDUC.";
				else if (bnCliente.getF01CinternoCic().isEmpty())
					mensajeError = "La empresa no tiene un CIC asignado.";
				else {
					direccion = datacomService.obtenerDireccion(bnCliente
							.getF01CinternoCic());
					nombreCorto = nombreCortoService.obtenerNombreCorto(ruc);

					if (direccion == null)
						mensajeError = "La empresa no tiene una direcci�n asignada. ";

					if (nombreCorto == null)
						mensajeError += "La empresa no tiene un nombre corto asignado.";
				}

				if (mensajeError.isEmpty()) {
					return new Empresa(bnCliente, direccion, ruc,
							nombreCorto.getNombre());
				} else {
					throw new ServiceException(mensajeError);
				}
			} else {
				throw new ServiceException(
						"El número de RUC pertenece a una empresa afiliada.");
			}
		} catch (InternalServiceException ise) {
			throw ise;
		} catch (ExternalServiceDatacomException esde) {
			throw esde;
		} catch (ExternalServiceBducException esbe) {
			throw esbe;
		} catch (SOAPFaultException sfe) {
			throw new ServiceException("ERROR-017. Consulte con el administrador", sfe);
		}
	}

	public List<Firmante> obtenerListaFirmantes(String ruc) {
		try {
			return firmanteService.obtenerListaFirmantes(ruc);
		} catch (ExternalServiceSaraSignException essx) {
			throw essx;
		}
	}

	public EstadoEmpresa buscarEstadoEmpresa(String ruc) {
		try {
			return empresaService.buscarEstadoEmpresa(ruc);
		} catch (InternalServiceException ex) {
			throw ex;
		}
	}

	public Empresa buscarEmpresaPorRepresentante(String tipoDocumento,
			String numDocumento) {
		try {
			return empresaService.buscarEmpresaPorRepresentante(tipoDocumento,
					numDocumento);
		} catch (InternalServiceException ex) {
			throw ex;
		}
	}

	public List<Ubigeo> buscarDepartamentos() {
		try {
			return ubigeoService.buscarDepartamentos();
		} catch (ExternalServiceBnTablasException ex) {
			throw ex;
		}
	}

	public List<Ubigeo> buscarProvinciasPorDepartamento(String codDepartamento) {
		try {
			return ubigeoService
					.buscarProvinciasPorDepartamento(codDepartamento);
		} catch (InternalServiceException ex) {
			throw ex;
		}
	}

	public List<Ubigeo> buscarDistritosPorProvincia(String codDepartamento,
			String codProvincia) {
		try {
			return ubigeoService.buscarDistritosPorProvincia(codDepartamento,
					codProvincia);
		} catch (InternalServiceException ex) {
			throw ex;
		}
	}

	public Ubigeo buscarLocalidad(String ubigeo, int tipo) {
		try {
			return ubigeoService.buscarLocalidad(ubigeo, tipo);
		} catch (ExternalServiceBnTablasException esne) {
			throw esne;
		}
	}

	public Representante obtenerRepresentanteActual(Long idEmpresa) {
		try {
			return representanteService.obtenerRepresentanteActual(idEmpresa);
		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public Empresa buscarEmpresa(String ruc) {
		try {
			return empresaService.buscarEmpresa(ruc);

		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public void bloquearEmpresa(EstadoEmpresa estadoEmpresa) {
		try {
			List<Representante> usuarios = usuarioService
					.buscarUsuariosDeEmpresa(estadoEmpresa.getIdEmpresa());

			empresaService.bloquearEmpresa(estadoEmpresa);

			for (Representante usuario : usuarios) {
				usuarioService.desactivarUsuario(usuario.getId());
				usuarioService.DesactivarUsuarioHistoricoRepresentante(usuario);
			}

		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public boolean validarCorreo(String correo, String tipoDocumento,
			String numeroDocumento) {
		try {
			return usuarioService.validarCorreo(correo, tipoDocumento,
					numeroDocumento);
		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public boolean validarCelular(String numero, String tipoDocumento,
			String numeroDocumento) {
		try {
			return usuarioService.validarCelular(numero, tipoDocumento,
					numeroDocumento);
		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public Representante convertirARepresentante(Firmante firmante) {
		return representanteService.convertirARepresentante(firmante);
	}

}
