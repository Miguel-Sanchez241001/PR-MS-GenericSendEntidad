package pe.bn.com.sate.infrastructure.service.external.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceMessageException;
import pe.bn.com.sate.infrastructure.service.external.NotificacionService;
import pe.bn.com.sate.infrastructure.service.external.domain.message.Adjunto;
import pe.bn.com.sate.infrastructure.service.external.domain.message.ArrayOfTns1ReqListMessage;
import pe.bn.com.sate.infrastructure.service.external.domain.message.DatosCorreo;
import pe.bn.com.sate.infrastructure.service.external.domain.message.DatosParametro;
import pe.bn.com.sate.infrastructure.service.external.domain.message.ReqListMessage;
import pe.bn.com.sate.infrastructure.service.external.domain.message.RequestMessage;
import pe.bn.com.sate.infrastructure.service.external.domain.message.ServiceMessageProxy;
import pe.bn.com.sate.persistence.mapper.internal.ParametroMapper;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Autowired
	private ParametrosComp parametrosComp;

	@Autowired
	private ParametroMapper parametroMapper;

	public void enviarMailUsuarioClave(Representante representante, String clave) {

		try {
			ServiceMessageProxy serviceMessage = new ServiceMessageProxy();
			// ServicioWebUtil.cambiarTiempoEspera(parametros.getConexionTiempo(),
			// parametros.getRespuestaTiempo(),
			// (BindingProvider) serviceMessage._getDescriptor()
			// .getProxy());

			RequestMessage rqMessage = new RequestMessage();

			rqMessage
					.setCodRequermiento(ConstantesGenerales.COD_REQUERIMIENTO_ENVIO_CORREO);

			ArrayOfTns1ReqListMessage arr = new ArrayOfTns1ReqListMessage();

			// ReqListMessage[] arr=new ReqListMessage[1];
			ReqListMessage rlm = new ReqListMessage();

			DatosCorreo datos = new DatosCorreo();
			// datos.setAsunto("Acceso a Multired Virtual para Empresa - Banco de la Nacion");
			datos.setAsunto("Acceso al Sistema Administrativo de Tarjeta Empresarial - Banco de la Nacion");
			datos.setCorreoDestinatario(representante.getCorreoLaboral());

			DatosParametro params = new DatosParametro();
			params.setParametro1(representante.nombreCompleto());
			params.setParametro2(parametroMapper.buscarParametro(
					ConstantesGenerales.URL_BASE_OPE, "1").getValor()
					+ "SATE_ope");
			params.setParametro3(representante.getNumeroDocumento());
			params.setParametro4(clave);

			rlm.setDatosCorreo(datos);
			rlm.setAdjunto(leerAdjunto());
			rlm.setDatosParametro(params);

			arr.getItem().add(rlm);
			// arr[0]=rlm;
			rqMessage.setReqListMessage(arr);
			serviceMessage.sendMessage(rqMessage);
		} catch (Exception ex) {
			throw new ExternalServiceMessageException(ex.getMessage(), ex);
		}
	}

	private Adjunto leerAdjunto() {
		FileInputStream fis = null;
		Adjunto adjunto = new Adjunto();
		try {

			File file = new File(FacesContext.getCurrentInstance()
					.getExternalContext()
					.getResource("/WEB-INF/adjuntos/Contrato.pdf").getFile());

			byte[] byteArray = new byte[(int) file.length()];

			fis = new FileInputStream(file);
			fis.read(byteArray);
			adjunto.setDocAdjunto(byteArray);
			return adjunto;

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

}
