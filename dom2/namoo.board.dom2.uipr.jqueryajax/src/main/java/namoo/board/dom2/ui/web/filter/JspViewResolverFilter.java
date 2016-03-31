package namoo.board.dom2.ui.web.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JspViewResolverFilter extends AbstractFilter {
	//
	private static final String JSP_PHY_PATH = "/WEB-INF/views";
	
	@Override
	protected boolean doFilter(ServletRequest req, ServletResponse resp)
			throws IOException, ServletException {
		// 
		if (req instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) req;
			String fullUrl = httpRequest.getRequestURI();
			
			// URL이 .xhtml로 끝나는 경우만
			if (fullUrl.endsWith(".xhtml")) {
				String ctx = httpRequest.getContextPath();
				String jspPath = fullUrl.replace(ctx, JSP_PHY_PATH).replace(".xhtml", ".jsp");
				RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath);
				dispatcher.forward(req, resp);
				
				// 후속 필터 수행하지 않음
				return false;
			}
		}
		return true;
	}
}
