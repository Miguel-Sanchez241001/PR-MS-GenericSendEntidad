package pe.bn.com.sate.transversal.configuration.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pe.bn.com.sate.transversal.util.DateFormaterUtil;

public class Usuario extends User {

	private String codigoEmpleado;
	private String codigoArea;
	private String nombreArea;
	private String apPaterno;
	private String apMaterno;
	private String nombres;
	private String dni;
	private List<String> permisos;
	private String cargo;
	private String tipoDocumento;
	private String numeroDocumento;

	public Usuario(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public Usuario(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			String codigoEmpleado, String codigoArea, String nombreArea,
			String apPaterno, String apMaterno, String nombres, String dni,
			String cargo, List<String> permisos) {
		super(username, password, authorities);
		this.codigoEmpleado = codigoEmpleado;
		this.codigoArea = codigoArea;
		this.nombreArea = nombreArea;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
		this.dni = dni;
		this.cargo = cargo;
		this.permisos = permisos;
	}

	/**
	 * 
	 */
	public String nombreCompleto() {
		return this.nombres + " " + this.apPaterno + " " + this.apMaterno;
	}

	public String fechaAcceso() {
		return DateFormaterUtil.fechaHoy();
	}

	public Usuario(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			String nombreArea, String apPaterno, String apMaterno,
			String nombres, List<String> permisos) {
		super(username, password, authorities);
		this.nombreArea = nombreArea;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
		this.permisos = permisos;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<String> permisos) {
		this.permisos = permisos;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
