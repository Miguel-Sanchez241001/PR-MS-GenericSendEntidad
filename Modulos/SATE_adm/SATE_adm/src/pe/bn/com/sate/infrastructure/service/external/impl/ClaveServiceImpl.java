package pe.bn.com.sate.infrastructure.service.external.impl;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceIGWException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.service.external.ClaveService;
import pe.bn.com.sate.infrastructure.service.external.domain.gateway.GatewayInterfaceProxy;
import pe.bn.com.sate.infrastructure.service.external.domain.gateway.RequestGateway;
import pe.bn.com.sate.infrastructure.service.external.domain.gateway.ResponseGateway;
import pe.bn.com.sate.infrastructure.service.external.domain.tramaHost.BodySolicitud;
import pe.bn.com.sate.transversal.configuration.security.SecurityContextFacade;
import pe.bn.com.sate.transversal.dto.host.ClaveRespuesta;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;
import pe.bn.com.sate.transversal.util.ServicioWebUtil;
import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.TipoOperacionIG;

@Service
public class ClaveServiceImpl implements ClaveService {
	
	private final Logger logger = Logger.getLogger(ClaveServiceImpl.class);

	private @Autowired
	ParametrosComp parametrosComp;

	public ClaveRespuesta enviarSolicitud(Empresa empresa,
			Representante representante, String clave, String claveNueva,
			TipoOperacionIG tipoOperacionIG) {

		try {

			BodySolicitud solicitudInput = new BodySolicitud();
			ClaveRespuesta claveRespuesta = null;
			BodySolicitud bodyIn = new BodySolicitud();
			BodySolicitud bodyOut = new BodySolicitud();

			solicitudInput.cargarData(tipoOperacionIG.getCod(), "SATE",
					DateFormaterUtil.getTimeStamp(), "WEB",
					SecurityContextFacade.obtenerIpCliente(), empresa.getRuc(),
					empresa.getCic(), representante.getTipoDocumento(),
					representante.getNumeroDocumento(), clave, claveNueva,
					UsefulWebApplication.obtenerUsuario().getUsername());

			// Input
			String head = StringUtil.enmascararTramaRecepcion(solicitudInput
					.toString());
			String trama = head + bodyIn;

			GatewayInterfaceProxy proxy = new GatewayInterfaceProxy();
			ServicioWebUtil.cambiarTiempoEspera(
					parametrosComp.getConexionTiempo(),
					parametrosComp.getRespuestaTiempo(),
					(BindingProvider) proxy._getDescriptor().getProxy());

			RequestGateway peticion = new RequestGateway();
			peticion.setLongitud(ConstantesGenerales.LONGITUD_DEFECTO);
			peticion.setTransid(ConstantesGenerales.SEG_LOGIN_TRAN);
			peticion.setDatos(trama);
			peticion.setFiller("");

			// respuesta
			ResponseGateway respuesta = new ResponseGateway();
			respuesta = proxy.enviarTramaConsulta(peticion);

			if (("0000".equals(respuesta.getMsgno()))&& (!respuesta.getDatos().equals(""))) {

				BodySolicitud cabOut = new BodySolicitud();

				if (respuesta.getDatos().length() < cabOut.LongitudTrama()) {
					logger.error("ERROR-014: La cabecera del cics soap no es válida.");
					throw new Exception("ERROR-014.Consulte con el administrador");
				}

				bodyOut.FillBobyOk(respuesta.getDatos() + respuesta.getMsgno()
						+ respuesta.getMensaje());

				claveRespuesta = new ClaveRespuesta();
				claveRespuesta.setcError(bodyOut.getByTag("DFH-CERROR").trim());
				claveRespuesta.setMsj(bodyOut.getByTag("DFH-MSJ").trim());

			} else {
				bodyOut.FillBobyHost(respuesta.getMsgno()+ respuesta.getMensaje());
				throw new ExternalServiceIGWException("Error en el servicio Infertace gateway ,"+bodyOut.getByTag("msgnoHost").trim()+":"+bodyOut.getByTag("msjeHost").trim());
			}
			return claveRespuesta;
		} catch (SOAPFaultException sfe) {
			logger.error("ERROR-013: No se puede establecer comunicación con el servicio Interface Gateway, sobrepaso el tiempo de espera.");
			throw new ServiceException("ERROR-013. Consulte con el administrador.",sfe);
		} catch (Exception ex) {
			throw new ExternalServiceIGWException(ex.getMessage(), ex);
		}

	}

}
