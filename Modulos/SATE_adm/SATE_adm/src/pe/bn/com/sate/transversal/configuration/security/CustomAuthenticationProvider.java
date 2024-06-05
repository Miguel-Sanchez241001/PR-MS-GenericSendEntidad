package pe.bn.com.sate.transversal.configuration.security;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceCompException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.service.external.CompService;
import pe.bn.com.sate.infrastructure.service.external.FirmanteService;
import pe.bn.com.sate.infrastructure.service.external.UbigeoService;
import pe.bn.com.sate.infrastructure.service.external.domain.autentica.AutenticaRegProxy;
import pe.bn.com.sate.infrastructure.service.internal.EmpresaService;
import pe.bn.com.sate.transversal.util.Texto;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.constantes.ConstantesSeguridad;
import pe.bn.com.sate.transversal.util.excepciones.LoginException;

import com.ibm.websphere.ce.cm.StaleConnectionException;

//import pe.bn.com.sate.transversal.ws.autenticaReg.AutenticaRegProxy;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UbigeoService ubigeoService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private FirmanteService firmanteService;

	@Autowired
	private CompService compService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
//		 return autenticacionLocal(authentication);
		return autenticacion(authentication);
	}

	private Authentication autenticacion(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		String[] respuesta = null;
		try {
			respuesta = enviarAutentificacion(authentication).split("\\|");
		} catch (RemoteException e) {
			throw new LoginException(ConstantesGenerales.WS_SIN_CONEXION);
		}

		if (respuesta[ConstantesGenerales.WS_CLAVES_POSICION_STATUS]
				.equals(ConstantesGenerales.WS_CLAVES_OPERACION_EXISTOSA)) {
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
					username,
					password,
					obtenerPermisosSeguridad(respuesta[ConstantesGenerales.WS_CLAVES_POSICION_PERMISOS]));
			String nombreCompleto[] = respuesta[ConstantesGenerales.WS_CLAVES_POSICION_NOMBRES]
					.split("/");

			Usuario usuario = new Usuario(
					username,
					password,
					obtenerPermisosSeguridad(respuesta[ConstantesGenerales.WS_CLAVES_POSICION_PERMISOS]),
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_CODIGO_EMPLEADO],
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_CODIGO_AREA],
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_AREA],
					nombreCompleto[ConstantesGenerales.WS_CLAVES_APMATERNO],
					nombreCompleto[ConstantesGenerales.WS_CLAVES_APPATERNO],
					nombreCompleto[ConstantesGenerales.WS_CLAVES_NOMBRE],
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_DNI],
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_CARGO],
					obtenerListaPermisos(respuesta[ConstantesGenerales.WS_CLAVES_POSICION_PERMISOS]));
			userToken.setDetails(usuario);
			try {
				probarConexiones(2);
				compService.asignarParametros();
			} catch (ExternalServiceCompException esce) {
				throw new LoginException(
						ConstantesGenerales.EXTERNAL_PERSISTENCE_WEB_SERVICE_COMP_ERROR,
						esce);
			}
			return userToken;
		} else {
			throw new LoginException(
					respuesta[ConstantesGenerales.WS_CLAVES_POSICION_MENSAJE_FALLO_STATUS]);
		}
	}

	private String enviarAutentificacion(Authentication authentication)
			throws RemoteException {
		AutenticaRegProxy proxy = new AutenticaRegProxy();
		String usuario = authentication.getName();
		String password = Texto.aumentarCaracteres(
				(String) authentication.getCredentials(),
				ConstantesGenerales.WS_CLAVES_NUMERO_CARACTERES_PASSWORD);
		return proxy.claveHost(usuario + password
				+ ConstantesGenerales.WS_CLAVES_APP
				+ ConstantesGenerales.WS_CLAVES_CONSTID);
	}

	private List<GrantedAuthority> obtenerPermisosSeguridad(String permisos) {
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		for (int i = 0; i < permisos.length() - 3; i = i + 3) {

			if (ConstantesSeguridad.OPCION_ACC.containsKey(permisos.substring(
					i, i + 3))) {
				AUTHORITIES.add(new SimpleGrantedAuthority(
						ConstantesSeguridad.OPCION_ACC.get(permisos.substring(
								i, i + 3))));
			}
		}
		return AUTHORITIES;
	}

	private List<String> obtenerListaPermisos(String permisos) {
		List<String> listaPermisos = new ArrayList<String>();
		for (int i = 0; i < permisos.length() - 3; i = i + 3) {
			listaPermisos.add(permisos.substring(i, i + 3));
		}
		return listaPermisos;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private Authentication autenticacionLocal(Authentication authentication) {

		try {
			probarConexiones(1);
			compService.asignarParametros();

		} catch (ExternalServiceCompException esce) {
			esce.printStackTrace();
		} catch (ServiceException se) {
			se.printStackTrace();
		}

		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		String permisos = obtenerPermisos(username);

		List<String> accesos = new ArrayList<String>();
		List<GrantedAuthority> roles = obtenerPermisosSeguridad(permisos);
		Collection<? extends GrantedAuthority> authorities = roles;

		Usuario usuario = new Usuario(username, password, authorities,
				"Infraestructura", "Renzo", "Broncano", "Rivera",
				obtenerListaPermisos(permisos));

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
				username, password, roles);

		userToken.setDetails(usuario);

		return userToken;
	}

	public String obtenerPermisos(String username) {
		if (username.equals("admi"))
			return "01S02S03S04S05S06S07S08S09S10S11S12S13S14S15S16S17S18S19S20S21S22S23S24S25S26S27S28S29S30S";
		else if (username.equals("pcmo")) {
			return "01S02S03S04S05S06S07N08S09N10N11N12N13N14N15N16N17N18N19N20N21N22N23N24N25N26N27N28N29N30N";
		} else if (username.equals("mrea")) {
			return "01S02S03S04S05S06S07S08S09S10S11S12S13S14S15S16S17S18S19S20S21S22S23S24S25S26S27S28S29S30S";
		}
		return "";
	}

//	@Transactional(propagation = Propagation.REQUIRED)
	public void probarConexiones(int intentosExtra) {
		try {
			ubigeoService.probarConexion();
			empresaService.probarConexion();
			firmanteService.probarConexion();
		} catch (DataAccessResourceFailureException dre) {
			probarConexiones(--intentosExtra);
		} catch (Exception ex) {
			if (intentosExtra > 0)
				probarConexiones(--intentosExtra);
			else
				throw new LoginException("Ocurrio un error, intente nuevamente",ex);
		}
	}

}