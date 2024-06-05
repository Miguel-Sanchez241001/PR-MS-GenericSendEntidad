package pe.bn.com.sate.transversal.dto.sate;

import java.util.Date;

public class Actividad {

	private Long idUsuario;
	private String accion;
	private String funcionalidad;
	private Date fecha;
	private String registroCambios;

	public Actividad() {
		// TODO Auto-generated constructor stub
	}

	public Actividad(Long idUsuario, String accion, String funcionalidad,
			Date fecha, String registroCambios) {
		super();
		this.idUsuario = idUsuario;
		this.accion = accion;
		this.funcionalidad = funcionalidad;
		this.fecha = fecha;
		this.registroCambios = registroCambios;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(String funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRegistroCambios() {
		return registroCambios;
	}

	public void setRegistroCambios(String registroCambios) {
		this.registroCambios = registroCambios;
	}

}
