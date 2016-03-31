/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:wckang@nextree.co.kr">Kang Woo Chang</a>
 * @since 2015. 2. 23.
 */

package namoo.board.sp.jersey.common.filter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import namoo.board.dom2.util.exception.NamooBoardException;

@Provider
public class NamooExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(final Exception exception) {
		//
		ResponseBuilder builder = Response.status(Status.INTERNAL_SERVER_ERROR)
		.entity(jsonError(exception))
		.type(MediaType.APPLICATION_JSON);
		
		return builder.build();
	}
	
	private String jsonError(final Exception exception) {
		//
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"detailMessage\":\"" + exception.getMessage() + "\"");
		if(exception instanceof NamooBoardException){
			NamooBoardException ex = (NamooBoardException) exception;
			builder.append(",\"code\":\"" + ex.getCode() + "\"");
		}	
		builder.append("}");
		return builder.toString();
	}
}
