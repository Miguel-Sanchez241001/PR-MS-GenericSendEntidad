package pe.bn.com.sate.infrastructure.service.external.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceDatacomException;
import pe.bn.com.sate.infrastructure.service.external.DatacomService;
import pe.bn.com.sate.persistence.dao.external.DireccionHost;
import pe.bn.com.sate.transversal.dto.host.Direccion;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;

@Service
public class DatacomServiceImpl implements DatacomService {

	@Autowired
	private ParametrosComp parametrosComp;

	@Override
	public Direccion obtenerDireccion(String cic) {

		try {
			DireccionHost direccionHost = new DireccionHost(
					parametrosComp.getDriverDatacom(), parametrosComp.getDbDatacom(),
					parametrosComp.getUrlDatacom(), parametrosComp.getPortDatacom(),
					parametrosComp.getUserDatacom(), parametrosComp.getPassDatacom(),
					parametrosComp.getSystemDatacom(), parametrosComp.getAppDatacom(),
					parametrosComp.getServerDatacom(),
					parametrosComp.getMaxconnectionsDatacom(),
					parametrosComp.getWaittimeDatacom(),
					parametrosComp.getInitialconnectionsDatacom());

			return direccionHost.obtenerDatosDireccion(cic);
		} catch (Exception ex) {
			throw new ExternalServiceDatacomException(ex.getMessage(), ex);
		}

	}

}
