package pe.bn.com.sate.infrastructure.thread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import pe.bn.com.sate.application.view.ActualizarRepresentanteController;
import pe.bn.com.sate.infrastructure.service.external.NotificacionService;
import pe.bn.com.sate.transversal.dto.sate.Representante;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NotificacionThread extends Thread {

	private final Logger logger = Logger.getLogger(NotificacionThread.class);

	@Autowired
	private NotificacionService notificacionService;

	private Representante representante;

	private String clave;

	@Override
	public void run() {
		try {
			notificacionService.enviarMailUsuarioClave(representante, clave);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void enviarMailUsuarioClave(Representante representante, String clave) {
		this.representante = representante;
		this.clave = clave;
		run();
	}

}
