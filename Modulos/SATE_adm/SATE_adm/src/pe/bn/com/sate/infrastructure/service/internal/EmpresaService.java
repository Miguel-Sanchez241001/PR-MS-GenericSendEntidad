package pe.bn.com.sate.infrastructure.service.internal;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EmpresaResumen;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;

public interface EmpresaService {

	public void afiliarEmpresa(Empresa empresa);

	public void bloquearEmpresa(EstadoEmpresa estadoEmpresa);

	public EstadoEmpresa buscarEstadoEmpresa(String ruc);

	public List<EmpresaResumen> buscarEmpresas(Date fechaInicio, Date fechaFin);
	
	public List<EmpresaResumen> buscarHistorialEmpresas(Date fechaInicio, Date fechaFin);

	public Empresa buscarEmpresa(String ruc);
	
	public void actualizarEmpresa(Empresa empresa);
	
	public Empresa buscarEmpresaPorRepresentante(String tipoDocumento,String numDocumento);
	
	public Empresa buscarEmpresaPorUsuario(String tipoDocumento,String numDocumento);
	
	public void probarConexion();

}
