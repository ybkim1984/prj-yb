/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package namoo.board.dom2.ui.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * request객체에 몇가지 속성을 추가하는 인터셉터
 * 
 * <pre>
 *  JSP에서 아래 정보가 필요한 경우 아래와 같이 사용할 수 있다.
 *  예시 :
 *     <a href="${ctx}/some.do">Some Link</a>
 * </pre>
 * @author HaroldKim
 */
public class RequestSetupInterceptor extends HandlerInterceptorAdapter {
	//
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//
		// ctx : ContextPath
		request.setAttribute("ctx", request.getContextPath());
		
		// origin : 요청된 실제 URL
		request.setAttribute("origin", request.getRequestURL());
			
		// referer : 요청 페이지를 호출한 페이지 URL
		request.setAttribute("referer", request.getHeader("referer"));
		
		return true;
	}
}
