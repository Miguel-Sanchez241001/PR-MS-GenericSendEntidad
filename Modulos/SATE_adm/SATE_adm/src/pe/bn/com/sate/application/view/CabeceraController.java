package pe.bn.com.sate.application.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.CabeceraModel;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;

@Controller("cabeceraController")
@Scope("view")
public class CabeceraController {

	private final static Logger logger = Logger
			.getLogger(CabeceraController.class);

	private CabeceraModel cabeceraModel;

	@PostConstruct
	public void init() {
		cabeceraModel = new CabeceraModel();
		obtenerDatosUsuario();
		obtenerFechaHoy();
	}

	public void obtenerDatosUsuario() {
		logger.info("[CabeceraController] - Iniciando método obtenerDatosUsuario");
		cabeceraModel.setUsuario(UsefulWebApplication.obtenerUsuario());
		logger.info("[CabeceraController] - Finalizando método obtenerDatosUsuario");
	}

	public void obtenerFechaHoy() {
		this.cabeceraModel.setFecha(DateFormaterUtil.fechaHoy());
	}

	public CabeceraModel getCabeceraModel() {
		return cabeceraModel;
	}

	public void setCabeceraModel(CabeceraModel cabeceraModel) {
		this.cabeceraModel = cabeceraModel;
	}

}
