package pe.bn.com.sate.infrastructure.service.external;

import pe.bn.com.sate.transversal.dto.sate.Representante;

public interface NotificacionService {

	public void enviarMailUsuarioClave(Representante representante, String clave);

}
