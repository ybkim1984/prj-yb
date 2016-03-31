package namoo.board.dom2.ui.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 모든 필터는 이 클래스를 상속받아 구현한다.
 * <pre>
 *   1. 정적 컨텐츠는 필터가 수행되지 않도록 함
 *   2. 필터 체인에서 후속필터의 수행여부를 판단
 * </pre> 
 */
public abstract class AbstractFilter implements Filter {
	
	public void init(FilterConfig config) throws ServletException {
		//
	}
	
	public void destroy() {
		// 
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// 
		if (doFilter(req, resp) == true) {
			chain.doFilter(req, resp);
		}
	}
	
	/**
	 * 필터를 수행하고 후속필터 동작여부를 리턴한다.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract boolean doFilter(ServletRequest req, ServletResponse resp) throws IOException, ServletException;
}
