package pe.bn.com.sate.infrastructure.service.external.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceSaraSignException;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.external.FirmanteService;
import pe.bn.com.sate.persistence.mapper.external.sarasign.FirmanteMapper;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;

@Service
public class FirmanteServiceImpl implements FirmanteService {

	private @Autowired
	FirmanteMapper firmanteMapper;

	@Override
	public List<Firmante> obtenerListaFirmantes(String numeroRuc) {
		try {
			return firmanteMapper.buscarFirmantes(numeroRuc);

		} catch (Exception ex) {
			throw new ExternalServiceSaraSignException(ex.getMessage(), ex);
		}

	}

	@Override
	public void probarConexion() {
		try {
			firmanteMapper.probarConexion();
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
