package org.harsh.webservice.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectDemo")
public class InjectDemoResource {

	@GET
	@Path("annotations")
	@Produces(MediaType.TEXT_PLAIN)
	public String getParamUsingAnnotation(@MatrixParam("param") String matrixParam,
										  @HeaderParam("authSessionID") String header,
										  @CookieParam("JSESSIONID") String cookie){
		return "Matrix Param is: " + matrixParam + "   authSessionID is: " + header + "   authSessionID is: " + header + "  JSESSIONID: " + cookie;
	}
	
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,
										@Context HttpHeaders headers){
		String URI_PATH = uriInfo.getAbsolutePath().toString();
		String Cookies = headers.getCookies().toString();
		return "URI Link is:    " + URI_PATH + "Headers Data:   " + "Cookies:  " + Cookies ;
	}
}
	