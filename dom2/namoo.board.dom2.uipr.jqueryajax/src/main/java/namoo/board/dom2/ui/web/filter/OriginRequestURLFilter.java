package namoo.board.dom2.ui.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "OriginRequestURLFilter", urlPatterns = {"/*"})
public class OriginRequestURLFilter extends AbstractFilter {

	@Override
	public boolean doFilter(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
		// 
		if (req instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) req;
			
			// origin : 요청된 실제 URL
			req.setAttribute("origin", httpRequest.getRequestURL());
			
			// referer : 요청 페이지를 호출한 페이지 URL
			req.setAttribute("referer", httpRequest.getHeader("referer"));
		}
		return true;
	}
}
