package pe.bn.com.sate.infrastructure.service.external;

import pe.bn.com.sate.transversal.dto.host.ClaveRespuesta;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.util.enums.TipoOperacionIG;

public interface ClaveService {

	public ClaveRespuesta enviarSolicitud(Empresa empresa,
			Representante representante, String clave, String claveNueva,
			TipoOperacionIG tipoOperacionIG);

}
