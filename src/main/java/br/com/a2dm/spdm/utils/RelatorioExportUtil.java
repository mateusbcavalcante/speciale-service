package br.com.a2dm.spdm.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public final class RelatorioExportUtil {

	private RelatorioExportUtil() {
	}

	public static void exportarCsvExcel(HttpServletResponse response, String nomeArquivo, List<String> cabecalhos,
			List<String[]> linhas) throws IOException {
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");

		PrintWriter writer = response.getWriter();
		writer.print('\ufeff');
		writer.println(String.join(";", cabecalhos));
		for (String[] linha : linhas) {
			writer.println(String.join(";", linha));
		}
		writer.flush();
	}
}
