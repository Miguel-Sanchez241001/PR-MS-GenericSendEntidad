package pe.bn.com.sate.infrastructure.service.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.NombreCortoService;
import pe.bn.com.sate.persistence.mapper.internal.NombreCortoMapper;
import pe.bn.com.sate.transversal.dto.sate.NombreCorto;

@Service
public class NombreCortoServiceImpl implements NombreCortoService {

	@Autowired
	private NombreCortoMapper nombreCortoMapper;

	@Override
	public NombreCorto obtenerNombreCorto(String ruc) {
		try {
			return nombreCortoMapper.obtenerNombreCortoPorRuc(ruc);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
