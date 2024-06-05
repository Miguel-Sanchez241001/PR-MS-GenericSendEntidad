package pe.bn.com.sate.application.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pe.bn.com.sate.application.model.LoginModel;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.excepciones.LoginException;

@Controller("loginController")
@Scope("view")
public class LoginController implements PhaseListener, Serializable {

	private final Logger logger = Logger.getLogger(LoginController.class);

	private static final long serialVersionUID = 1L;
	private LoginModel loginModel;

	@PostConstruct
	public void init() {
		estaLogeado();
		loginModel = new LoginModel();
	}

	public void iniciarSesion() {
		logger.info("[LoginController] - Iniciando método iniciarSesion");
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/login_autenticacion");
		try {
			dispatcher.forward((ServletRequest) context.getRequest(),
					(ServletResponse) context.getResponse());
		} catch (ServletException se) {
			logger.error(se.getMessage(), se);
		} catch (IOException ie) {
			logger.error(ie.getMessage(), ie);
		}
		logger.info("[LoginController] - Finalizando método iniciarSesion");
		FacesContext.getCurrentInstance().responseComplete();
	}

	public boolean estaLogeado() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if ((!(auth instanceof AnonymousAuthenticationToken))) {
			this.redireccionar();
			return true;
		}
		return false;
	}

	private void redireccionar() {
		try {
			UsefulWebApplication.redireccionar("/principal.jsf");
		} catch (IOException ie) {
			logger.error(ie.getMessage(), ie);
		}
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(LoginModel loginModel) {
		this.loginModel = loginModel;
	}

	@Override
	public void afterPhase(PhaseEvent arg0) {
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.remove(WebAttributes.AUTHENTICATION_EXCEPTION);

		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest servletRequest = (HttpServletRequest) ctx
				.getExternalContext().getRequest();
		String fullURI = servletRequest.getRequestURI();

		if (fullURI.equals("/SATE_adm/index.jsf")) {
			if (e instanceof LoginException) {
				logger.error(e.getCause() == null ? e.getMessage() : e
						.getCause().getMessage());
				UsefulWebApplication.mostrarMensajeJSF(
						ConstantesGenerales.SEVERITY_ERROR, e.getMessage(), "");
			}
			UsefulWebApplication.actualizarComponente("loginForm");
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
