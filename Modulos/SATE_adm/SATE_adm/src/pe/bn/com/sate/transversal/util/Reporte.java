package pe.bn.com.sate.transversal.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.apache.log4j.Logger;

import pe.bn.com.sate.transversal.util.enums.TipoReporte;

public class Reporte {
	private static final Logger logger = Logger.getLogger(Reporte.class);

	private static void exportarDocumento(JasperPrint jasperPrint,
			FacesContext context, HttpServletResponse response,
			HttpServletRequest request, String nombreArchivo) {
		byte[] pdf;
		try {
			pdf = JasperExportManager.exportReportToPdf(jasperPrint);
			response.addHeader("Content-disposition", "attachment;filename="
					+ nombreArchivo + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.getOutputStream().write(pdf);
			response.flushBuffer();
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int mostrarReporte(TipoReporte reporte, HashMap parametros,
			ArrayList lista, FacesContext context,
			ServletContext servletContext, long tipoReporte) {
		parametros.put("tipoReporte", "" + tipoReporte);
		if (tipoReporte == 1)
			return mostrarReportePDF(reporte, parametros, lista, context,
					servletContext);
		else if (tipoReporte == 2)
			return mostrarReporteExcel(reporte, parametros, lista, context,
					servletContext);
		return 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int mostrarReporteExcel(TipoReporte reporte,
			HashMap parametros, ArrayList list, FacesContext context,
			ServletContext servletContext) {
		try {
			String reportPath = FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRealPath(
							"/WEB-INF/reportes/" + reporte.getNombreReporte()
									+ ".jrxml");
			JasperReport report = JasperCompileManager
					.compileReport(reportPath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report,
					parametros, new JRBeanCollectionDataSource(list));
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse
					.addHeader("Content-Type",
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename=" + reporte.getNombreReporte()
							+ ".xlsx");
			ServletOutputStream outputStream = httpServletResponse
					.getOutputStream();

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
					outputStream));
			configuration.setIgnoreGraphics(false);
			configuration.setShowGridLines(true);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			configuration.setWhitePageBackground(true);
			configuration.setDetectCellType(true);
			configuration.setOnePagePerSheet(false);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			outputStream.flush();
			outputStream.close();

		} catch (Exception jRException) {
			logger.info("ERROR : " + jRException.getMessage());
			jRException.printStackTrace();
			return 3;
		}
		return 0;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int mostrarReportePDF(TipoReporte reporte,
			HashMap parametros, ArrayList list, FacesContext context,
			ServletContext servletContext) {
		try {
			String imagen = FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRealPath("/WEB-INF/reportes/logo-banco-nacion.png");
			parametros.put("LOGO", imagen);
			String reportPath = FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRealPath(
							"/WEB-INF/reportes/"
									+ (reporte.getNombreReporte() + ".jrxml"));
			JasperReport report = JasperCompileManager
					.compileReport(reportPath);
			JasperPrint documento;
			if (list == null || list.isEmpty()) {
				documento = JasperFillManager.fillReport(report, parametros,
						new JREmptyDataSource());
			} else {
				documento = JasperFillManager.fillReport(report, parametros,
						new JRBeanCollectionDataSource(list));
			}
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			if (documento.getPages().size() > 0) {
				logger.info("saca pdF");
				exportarDocumento(documento, context, response, request,
						reporte.getNombreArchivo());
				return 1;
			} else {
				return 2;
			}

		} catch (JRException jRException) {
			logger.error("ERROR : " + jRException.getMessage());
			jRException.printStackTrace();
			return 3;
		}
	}
}