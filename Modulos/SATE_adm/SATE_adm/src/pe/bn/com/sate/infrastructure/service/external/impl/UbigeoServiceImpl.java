package pe.bn.com.sate.infrastructure.service.external.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceBnTablasException;
import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.external.UbigeoService;
import pe.bn.com.sate.persistence.mapper.external.bntablas.UbigeoMapper;
import pe.bn.com.sate.transversal.dto.external.bntablas.Ubigeo;

@Service
public class UbigeoServiceImpl implements UbigeoService {

	private @Autowired
	UbigeoMapper ubigeoMapper;

	@Override
	public List<Ubigeo> buscarDepartamentos() {
		try {
			return ubigeoMapper.buscarDepartamentos();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);

		}
	}

	@Override
	public List<Ubigeo> buscarProvinciasPorDepartamento(String codDepartamento) {
		try {
			return ubigeoMapper
					.buscarProvinciasPorDepartamento(codDepartamento);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Ubigeo> buscarDistritosPorProvincia(String codDepartamento,
			String codProvincia) {
		try {
			return ubigeoMapper.buscarDistritosPorProvincia(codDepartamento,
					codProvincia);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public Ubigeo buscarLocalidad(String ubigeo, int tipo) {
		try {
			if (tipo == 1) {
				return ubigeoMapper.buscarDepartamento(ubigeo);
			} else if (tipo == 2) {
				return ubigeoMapper.buscarProvincia(ubigeo);
			} else if (tipo == 3) {
				return ubigeoMapper.buscarDistrito(ubigeo);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public void probarConexion() {
		try {
			ubigeoMapper.probarConexion();
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}
}
