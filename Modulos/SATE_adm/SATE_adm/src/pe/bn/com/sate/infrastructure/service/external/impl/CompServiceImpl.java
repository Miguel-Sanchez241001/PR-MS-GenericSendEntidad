package pe.bn.com.sate.infrastructure.service.external.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.bn.com.sate.infrastructure.exception.ExternalServiceCompException;
import pe.bn.com.sate.infrastructure.service.external.CompService;
import pe.bn.com.sate.infrastructure.service.external.domain.comp.ParametroInterfazKeyProxy;
import pe.bn.com.sate.infrastructure.service.external.domain.comp.SistemaParametro;
import pe.bn.com.sate.transversal.util.Fecha;
import pe.bn.com.sate.transversal.util.componentes.ParametrosComp;
import pe.bn.com.sate.transversal.util.constantes.ConstantesGenerales;

@Service
public class CompServiceImpl implements CompService {

	private static final Logger logger = Logger
			.getLogger(CompServiceImpl.class);

	@Autowired
	ParametrosComp parametrosComp;

	@Override
	public void asignarParametros() {
		try {
			byte[] llave = leerClavesSegurades();

			ParametroInterfazKeyProxy proxyComp = new ParametroInterfazKeyProxy();

			SistemaParametro sParam = proxyComp.datoParametroService(
					ConstantesGenerales.SISTEMA, ConstantesGenerales.CUENTA,
					ConstantesGenerales.SEMILLA, llave,
					ConstantesGenerales.IDUSUARIO);

			logger.info("Codigo de proceso : "
					+ sParam.getProceso().getCodigo());
			parametrosComp.setErrorComp(sParam.getProceso().getCodigo());
			parametrosComp.setDesErrorComp(sParam.getProceso().getDescripcion());

			if (sParam.getProceso().getCodigo().equals("00000")) {
				for (int n = 0; n < sParam.getGrupoParametro()
						.getGrupoParametro().size(); n++) {
					int cantFilas = sParam.getGrupoParametro()
							.getGrupoParametro().get(n).getParametro()
							.getParametro().size() - 1;
					for (int j = 0; j < cantFilas + 1; j++) {
						String param = sParam.getGrupoParametro()
								.getGrupoParametro().get(n).getParametro()
								.getParametro().get(j).getAliasParam();
						String valor = sParam.getGrupoParametro()
								.getGrupoParametro().get(n).getParametro()
								.getParametro().get(j).getValorParam();
						if (!param.equals("")) {
							this.setParametros(
									sParam.getGrupoParametro()
											.getGrupoParametro().get(n)
											.getAliasGrupo(), param, valor);
						}
					}
				}
				logger.info("Asignacion de parametros correctamente.");
			} else {
				throw new ExternalServiceCompException(
						"No se pudo obtener los datos Comp "
								+ sParam.getProceso().getCodigo());
			}
		} catch (Exception e) {
			throw new ExternalServiceCompException(e.getMessage());
		}
	}

	private void setParametros(String aliasGrupo, String param, String valor) {
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_CONEXION_BDUC)) {
			this.setDatosBduc(param, valor);
		}
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_CONEXION_DATACOM)) {
			this.setDatosDatacom(param, valor);
		}
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_TIEMPO)) {
			this.setDatosTiempo(param, valor);
		}
	}

	private void setDatosBduc(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_PERFIL2BDUC)) {
			parametrosComp.setPerfil2BDUC(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_PERFILBDUC)) {
			parametrosComp.setPerfilBDUC(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_TIPODOCBDUC)) {
			parametrosComp.setTipoDocBDUC(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_TRANSAC2BDUC)) {
			parametrosComp.setTransac2BDUC(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_TRANSACBDUC)) {
			parametrosComp.setTransacBDUC(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_USERBDUC)) {
			parametrosComp.setUserBDUC(valor);
		}
	}

	private void setDatosDatacom(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_DRIVERDATACOM)) {
			parametrosComp.setDriverDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_DBDATACOM)) {
			parametrosComp.setDbDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_URLDATACOM)) {
			parametrosComp.setUrlDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_PORTDATACOM)) {
			parametrosComp.setPortDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_USERDATACOM)) {
			parametrosComp.setUserDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_PASSDATACOM)) {
			parametrosComp.setPassDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_SYSTEMDATACOM)) {
			parametrosComp.setSystemDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_APPDATACOM)) {
			parametrosComp.setAppDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_SERVERDATACOM)) {
			parametrosComp.setServerDatacom(valor);
		} else if (param
				.equals(ConstantesGenerales.PARAM_MAXCONNECTIONSDATACOM)) {
			parametrosComp.setMaxconnectionsDatacom(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_WAITTIMEDATACOM)) {
			parametrosComp.setWaittimeDatacom(valor);
		} else if (param
				.equals(ConstantesGenerales.PARAM_INITIALCONNECTIONSDATACOM)) {
			parametrosComp.setInitialconnectionsDatacom(valor);
		}
	}

	private void setDatosTiempo(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_SESIONEXPIRADATIEMPO)) {
			parametrosComp.setSesionExpiradaTiempo(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_CONEXIONTIEMPO)) {
			parametrosComp.setConexionTiempo(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_RESPUESTATIEMPO)) {
			parametrosComp.setRespuestaTiempo(valor);
		}
	}

	public byte[] leerClavesSegurades() {
		try {
			FileInputStream fis = new FileInputStream(new File(
					ConstantesGenerales.RUTA_CLAVE_SEGURA));
			return IOUtils.toByteArray(fis);
		} catch (IOException ioe) {
			throw new ExternalServiceCompException(
					"Error con la lectura del archivo clavesegurades.key", ioe);
		}
	}
}
