package pe.bn.com.sate.infrastructure.service.internal;

import java.util.Date;
import java.util.List;

import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;

public interface UsuarioService {

	public List<UsuarioHistoricoRepresentante> buscarUsuariosHistorico(
			Date fechaInicio, Date fechaFin, Long idRepresentante);

	public Representante buscarUsuario(String tipoDocumento,
			String numeroDocumento);

	public List<Representante> buscarUsuariosDeEmpresa(Long idEmpresa);

	public List<Representante> buscarUsuariosDeRepresentante(
			Long idRepresentante);

	public void desactivarUsuario(Long idUsuario);

	public void actualizarRepresentanteUsuario(Long idRepresentante,
			Long idUsuario);

	public void DesactivarUsuarioHistoricoRepresentante(Representante usuario);

	public void registrarUsuarioHistoricoRepresentante(Representante usuario);

	public void asignarUsuariosANuevoRepresentante(
			Representante representanteAntiguo, Representante representanteNuevo);

	public boolean validarCorreo(String correo , String tipoDocumento,
			String numeroDocumento);

	public boolean validarCelular(String telefonoMovil , String tipoDocumento,
			String numeroDocumento);
}
