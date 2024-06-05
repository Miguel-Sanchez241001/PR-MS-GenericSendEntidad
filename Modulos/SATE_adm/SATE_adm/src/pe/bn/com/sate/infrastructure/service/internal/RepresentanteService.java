package pe.bn.com.sate.infrastructure.service.internal;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;
import pe.bn.com.sate.transversal.dto.sate.Representante;

public interface RepresentanteService {

	
	public List<Representante> buscarHistorialRepresentantes(Date fechaInicio,
			Date fechaFin, Long idEmpresa);

	public List<Representante> buscarRepresentantes(Date fechaInicio,
			Date fechaFin, Long idEmpresa);

	public Representante obtenerRepresentanteActual(Long idEmpresa);
	
	public Representante buscarRepresentante(String tipoDocumento,String numDocumento);
	
	public Representante convertirARepresentante(Firmante firmante);

}
