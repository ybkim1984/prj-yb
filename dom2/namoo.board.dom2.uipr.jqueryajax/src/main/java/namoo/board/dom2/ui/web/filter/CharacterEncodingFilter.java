package namoo.board.dom2.ui.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = {"/*"})
public class CharacterEncodingFilter extends AbstractFilter {

	private static final String ENCODING = "UTF-8";

	@Override
	public boolean doFilter(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
		// 
		req.setCharacterEncoding(ENCODING);
		resp.setContentType("text/html; charset=" + ENCODING);
		resp.setCharacterEncoding(ENCODING);
		
		return true;
	}
}
