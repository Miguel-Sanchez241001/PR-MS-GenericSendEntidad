package pe.bn.com.sate.infrastructure.service.internal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.ActividadService;
import pe.bn.com.sate.persistence.mapper.internal.ActividadMapper;
import pe.bn.com.sate.transversal.dto.sate.Actividad;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;

@Service
public class ActividadServiceImpl implements ActividadService {

	@Autowired
	private ActividadMapper actividadMapper;

	public List<Actividad> buscarActividades(Long idUsuario, Date fechaInicio,
			Date fechaFin) {
		try {
			return actividadMapper.buscarActividades(idUsuario,
					DateFormaterUtil.formatoFechaBD(fechaInicio),
					DateFormaterUtil
							.formatoFechaBD(fechaFin == null ? new Date()
									: fechaFin));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}
}
