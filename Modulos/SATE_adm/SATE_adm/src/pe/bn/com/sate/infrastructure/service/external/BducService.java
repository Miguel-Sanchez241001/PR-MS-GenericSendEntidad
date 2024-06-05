package pe.bn.com.sate.infrastructure.service.external;

import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;

public interface BducService {

	public BnCliente obtenerBnCliente(String ruc);
}
