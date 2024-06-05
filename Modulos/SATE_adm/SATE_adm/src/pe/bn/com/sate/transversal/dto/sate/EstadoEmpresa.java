package pe.bn.com.sate.transversal.dto.sate;

import java.util.Date;

public class EstadoEmpresa {

	private Long id;
	private Long idEmpresa;
	private Date fechaAfiliacion;
	private Date fechaDesafiliacion;
	private String motivo;
	private String detalle;
	private String usuarioAfiliacion;
	private String usuarioDesafiliacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	public Date getFechaDesafiliacion() {
		return fechaDesafiliacion;
	}

	public void setFechaDesafiliacion(Date fechaDesafiliacion) {
		this.fechaDesafiliacion = fechaDesafiliacion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle.toUpperCase();
	}

	public String getUsuarioAfiliacion() {
		return usuarioAfiliacion;
	}

	public void setUsuarioAfiliacion(String usuarioAfiliacion) {
		this.usuarioAfiliacion = usuarioAfiliacion;
	}

	public String getUsuarioDesafiliacion() {
		return usuarioDesafiliacion;
	}

	public void setUsuarioDesafiliacion(String usuarioDesafiliacion) {
		this.usuarioDesafiliacion = usuarioDesafiliacion;
	}

}
