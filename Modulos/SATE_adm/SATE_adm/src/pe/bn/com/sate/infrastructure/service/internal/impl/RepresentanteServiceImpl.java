package pe.bn.com.sate.infrastructure.service.internal.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.RepresentanteService;
import pe.bn.com.sate.persistence.mapper.internal.EmpresaMapper;
import pe.bn.com.sate.persistence.mapper.internal.RepresentanteMapper;
import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.RepresentanteHistoricoEmpresa;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;
import pe.bn.com.sate.transversal.util.NumeroUtil;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoEstadoUsuario;

@Service
public class RepresentanteServiceImpl implements RepresentanteService {

	private @Autowired
	RepresentanteMapper representanteMapper;
	private @Autowired
	EmpresaMapper empresaMapper;

	

	@Override
	public List<Representante> buscarRepresentantes(Date fechaInicio,
			Date fechaFin, Long idEmpresa) {
		try {
			List<Representante> listaRepresentante = representanteMapper
					.buscarRepresentantes(DateFormaterUtil
							.formatoFechaBD(fechaInicio), DateFormaterUtil
							.formatoFechaBD(fechaFin), idEmpresa);

			return listaRepresentante;
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Representante obtenerRepresentanteActual(Long idEmpresa) {
		try {
			return representanteMapper.obtenerRepresentanteActual(idEmpresa);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Representante> buscarHistorialRepresentantes(Date fechaInicio,
			Date fechaFin, Long idEmpresa) {
		try {
			List<Representante> listaRepresentante = representanteMapper
					.buscarHistorialRepresentantes(DateFormaterUtil
							.formatoFechaBD(fechaInicio), DateFormaterUtil
							.formatoFechaBD(fechaFin), idEmpresa);
			return listaRepresentante;
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Representante buscarRepresentante(String tipoDocumento,
			String numDocumento) {
		try {
			return representanteMapper.buscarRepresentante(numDocumento,
					tipoDocumento);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Representante convertirARepresentante(Firmante firmante) {
		return new Representante(TipoDocumento.obtenerCodigoBduc(firmante
				.getTipoDocumento()), firmante.getNumeroDocumento(),
				firmante.getNombres(), firmante.getApellidoPaterno(),
				firmante.getApellidoMaterno());
	}

}
