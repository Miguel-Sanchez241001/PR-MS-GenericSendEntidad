package pe.bn.com.sate.transversal.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import pe.bn.com.sate.transversal.util.constantes.ConstantesPagina;
import pe.bn.com.sate.transversal.util.constantes.ConstantesSeguridad;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private @Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	private @Autowired
	CustomFailureLoginHandler customFailureLoginHandler;

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/resources/**");
		webSecurity.ignoring().antMatchers("/error/*");
	}

	protected void configure(HttpSecurity http) throws Exception {

		http.headers().httpStrictTransportSecurity().cacheControl();

		http.sessionManagement().maximumSessions(1)
				.expiredUrl(ConstantesPagina.PAGINA_INDEX).and()
				.invalidSessionUrl(ConstantesPagina.PAGINA_INDEX);

		http.authorizeRequests()
				.antMatchers(ConstantesPagina.PAGINA_TRAMITE_AFILIAR_SERVICIO)
				.hasAnyAuthority(
						ConstantesSeguridad.ACCESO_TRAMITES_AFILIAR_SERVICIO)
				.antMatchers(
						ConstantesPagina.PAGINA_TRAMITE_DESAFILIAR_SERVICIO)
				.hasAnyAuthority(
						ConstantesSeguridad.ACCESO_TRAMITES_DESAFILIAR_SERVICIO)
				.antMatchers(
						ConstantesPagina.PAGINA_TRAMITE_ACTUALIZAR_REPRESENTANTE)
				.hasAnyAuthority(
						ConstantesSeguridad.ACCESO_TRAMITES_ACTUALIZAR_REPRESENTANTE)
				.antMatchers(ConstantesPagina.PAGINA_CONSULTA_EMPRESAS)
				.hasAnyAuthority(ConstantesSeguridad.ACCESO_CONSULTA_EMPRESAS)
				.antMatchers(ConstantesPagina.PAGINA_CONSULTA_REPRESENTANTES)
				.hasAnyAuthority(
						ConstantesSeguridad.ACCESO_CONSULTA_REPRESENTANTES)
				.antMatchers(ConstantesPagina.PAGINA_CONSULTA_USUARIOS)
				.hasAnyAuthority(ConstantesSeguridad.ACCESO_CONSULTA_USUARIOS)
				.antMatchers(ConstantesPagina.PAGINA_PRINCIPAL)
				.authenticated()
				.and()
				.formLogin()
				.loginPage(ConstantesPagina.PAGINA_INDEX)
				.usernameParameter(ConstantesPagina.LOGIN_PARAMETRO_USUARIO)
				.passwordParameter(ConstantesPagina.LOGIN_PARAMETRO_CONTRASENIA)
				.loginProcessingUrl(ConstantesPagina.LOGIN_URL_AUTENTICACION)
				.successHandler(authenticationSuccessHandler()).and()
				.exceptionHandling()
				.accessDeniedPage(ConstantesPagina.PAGINA_ACCESO_DENEGADO)
				.and().logout()
				.logoutUrl(ConstantesPagina.LOGIN_URL_CERRAR_SESION)
				.logoutSuccessUrl(ConstantesPagina.PAGINA_INDEX).and().csrf()
				.disable();

		http.addFilterAfter(new AjaxTimeoutRedirectFilter(),
				ExceptionTranslationFilter.class);
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomSuccessLoginHandler();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

}
