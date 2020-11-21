package utilitarios;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tratamento de filtros (acentuação, segurança, logging, etc) Mais info:
 * https://www.caelum.com.br/apostila-java-web/recursos-importantes-filtros
 * 
 * @WebFilter("/*") => filtra TODAS (*) as requisições da aplicação
 * 
 * @author Claudio Martins
 *
 */
@WebFilter("/*")
public class WebFiltros implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// trata o encoding (acentuação) nas requisicoes para o servidor
	    request.setCharacterEncoding("UTF-8");

		// instrui o navegador a não armazenar em cache as páginas JSF dinâmicas.
		// O Filtro é mapeado no nome do servlet de FacesServlet e
		// adiciona os cabeçalhos de resposta necessários para desativar o cache do
		// navegador.
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
			// Skip JSF resources (CSS/JS/Images/etc)
			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			res.setDateHeader("Expires", 0); // Proxies.
		}

		// método que permite ao usuário prosseguir com seu fluxo de navegação:
		chain.doFilter(request, response);
	}

}
