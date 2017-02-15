package org.harsh.webservice.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.harsh.webservice.messenger.model.Message;
import org.harsh.webservice.messenger.resources.beans.MessageFilterBean;
import org.harsh.webservice.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	MessageService msgservice = new MessageService(); 
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONmessages(@BeanParam MessageFilterBean msgFilterBean){
		if(msgFilterBean.getYear() > 0){
			return msgservice.getAllMessagesForYear(msgFilterBean.getYear());
		}
		if(msgFilterBean.getStart() >= 0 && msgFilterBean.getSize() >= 0){
			return msgservice.getAllMessagesPaginated(msgFilterBean.getStart(), msgFilterBean.getSize());
		}
		return msgservice.getallmessages();
	}
	
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLmessages(@BeanParam MessageFilterBean msgFilterBean){
		if(msgFilterBean.getYear() > 0){
			return msgservice.getAllMessagesForYear(msgFilterBean.getYear());
		}
		if(msgFilterBean.getStart() >= 0 && msgFilterBean.getSize() >= 0){
			return msgservice.getAllMessagesPaginated(msgFilterBean.getStart(), msgFilterBean.getSize());
		}
		return msgservice.getallmessages();
	}
	
	
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getmessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size){
		if(year > 0)
			return msgservice.getAllMessagesForYear(year);
		if(start >= 0 && size >= 0)
			return msgservice.getAllMessagesPaginated(start, size);
		return msgservice.getallmessages();
	}*/

	
	@GET
	@Path("/{messageId}")
	@Produces(value =  { MediaType.TEXT_XML,  MediaType.APPLICATION_JSON})
	public Message getmessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
		
		Message message = msgservice.getMessage(id);
		
/*		String uri = getUriForSelf(uriInfo, message);
		message.addLink(uri, "self");
*/		
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}


	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()										//  http://localhost:8080/messenger/webapi/
			.path(MessageResource.class)												// /messages
			.path(Long.toString(message.getId()))
			.build()
			.toString();
		return uri;
	}
	
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()										//  http://localhost:8080/messenger/webapi/
				.path(ProfileResource.class)											//  /profiles
				.path(message.getAuthor())												//  /{authorName}
				.build()
				.toString();
		return uri;
	}
	

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()										//  http://localhost:8080/messenger/webapi/
				.path(MessageResource.class)											//  /Message -->>  (getting path at class level)
				.path(MessageResource.class, "getCommentResource")						//  since we have mapped path of CommentsResource from MessageResource class using the function getCommentResource() defined in MessageResource so to get the extract the path here.   -->> (getting class at method level) 
				.path(CommentResource.class)											//  add the path of CommentsResource -->> not necessary but still do it   -->>  (getting path at subResource level)
				.resolveTemplate("messageId", message.getId())							//  .resolveTemplate(String name, Object value)  -->> (name replaced by value) -->>  we want the messageId form the Postman URL to be replaced by the message id of the actual message as in the method argument 
				.build()
				.toString();
		return uri;
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addResponse(Message message, @Context UriInfo uriInfo){				// here we use @context to get absolute path upto class MessageResource
		
		/* to create response we use response builder and that let's us modify the satus code, headers 
		 * Changing the status, return the entity back and then use build to return an instance of the response 
		 * build() -->> builds a response with all the above values */
		
		//return Response.status(Status.CREATED).entity(msgservice.addMessage(message)).build();		

		
		//return Response.created(new URI("/messenger/webapi/messages/" + message.getId())).entity(newMessage).build();

		Message newMessage = msgservice.addMessage(message);

		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();

	}																								
	
	
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message){
		return msgservice.addMessage(message);
	}*/


	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id , Message message){
		message.setId(id);
		return msgservice.updateMessage(message);
	}
	
	
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletemessage(@PathParam("messageId") long id){
		msgservice.deleteMessage(id);
	}
	
	
/*	@GET
	@Path("/{messageId}/comments")
	public String comments(){
		return "comments";
	}*/
	
	
	
	/* This is the function directing the call further to comment classes to handle comments*/
	@Path("/{messageId}/comments")									// directing the control for all methods regarding comments to new defined class CommentResource
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
	
}
