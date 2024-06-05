package pe.bn.com.sate.infrastructure.service.external;

import java.util.List;

import pe.bn.com.sate.transversal.dto.external.sarasign.Firmante;

public interface FirmanteService {

	public List<Firmante> obtenerListaFirmantes(String numeroRuc);
	
	public void probarConexion();
}
