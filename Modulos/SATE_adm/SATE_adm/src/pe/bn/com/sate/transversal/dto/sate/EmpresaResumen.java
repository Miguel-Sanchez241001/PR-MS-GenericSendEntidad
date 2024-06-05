package pe.bn.com.sate.transversal.dto.sate;

import java.util.Date;

public class EmpresaResumen {

	private Long id;
	private String cuentaCorriente;
	private String ruc;
	private String tipo;
	private String razonSocial;
	private String direccion;
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

	public String getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(String cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
		this.detalle = detalle;
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
