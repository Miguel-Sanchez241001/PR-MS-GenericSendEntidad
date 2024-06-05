package pe.bn.com.sate.infrastructure.service.internal.impl;

import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.DEPARTAMENTO;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.INTERIOR;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.KILOMETRO;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.LOTE;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.MANZANA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.NOMBRE_VIA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.NOMBRE_ZONA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.NUMERO_VIA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.OFICINA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.PISO;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.SECTOR;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.SIN_NUMERO_VIA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.TIPO_VIA;
import static pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales.TIPO_ZONA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.bn.com.sate.infrastructure.exception.InternalServiceException;
import pe.bn.com.sate.infrastructure.service.internal.EmpresaService;
import pe.bn.com.sate.persistence.mapper.internal.EmpresaMapper;
import pe.bn.com.sate.persistence.mapper.internal.NombreCortoMapper;
import pe.bn.com.sate.persistence.mapper.internal.ParametroMapper;
import pe.bn.com.sate.persistence.mapper.internal.RepresentanteMapper;
import pe.bn.com.sate.transversal.dto.sate.Empresa;
import pe.bn.com.sate.transversal.dto.sate.EmpresaResumen;
import pe.bn.com.sate.transversal.dto.sate.EstadoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.Parametro;
import pe.bn.com.sate.transversal.dto.sate.Representante;
import pe.bn.com.sate.transversal.dto.sate.RepresentanteHistoricoEmpresa;
import pe.bn.com.sate.transversal.dto.sate.UsuarioHistoricoRepresentante;
import pe.bn.com.sate.transversal.util.DateFormaterUtil;
import pe.bn.com.sate.transversal.util.NumeroUtil;
import pe.bn.com.sate.transversal.util.StringUtil;
import pe.bn.com.sate.transversal.util.UsefulWebApplication;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;
import pe.bn.com.sate.transversal.util.enums.TipoDocumento;
import pe.bn.com.sate.transversal.util.enums.TipoEstadoUsuario;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private @Autowired EmpresaMapper empresaMapper;

	private @Autowired RepresentanteMapper representanteMapper;

	@Autowired
	private ParametrosComp parametrosComp;

	private @Autowired NombreCortoMapper nombreCortoMapper;

	@Autowired
	private ParametroMapper parametroMapper;

	@Override
	@Transactional
	public void afiliarEmpresa(Empresa empresa) {
		try {
			Empresa empresaBD = empresaMapper.buscarEmpresa(empresa.getRuc());
			empresa.setDireccionMc(empresa.getDireccion() == null ? null : generarDireccionMc(empresa.getDireccion()));
			if (empresaBD == null) {
				empresaMapper.registrarEmpresa(empresa);
				empresaBD = empresa;
			} else {
				empresa.setId(empresaBD.getId());
				empresaMapper.actualizarEmpresa(empresa);
			}

			EstadoEmpresa estadoEmpresa = new EstadoEmpresa();
			estadoEmpresa.setFechaAfiliacion(new Date());
			estadoEmpresa.setIdEmpresa(empresaBD.getId());
			estadoEmpresa.setUsuarioAfiliacion(UsefulWebApplication.obtenerUsuario().getUsername());
			empresaMapper.registrarEstadoEmpresa(estadoEmpresa);

//			if (true) {
//				throw new RuntimeException();
//			}
			actualizarRepresentante(empresa.getRepresentante(), empresa.getId());

		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}

	}

	public void actualizarRepresentante(Representante representante, Long idEmpresa) {
		try {
			Representante representanteExistente = representanteMapper.buscarUsuario(representante.getNumeroDocumento(),
					representante.getTipoDocumento());

			representante.setRepresentanteCreador(null);
			representante.setFlagCambioClave(ConstantesGenerales.DEBE_CAMBIAR_CLAVE);
			representante.setPerfilUsuario((long) 1);
			representante.setEstado(TipoEstadoUsuario.ACTIVO.getId());

			if (representante.getTipoDocumento().equals(TipoDocumento.DNI.getCodigoBduc()))
				representante.setNumeroDocumento(NumeroUtil.agregarCerosIzquierda(representante.getNumeroDocumento(),
						TipoDocumento.obtenerLengthPorTipo(representante.getTipoDocumento())));

			representante.setRepresentanteCreador(null);
			representante.setFechaActualizacionClave(new Date());
			representante.setIntentosIniciarSesion((long) 0);

			if (representanteExistente == null) {
				representante.setFechaRegistro(new Date());
				representanteMapper.registrarRepresentante(representante);
			} else {
				//asignarUsuariosANuevoRepresentante(representanteExistente, representante);
				representante.setId(representanteExistente.getId());
				representanteMapper.actualizarRepresentante(representante);

			}

			Representante representanteActual = representanteMapper.obtenerRepresentanteActual(idEmpresa);
			if (representanteActual == null || !representante.getId().equals(representanteActual.getId())) {
				RepresentanteHistoricoEmpresa representanteHistoricoEmpresa = new RepresentanteHistoricoEmpresa();
				representanteHistoricoEmpresa.setIdEmpresa(idEmpresa);
				representanteHistoricoEmpresa.setIdRepresentante(representante.getId());
				representanteHistoricoEmpresa.setFechaRegistro(new Date());
				representanteMapper.registrarRepresentanteHistoricoEmpresa(representanteHistoricoEmpresa);
			}
			
			
			asignarUsuariosANuevoRepresentante(representanteActual, representante);

			empresaMapper.actualizarRepresentanteEmpresa(idEmpresa, representante.getId());

		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	private void asignarUsuariosANuevoRepresentante(Representante representanteAntiguo,
			Representante representanteNuevo) {
		try {
			if (representanteAntiguo != null && !representanteNuevo.getId().equals(representanteAntiguo.getId())) {
				for (Representante usuario : representanteMapper.buscarUsuariosDeRepresentante(representanteAntiguo.getId())) {
					DesactivarUsuarioHistoricoRepresentante(usuario);
					representanteMapper.actualizarRepresentanteUsuario(representanteNuevo.getId(), usuario.getId());
					usuario.setRepresentanteCreador(representanteNuevo.getId());
					representanteMapper.registrarUsuarioHistoricoRepresentante(
							new UsuarioHistoricoRepresentante(usuario.getRepresentanteCreador(), usuario.getId(),
									usuario.getPerfilUsuario(), new Date()));
				}
			}
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	private void DesactivarUsuarioHistoricoRepresentante(Representante usuario) {
		try {
			UsuarioHistoricoRepresentante usuarioHistoricoRepresentante = representanteMapper
					.buscarUsuarioHistoricoRepresentante(usuario.getRepresentanteCreador(), usuario.getId());

			if (usuarioHistoricoRepresentante != null)
				representanteMapper.DesactivarUsuarioHistoricoRepresentante(
						new UsuarioHistoricoRepresentante(usuarioHistoricoRepresentante.getId(), new Date()));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	@Transactional
	public void bloquearEmpresa(EstadoEmpresa estadoEmpresa) {
		try {
			List<Representante> usuarios = representanteMapper.buscarUsuariosDeEmpresa(estadoEmpresa.getIdEmpresa());

			empresaMapper.bloquearEmpresa(estadoEmpresa);
			empresaMapper.actualizarRepresentanteEmpresa(estadoEmpresa.getIdEmpresa(), null);

			for (Representante usuario : usuarios) {
				representanteMapper.desactivarUsuario(usuario.getId());
				DesactivarUsuarioHistoricoRepresentante(usuario);
			}
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}

	}

	@Override
	public EstadoEmpresa buscarEstadoEmpresa(String ruc) {
		try {
			return empresaMapper.buscarEstadoEmpresa(ruc);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<EmpresaResumen> buscarEmpresas(Date fechaInicio, Date fechaFin) {
		try {
			return empresaMapper.buscarEmpresas(DateFormaterUtil.formatoFechaBD(fechaInicio),
					DateFormaterUtil.formatoFechaBD(fechaFin));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<EmpresaResumen> buscarHistorialEmpresas(Date fechaInicio, Date fechaFin) {
		try {
			return empresaMapper.buscarHistorialEmpresas(DateFormaterUtil.formatoFechaBD(fechaInicio),
					DateFormaterUtil.formatoFechaBD(fechaFin));
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Empresa buscarEmpresa(String ruc) {
		try {
			return empresaMapper.buscarEmpresa(ruc);

		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	@Transactional
	public void actualizarEmpresa(Empresa empresa) {
		try {
			empresaMapper.actualizarEmpresa(empresa);
			actualizarRepresentante(empresa.getRepresentante(), empresa.getId());
//			throw new RuntimeException();
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Empresa buscarEmpresaPorRepresentante(String tipoDocumento, String numDocumento) {
		try {
			return empresaMapper.buscarEmpresaPorRepresentante(tipoDocumento, numDocumento);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Empresa buscarEmpresaPorUsuario(String tipoDocumento, String numDocumento) {
		try {
			return empresaMapper.buscarEmpresaPorUsuario(tipoDocumento, numDocumento);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void probarConexion() {
		try {
			empresaMapper.probarConexion();
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	public String generarDireccionMc(String direccion) {

		String[] direccionMc = new String[14];
		Integer posiciones[] = new Integer[14];
		List<String>[] palabrasReservadas = new ArrayList[14];

		for (int i = 0; i < 14; i++) {
			direccionMc[i] = "";
			palabrasReservadas[i] = new ArrayList<String>();
		}

		LOOP_PALABRAS_RESERVADAS: for (Parametro parametro : parametroMapper.buscarParametrosDireccionMC()) {
			if (parametro.getIdTabla() == TIPO_VIA) {
				palabrasReservadas[TIPO_VIA].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == NUMERO_VIA) {
				palabrasReservadas[NUMERO_VIA].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == DEPARTAMENTO) {
				palabrasReservadas[DEPARTAMENTO].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == PISO) {
				palabrasReservadas[PISO].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == MANZANA) {
				palabrasReservadas[MANZANA].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == LOTE) {
				palabrasReservadas[LOTE].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == INTERIOR) {
				palabrasReservadas[INTERIOR].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == SECTOR) {
				palabrasReservadas[SECTOR].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == KILOMETRO) {
				palabrasReservadas[KILOMETRO].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == TIPO_ZONA) {
				palabrasReservadas[TIPO_ZONA].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
			if (parametro.getIdTabla() == SIN_NUMERO_VIA) {
				palabrasReservadas[SIN_NUMERO_VIA].add(parametro.getValor());
				continue LOOP_PALABRAS_RESERVADAS;
			}
		}

		String palabras[] = NumeroUtil.separarLetrasDeNumeros(direccion).replace(',', ' ').replace('"', ' ')
				.replace('.', ' ').replace('-', ' ').replace("  ", " ").replace("   ", " ").split(" ");

		LOOP_PALABRAS: for (int i = 0; i < palabras.length; i++) {
			if (posiciones[DEPARTAMENTO] == null)
				for (String palabraReservada : palabrasReservadas[DEPARTAMENTO]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[DEPARTAMENTO] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[PISO] == null)
				for (String palabraReservada : palabrasReservadas[PISO]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[PISO] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[MANZANA] == null)
				for (String palabraReservada : palabrasReservadas[MANZANA]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[MANZANA] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[LOTE] == null)
				for (String palabraReservada : palabrasReservadas[LOTE]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[LOTE] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[INTERIOR] == null)
				for (String palabraReservada : palabrasReservadas[INTERIOR]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[INTERIOR] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[SECTOR] == null)
				for (String palabraReservada : palabrasReservadas[SECTOR]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[SECTOR] = i;
						continue LOOP_PALABRAS;
					}
				}
			if (posiciones[KILOMETRO] == null)
				for (String palabraReservada : palabrasReservadas[KILOMETRO]) {
					if (palabras[i].equals(palabraReservada)) {
						posiciones[KILOMETRO] = i;
						continue LOOP_PALABRAS;
					}
				}
		}

		Integer menorPosicion = NumeroUtil.menorValor(posiciones);
		if (menorPosicion == null)
			menorPosicion = palabras.length;
		Integer menorPosicionAnterior = menorPosicion - 1;

		for (String palabraReservada : palabrasReservadas[TIPO_VIA]) {
			if (palabras[TIPO_VIA].equals(palabraReservada)) {
				direccionMc[TIPO_VIA] = palabras[TIPO_VIA];
				palabras[TIPO_VIA] = "";
				break;
			}
		}

		if (menorPosicionAnterior > 0) {
			if (NumeroUtil.esNumero(palabras[menorPosicionAnterior])) {
				direccionMc[NUMERO_VIA] = palabras[menorPosicionAnterior];
				palabras[menorPosicionAnterior] = "";
			} else
				for (String palabraReservada : palabrasReservadas[SIN_NUMERO_VIA]) {
					if (palabras[menorPosicionAnterior].equals(palabraReservada)) {
						direccionMc[NUMERO_VIA] = "000000";
						palabras[menorPosicionAnterior] = "";
					}
				}
		}

		LOOP_VIAS: for (int i = 0; i < (menorPosicion != null ? menorPosicion : palabras.length); i++) {
			for (String palabraReservada : palabrasReservadas[NUMERO_VIA]) {
				if (palabras[i].equals(palabraReservada)) {
					palabras[i] = "";
					continue LOOP_VIAS;
				}
			}
			for (String palabraReservada : palabrasReservadas[SIN_NUMERO_VIA]) {
				if (palabras[i].equals(palabraReservada)) {
					palabras[i] = "";
					continue LOOP_VIAS;
				}
			}
			direccionMc[NOMBRE_VIA] += palabras[i] + " ";
			palabras[i] = "";
		}

		if (posiciones[DEPARTAMENTO] != null) {
			direccionMc[DEPARTAMENTO] = palabras[posiciones[DEPARTAMENTO] + 1];
			palabras[posiciones[DEPARTAMENTO]] = "";
			palabras[posiciones[DEPARTAMENTO] + 1] = "";
		}

		direccionMc[OFICINA] = "";

		if (posiciones[PISO] != null) {
			direccionMc[PISO] = palabras[posiciones[PISO] - 2] + palabras[posiciones[PISO] - 1];
			palabras[posiciones[PISO]] = "";
			palabras[posiciones[PISO] - 2] = "";
			palabras[posiciones[PISO] - 1] = "";
		}

		if (posiciones[MANZANA] != null) {
			for (int i = posiciones[MANZANA] + 1; i < posiciones[LOTE]; i++) {
				direccionMc[MANZANA] += palabras[i] + " ";
				palabras[i] = "";
			}
			palabras[posiciones[MANZANA]] = "";
		}

		if (posiciones[LOTE] != null) {
			direccionMc[LOTE] = palabras[posiciones[LOTE] + 1];

			palabras[posiciones[LOTE]] = "";
			palabras[posiciones[LOTE] + 1] = "";

			if (palabras.length < posiciones[LOTE] + 2 && palabras[posiciones[LOTE] + 2].length() == 1) {
				direccionMc[LOTE] += palabras[posiciones[LOTE] + 2];
				palabras[posiciones[LOTE] + 2] = "";
			}
		}

		if (posiciones[INTERIOR] != null) {
			direccionMc[INTERIOR] = palabras[posiciones[INTERIOR] + 1]
					+ (palabras[posiciones[INTERIOR] + 2].length() == 1 ? palabras[posiciones[INTERIOR] + 2] : "");
			palabras[posiciones[INTERIOR]] = "";
			palabras[posiciones[INTERIOR] + 1] = "";
			if (palabras[posiciones[INTERIOR] + 2].length() == 1)
				palabras[posiciones[INTERIOR] + 2] = "";
		}

		if (posiciones[SECTOR] != null) {
			direccionMc[SECTOR] = palabras[posiciones[SECTOR] + 1];
			palabras[posiciones[SECTOR]] = "";
			palabras[posiciones[SECTOR] + 1] = "";
		}

		if (posiciones[KILOMETRO] != null) {
			direccionMc[KILOMETRO] = palabras[posiciones[KILOMETRO] + 1]
					+ (NumeroUtil.esNumero(palabras[posiciones[KILOMETRO] + 2])
							? "," + palabras[posiciones[KILOMETRO] + 2]
							: "");

			palabras[posiciones[KILOMETRO]] = "";
			palabras[posiciones[KILOMETRO] + 1] = "";
			if (NumeroUtil.esNumero(palabras[posiciones[KILOMETRO] + 2]))
				palabras[posiciones[KILOMETRO] + 2] = "";
		}

		LOOP_ZONAS: for (int i = 0; i < palabras.length; i++) {
			if (direccionMc[TIPO_ZONA].isEmpty())
				for (String palabraReservada : palabrasReservadas[TIPO_ZONA]) {
					if (palabras[i].equals(palabraReservada)) {
						direccionMc[TIPO_ZONA] = palabras[i];
						palabras[i] = "";
						continue LOOP_ZONAS;
					}
				}
			direccionMc[NOMBRE_ZONA] += palabras[i] + " ";
			palabras[i] = "";
		}

		return StringUtil.agregarEspacios(direccionMc[TIPO_VIA].trim(), 5)
				+ StringUtil.agregarEspacios(direccionMc[NOMBRE_VIA].trim(), 30)
				+ NumeroUtil.agregarCerosIzquierda(direccionMc[NUMERO_VIA], 6)
				+ StringUtil.agregarEspacios(direccionMc[DEPARTAMENTO].trim(), 6)
				+ StringUtil.agregarEspacios(direccionMc[OFICINA].trim(), 5)
				+ StringUtil.agregarEspacios(direccionMc[PISO].trim(), 3)
				+ StringUtil.agregarEspacios(direccionMc[MANZANA].trim(), 3)
				+ StringUtil.agregarEspacios(direccionMc[LOTE].trim(), 3)
				+ StringUtil.agregarEspacios(direccionMc[INTERIOR].trim(), 3)
				+ StringUtil.agregarEspacios(direccionMc[SECTOR].trim(), 3)
				+ StringUtil.agregarEspacios(direccionMc[KILOMETRO].trim(), 5)
				+ StringUtil.agregarEspacios(direccionMc[TIPO_ZONA].trim(), 5)
				+ StringUtil.agregarEspacios(direccionMc[NOMBRE_ZONA].trim(), 30);
	}
}
