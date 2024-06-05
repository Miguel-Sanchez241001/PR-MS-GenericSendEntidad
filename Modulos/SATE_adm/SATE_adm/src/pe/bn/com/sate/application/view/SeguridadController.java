package pe.bn.com.sate.application.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import pe.bn.com.sate.application.model.SeguridadModel;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.constantes.ConstantesPagina;

@Controller("seguridadController")
@Scope("view")
public class SeguridadController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(SeguridadController.class);

	private SeguridadModel seguridadModel;

	@Autowired
	ParametrosComp parametrosComp;

	@PostConstruct
	public void init() {
		seguridadModel = new SeguridadModel(
				UsefulWebApplication.obtenerUsuario());
	}

	public void cerrarSesion() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		SecurityContextHolder.clearContext();
		UsefulWebApplication.redireccionar(ConstantesPagina.PAGINA_INDEX);
	}

	public void forzarCierreSesion() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		SecurityContextHolder.clearContext();
	}

	/**
	 * @return tiempo de inactividad maximo de una sesión
	 */
	public int tiempoMaximoInactividad() {
		return Integer
				.parseInt(parametrosComp.getSesionExpiradaTiempo() != null ? parametrosComp
						.getSesionExpiradaTiempo() : "10") * 1000 * 60;
	}

	/**
	 * @return mensaje que mostrara el dialogo luego de que expire la sesión
	 */
	public String mensajeSesionExpirada() {
		return ConstantesGenerales.MENSAJE_SESION_EXPIRADA;
	}

	/**
	 * @return mensaje que mostrara el dialogo luego de que expire la sesión
	 */
	public String mensajeSesionInvalida() {
		return ConstantesGenerales.MENSAJE_SESION_INVALIDA;
	}

	/**
	 * @return url de la pagina de inicio de sesión
	 */
	public String paginaLogin() {
		return ConstantesPagina.PAGINA_INDEX;
	}

	public boolean renderizar(String valor) {
		for (GrantedAuthority sg : SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()) {
			if (valor.equals(sg.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	public void redireccionar(ActionEvent event) {
		String pagina = (String) event.getComponent().getAttributes()
				.get("pagina");
		try {
			UsefulWebApplication.redireccionar(pagina);
		} catch (IOException ie) {
			logger.error(ie.getMessage(), ie);
		}
	}

	public void estaLogueado() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if ((!(auth instanceof AnonymousAuthenticationToken))) {
			System.out.println("SI esta logueado");
			// esta logueado
		} else {
			System.out.println("NO esta logueado");
			// no esta logueado
		}
	}

	public boolean estaSeleccionado(String pagina) {
		return UsefulWebApplication.obtenerUrlActual().contains(pagina) ? true
				: false;
	}

	public SeguridadModel getseguridadModel() {
		return seguridadModel;
	}

	public void setseguridadModel(SeguridadModel seguridadModel) {
		this.seguridadModel = seguridadModel;
	}

}
