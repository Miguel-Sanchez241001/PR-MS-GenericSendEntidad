package pe.bn.com.sate.infrastructure.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.exception.ServiceException;
import pe.bn.com.sate.infrastructure.service.internal.ActividadService;
import pe.bn.com.sate.infrastructure.service.internal.EmpresaService;
import pe.bn.com.sate.infrastructure.service.internal.RepresentanteService;
import pe.bn.com.sate.infrastructure.service.internal.UsuarioService;
import pe.bn.com.sate.transversal.dto.sate.Actividad;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;

@Component
public class ConsultasFacade {

	private @Autowired
	EmpresaService empresaService;

	private @Autowired
	RepresentanteService representanteService;

	private @Autowired
	UsuarioService usuarioService;

	private @Autowired
	ActividadService actividadService;

	public List<Representante> buscarRepresentantes(String ruc,
			Date fechaInicio, Date fechaFin) {

		try {
			Empresa empresaBuscada = empresaService.buscarEmpresa(ruc);
			List<Representante> representantes = new ArrayList<Representante>();

			if (empresaBuscada != null) {
				representantes = representanteService.buscarRepresentantes(
						fechaInicio, fechaFin, empresaBuscada.getId());

				if (representantes != null && !representantes.isEmpty()) {
					return representantes;
				} else {
					throw new ServiceException(
							"No hay representantes con criterios de busqueda seleccionados.");
				}
			} else {
				throw new ServiceException(
						"El número de RUC no pertenece a una empresa afiliada.");
			}
		} catch (InternalServiceException ise) {
			throw new InternalServiceException(ise.getMessage(), ise);
		}

	}

	public List<Actividad> buscarActividades(Long idUsuario, Date fechaInicio,
			Date fechaFin) {
		try {
			return actividadService.buscarActividades(idUsuario, fechaInicio,
					fechaFin);
		} catch (InternalServiceException ise) {
			throw ise;
		}
	}

	public List<Representante> buscarHistorialRepresentantes(String ruc,
			Date fechaInicio, Date fechaFin) {

		try {
			Empresa empresaBuscada = empresaService.buscarEmpresa(ruc);
			List<Representante> representantes = new ArrayList<Representante>();

			if (empresaBuscada != null) {
				representantes = representanteService
						.buscarHistorialRepresentantes(fechaInicio, fechaFin,
								empresaBuscada.getId());

				if (representantes != null && !representantes.isEmpty()) {
					return representantes;
				} else {
					throw new ServiceException(
							"No hay representantes con criterios de busqueda seleccionados.");
				}
			} else {
				throw new ServiceException(
						"El número de RUC no pertenece a una empresa.");
			}
		} catch (InternalServiceException ise) {
			throw new InternalServiceException(ise.getMessage(), ise);
		}
	}

	public List<UsuarioHistoricoRepresentante> buscarUsuariosHistoricos(
			Date fechaInicio, Date fechaFin, Long idRepresentante) {
		try {

			List<UsuarioHistoricoRepresentante> usuarios = usuarioService
					.buscarUsuariosHistorico(fechaInicio, fechaFin,
							idRepresentante);

			if (usuarios != null && !usuarios.isEmpty()) {
				return usuarios;
			} else {
				throw new ServiceException(
						"No hay historial de usuarios con los criterios de busqueda seleccionados.");
			}

		} catch (InternalServiceException ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
