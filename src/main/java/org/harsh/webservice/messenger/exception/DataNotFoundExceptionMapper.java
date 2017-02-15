package org.harsh.webservice.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.harsh.webservice.messenger.model.ErrorMessage;


@Provider   // -->> this helps the below class to register with JAX_RS so that JAX-RS can map the exception when it occurs

//DataNotFoundExceptionMapper -->>  to map exception with response in JAX-RS is using Mapper Class
//ExceptionMapper -->> It is RAW type we need to make it generic type
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override		// in this method write code to return custom response
	public Response toResponse(DataNotFoundException exception) {						// takes an exception as parameter and returns a response

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404 , "http://www.google.com");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();			// entity is used to set body to the response
	}

}
