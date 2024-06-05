package pe.bn.com.sate.transversal.dto.sate;

import java.io.Serializable;

import pe.bn.com.sate.infrastructure.service.external.domain.bduc.BnCliente;
import pe.bn.com.sate.transversal.dto.host.Direccion;

public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String cuentaCorriente;
	private String cic;
	private String ruc;
	private String tipo;
	private String razonSocial;
	private String direccion;
	private String direccionMc;
	private String referencia;
	private String ubigeo;
	private String telefonoFijo;
	private String nombreCorto;
	private Representante representante;

	public Empresa(BnCliente bnCliente, Direccion direccion, String ruc,
			String nombreCorto) {
		telefonoFijo = direccion.getTelefonoFijo().trim();
		razonSocial = bnCliente.getF03ArazonSocial().replace('#', 'Ñ').trim();
		this.direccion = direccion.getDireccion().trim();
		ubigeo = direccion.getUbigeo().trim();
		this.ruc = ruc;
		referencia = direccion.getReferencia().trim();
		cic = bnCliente.getF01CinternoCic().trim();
		this.nombreCorto = nombreCorto;
	}

	public Empresa() {
	}

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

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getCic() {
		return cic;
	}

	public void setCic(String cic) {
		this.cic = cic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public String getDireccionMc() {
		return direccionMc;
	}

	public void setDireccionMc(String direccionMc) {
		this.direccionMc = direccionMc;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", cuentaCorriente=" + cuentaCorriente
				+ ", cic=" + cic + ", ruc=" + ruc + ", tipo=" + tipo
				+ ", razonSocial=" + razonSocial + ", direccion=" + direccion
				+ ", referencia=" + referencia + ", ubigeo=" + ubigeo
				+ ", telefonoFijo=" + telefonoFijo + ", nombreCorto="
				+ nombreCorto + "]";
	}

}
