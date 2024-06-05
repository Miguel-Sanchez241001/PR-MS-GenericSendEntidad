package pe.bn.com.sate.infrastructure.service.internal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.UsuarioService;
import pe.bn.com.sate.persistence.mapper.internal.RepresentanteMapper;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private RepresentanteMapper representanteMapper;

	@Override
	public List<UsuarioHistoricoRepresentante> buscarUsuariosHistorico(
			Date fechaInicio, Date fechaFin, Long idRepresentante) {
		try {
			return representanteMapper.buscarUsuarios(DateFormaterUtil
					.formatoFechaBD(fechaInicio), DateFormaterUtil
					.formatoFechaBD(fechaFin),
					idRepresentante);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Representante buscarUsuario(String tipoDocumento,
			String numeroDocumento) {
		try {
			return representanteMapper.buscarUsuario(tipoDocumento,
					numeroDocumento);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Representante> buscarUsuariosDeEmpresa(Long idEmpresa) {
		try {
			return representanteMapper.buscarUsuariosDeEmpresa(idEmpresa);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void desactivarUsuario(Long idUsuario) {
		try {
			representanteMapper.desactivarUsuario(idUsuario);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void actualizarRepresentanteUsuario(Long idRepresentante,
			Long idUsuario) {
		try {
			representanteMapper.actualizarRepresentanteUsuario(idRepresentante,
					idUsuario);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void DesactivarUsuarioHistoricoRepresentante(Representante usuario) {
		try {
			UsuarioHistoricoRepresentante usuarioHistoricoRepresentante = representanteMapper
					.buscarUsuarioHistoricoRepresentante(
							usuario.getRepresentanteCreador(), usuario.getId());

			if (usuarioHistoricoRepresentante != null)
				representanteMapper
						.DesactivarUsuarioHistoricoRepresentante(new UsuarioHistoricoRepresentante(
								usuarioHistoricoRepresentante.getId(),
								new Date()));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void registrarUsuarioHistoricoRepresentante(Representante usuario) {
		try {
			representanteMapper
					.registrarUsuarioHistoricoRepresentante(new UsuarioHistoricoRepresentante(
							usuario.getRepresentanteCreador(), usuario.getId(),
							usuario.getPerfilUsuario(), new Date()));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Representante> buscarUsuariosDeRepresentante(
			Long idRepresentante) {
		try {
			return representanteMapper
					.buscarUsuariosDeRepresentante(idRepresentante);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void asignarUsuariosANuevoRepresentante(
			Representante representanteAntiguo, Representante representanteNuevo) {
		try {
			if (representanteAntiguo != null
					&& !representanteNuevo.getId().equals(
							representanteAntiguo.getId())) {
				for (Representante usuario : buscarUsuariosDeRepresentante(representanteAntiguo
						.getId())) {
					DesactivarUsuarioHistoricoRepresentante(usuario);
					actualizarRepresentanteUsuario(representanteNuevo.getId(),
							usuario.getId());
					usuario.setRepresentanteCreador(representanteNuevo.getId());
					registrarUsuarioHistoricoRepresentante(usuario);
				}
			}
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean validarCorreo(String correo, String tipoDocumento,
			String numeroDocumento) {
		try {
			if (representanteMapper.existeCorreoLaboral(correo.toLowerCase(), tipoDocumento,
					numeroDocumento) > 0)
				return false;
			else
				return true;
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean validarCelular(String telefonoMovil, String tipoDocumento,
			String numeroDocumento) {
		try {
			if (representanteMapper.existeTelefonoMovil(telefonoMovil,
					tipoDocumento, numeroDocumento) > 0)
				return false;
			else
				return true;
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
