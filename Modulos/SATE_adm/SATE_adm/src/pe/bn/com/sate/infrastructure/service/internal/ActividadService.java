package pe.bn.com.sate.infrastructure.service.internal;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.Actividad;

public interface ActividadService {

	public List<Actividad> buscarActividades(Long idUsuario, Date fechaInicio,
			Date fechaFin);

}
