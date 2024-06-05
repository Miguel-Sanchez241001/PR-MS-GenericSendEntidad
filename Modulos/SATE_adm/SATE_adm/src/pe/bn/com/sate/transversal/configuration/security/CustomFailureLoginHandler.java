package pe.bn.com.sate.transversal.configuration.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.constantes.ExceptionConstants;
import pe.bn.com.sate.transversal.util.constantes.FormatConstants;
import pe.bn.com.sate.transversal.util.excepciones.LoginException;

@Component
public class CustomFailureLoginHandler extends
		SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);
		if (exception.getClass().isAssignableFrom(
				UsernameNotFoundException.class)) {

		}
	}
}