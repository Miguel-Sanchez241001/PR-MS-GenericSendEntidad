package pe.bn.com.sate.infrastructure.service.external;

import java.util.List;

import pe.bn.com.sate.transversal.dto.external.bntablas.Ubigeo;

public interface UbigeoService {

	public List<Ubigeo> buscarDepartamentos();

	public List<Ubigeo> buscarProvinciasPorDepartamento(String codDepartamento);

	public List<Ubigeo> buscarDistritosPorProvincia(String codDepartamento,
			String codProvincia);
	
	public Ubigeo buscarLocalidad(String ubigeo,int tipo);
	
	public void probarConexion();
}
