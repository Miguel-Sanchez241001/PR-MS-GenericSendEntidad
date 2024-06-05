package pe.bn.com.sate.infrastructure.service.external.impl;

import java.sql.SQLException;

import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceBducException;
import pe.bn.com.sate.infrastructure.exception.ExternalServiceBnTablasException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.service.external.BducService;
import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BducWebserviceProxy;
import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;
import pe.bn.com.sate.transversal.util.NumeroUtil;
import pe.bn.com.sate.transversal.util.ServicioWebUtil;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import java.net.SocketTimeoutException;
import com.ibm.io.async.AsyncTimeoutException;
import javax.xml.ws.soap.SOAPFaultException;

@Service
public class BducServiceImpl implements BducService {

	@Autowired
	private ParametrosComp parametrosComp;

	public BnCliente obtenerBnCliente(String ruc) {
		try {
			BducWebserviceProxy bducWebserviceProxy = new BducWebserviceProxy();

			ServicioWebUtil.cambiarTiempoEspera(parametrosComp.getConexionTiempo(),
					parametrosComp.getRespuestaTiempo(),
					(BindingProvider) bducWebserviceProxy._getDescriptor()
							.getProxy());

			return bducWebserviceProxy.getDatosGenerales(
					TipoDocumento.RUC.getCodigoBduc(),
					NumeroUtil.agregarCerosIzquierda(ruc, 12),
					parametrosComp.getTransac2BDUC(), parametrosComp.getPerfil2BDUC(),
					parametrosComp.getUserBDUC());
		} catch (SOAPFaultException sfe) {
			throw new ServiceException("ERROR-017. Consulte con el administrador", sfe);
		} catch (Exception ex) {
			throw new ExternalServiceBducException(ex.getMessage(), ex);
		}

	}
}
