/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.borad.pr.jersey.request;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class JerseyPresentRequester {

	public Builder getRequestBuilder(String url) {
		//
		Client client = Client.create();
		 
		WebResource webResource = client.resource("http://localhost:8080/namooboard" + url);

		return webResource.type(MediaType.APPLICATION_JSON);
	}
}
