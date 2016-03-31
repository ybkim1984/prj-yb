package namoo.board.dom2.ui.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "ContextPathFilter", urlPatterns = {"/*"})
public class ContextPathFilter extends AbstractFilter {

	@Override
	protected boolean doFilter(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
		//
		// JSP에서 사용하기 편하도록 ContextPath를 ctx로 세팅한다. 
		req.setAttribute("ctx", req.getServletContext().getContextPath());
		return true;
	}
}
